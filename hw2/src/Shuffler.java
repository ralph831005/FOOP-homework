import java.util.ArrayList;
import java.util.Collections;

class Shuffler{
    private ArrayList<Card> deck;   //a deck of cards
    private int index;              //points to the top of the deck ( because the cards taken aren't actually popped )
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
    public ArrayList<Card> getTop(int n){ //get top n cards of the deck
        if(index+n > 51){
            shuffle();
            index = 0;
        }
        ArrayList<Card> topNCards = new ArrayList<Card>(deck.subList(index, index+n));
        index += n;
        return topNCards;
    }
}