import foop.Card;
import foop.Hand;
import foop.Player;

import java.util.ArrayList;

/**
 * Created by ralph on 2015/12/26.
 */
public class Arranger {
    private Player player;
    private int bet;
    private boolean insured;
    private ArrayList<Card> face_up;
    private Card face_down;
    private boolean doubled;
    private boolean split;
    private boolean no_bet;
    private boolean surrender;
    private int position;
    private String name;

    public Arranger(int i, Player _player){
        face_up = new ArrayList<>();
        doubled = false;
        split = false;
        no_bet = false;
        surrender = false;
        position = i;
        player = _player;
        name = _player.getClass().toString().substring(12);
    }
    public Arranger(Arranger _arranger){
        player = _arranger.player;
        bet = _arranger.bet;
        insured = false;
        face_up =  new ArrayList<>(_arranger.face_up.subList(1, 2));
        doubled = _arranger.doubled;
        split = _arranger.split;
        position = _arranger.position;
        no_bet = _arranger.no_bet;
        surrender = _arranger.surrender;
        name = _arranger.name+"_split-2";
    }
	public void giveCards(Card up, Card down){
		face_up.add(up);
		face_down = down;
        System.out.printf("%s at %d gets : ", name, position);
        Checker.print(face_up);
        System.out.println();
	}
	public ArrayList<Card> showCards(){
		return new ArrayList<>(face_up);
	}
	public boolean hit_card(Card c){
		face_up.add(c);
        if(Checker.isBusted(face_up)){
            System.out.printf("%s at %d is busted : ", name, position);
            Checker.print(face_up);
            System.out.println();
            return false;
        }
        else{
            System.out.printf("%s at %d now has : ", name, position);
            Checker.print(face_up);
            System.out.println();
            return true;
        }
	}
    public void flipUp(){
        if(no_bet || surrender)
            return;
        face_up.add(face_down);
        face_down = null;
        System.out.printf("%s at %d now has : ", name, position);
        Checker.print(face_up);
        System.out.println();
    }
    public int make_bet (ArrayList<Hand> lastTable, int total_player){
        bet = player.make_bet(lastTable, total_player, position);
        try {
            set_player_chip(-(double) bet);
            System.out.printf("%s at %d bets %d.\n", name, position, bet);
            return bet;
        }
        catch (Player.BrokeException e){
            System.out.println("Out of money!!!");
            System.out.println("Bet is set 0 this turn");
            try {
                set_player_chip(bet);
            }catch (Player.BrokeException e2){
                System.out.println("Set back chips for broken.");
            }
            bet = 0;
            no_bet = true;
            return 0;
        }
    }
    public boolean buy_insurance(Card dealer_open, ArrayList<Hand> current_table){
        if(no_bet || surrender)
            return false;
        insured = player.buy_insurance(face_up.get(0), dealer_open, current_table);
        if(insured) {
            try {
                set_player_chip(-bet / 2.0);
                System.out.printf("%s at %d decides to buy insurance.\n", name, position);
                return insured;
            } catch (Player.BrokeException e) {
                System.out.println("out of money!!!");
                System.out.println("can't buy insurance.");
                try {
                    set_player_chip(bet/2.0);
                }catch (Player.BrokeException e2){
                    System.out.println("Set back chips for broken.");
                }
                insured = false;
            }
        }
        else{
            System.out.printf("%s at %d decides not to buy insurance.\n", name, position);
        }
        return false;

    }
    public boolean do_double(Card dealer_open, ArrayList<Hand> current_table){
        if(no_bet || surrender)
            return false;
        doubled = player.do_double(new Hand(face_up), dealer_open, current_table);
        if(doubled) {
            try{
                set_player_chip(-bet);
                bet *= 2;
                System.out.printf("%s at %d decides to double.\n", name, position);
                return true;
            }catch (Player.BrokeException e){
                System.out.println("out of money!!!");
                System.out.println("can't double");
                try {
                    set_player_chip(bet);
                }catch (Player.BrokeException e2){
                    System.out.println("Set back chips for broken.");
                }
                doubled = false;
            }
        }
        System.out.printf("%s at %d decides not to double.\n", name, position);
        return false;
    }
    public boolean do_surrender(Card dealer_open, ArrayList<Hand> current_table){
        if(no_bet)
            return false;
        if(player.do_surrender(face_up.get(0), dealer_open, current_table)){
            try{
                set_player_chip(bet/2.0);
            }catch (Player.BrokeException e){
                System.out.println("broken when increasing chips");
            }
            surrender = true;
            System.out.printf("%s at %d decides to surrender.\n", name, position);
            return true;
        }
        System.out.printf("%s at %d decides not to surrender.\n", name, position);
        return false;
    }
    public Arranger do_split(Card dealer_open, ArrayList<Hand> current_table){
        if(no_bet || surrender)
            return null;
        if(face_up.get(0).getValue() != face_up.get(1).getValue())
            return null;
        split = player.do_split(face_up, dealer_open, current_table);
        if(split){
            try {
                set_player_chip(-bet);
                Arranger splited = new Arranger(this);
                face_up.remove(1);
                name += "_split-1";
                System.out.printf("%s at %d decides to split.\n", name, position);
                return splited;
            }catch (Player.BrokeException e){
                System.out.println("out of money!!!");
                System.out.println("can't split");
                try {
                    set_player_chip(bet);
                }catch (Player.BrokeException e2){
                    System.out.println("Set back chips for broken.");
                }
                split = false;
            }
        }
        else
            System.out.printf("%s at %s decides not to split.\n", name, position);
        return null;
    }
    public boolean hit_me(Card dealer_open, ArrayList<Hand> current_table){
        if(no_bet || surrender)
            return false;
        if(player.hit_me(new Hand(face_up), dealer_open, current_table)) {
            System.out.printf("%s at %d decides to hit.\n", name, position);
            return true;
        }
        System.out.printf("%s at %d decides to stand.\n", name, position);
        return false;
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
    public String get_name(){
        return name;
    }
    public boolean pass(){
        return no_bet;
    }
    public int value(){
        return  Checker.value(face_up);
    }
    public boolean isSurrender(){
        return surrender;
    }
    public boolean isBusted(){
        return Checker.isBusted(face_up);
    }
    public boolean isBlackJack(){
        return Checker.isBlackJack(face_up) && (!split);
    }
    public boolean isInsured(){
        return insured;
    }
    public boolean isDoubled(){
        return doubled;
    }
    public boolean isSplit(){
        return split;
    }
    public void push(){
        System.out.printf("%s at %d has the same point with dealer.\nGet %d chips back.\n", name, position, bet);
        try {
            set_player_chip(bet);
        } catch(Player.BrokeException e){
            System.out.print("Broken!?");
        }
    }
    public void win(double multi){
        double win_chips = 0;
        if(multi == 1){
            if(doubled) {
                System.out.printf("%s at %d doubles and wins %d chips!!\n", name, position, 2 * bet);
                win_chips = 2*bet + bet;
            }
            else {
                System.out.printf("%s at %d wins %d chips!!\n", name, position, bet);
                win_chips = bet + bet;
            }
        }
        else{
            System.out.printf("%s at %d gets Black Jack and wins %.01f chips!!\n", name, position, 1.5*bet);
            win_chips = 1.5*bet + bet;
        }
        try{
            set_player_chip(win_chips);
        }catch (Player.BrokeException e){
            System.out.print("Broken!?");
        }
    }
    public void lose(){
        System.out.printf("%s at %d lose.\n", name, position);
    }
    public void winInsurance(){
        System.out.printf("%s at %d is insured, get %d chips back.\n", name, position,  bet);
        try {
            set_player_chip(bet);
        } catch(Player.BrokeException e){
            System.out.print("Broken!?");
        }

    }
    public int getPosition(){
        return position;
    }
    public String toString(){
        return player.toString();
    }
    @Override
    public boolean equals(Object arranger){
        return position == ((Arranger)arranger).position;
    }
}
