import java.util.Scanner;

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
		System.out.println("(C) Old-Maid -- Two Jokers, all computer players.");
		System.out.println("(D) Old-Maid -- Random Pick A Card Out, all computer players.");
		System.out.println("(Else) Quit.");
	}
	static OldMaid getChoice(Scanner sc){
		String choice = sc.nextLine();
		if(choice.indexOf('A') != -1 || choice.indexOf('a') != -1)
			return new Jokers(true);
		else if(choice.indexOf('B') != -1 || choice.indexOf('b') != -1)
			return new PickOneOut(true);
		else if(choice.indexOf('C') != -1 || choice.indexOf('c') != -1)
			return new Jokers(false);
		else if(choice.indexOf('D') != -1 || choice.indexOf('d') != -1)
			return new PickOneOut(false);
		else
			return null;
	}
	static void start(OldMaid game, String player_name){
		game.setCards();
		game.setPlayers(player_name);
		game.dealCards();
		game.dropCards();
		game.Process();
	}
	public static void main(String[] argv){
		Scanner sc = new Scanner(System.in);
		String player_name = Welcome(sc);
		while(true){
			displayChoice();
			OldMaid game = getChoice(sc);
			if(game == null)
				break;
			start(game, player_name);
		}
	}
}
