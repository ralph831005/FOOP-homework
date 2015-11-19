class HumanPlayer extends Player{
	private Scanner sc;
	Player(String n){
		super();
		name = n;
		sc = new Scanner(System.in);
	}
	public int pick(int select_range){
		writer.printf("Which card to pick? 0~%d : ", select_range-1);
		int choice = Integer.parseInt(sc.nextLine());
		return choice;
	}
	public int getCard(Card c){
		writer.printf("You get %s\n", c);
		super();
	}
}