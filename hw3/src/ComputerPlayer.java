import java.util.Random;

class ComputerPlayer extends Player{
    private Random random;
    ComputerPlayer(int n){
        name = (new StringBuilder("Player")).append(n).toString();
        random = new Random();
    }
    public int pick(int select_range){
        return random.nextInt(select_range);
    }
}