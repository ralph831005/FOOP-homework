import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.*;
import java.lang.Exception;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.System;
import java.lang.management.BufferPoolMXBean;
import java.net.CookieHandler;
import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

class Computer {
    private Shuffler shuffler;
    private Player player;
    private int round;
    private PrintWriter writer;

    Computer(){
        shuffler = new Shuffler();
        player = new Player();
        writer = new PrintWriter(System.out, true);
        round = 1;
    }
    private void gameInit(){
        writer.printf("Please enter your name: ");
        writer.printf("Welcome, %s\n", player.initName());
        writer.printf("You have 1000 P-dollars now.\n");
        writer.printf("Please enter your P-dollar bet for round %d (1-5 or 0 for quitting the game) : ", round);
    }
    private void printGetCards(ArrayList<Card> cards, boolean option) {
        if (option){
            writer.printf("Your cards are");
            for(int i = 0; i < cards.size(); ++i){
                writer.printf(" (%c) %s", (char) (i + 97), cards.get(i));
            }
            writer.printf("\n");
            writer.printf("Which cards do you want to keep? ");
        }
        else{
            writer.printf("Your new cards are");
            for(Card c: cards)
                writer.printf(" %s", c.toString());
            writer.printf(".\n");
        }
    }
    private void printDropCards(String drop){
        writer.printf("Okay. I will discard%s.\n", drop);
    }
    public void power(){
        gameInit();
        while(player.betDollars() > 0){
            shuffler.shuffle();
            player.getCards(shuffler.getTop(5));
            printGetCards(player.showCards(), true);
            printDropCards(player.dropCards());
            player.getCards(shuffler.getTop(player.requestCards()));
            printGetCards(player.showCards(), false);


        }
    }
}