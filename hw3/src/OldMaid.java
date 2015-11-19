class OldMaid extends GameRule{
	public OldMaid(){
		super();
	}
	public void setCards(){
		for(int i = 0; i < 52; ++i)
			cards.add(new Card(i));
		shuffle();

		//pop the last card. 
		//Since it's shuffled, this is randomly removing a card.
		cards.remove(51); 
	}
	public void dealCards(){
		writer.println("Deal cards");
		players.get(0).getCards(cards.subList(0,13));
		players.get(1).getCards(cards.subList(13, 26));
		players.get(2).getCards(cards.subList(26, 39));
		players.get(3).getCards(cards.subList(39, 51));
	}
}