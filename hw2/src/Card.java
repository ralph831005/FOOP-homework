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
class Card implements Comparable<Card> {
    private static final String[] NumberTable = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static final String[] ColorTable = {"C", "D", "H", "S"};

    private int serialNumber; //0~51 for poker cards
    Card(int index){
        serialNumber = index;
    }
    Card(Card c){
        serialNumber = c.serialNumber;
    }
    public String getNumber(){
        return NumberTable[serialNumber/4];
    }
    public int getNumberInt(){
        return serialNumber/4 + 1;
    }
    public String getColor(){
        return ColorTable[serialNumber%4];
    }
    public String toString(){
        return (new StringBuilder()).append(getColor()).append(getNumber()).toString();
    }
    public int compareTo(Card obj){
        if(this.serialNumber < obj.serialNumber)
            return -1;
        else if(this.serialNumber == obj.serialNumber)
            return 0;
        else
            return 1;
    }
}