import java.util.*;
/**
 * Write a description of class ResultChecker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HighStraight extends ResultChecker
{
    private int numberPossibility = 10;
    private int score;
    private ArrayList<Integer> tempDice = new ArrayList<Integer>();

    public void findScore(ArrayList<Integer> diceRoll)
    {
        tempDice.clear();
        for(int value : diceRoll)
        {
            tempDice.add(value);   
        }
        Collections.sort(tempDice);  
        String diceString = "";
        int lastValue = 0;
        for(int value : tempDice)
        {
            if(value != lastValue){
                diceString += value;
                lastValue = value;
            }
        }
        if(diceString.contains("12345") || diceString.contains("23456"))
        {
            this.score = 35;
        }
        else
        {
            this.score = 0;
        }
    }

    public int getScore(ArrayList<Integer> diceRoll)
    {
        findScore(diceRoll);
        return score;
    }

    public int getPossibilityNumber()
    {
        return numberPossibility;
    }
}
