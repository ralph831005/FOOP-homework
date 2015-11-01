import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.zip.CheckedInputStream;

class Computer {
    private Shuffler shuffler;
    private int bet;
    private int round;
    private PrintWriter writer;

    Computer(){
        shuffler = new Shuffler();
        writer = new PrintWriter(System.out, true);
        round = 0;
    }
    public void betOrNot(int money){
        writer.printf("You have %d P-dollars now\n", money);
        writer.printf("Please enter your P-dollar bet for round %d (1-5 or 0 for quitting the game) : ", round+1);
    }
    public void gameInit(Player player){
        writer.printf("Please enter your name: ");
        writer.printf("Welcome, %s\n", player.initName());
    }
    public void printGetCards(ArrayList<Card> cards) {
        writer.printf("Your cards are");
        for(int i = 0; i < cards.size(); ++i){
            writer.printf(" (%c) %s", (char) (i + (int)'a'), cards.get(i));
        }
        writer.printf("\n");
        writer.printf("Which cards do you want to keep? ");
    }
    public void insertCoinsAndPlay(int money){
        shuffler.shuffle();
        bet = money;
        round++;
    }
    public ArrayList<Card> getTopCards(int n){
        return shuffler.getTop(n);
    }
    public int getReward(ArrayList<Card> cards){
        writer.printf("Your new cards are");
        for(Card c: cards)
            writer.printf(" %s", c);
        writer.printf(".\n");
        //judge the cards
        int reward;
        if(CheckHand.isRoyalFlush(cards)){
            writer.printf("You get a royal flush hand. ");
            if(bet == 5)
                reward = 4000;
            else
                reward = CheckHand.RoyalFlush * bet;
        }
        else if(CheckHand.isStraightFlush(cards)){
            writer.printf("You get a straight flush hand. ");
            reward = CheckHand.StraightFlush * bet;
        }
        else if(CheckHand.isFourOfAKind(cards)){
            writer.printf("You get a four of a kind hand. ");
            reward = CheckHand.FourOfAKind * bet;
        }
        else if(CheckHand.isFullHouse(cards)){
            writer.printf("You get a full house hand. ");
            reward = CheckHand.FullHouse * bet;
        }
        else if(CheckHand.isFlush(cards)){
            writer.printf("You get a flush hand. ");
            reward = CheckHand.Flush * bet;
        }
        else if(CheckHand.isStraight(cards)){
            writer.printf("You get a straight hand. ");
            reward = CheckHand.Straight * bet;
        }
        else if(CheckHand.isThreeOfAKind(cards)){
            writer.printf("You get a three of a kind hand. ");
            reward = CheckHand.ThreeOfAKind * bet;
        }
        else if(CheckHand.isTwoPair(cards)){
            writer.printf("You get a two pair hand. ");
            reward = CheckHand.TwoPair * bet;
        }
        else if(CheckHand.isJacksOrBetter(cards)){
            writer.printf("You get a jacks or better hand. ");
            reward = CheckHand.JacksOrBetter * bet;
        }
        else{
            writer.printf("You get nothing. Keep trying :)\n");
            reward  = 0;
        }
        if(reward > 0)
            writer.printf("The payoff is %d.\n", reward);
        return reward;
    }
    public void printDropCards(String drop){
        writer.printf("Okay. I will discard%s.\n", drop);
    }
    public void goodbye(Player player){
        writer.printf("Good bye, %s. You played for %d round and have %d P-dollars now.\n", player, round, player.getPDollar());
    }
}