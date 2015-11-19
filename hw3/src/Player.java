import java.io.*;
import java.util.*;

abstract class Player{
	protected String name;
	protected ArrayList<Card> cards;
	protected PrintWriter writer;
	Player(){
		cards = new ArrayList<Card>();
		writer = new PrintWriter(System.out, true);
	}
	public boolean hasNoCard(){
		return cards.size() == 0;
	}
	public void printCards(){
		writer.printf("%s :", name);
		for(Card c : cards)
			writer.printf("%s ", c);
		writer.println("");
	}
	public void dropCards(){
		Collections.sort(cards);
		for(int i = 0; i < cards.size()-1;)
			if(cards.get(i).equalTo(cards.get(i+1))){
				cards.remove(i);
				cards.remove(i);
			}
			else
				i++;
		printCards();
	}
	public void getCards(List<Card> c){
		cards.addAll(c);
		Collections.sort(cards);
		printCards();
	}
	public void getCard(Card c){
		cards.add(c);
	}
	public abstract int pick(int select_range);
	public Card giveCard(int choice){
		return cards.remove(choice);
	}
	public int getNumber(){
		return cards.size();
	}
	@Override
	public String toString(){
		return name;
	}
}