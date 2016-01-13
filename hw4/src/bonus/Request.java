import foop.Card;
import foop.Hand;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ralph on 2016/1/13.
 */
public class Request implements Serializable{
    private static final String header_type = "<Type>";
    private static final String header_int = "<Int>";
    private static final String header_bool = "<Bool>";
    private static final String header_card = "<Card>";
    private static final String header_hand = "<Hand>";
    private static final String header_table = "<Table>";
    private static final String tailer_type = "</Type>";
    private static final String tailer_int = "</Int>";
    private static final String tailer_bool = "<Bool>";
    private static final String tailer_card = "</Card>";
    private static final String tailer_hand = "</Hand>";
    private static final String tailer_table = "</Table>";
    private String message;
    public Request(){
        message = "";
    }
    public void setMessage(String m){
        message = "<Type>0</Type>"+m;
    }
    public void setMessage(ArrayList<Card> my_open, Card dealer_open, ArrayList<Hand> table){
        StringBuilder builder = new StringBuilder(header_type).append(1).append(tailer_type);
        builder.append(setHand(my_open)).append(setCard(dealer_open)).append(setTable(table));
        message = builder.toString();
    }
    public void setMessage(ArrayList<Hand> table, int total_player, int position){
        StringBuilder builder = new StringBuilder(header_type).append(2).append(tailer_type);
        builder.append(setTable(table)).append(setInt(total_player)).append(setInt(position));
        message = builder.toString();
    }
    public void setMessage(Card my_open, Card dealer_open, ArrayList<Hand> table){
        StringBuilder builder = new StringBuilder(header_type).append(3).append(tailer_type);
        builder.append(setCard(my_open)).append(setCard(dealer_open)).append(setTable(table));
        message = builder.toString();
    }

    public void setMessage(ArrayList<Hand> table){
        StringBuilder builder = new StringBuilder(header_type).append(4).append(tailer_type);
        builder.append(setTable(table));
        message = builder.toString();
    }
    public void setMessage(boolean decision){
        StringBuilder builder = new StringBuilder(header_type).append(5).append(tailer_type);
        builder.append(setBool(decision));
        message =  builder.toString();
    }
    public void setMessage(int bet){
        StringBuilder builder = new StringBuilder(header_type).append(6).append(tailer_type);
        builder.append(setInt(bet));
        message =  builder.toString();
    }
    public void appendMessage(int type){
        message = new StringBuilder(message).append(type).toString();
    }
    public boolean getDecision(){
        try {
            if (message.charAt(6) - '0' == 5)
                return Boolean.parseBoolean(message.split(header_bool)[1].split(tailer_bool)[0]);
        }catch (Exception e){
            System.out.println("parse error");
        }
        return false;
    }
    public int getBet(){
        try {
            if(message.charAt(6) - '0' == 6)
                return Integer.valueOf(message.split(header_int)[1].split(tailer_int)[0]);
        }catch (Exception e){
            System.out.println("parse error");
        }
        return 0;
    }
    public int getOpCode(){
        return message.charAt(message.length()-1)-'0';
    }
    public int getType(){
        return message.charAt(6) - '0';
    }
    public int getInt(){
        int i = Integer.valueOf(message.split(header_int, 2)[1].split(tailer_int, 2)[0]);
        message = message.split(tailer_int, 2)[1];
        return i;
    }
    public String getString(){
        return message.split(tailer_type)[1];
    }
    public ArrayList<Card> getHand(){
        ArrayList<Card> hand = new ArrayList<>();
        String cards = message.split(header_hand)[1].split(tailer_hand)[0];
        for(String c : cards.split(header_card)){
            if(c.length() == 0)
                continue;
            String[] info = c.split(tailer_card)[0].split(",");
            hand.add(new Card(Byte.parseByte(info[0]), Byte.parseByte(info[1])));
        }
        message = message.split(tailer_hand, 2)[1];
        return hand;
    }
    public Card getCard(){
        String[] info = message.split(header_card)[1].split(tailer_card)[0].split(",");
        message = message.split(tailer_card, 2)[1];
        return new Card(Byte.parseByte(info[0]), Byte.parseByte(info[1]));

    }
    public ArrayList<Hand> getTable(){
        ArrayList<Hand> table = new ArrayList<>();
        String hands = message.split(header_table)[1].split(tailer_table)[0];
        if(hands.length() == 0)
            return table;
        for(String hand : hands.split(header_hand)){
            if(hand.length() == 0)
                continue;
            ArrayList<Card> buffer  = new ArrayList<>();
            for(String c : hand.split(tailer_hand)[0].split(header_card)){
                if(c.length() == 0)
                    continue;
                String[] info = c.split(tailer_card)[0].split(",");
                buffer.add(new Card(Byte.parseByte(info[0]), Byte.parseByte(info[1])));
            }
            table.add(new Hand(buffer));
        }
        message = message.split(tailer_table, 2)[1];
        return table;
    }

    private static String setBool(boolean b){
        return (new StringBuilder()).append(header_bool).append(b).append(tailer_bool).toString();
    }
    private static String setInt(int i){
        return (new StringBuilder()).append(header_int).append(i).append(tailer_int).toString();
    }
    private static String setCard(Card card){
        return (new StringBuilder()).append(header_card).append(card.getSuit()).append(",").append(card.getValue()).append(tailer_card).toString();
    }
    private static String setHand(ArrayList<Card> hand){
        StringBuilder builder = new StringBuilder(header_hand);
        for(Card card : hand)
            builder.append(setCard(card));
        builder.append(tailer_hand);
        return builder.toString();
    }
    private static String setTable(ArrayList<Hand> table){
        StringBuilder builder = new StringBuilder(header_table);
        for(Hand hand : table)
            builder.append(setHand(hand.getCards()));
        builder.append(tailer_table);
        return builder.toString();
    }
    public String toString(){
        return message;
    }
}
