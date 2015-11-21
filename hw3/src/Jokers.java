class Jokers extends OldMaid{
	public Jokers(boolean human){
		super(human);
	}
	public void setCards(){
		for(int i = 0; i < 54; ++i)
			cards.add(new Card(i));
		shuffle();
	}
	public void dealCards(){
		writer.println("Deal cards");
		players.get(0).getCards(cards.subList(0, 14));
		players.get(1).getCards(cards.subList(14, 28));
		players.get(2).getCards(cards.subList(28, 41));
		players.get(3).getCards(cards.subList(41, 54));
	}
}
