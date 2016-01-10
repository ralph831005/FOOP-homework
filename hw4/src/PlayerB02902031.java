import foop.Card;
import foop.Hand;
import foop.Player;

import java.util.ArrayList;

/**
 * Created by ralph on 2015/12/26.
 */
public class PlayerB02902031 extends Player{
    private boolean insured;
    private static final String[] hard_table = {"SSSSSSSSSS", "HSSSSSHHHH", "HSSSSSHHHH", "HSSSSSHHHH", "HHHSSSHHHH", "HAAAAAAAAA", "HHAAAAAAAA", "HHAAAAHHHH", "HHHHHHHHHH"};
    private static final String[] soft_table = {"SSSSSSSSSS", "HSBBBBSSHH", "HHAAAAHHHH", "HHHAAAHHHH", "HHHHAAHHHH"};
    private static final String[] pair_table = {"PPPPPPPPPP", "SSSSSSSSSS", "SPPPPPSPPS", "PPPPPPPPPP", "HPPPPPPHHH", "HPPPPPHHHH", "HAAAAAAAAH", "HHHHPPHHHH", "HPPPPPPHHH"};
    private static final int[] hard = {-1, 8, 8, 8, 8, 8, 8, 8, 8, 7, 6, 5, 4, 3, 3, 2, 1, 0, 0 ,0 ,0};
    private static final int[] soft = {-1, 4, 4, 4, 4, 3, 3, 2, 1, 0, 0};
    private static final int[] pair = {-1, 0, 8, 8, 7, 6, 5, 4, 3, 2, 1};
    private static int hard_total(ArrayList<Card> hand){
        int sum = 0;
        boolean ace = false;
        for(Card card : hand){
            sum += Math.min((int)card.getValue(), 10);
            if(card.getValue() == 1)
                ace = true;
        }
        if(ace && ((sum + 10) < 22))
            sum += 10;
        return sum;
    }
    private static int soft_total(ArrayList<Card> hand){
        int sum = 0;
        boolean ace = false;
        for(Card card : hand){
            sum += Math.min((int)card.getValue(), 10);
            if(card.getValue() == 1)
                ace = true;
        }
        if(ace)
            return sum;
        else
            return 22;
    }
    private static boolean isBlackJack(ArrayList<Card> hand){
        return ((hand.get(0).getValue() == 1 && hand.get(1).getValue() >= 10) || (hand.get(1).getValue() == 1 && hand.get(0).getValue() >= 10));
    }
    public PlayerB02902031(int chips){
        super(chips);
        insured = false;
    }

    @Override
    public int make_bet(ArrayList<Hand> last_table, int total_player, int my_position){
        double chips = this.get_chips();
        return (chips > 20)? (int)chips/20 : 1;
    }

    @Override
    public boolean buy_insurance(Card my_open, Card dealer_open, ArrayList<Hand> current_table){
        insured = true;
        return true;
    }

    @Override
    public boolean do_surrender(Card my_open, Card dealer_open, ArrayList<Hand> current_table){
        return false;
    }

    @Override
    public boolean do_split(ArrayList<Card> my_open, Card dealer_open, ArrayList<Hand> current_table){
        int dealer_value = Math.min(dealer_open.getValue(), 10) % 10;
        if(my_open.get(0).getValue() == my_open.get(1).getValue()){
            try {
                if (pair_table[pair[Math.min(my_open.get(1).getValue(), 10)]].charAt(dealer_value) == 'P')
                    return true;
            }catch (Exception e){
                System.out.println(e);
                return false;
            }

        }
        return false;
    }
    @Override
    public boolean do_double(Hand my_open, Card dealer_open, ArrayList<Hand> arrayList) {
        int dealer_value = Math.min(dealer_open.getValue(), 10) % 10;
        if(!isBlackJack(my_open.getCards())){
            try {
                int s_total = soft_total(my_open.getCards());
                if (s_total <= 10) {
                    if (soft_table[soft[s_total]].charAt(dealer_value) == 'A' || soft_table[soft[s_total]].charAt(dealer_value) == 'B')
                        return true;
                } else {
                    int h_total = hard_total(my_open.getCards());
                    if (hard_table[hard[h_total]].charAt(dealer_value) == 'A' || hard_table[hard[h_total]].charAt(dealer_value) == 'B')
                        return true;
                }
            } catch (Exception e){
                System.out.println(e);
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean hit_me(Hand my_open, Card dealer_open, ArrayList<Hand> current_table){
        int dealer_value = Math.min(dealer_open.getValue(), 10) % 10;
        if(!isBlackJack(my_open.getCards())){
            try {
                int s_total = soft_total(my_open.getCards());
                if (s_total <= 10) {
                    if (soft_table[soft[s_total]].charAt(dealer_value) == 'H' || soft_table[soft[s_total]].charAt(dealer_value) == 'A')
                        return true;
                } else {
                    int h_total = hard_total(my_open.getCards());
                    if (h_total == 21)
                        return false;
                    if (hard_table[hard[h_total]].charAt(dealer_value) == 'H' || hard_table[hard[h_total]].charAt(dealer_value) == 'A')
                        return true;
                }
            }catch (Exception e){
                System.out.println(e);
                return false;
            }
        }
        return false;

    }
    @Override
    public String toString(){
        return (new StringBuilder()).append(this.getClass().getName().substring(6)).append(" : ").append(this.get_chips()).toString();
    }


}
