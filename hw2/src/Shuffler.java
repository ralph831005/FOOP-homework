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

class Shuffler{
    private ArrayList<Card> deck;
    private int index;
    Shuffler(){
        deck = new ArrayList<Card>();
        for(int i = 0; i < 52; ++i)
            deck.add(new Card(i));
        index = 0;
    }
    void shuffle(){
        Collections.shuffle(deck);
        index = 0;
    }
    ArrayList<Card> getTop(int n){
        if(index+n > 51){
            shuffle();
            index = 0;
        }
        ArrayList<Card> topNCards = new ArrayList<Card>(deck.subList(index, index+n));
        index += n;
        return topNCards;
    }
}