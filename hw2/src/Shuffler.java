import java.util.ArrayList;
import java.util.Collections;

class Shuffler{
    private ArrayList<Card> deck;
    private int index;
    Shuffler(){
        deck = new ArrayList<Card>();
        for(int i = 0; i < 52; ++i)
            deck.add(new Card(i));
        index = 0;
    }
    public void shuffle(){
        Collections.shuffle(deck);
        index = 0;
    }
    public ArrayList<Card> getTop(int n){
        if(index+n > 51){
            shuffle();
            index = 0;
        }
        ArrayList<Card> topNCards = new ArrayList<Card>(deck.subList(index, index+n));
        index += n;
        return topNCards;
    }
}