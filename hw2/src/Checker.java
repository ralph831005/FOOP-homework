/*
class Checker{
    private static boolean isStraight(ArrayList<Card> cards){
        for(int i = 1; i < cards.size(); ++i)
            if(cards.get(i).getNumberInt() - cards.get(i-1).getNumberInt() != 1)
                return false;
        return true;
    }
    private static boolean isFlush(ArrayList<Card> cards){
        for(int i = 1; i < cards.size(); ++i)
            if(cards.get(i).getColor() != cards.get(i-1).getColor())
                return false;
        return true;
    }
    private static boolean isAKind(List<Card> cards){
        int maxNumber = Collections.max(cards).getNumberInt();
        int minNumber = Collections.min(cards).getNumberInt();
        if(maxNumber == minNumber)
            return true;
        return false;
    }
    private static boolean isFourOfAKind(ArrayList<Card> cards){
        if(isAKind(cards.subList(0,4)))
            return true;
        if(isAKind(cards.subList(1,5)))
            return true;
        return false;
    }
    private static boolean isFullHouse(ArrayList<Card> cards){
        if(isAKind(cards.subList(0,3)) && isAKind(cards.subList(3,5)))
            return true;
        if(isAKind(cards.subList(0,2)) && isAKind(cards.subList(2,5)))
            return true;
        return false;
    }
    private static boolean isThreeOfAKind(ArrayList<Card> cards){
        if(isAKind(cards.subList(0,3)))
            return true;
        if(isAkind(cards.sublist(1, 4)))
            return true;
        if(isAkind(cards.sublist(2, 5)))
            return true;
        return false;

    }
    private static boolean isTwoPair(ArrayList<Card> cards){
        if(isAKind(cards.subList(0,2)) && isAKind(cards.subList(2,4)))
            return true;
        if(isAKind(cards.subList(0,2)) && isAKind(cards.subList(3,5)))
            return true;
        if(isAKind(cards.subList(1,3)) && isAKind(cards.subList(3,5)))
            return true;
        return false;
    }
    private static boolean isJacksOrBetter(ArrayList<Card> cards){
        if(cards.get(2).getNumberInt() < 11 && cards.get(3).getNumberInt() >= 11)
            return true;
        return false;
    }
    private static boolean isStraightFlush(ArrayList<Card> cards){
        if(isStraight(cards)&&isFlush(cards))
            return true;
        return false;
    }
    private static boolean isRoyalFlush(ArrayList<Card> cards){
        if(isStraightFlush(cards))
            if(Collections.max(cards).getNumber() == "A")
                return true;
        return false;
    }
    public static int getReward(ArrayList<Card> cards, int bet){
        writer.printf("You get a ");
        if(isRoyalFlush(cards)){
            writer.printf("royal flush hand.");
        }
        if(isStraightFlush(cards)){

        }
        if(isFourOfAKind(cards)){

        }
        if(isFullHouse(cards)){

        }
        if(isFlush(cards)){

        }
        if(isStraight(cards)){

        }
        if(isThreeOfAKind(cards)){

        }
        if(isTwoPair(cards)){

        }
        if(isJacksOrBetter(cards)){

        }
    }
}
*/
