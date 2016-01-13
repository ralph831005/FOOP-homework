import foop.Card;
import foop.Hand;
import foop.Player;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ralph on 2016/1/13.
 */
public class PlayerHuman extends Player {
    Scanner scanner;
    private static void printCard(Card card){
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
    private static void printHand(ArrayList<Card> hand){
        for(Card c : hand) {
            printCard(c);
            System.out.print(" ");
        }
    }
    private static void printTable(ArrayList<Hand> table){
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
    public PlayerHuman(int chips){
        super(chips);
        scanner = new Scanner(System.in);
    }
    public int make_bet(ArrayList<Hand> last_table, int total_player, int position){
        System.out.println("Last table : ");
        printTable(last_table);
        System.out.printf("%d players in this turn. You're position is %d\n", total_player, position);
        System.out.printf("You have %.1f chips now.\n", get_chips());
        System.out.print("Make bet : ");
        while(true) {
            try {
                return Integer.valueOf(scanner.next());
            }catch (Exception e){
                System.out.println("Please give a positive integer.");
            }
        }
    }

    public boolean buy_insurance(Card my_open, Card dealer_open, ArrayList<Hand> current_table){
        System.out.println("Current Table : ");
        printTable(current_table);
        System.out.print("Your card is ");
        printCard(my_open);
        System.out.print(". And Dealer has ");
        printCard(dealer_open);
        System.out.print(".\nDo you want to buy insurance? (y/n) ");
        while(true){
            String buy = scanner.next();
            if(buy.contains("y"))
                return true;
            else if(buy.contains("n"))
                return false;
            System.out.print("y for yes and n for no, other input is ignored. ");
        }

    }

    public boolean do_surrender(Card my_open, Card dealer_open, ArrayList<Hand> current_table){
        System.out.println("Current Table  : ");
        printTable(current_table);
        System.out.print("Your card is ");
        printCard(my_open);
        System.out.print(". And Dealer has ");
        printCard(dealer_open);
        System.out.print(".\nDo you want to surrender? (y/n) ");
        while(true){
            String buy = scanner.next();
            if(buy.contains("y"))
                return true;
            else if(buy.contains("n"))
                return false;
            System.out.print("y for yes and n for no, other input is ignored. ");
        }
    }

    public boolean do_split(ArrayList<Card> my_open, Card dealer_open, ArrayList<Hand> current_table){
        System.out.println("Current Table  : ");
        printTable(current_table);
        System.out.print("Your card is ");
        printHand(my_open);
        System.out.print(". And Dealer has ");
        printCard(dealer_open);
        System.out.print(".\nDo you want to split? (y/n) ");
        while(true){
            String buy = scanner.next();
            if(buy.contains("y"))
                return true;
            else if(buy.contains("n"))
                return false;
            System.out.print("y for yes and n for no, other input is ignored. ");
        }
    }

    public boolean do_double(Hand my_open, Card dealer_open, ArrayList<Hand> current_table){
        System.out.println("Current Table  : ");
        printTable(current_table);
        System.out.print("Your card is ");
        printHand(my_open.getCards());
        System.out.print(". And Dealer has ");
        printCard(dealer_open);
        System.out.print(".\nDo you want to double? (y/n) ");
        while(true){
            String buy = scanner.next();
            if(buy.contains("y"))
                return true;
            else if(buy.contains("n"))
                return false;
            System.out.print("y for yes and n for no, other input is ignored. ");
        }
    }

    public boolean hit_me(Hand my_open, Card dealer_open, ArrayList<Hand> current_table){
        System.out.println("Current Table  : ");
        printTable(current_table);
        System.out.print("Your card is ");
        printHand(my_open.getCards());
        System.out.print(". And Dealer has ");
        printCard(dealer_open);
        System.out.print(".\nDo you want to hit? (y/n) ");
        while(true){
            String buy = scanner.next();
            if(buy.contains("y"))
                return true;
            else if(buy.contains("n"))
                return false;
            System.out.print("y for yes and n for no, other input is ignored. ");
        }
    }
    public String toString(){
        return (new StringBuilder()).append(this.getClass().getName().substring(6)).append(" : ").append(this.get_chips()).toString();
    }
}
