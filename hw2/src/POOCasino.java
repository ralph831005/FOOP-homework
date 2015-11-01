public class POOCasino {
	static void gameStart(){
		Computer computer = new Computer();
		Player player = new Player();
		computer.gameInit(player);
		computer.betOrNot(player.getPDollar());
		while(player.betDollars() > 0){  									//ask player for dollars to play
			computer.insertCoinsAndPlay(player.getBet());					//give dollars to computer, shuffle cards can play
			player.getCards(computer.getTopCards(5));						//deliever cards
			computer.printGetCards(player.showCards());						//print the cards with option a, b, c, d, e
			computer.printDropCards(player.dropCards());					//print the result of drop cards
			player.getCards(computer.getTopCards(player.requestCards()));	//deliever new cards to player
			int reward = computer.getReward(player.giveBackCards());		//check hand
			player.getReward(reward);										//get reward
			computer.betOrNot(player.getPDollar());
		}
		computer.goodbye(player);
	}
	public static void main(String[] argv){
		gameStart();
		return;
	}
}
