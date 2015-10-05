import java.lang.String;
import java.lang.StringBuilder;
import java.lang.System;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Card implements Comparable<Card> {
    private static final String[] convert = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private int number;
    private int color;
    private String name;
    Card(int c){
        if(c < 52) {
            this.number = c % 13 + 2;
            this.color = c / 13;

        }
        else{
            this.number = 0;
            this.color = c % 13 + 4;
        }
        switch(color){
            case 0:
                name = new String(new StringBuilder("C").append(convert[number]).toString());
                break;
            case 1:
                name = new String(new StringBuilder("D").append(convert[number]).toString());
                break;
            case 2:
                name = new String(new StringBuilder("H").append(convert[number]).toString());
                break;
            case 3:
                name = new String(new StringBuilder("S").append(convert[number]).toString());
                break;
            case 4:
                name = new String(new StringBuilder("R").append(convert[number]).toString());
                break;
            case 5:
                name = new String(new StringBuilder("B").append(convert[number]).toString());
                break;
        }

    }
    Card(Card c){
        this.number = c.number;
        this.color = c.color;
        this.name = c.name;
    }
    public int compareTo(Card o){
        if(this.number < o.number)
            return -1;
        else if(this.number > o.number)
            return 1;
        else{
            if(this.color < o.color)
                return -1;
            else if(this.color < o.color)
                return 1;
            else
                return 0;
        }
    }
    public boolean equalTo(Card c){
        if(this.number > 0) {
            if (this.number == c.number) return true;
            else return false;
        }
        else
            return false;
    }
    public String toString(){
        return name;
    }
}
class Player{
    private String name;
    private ArrayList<Card> cards;
    private Random random;
    Player(int n){
        cards = new ArrayList<Card>();
        random = new Random();
        name = new String(new StringBuilder("Player").append(n));
    }
    public int cardLength(){
        return this.cards.size();
    }
    public void getCard(Card card){
        this.cards.add(new Card(card));
    }
    public void drawCard(Player player){
        int len = player.cardLength();
        Card draw = new Card(player.giveCard(random.nextInt(len)));
        System.out.printf("%s draws a card from %s %s\n", this.toString(), player.toString(), draw.toString());
        this.cards.add(draw);
        this.drop();
        player.printCards();
    }
    public Card giveCard(int index){
        Card give = new Card(cards.get(index));
        cards.remove(index);
        return give;
    }
    public void drop(){
        Collections.sort(this.cards);
        for(int i = 0; i < cards.size()-1; )
            if (cards.get(i).equalTo(cards.get(i + 1))) {
                cards.remove(i);
                cards.remove(i);
            }
            else
                i++;
        this.printCards();
    }
    public void printCards(){
        System.out.printf("%s:", this.toString());
        for(Card card: cards){
            System.out.printf(" %s", card.toString());
        }
        System.out.print("\n");
    }
	public void sortCards(){ Collections.sort(cards);}
    public String toString(){
        return name;
    }

}
class Game{
    private Random random;
    ArrayList<Player> players;
    ArrayList<Card> cards;
    Game(){
        random = new Random();
        players = new ArrayList<Player>();
        for(int i = 0; i < 4; ++i) players.add(new Player(i));
        cards = new ArrayList<Card>();
        for(int i = 0; i < 54; ++i) cards.add(new Card(i));
    }
    public void shuffle(){
		Collections.shuffle(this.cards);
    }
    public void sendCards(){
        int count = 0;
		System.out.println("Deal cards");
        for(Card card: cards) {
            players.get(count++).getCard(card);
            count %= 4;
        }
		for(Player player: players){
			player.sortCards();
			player.printCards();
		}
		System.out.println("Drop cards");
		for(Player player: players){
			player.drop();
		}
		System.out.println("Game start");
    }
    public int basicGame(int n){
        int playerNumber = players.size();
        int now_turn = n%playerNumber;
        int next_turn = (n+1)%playerNumber;
        while(true){
            players.get(now_turn).drawCard(players.get(next_turn));
            if(players.get(now_turn).cardLength() == 0 || players.get(next_turn).cardLength() == 0)
                break;
            now_turn = next_turn++;
            next_turn %= playerNumber;
        }
        if(players.get(now_turn).cardLength() == 0 && players.get(next_turn).cardLength() == 0){
            Player next = players.get((next_turn+1)%playerNumber);
            if(now_turn != (playerNumber-1))
                System.out.printf("%s and %s win\n", players.get(now_turn).toString(), players.get(next_turn).toString());
            else
                System.out.printf("%s and %s win\n", players.get(next_turn).toString(), players.get(now_turn).toString());
            players.remove(now_turn);
            players.remove(now_turn % playerNumber-1);
            return players.indexOf(next);
        }
        else if(players.get(now_turn).cardLength() == 0) {
            Player next = players.get(next_turn);
            System.out.printf("%s wins\n", players.get(now_turn).toString());
            players.remove(now_turn);
            return players.indexOf(next);
        }
        else {
            Player next = players.get((next_turn+1)%playerNumber);
            System.out.printf("%s wins\n", players.get(next_turn).toString());
            players.remove(next_turn);
            return players.indexOf(next);
        }

    }
    public void letsPlay(){
        this.shuffle();
        this.sendCards();
        int startPlayer = this.basicGame(0);
        System.out.println("Basic game over");
        System.out.println("Continue");
        while(players.size() > 1){
            startPlayer = this.basicGame(startPlayer);
        }
        System.out.println("Bonus game over");
    }
}
public class PlayGame{
    public static void main(String[] args){
        Game game = new Game();
        game.letsPlay();
        return;
    }
}
