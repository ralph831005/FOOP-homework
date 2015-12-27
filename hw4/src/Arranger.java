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
    private int position;

    public Arranger(int i, Player _player){
        face_up = new ArrayList<>();
        doubled = false;
        split = false;
        position = i;
        player = _player;
        face_up = new ArrayList<>();
    }
    public Arranger(Arranger _arranger){
        player = _arranger.player;
        bet = _arranger.bet;
        insured = _arranger.insured;
        face_up =  new ArrayList<>(_arranger.face_up);
        doubled = _arranger.doubled;
        split = _arranger.split;
        position = _arranger.position;
    }
    public int make_bet (ArrayList<Hand> lastTable, int total_player){
        bet = player.make_bet(lastTable, total_player, position);
        try {
            set_player_chip(-(double) bet);
            return bet;
        }
        catch (Player.BrokeException e){
            System.out.println("Out of money!!!");
            System.out.println("Bet is set 0 this turn");
            bet = 0;
            return 0;
        }
    }
    public boolean buy_insurance(Card my_open, Card dealer_open, ArrayList<Hand> current_table){
        insured = player.buy_insurance(my_open, dealer_open, current_table);
        if(insured) {
            try {
                set_player_chip(-bet / 2.0);
                return insured;
            } catch (Player.BrokeException e) {
                System.out.println("out of money!!!");
                System.out.println("can't buy insurance.");
            }
        }
        return false;

    }
    public boolean do_double(Hand my_open, Card dealer_open, ArrayList<Hand> current_table){
        doubled = player.do_double(my_open, dealer_open, current_table);
        if(doubled) {
            try{
                set_player_chip(-bet);
                bet *= 2;
                return true;
            }catch (Player.BrokeException e){
                System.out.println("out of money!!!");
                System.out.println("can't double");
            }
        }
        return false;
    }
    public boolean do_surrender(Card my_open, Card dealer_open, ArrayList<Hand> current_table){
        if(player.do_surrender(my_open, dealer_open, current_table)){
            try{
                set_player_chip(bet/2.0);
            }catch (Player.BrokeException e){
                System.out.println("broken when increasing chips");
            }
            return true;
        }
        else
            return false;
    }
    public boolean do_split(ArrayList<Card> my_open, Card dealer_open, ArrayList<Hand> cuurent_table){
        split = player.do_split(my_open, dealer_open, cuurent_table);
        if(split){
            try {
                set_player_chip(-bet);
                face_up.remove(1);
                return true;
            }catch (Player.BrokeException e){
                System.out.println("out of money!!!");
                System.out.println("can't split");
            }
        }
        return false;
    }
    public boolean hit_me(Hand my_open, Card dealer_open, ArrayList<Hand> current_table){
        return player.hit_me(my_open, dealer_open, current_table);
    }

    private void set_player_chip(double n) throws Player.BrokeException{
        try{
            player.increase_chips(n);
        }
        catch (Player.NegativeException e){
            try {
                player.decrease_chips(-n);
            }
            catch (Player.NegativeException e){
                System.err.print(e);
            }
        }
    }


}
