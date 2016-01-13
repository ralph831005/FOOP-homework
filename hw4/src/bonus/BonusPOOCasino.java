import foop.Hand;
import foop.Player;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ralph on 2016/1/13.
 */
public class BonusPOOCasino extends POOCasino{
    private ServerListener listener;
    private int chips;
    private int port;
    public BonusPOOCasino(int _chips, int _port){
        super(8); //create a POOCasino with 8 piles of cards
        chips = _chips;
        port = _port;
        listener =  new ServerListener(port);
        System.out.println("\nThis is the Bonus POOCasino written by Ralph Lee, ID B02902031.");
    }

    private void broadcast(String message){
        for(Player player : players){
            ((ServerPlayer)player).send(message);
        }
    }
    private void broadcast(ArrayList<Hand> message){
        for(Player player : players){
            ((ServerPlayer)player).send(message);
        }
    }
    private void accept(){
        ArrayList<Socket> buffer = listener.getConnection();
        for(Socket socket : buffer){
            players.add(new ServerPlayer(chips, socket));
        }
    }
    @Override
    public void run(){
        listener.start();
        while(players.size() == 0){
            accept();
        }
        while(true){
            accept();
            if(players.size() == 0)
                break;
            broadcast("=========New Game Start=========");
            process();
            broadcast(last_table);
            Iterator<Player> player = players.iterator();
            while(player.hasNext()){
                if(((ServerPlayer)player.next()).isOffline())
                    player.remove();
            }
        }
        System.out.println("POOCasino closed");
        listener.interrupt();
        try {
            listener.server.close();
            listener.join();
        }catch (Exception e){

        }

    }
    public static void main(String[] args){
        int chips = Integer.valueOf(args[0]);
        int port = Integer.valueOf(args[1]);
        POOCasino pooCasino = new BonusPOOCasino(chips, port);
        pooCasino.run();
    }


}
