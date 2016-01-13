import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ralph on 2016/1/13.
 */
class ServerListener extends Thread{
    public ServerSocket server;
    private final LinkedBlockingQueue<Socket> connections;
    public ServerListener(int port){
        server = null;
        connections = new LinkedBlockingQueue<>();
        try {
            server = new ServerSocket(port);
        }catch (Exception e){
            System.err.println("Server create failed");
        }
    }
    public ArrayList<Socket> getConnection(){
        ArrayList<Socket> buffer = new ArrayList<>(10);
        synchronized (connections){
            connections.drainTo(buffer, 10);
        }
        return buffer;
    }
    public void run(){
        while(true){
            try {
                Socket socket = server.accept();
                synchronized (connections){
                    connections.offer(socket);
                }
            }catch (Exception e){
                System.out.println("Socket error, exit");
                break;
            }
        }
    }

}
