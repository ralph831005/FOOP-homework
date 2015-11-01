import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Override;
import java.util.ArrayList;
import java.util.Collections;
class Player {
    private ArrayList<Card> cardsOnHand;
    private BufferedReader reader;
    private String name;
    private int request;
    private int PDollar;
    private int bet;
    Player(){
        cardsOnHand = new ArrayList<Card>();
        reader = new BufferedReader(new InputStreamReader(System.in));
        name = new String();
        PDollar = 1000;
        request = 5;
    }
    public int getPDollar(){
        return PDollar;
    }
    public int betDollars(){
        try {
            bet = Integer.valueOf(reader.readLine());
            PDollar -= bet;
            return bet;
        }
        catch (Exception e){
            return 0;
        }
    }
    public int getBet(){
        return bet;
    }
    @Override
    public String toString(){
        return name;
    }
    String initName(){
        try {
            name = new String(reader.readLine());
            return name;
        }
        catch (Exception e){
            name = new String("");
            return name;
        }
    }
    public void getCards(ArrayList<Card> cards){
        cardsOnHand.addAll(cards);
        Collections.sort(cardsOnHand);
    }
    public ArrayList<Card> showCards(){
        return cardsOnHand;
    }
    public String dropCards(){
        try {
            String retainCards = new String(reader.readLine());
            StringBuilder drop = new StringBuilder();
            ArrayList<Card> cardsToDrop = new ArrayList<Card>();
            for(char c : (new String("abcde")).toCharArray()) {
                if (retainCards.indexOf(c) < 0) {
                    drop.append(" (").append(c).append(") ");
                    drop.append(cardsOnHand.get((int) c - ((int) 'a')).toString());
                    cardsToDrop.add(cardsOnHand.get((int) c - ((int) 'a')));
                }
            }
            cardsOnHand.removeAll(cardsToDrop);
            request = 5 - cardsOnHand.size();
            return drop.toString();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public int requestCards(){
        return request;
    }
    public ArrayList<Card> giveBackCards(){
        ArrayList<Card> tempCards = new ArrayList<Card>(cardsOnHand);
        cardsOnHand.clear();
        return tempCards;
    }
    public void getReward(int money){
        PDollar += money;
    }
}