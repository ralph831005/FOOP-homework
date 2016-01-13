import foop.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ralph on 2016/1/14.
 */
public class Client implements Runnable{
    private Player player;
    private int bet;
    private int chips;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    Client(String address, int port){
        InetSocketAddress addr = new InetSocketAddress(address, port);
        socket = new Socket();
        try {
            socket.connect(addr);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            chips = ((Request)in.readObject()).getBet();
            player = new PlayerHuman(chips);
        }catch (Exception e){
            System.out.println("Connection failed.");
        }
    }
    public void run(){
        while(true){
            try {
                Request request = (Request)in.readObject();
            }catch (Exception e){
                System.out.println("Socket broken!!");
                break;
            }


        }
    }

    public static void main(String[] args){
        Client client = new Client(args[0], Integer.valueOf(args[1]));
    }



}
