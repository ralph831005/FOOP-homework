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

class CheckHand{
    public static final int RoyalFlush = 250;
    public static final int StraightFlush = 50;
    public static final int FourOfAKind = 25;
    public static final int FullHouse = 9;
    public static final int Flush = 6;
    public static final int Straight = 4;
    public static final int ThreeOfAKind = 3;
    public static final int TwoPair = 2;
    public static final int JacksOrBetter = 1;
    public static final int Others = 0;
    public static boolean isStraight(List<Card> cards){
        if(cards.get(4).getNumber() == "A" && cards.get(0).getNumber() == "2") //special for A2345
            if(isStraight(cards.subList(0, 4))) //given the cards start with A2, check if the cards is 2345
                return true;
        for(int i = 1; i < cards.size(); ++i)
            if(cards.get(i).getNumberInt() - cards.get(i-1).getNumberInt() != 1)
                return false;
        return true;
    }
    public static boolean isFlush(ArrayList<Card> cards){
        for(int i = 1; i < cards.size(); ++i)
            if(cards.get(i).getColor() != cards.get(i-1).getColor())
                return false;
        return true;
    }
    public static boolean isAKind(List<Card> cards){
        //if all the cards in the List is a kind, then the min and max number of the card is same
        int maxNumber = Collections.max(cards).getNumberInt();
        int minNumber = Collections.min(cards).getNumberInt();
        if(maxNumber == minNumber)
            return true;
        return false;
    }
    public static boolean isFourOfAKind(ArrayList<Card> cards){
        //four same number cards is either the sequence of 0~3 or 1~4
        if(isAKind(cards.subList(0,4)))
            return true;
        if(isAKind(cards.subList(1,5)))
            return true;
        return false;
    }
    public static boolean isFullHouse(ArrayList<Card> cards){

        if(isAKind(cards.subList(0,3)) && isAKind(cards.subList(3,5)))
            return true;
        if(isAKind(cards.subList(0,2)) && isAKind(cards.subList(2,5)))
            return true;
        return false;
    }
    public static boolean isThreeOfAKind(ArrayList<Card> cards){
        if(isAKind(cards.subList(0, 3)))
            return true;
        if(isAKind(cards.subList(1, 4)))
            return true;
        if(isAKind(cards.subList(2, 5)))
            return true;
        return false;

    }
    public static boolean isTwoPair(ArrayList<Card> cards){
        if(isAKind(cards.subList(0,2)) && isAKind(cards.subList(2,4)))
            return true;
        if(isAKind(cards.subList(0,2)) && isAKind(cards.subList(3,5)))
            return true;
        if(isAKind(cards.subList(1,3)) && isAKind(cards.subList(3,5)))
            return true;
        return false;
    }
    public static boolean isJacksOrBetter(ArrayList<Card> cards){
        for(int i = 0; i < cards.size()-1; ++i)
            if(cards.get(i).getNumberInt() >= 11 && isAKind(cards.subList(i,i+2)))
                return true;
        return false;
    }
    public static boolean isStraightFlush(ArrayList<Card> cards){
        if(isStraight(cards)&&isFlush(cards))
            return true;
        return false;
    }
    public static boolean isRoyalFlush(ArrayList<Card> cards){
        if(isStraightFlush(cards))
            if(Collections.min(cards).getNumber() == "10")
                return true;
        return false;
    }
}
