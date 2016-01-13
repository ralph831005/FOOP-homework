import foop.Card;
import foop.Hand;
import foop.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by ralph on 2016/1/14.
 */
public class Client implements Runnable{
    private Player player;
    private int bet;
    private boolean insurance;
    private boolean doubled;
    private int chips;
    private ArrayList<Card> cards;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    Client(String address, int port){
        cards = new ArrayList<>();
        bet = 0;
        insurance = false;
        doubled = false;

        InetSocketAddress addr = new InetSocketAddress(address, port);
        socket = new Socket();
        try {
            socket.connect(addr);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            chips = ((Request)in.readObject()).getBet();
            player = new PlayerHuman(chips);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Connection failed.");
        }
    }
    public void run(){
        while(true){
            Request request;
            try {
                request = (Request)in.readObject();
            }catch (Exception e){
                System.out.println("Socket broken!!");
                break;
            }
            Request decision = new Request();
            switch (request.getType()) {
                case 0:
                    System.out.println(request.getString());
                    break;
                case 1:
                    ArrayList<Card> my_open = request.getHand();
                    Card dealer_open = request.getCard();
                    ArrayList<Hand> table = request.getTable();
                    cards = new ArrayList<>(my_open);
                    switch (request.getOpCode()) {
                        case 3:
                            boolean split = player.do_split(my_open, dealer_open, table);
                            if(split) {
                                try {
                                    set_player_chip(-bet);
                                } catch (Exception e) {
                                    System.out.println("No money for split.");
                                    split = false;
                                }
                            }
                            decision.setMessage(split);
                            break;
                        case 4:
                            doubled = false;
                            decision.setMessage(doubled);
                            break;
                        case 5:
                            decision.setMessage(player.hit_me(new Hand(my_open), dealer_open, table));
                            break;
                        default:
                            System.out.println("Something wrong.");
                    }
                    try{
                        out.writeObject(decision);
                    }catch (Exception e){
                        System.out.println("IO Exception");
                    }
                    break;
                case 2:

                    ArrayList<Hand> table2 = request.getTable();
                    int total = request.getInt();
                    int position = request.getInt();
                    bet = player.make_bet(table2, total, position);
                    if(bet <= 0) {
                        System.out.println("No bet <= 0, skip this round.");
                        bet = 0;
                    }
                    try {
                        player.decrease_chips(bet);
                    } catch (Exception e){
                        System.out.println("You are broken!!!");
                        System.out.println("Come back later, bye bye~");
                        return;
                    }
                    decision.setMessage(bet);
                    try{
                        out.writeObject(decision);
                    }catch (Exception e){
                        System.out.println("IO Exception");
                    }
                    break;
                case 3:
                    Card my_open3 = request.getCard();
                    Card dealer_open3 = request.getCard();
                    ArrayList<Hand> table3 = request.getTable();
                    switch (request.getOpCode()) {
                        case 1:
                            insurance = player.buy_insurance(my_open3, dealer_open3, table3);
                            if(insurance) {
                                try {
                                    player.decrease_chips(bet / 2.0);
                                } catch (Exception e) {
                                    System.out.println("No chips for insurance.");
                                    insurance = false;
                                    try {
                                        player.increase_chips(bet/2.0);
                                    } catch (Exception e1){

                                    }
                                }
                            }
                            decision.setMessage(insurance);
                            break;
                        case 2:
                            decision.setMessage(player.do_surrender(my_open3, dealer_open3, table3));
                            break;
                    }
                    try{
                        out.writeObject(decision);
                    }catch (Exception e){
                        System.out.println("IO Exception");
                    }
                    break;
                case 4:
                    ArrayList<Hand> table4 = request.getTable();
                    ArrayList<Card> dealer = table4.get(table4.size()-1).getCards();
                    double award = 0;
                    int value = Checker.value(cards);
                    int dealer_value = Checker.value(dealer);
                    if(value > 21)
                        System.out.println("You are busted.");
                    else if(dealer_value > 21)
                        dealer_value = 0;
                    System.out.print("You get : ");
                    printHand(cards);
                    System.out.println();
                    System.out.print("Dealer get : ");
                    printHand(dealer);
                    System.out.println();
                    if(Checker.isBlackJack(cards) && Checker.isBlackJack(dealer)){
                        System.out.printf("You has the same point with dealer.\nGet %d chips back.\n", bet);
                        award = bet;
                    }
                    else if(Checker.isBlackJack(cards)){
                        System.out.printf("You get a Black Jack !!!\n Wins %.1f chips\n", 1.5 * bet);
                        award = 2.5*bet;
                    }
                    else if(Checker.isBlackJack(dealer)){
                        System.out.println("You lose.");
                        if(insurance){
                            System.out.println("You get insurance!");
                            award = bet;
                        }
                    }
                    else if(value > dealer_value){
                        if(doubled){
                            System.out.printf("You win double!!!\nWins %d chips\n", 2*bet);
                            award = 3*bet;
                        }
                        else{
                            System.out.printf("You wins %d chips!!!", bet);
                            award = 2*bet;
                        }
                    }
                    else if(value == dealer_value){
                        System.out.printf("You get a Black Jack !!!\n Wins %.1f chips\n", 1.5 * bet);
                        award = bet;
                    }
                    else{
                        System.out.println("You lose.");
                    }
                    try {
                        set_player_chip(award);
                    }catch (Exception e){}
                    System.out.println("Result : ");
                    printTable(table4);
                    break;
            }
        }
    }
    public static void printCard(Card card){
        StringBuilder s = new StringBuilder();
        switch (card.getSuit()){
            case Card.CLUB:
                s.append("C");
                break;
            case Card.DIAMOND:
                s.append("D");
                break;
            case Card.HEART:
                s.append("H");
                break;
            case Card.SPADE:
                s.append("S");
                break;
            default:
                System.out.println("This is not a card.");
        }
        switch (card.getValue()){
            case 1:
                s.append("A");
                break;
            case 2:case 3:case 4:case 5:case 6:case 7:case 8:case 9:case 10:
                s.append((int)card.getValue());
                break;
            case 11:
                s.append("J");
                break;
            case 12:
                s.append("Q");
                break;
            case 13:
                s.append("K");
                break;
        }
        System.out.print(s);
    }
    public static void printHand(ArrayList<Card> hand){
        for(Card c : hand) {
            printCard(c);
            System.out.print(" ");
        }
    }
    public static void printTable(ArrayList<Hand> table){
        for(int i = 0; i < table.size()-1; ++i) {
            System.out.printf("Hand%d : ", i+1);
            printHand(table.get(i).getCards());
            System.out.println();
        }
        try {
            System.out.print("Dealer : ");
            printHand(table.get(table.size() - 1).getCards());
            System.out.println();
        }catch (Exception e){
        }
    }
    private void set_player_chip(double n) throws Player.BrokeException{
        try{
            player.increase_chips(n);
        }
        catch (Player.NegativeException e){
            try {
                player.decrease_chips(-n);
            }
            catch (Player.NegativeException f){
                System.err.print(f);
            }
        }
    }
    public static void main(String[] args){
        Client client = new Client(args[0], Integer.valueOf(args[1]));
        client.run();
    }



}
