import foop.Card;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ralph on 2015/12/26.
 */
class Shuffler {
    private final byte[] suits = {1, 2, 3, 4};
    private final byte[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    ArrayList<Card> cards;
    int index;

    public Shuffler(){
        cards = new ArrayList<>();
        index = 0;
    }
    public void setnPiles(int n) {
        cards.clear();
        index = 0;
        while(n-->0)
            for (byte i : suits)
                for (byte j : values)
                    cards.add(new Card(i, j));
        this.shuffle();
    }
    public void shuffle(){
        Collections.shuffle(cards);
        index = 0;
    }

    public ArrayList<Card> getnTop(int n){
        ArrayList<Card> topNCards = new ArrayList<Card>(cards.subList(index, index+n));
        index += n;
        return topNCards;
    }
    public Card getTop(){
        return cards.get(index++);
    }

    public boolean notEnoughForNextRound(int player){
        return (player * 5 + 5 > cards.size()-index);
    }

}
