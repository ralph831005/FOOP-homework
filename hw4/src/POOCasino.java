import foop.Card;
import foop.Hand;
import foop.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by ralph on 2015/12/26.
 */
public class POOCasino implements Runnable{
    protected ArrayList<Player> players;
    private Shuffler shuffler;
    private int round;
    protected ArrayList<Hand> current_table;
    private ArrayList<Hand> last_table;
    private Dealer dealer;
    protected POOCasino(int nPiles){
        players = new ArrayList<>();
        shuffler = new Shuffler();
        current_table =  new ArrayList<>();
        last_table = new ArrayList<>();
        dealer = new Dealer();
        round = 0;
        shuffler.setnPiles(nPiles);
    }
    public POOCasino(int chips, int _round, String[] player_class){
        players = new ArrayList<>();
        shuffler = new Shuffler();
        current_table =  new ArrayList<>();
        last_table = new ArrayList<>();
        dealer = new Dealer();
        round = _round;
        for(String class_name : player_class){
            try {
                players.add((Player)Class.forName(class_name).getDeclaredConstructor(Integer.TYPE).newInstance(chips));
            }
            catch (Exception e) {
                System.err.printf("Error on constructing player %s\nSkip the player...\n", class_name);
                System.err.println(e);
            }
        }
        shuffler.setnPiles(1);
        System.out.println("\nWelcome to POOCasino established by Ralph Lee (B02902031)");
        System.out.printf("There are %d players:", players.size());
        for(Player p : players)
            System.out.printf(" %s", p.getClass().toString());
        System.out.println(".");
        System.out.printf("Initially, they all have %d chips to play black jacks.\n", chips);
    }
    public void run(){
        System.out.println("Game Start!!");
        for(int T = 1; T <= round; ++T) {
            System.out.printf("==============Round %d start================\n", T);
            process();
        }
    }
    protected void process(){
        ArrayList<Arranger> player_list = setPlayers();
        if(shuffler.notEnoughForNextRound(player_list.size())){
            System.out.println("Shuffle!!!");
            shuffler.shuffle();
        }
        bet(player_list);
        assignCards(player_list);
        buyInsurance(player_list);
        surrender(player_list);
        hit(player_list);
        dealer_hit();
        checkResult(player_list);
    }
    private ArrayList<Arranger> setPlayers(){
        dealer = new Dealer();
        ArrayList<Arranger> player_list = new ArrayList<>();
        for(int i = 1; i < players.size()+1; ++i){
            player_list.add(new Arranger(i, players.get(i-1)));
        }
        return player_list;
    }
    private void bet(ArrayList<Arranger> player_list){
        System.out.println("Ask players to bet...");
        for(Arranger player : player_list){
            player.make_bet(last_table, players.size());
        }
        Iterator<Arranger> player = player_list.iterator();
        while(player.hasNext()){
            if(player.next().pass())
                player.remove();
        }
    }
    private void assignCards(ArrayList<Arranger> player_list){
        System.out.println("Assign cards to players...");
        for(Arranger player : player_list){
            if(player.pass())
                continue;
            player.giveCards(shuffler.getTop(), shuffler.getTop());
        }
        dealer.giveCards(shuffler.getTop(), shuffler.getTop());
    }
    private void buyInsurance(ArrayList<Arranger> player_list){
        if(dealer.face_up_isA()){
            System.out.println("Dealer gets an Ace faced up!!");
            System.out.println("Ask players whether to buy insurance...");
            for(Arranger player : player_list){
                setCurrentTable(player, player_list);
                player.buy_insurance(dealer.getFaceUp(), current_table);
            }
        }
    }
    private void surrender(ArrayList<Arranger> player_list){
        if(!dealer.peekIsBlackJack()) {
            System.out.println("Dealer do not get a Black Jack.");
            System.out.println("Ask players whether to surrender...");
            for(Arranger player : player_list) {
                setCurrentTable(player, player_list);
                player.do_surrender(dealer.getFaceUp(), current_table);
            }
        }
    }
    private void hit(ArrayList<Arranger> player_list){
        ArrayList<Arranger> split_players = new ArrayList<>();
        for(Arranger player : player_list){
            setCurrentTable(player, player_list);
            if(player.pass())
                continue;
            System.out.printf("Flip up %s 's faced-down card\n", player.get_name());
            player.flipUp();
            Arranger split = player.do_split(dealer.getFaceUp(), current_table);

            //give one card to each of the split hand.
            if(split != null) {
                player.hit_card(shuffler.getTop());
                split.hit_card(shuffler.getTop());
            }
            if(split == null && player.do_double(dealer.getFaceUp(), current_table)){
                player.hit_card(shuffler.getTop());
                continue;
            }
            // ask player to hit and then assign a card, hit_card method returns a boolean variable of his hand busted.
            while(player.hit_me(dealer.getFaceUp(), current_table) && player.hit_card(shuffler.getTop()));
            System.out.printf("%s now have %d points\n", player.get_name(), player.value());
            if(split != null) {
                // ask player to hit and then assign a card, hit_card method returns a boolean variable of his hand busted
                while (split.hit_me(dealer.getFaceUp(), current_table) && split.hit_card(shuffler.getTop())) ;
                System.out.printf("%s now have %d points\n", split.get_name(), split.value());
                split_players.add(split);
            }
        }
        for(Arranger player : split_players){
            int index = player_list.indexOf(player);
            if(index == -1)
                System.out.println("-1!!!");
            player_list.add(index+1, player);
        }

    }
    private void setCurrentTable(Arranger current, ArrayList<Arranger> player_list){
        current_table.clear();
        if(current != null) {
            for (Arranger player : player_list) {
                if (!player.equals(current))
                    current_table.add(new Hand(player.showCards()));
            }
        }
        else
            for (Arranger player : player_list)
                current_table.add(new Hand(player.showCards()));
        current_table.add(new Hand(dealer.showCards()));
    }
    private void dealer_hit(){
        System.out.println("Flip up dealer's card");
        dealer.flipUp();
        while(dealer.hit_me() && dealer.hit_card(shuffler.getTop()));
    }
    private void checkResult(ArrayList<Arranger> player_list){
        System.out.println("Check the result of each player...");
        if(dealer.isBlackJack())
            System.out.println("Dealer gets BlackJack!");
        else if(dealer.isBusted())
            System.out.println("Dealer is busted!!");
        else
            System.out.printf("Dealer gets %d points.\n", dealer.value());
        for(Arranger player : player_list){
            if(player.pass())
                continue;
            if(player.isSurrender()){
                System.out.printf("%s surrenders\n", player.get_name());
                continue;
            }
            if(player.isBusted()){
                System.out.printf("%s is busted\n", player.get_name());
                continue;
            }
            if(player.isBlackJack()) {
                if (dealer.isBlackJack())
                    player.push();
                else
                    player.win(1.5);
            }
            else{
                if(dealer.isBlackJack()){
                    if(player.isInsured())
                        player.winInsurance();
                    else
                        player.lose();
                }
                else{
                    if(player.value() > dealer.value() || dealer.isBusted())
                        player.win(1);
                    else if(player.value() < dealer.value())
                        player.lose();
                    else
                        player.push();
                }
            }
        }
        System.out.println("Print status of each player.");
        int position = 0;
        for(Arranger player :  player_list){
            if(player.getPosition() != position) {
                position = player.getPosition();
                System.out.println(player);
            }
        }
        setCurrentTable(null, player_list);
        last_table = new ArrayList<>(current_table);
        current_table.clear();

    }
    public static void main(String[] args){
        int chips = Integer.valueOf(args[0]);
        int round = Integer.valueOf(args[1]);
        POOCasino pooCasino = new POOCasino(chips, round, Arrays.copyOfRange(args, 2, args.length));
        pooCasino.run();
    }
}
