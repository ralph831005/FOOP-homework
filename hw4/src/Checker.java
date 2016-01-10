import foop.*;

import java.util.ArrayList;

//tools operate with Card and Hand
class Checker{
	public static void print(Card card){
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
	public static void print(Hand hand){
		for(Card card : hand.getCards()){
			print(card);
			System.out.print(" ");
		}
	}
	public static void print(ArrayList<Card> cards){
		print(new Hand(cards));
	}
	public static boolean isBusted(Hand hand){
		int sum = 0;
		for(Card card : hand.getCards()){
			sum += Math.min((int)card.getValue(), 10);
		}
		return sum > 21;
	}
	public static boolean isBusted(ArrayList<Card> hand){
		return isBusted(new Hand(hand));
	}
	public static boolean isSoftSeveteen(Hand hand){
		int sum = 0;
		boolean ace = false;
		for(Card card : hand.getCards()){
			sum += Math.min((int)card.getValue(), 10);
			if(card.getValue() == 1)
				ace = true;
		}
		return ace && (sum+10) == 17;
	}
	public static boolean isSoftSeveteen(ArrayList<Card> hand) {
		return isSoftSeveteen(new Hand(hand));
	}
	public static int value(Hand hand){
		int sum = 0;
		boolean ace = false;
		for(Card card : hand.getCards()){
			sum += Math.min((int)card.getValue(), 10);
			if(card.getValue() == 1)
				ace = true;
		}
		if(ace && ((sum + 10) < 22))
			sum += 10;
		return sum;
	}
	public static int value(ArrayList<Card> hand){
		return value(new Hand(hand));
	}
	public static boolean isBlackJack(Hand hand){
		return (value(hand) == 21 && hand.getCards().size() == 2);
	}
	public static boolean isBlackJack(ArrayList<Card> hand){
		return isBlackJack(new Hand(hand));
	}

}
