import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
abstract class OldMaid{
    protected ArrayList<Card> cards;
    protected ArrayList<Player> players;
    protected PrintWriter writer;
    private Random random;
	private boolean human;
	
    public OldMaid(boolean h){
        cards = new ArrayList<Card>();
        players = new ArrayList<Player>();
        writer = new PrintWriter(System.out, true);
        random = new Random();
		human = h;
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }
    public abstract void setCards();
    public abstract void dealCards();
    public void dropCards(){
        writer.println("Drop cards");
        for(Player p: players)
            p.dropCards();
    }
    public void setPlayers(String name){
        for(int i = 0; i < 3; ++i)
            players.add(new ComputerPlayer(i));
		if(human){
			int human_index = random.nextInt(4);
			writer.printf("You are player %d\n", human_index);
			players.add(human_index, new HumanPlayer(name));
		}
		else
			players.add(new ComputerPlayer(3));
    }
    public int checkWin(int round, int now){
        int new_now = now;
        for(int index = players.size() -1; index >= 0; --index)
            if(players.get(index).hasNoCard()) {
                if(index == now)
                    new_now = now - 1;
                writer.printf("%s win at %d round\n", players.get(index), round);
                players.remove(index);
            }
        if(new_now == players.size())
            new_now -= 1;
        return  new_now;
    }
    public void Process(){
        writer.println("Game Start");

        int round = 0;

        //Check if some players have all cards dropped at the first time.
        checkWin(round, 0); //second arguement is unused.
        
        int now_turn = 0;
        int next_turn = 1;

        //game start
        while(players.size() > 1){
            round++;
            Player current = players.get(now_turn);
            Player next = players.get(next_turn);
            int choice = current.pick(next.getNumber());
            Card card = next.giveCard(choice);
            writer.printf("%s draws %s from %s\n", current, card, next);
            current.getCard(card);
            current.dropCards(); //drop cards and print cards
            next.printCards();
            now_turn =  checkWin(round, now_turn);
            now_turn = (now_turn + 1) % players.size();
            next_turn = (now_turn + 1) % players.size();
        }

        writer.printf("%s lose\n", players.get(0));
    }
}
