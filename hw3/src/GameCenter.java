public class GameCenter{
	static String Welcome(Scanner sc){
		System.out.println("Welcome to GameCenter, please enter your name.");
		String name = sc.nextLine();
		System.out.printf("Welcome, %s\n", name);
		System.out.println("Please choose which game to play.");
		return name;
	}
	static void displayChoice(){
		System.out.println("(A) Old-Maid -- Two Jokers.");
		System.out.println("(B) Old-Maid -- Random Pick A Card Out.");
		System.out.println("(Else) Quit.")
	}
	static GameRule getChoice(Scanner sc){
		String choice = sc.nextLine();
		if(choice.indexOf('A') != -1 || choice.indexOf('a') != -1)
			return new Jokers();
		else if(choice.indexOf('B') != -1 || choice.indexOf('b') != -1)
			return new OldMaid();
		else
			return NULL;
	}
	static void start(GameRule game, String player_name){
		game.setCards();
		game.setPlayers(player_name);
		game.dealCards();
		game.Process();
	}
	public static void main(String[] argv){
		Scanner sc = new Scanner(System.in);
		String player_name = Welcome();
		while(True){
			displayChoice();
			GameRule game = getChoice(sc);
			if(game == null)
				break;
			start(game, player_name);
		}
	}
}