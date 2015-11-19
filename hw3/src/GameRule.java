import java.io.PrintWriter;
import java.lang.System;
import java.util.ArrayList;
import java.util.Collections;

abstract class GameRule{
    protected ArrayList<Card> cards;
    protected ArrayList<Player> players;
    protected PrintWriter writer;
    private Random random;

    public GameRule(){
        cards = new ArrayList<Cards>();
        players = new ArrayList<Player>();
        writer = new PrintWriter(System.out, true);
        random = new Random();
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
        int human_index = random.nextInt(4);
        writer.printf("You are player %d\n", human_index);
        players.add(human_index, new HumanPlayer());
    }
    public boolean checkWin(int round){
        for(int index = players.size() -1; index >= 0; --index)
            if(players.get(index).hasNoCard()){
                writer.printf("%s win at %d round\n", players.get(index), round);
                players.remove(index);
            }
    }
    public void Process(){
        writer.println("Game Start");

        int round = 0;

        //Check if some players have all cards dropped at the first time.
        checkWin(round);
        
        int now_turn = 0;
        int next_turn = 1;

        //game start
        while(players.size() > 1){
            round++;
            Player current = players.get(now_turn);
            Player next = players.get(next_turn);
            int choice = current.pick(next.getNumber());
            Card card = next.giveCard(choice);
            writer.printf("%s draws %s from %s", current, card, next);
            current.getCard(card);
            current.dropCards(); //drop cards and print cards
            next.printCards();
            checkWin(round);
            now_turn = (now_turn + 1) % players.size();
            next_turn = (next_turn + 1) % players.size();
        }

        writer.printf("%s lose\n", players.get(0));
    }
}