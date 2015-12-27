import foop.Card;
import foop.Hand;
import foop.Player;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ralph on 2015/12/26.
 */
public class POOCasino {
    private ArrayList<Player> players;
    private ArrayList<Hand> hands;
    private Shuffler shuffler;
    private int round;
    private ArrayList<Hand> current_table;
    private ArrayList<Hand> last_table;

    public POOCasino(int chips, int _round, String[] player_class){
        players = new ArrayList<>();
        shuffler = new Shuffler();
        current_table =  new ArrayList<>();
        last_table = new ArrayList<>();
        round = _round;
        for(String class_name : player_class){
            try {
                players.add((Player)Class.forName(class_name).getDeclaredConstructor(Integer.TYPE).newInstance(chips));
            }
            catch (Exception e) {
                System.err.printf("Error on constructing player %s\nSkip the player...\n", class_name);
                System.err.println(e);
            }
        }
        shuffler.setnPiles(1);
        System.out.println("Welcome to POOCasino established by Ralph Lee (B02902031)");
        System.out.printf("There are %d players:", players.size());
        for(Player p : players)
            System.out.printf(" %s", p.getClass());
        System.out.println(".");
        System.out.printf("Initially, they all have %d chips to play black jacks.", chips);
    }

    public void process(){
        System.out.println("Game Start!!");
        for(int T = 1; T <= round; ++T){
            System.out.printf("Round %d\n", T);
            ArrayList<Arranger> player_list = new ArrayList<>();
            for(int i = 1; i < players.size()+1; ++i){
                player_list.add(new Arranger(i, players.get(i)));
            }
        }
    }


    public static void main(String[] args){
        int chips = Integer.valueOf(args[0]);
        int round = Integer.valueOf(args[1]);
        POOCasino pooCasino = new POOCasino(chips, round, Arrays.copyOfRange(args, 2, args.length));
        pooCasino.test();
    }
}
