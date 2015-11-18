import java.io.PrintWriter;
import java.lang.System;
import java.util.ArrayList;
import java.util.Collections;

abstract class GameRule{
    protected ArrayList<Card> cards;
    private ArrayList<Player> players;
    private PrintWriter writer;

    public GameRule(){
        cards = new ArrayList<Cards>();
        players = new ArrayList<Player>();
        writer = new PrintWriter(System.out, true);
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }
    public abstract void setCards();
    public abstract void sendCards();
    public void GameProcess(int start_with){
        int numberOfPlayers = players.size();
        int now_turn = start_with;

    }
}