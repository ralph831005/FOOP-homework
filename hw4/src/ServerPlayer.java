import foop.Card;
import foop.Hand;
import foop.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by ralph on 2016/1/13.
 */
public class ServerPlayer extends Player {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Request request;
    public ServerPlayer(int chips, Socket socket){
        super(chips);
        request = new Request();
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            request.setMessage(chips);
            out.writeObject(request);
        }catch (Exception e){
            System.out.println("Error while creating streams...");
        }
    }
    public void send(String message){
        try {
            request.setMessage(message);
            out.writeObject(request);
        }catch (Exception e){
            System.out.println("Socket IO Exception");
        }

    }
    public void send(ArrayList<Hand> message){
        try{
            request.setMessage(message);
            out.writeObject(request);
        }catch (Exception e){
            System.out.println("Socket IO Exception");

        }
    }
    public int make_bet(ArrayList<Hand> last_table, int total_player, int position) {
        request.setMessage(last_table, total_player, position);
        request.appendMessage(0);
        try {
            out.writeObject(request);
            return ((Request)in.readObject()).getBet();
        }catch (Exception e){
            System.out.println("Socket IO Exception");
            return 0;
        }

    }
    public boolean buy_insurance(Card my_open, Card dealer_open, ArrayList<Hand> current_table) {
        request.setMessage(my_open, dealer_open, current_table);
        request.appendMessage(1);
        try {
            out.writeObject(request);
            return ((Request)in.readObject()).getDecision();
        }catch (Exception e){
            System.out.println("Socket IO Exception");
            return false;
        }
    }
    public boolean do_surrender(Card my_open, Card dealer_open, ArrayList<Hand> current_table){
        request.setMessage(my_open, dealer_open, current_table);
        request.appendMessage(2);
        try {
            out.writeObject(request);
            return ((Request)in.readObject()).getDecision();
        }catch (Exception e){
            System.out.println("Socket IO Exception");
            return false;
        }
    }
    public boolean do_split(ArrayList<Card> my_open, Card dealer_open, ArrayList<Hand> current_table){
        request.setMessage(my_open, dealer_open, current_table);
        request.appendMessage(3);
        try{
            out.writeObject(request);
            return ((Request)in.readObject()).getDecision();
        } catch (Exception e){
            System.out.println("Socket IO Exception");
            return false;
        }
    }
    public boolean do_double(Hand my_open, Card dealer_open, ArrayList<Hand> current_table){
        request.setMessage(my_open.getCards(), dealer_open, current_table);
        request.appendMessage(4);
        try{
            out.writeObject(request);
            return ((Request)in.readObject()).getDecision();
        } catch (Exception e){
            System.out.println("Socket IO Exception");
            return false;
        }
    }
    public boolean hit_me(Hand my_open, Card dealer_open, ArrayList<Hand> current_table){
        request.setMessage(my_open.getCards(), dealer_open, current_table);
        request.appendMessage(5);
        try{
            out.writeObject(request);
            return ((Request)in.readObject()).getDecision();
        } catch (Exception e){
            System.out.println("Socket IO Exception");
            return false;
        }
    }
    public String toString(){
        return (new StringBuilder()).append(this.getClass().getName().substring(6)).append(" : ").append(this.get_chips()).toString();
    }
}