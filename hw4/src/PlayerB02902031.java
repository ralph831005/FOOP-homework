import foop.Card;
import foop.Hand;
import foop.Player;

import java.util.ArrayList;

/**
 * Created by ralph on 2015/12/26.
 */
public class PlayerB02902031 extends Player{
    public PlayerB02902031(int chips){
        super(chips);
    }

    @Override
    public int make_bet(ArrayList<Hand> last_table, int total_player, int my_position){
        double chips = this.get_chips();
        return 1;
    }

    @Override
    public boolean buy_insurance(Card my_open, Card dealer_open, ArrayList<Hand> current_table){
        return false;

    }

    @Override
    public boolean do_surrender(Card my_open, Card dealer_open, ArrayList<Hand> current_table){
        return false;

    }

    @Override
    public boolean do_split(ArrayList<Card> my_open, Card dealer_open, ArrayList<Hand> current_table){
        return false;


    }
    @Override
    public boolean do_double(Hand hand, Card card, ArrayList<Hand> arrayList) {
        return false;

    }

    @Override
    public boolean hit_me(Hand my_open, Card dealer_open, ArrayList<Hand> current_table){
        return false;

    }
    @Override
    public String toString(){
        return (new StringBuilder()).append(this.getClass().getName()).append(" ").append(this.get_chips()).toString();
    }


}
