import foop.Card;

import java.util.ArrayList;

/**
 * Created by ralph on 2015/12/28.
 */
class Dealer {
    private ArrayList<Card> hand;
    private Card face_down;

    public Dealer(){
        hand = new ArrayList<>();
    }
    public void giveCards(Card up, Card down){
        hand.add(up);
        face_down = down;
        System.out.print("Dealer gets : ");
        Checker.print(hand);
        System.out.println();
    }
    public Card getFaceUp(){
        return hand.get(0);
    }
    public void flipUp(){
        hand.add(face_down);
        face_down = null;
        System.out.print("Dealer now has : ");
        Checker.print(hand);
        System.out.println();
    }
    public ArrayList<Card> showCards(){
        return new ArrayList<>(hand);
    }
    public boolean face_up_isA(){
        return hand.get(0).getValue() == 1;
    }
    public boolean peekIsBlackJack(){
        int first = Math.min((int)hand.get(0).getValue(), 10);
        int second = Math.min((int)face_down.getValue(), 10);
        return (first == 1 && second == 10)|| (second == 1 && first == 10);
    }
    public boolean hit_card(Card card){
        hand.add(card);
        if(Checker.isBusted(hand)){
            System.out.print("Dealer is busted : ");
            Checker.print(hand);
            System.out.println();
            return false;
        }
        else{
            System.out.printf("Dealer now have : ");
            Checker.print(hand);
            System.out.println();
            return true;
        }
    }
    public boolean hit_me(){
        if(Checker.value(hand) <= 16 || Checker.isSoftSeveteen(hand)) {
            System.out.println("Dealer decides to hit.");
            return true;
        }
        System.out.println("Dealer decides to stand.");
        return false;
    }
    public int value(){
        return Checker.value(hand);
    }
    public boolean isBlackJack() {
        return Checker.isBlackJack(hand);
    }
    public boolean isBusted(){
        return Checker.isBusted(hand);
    }
}
