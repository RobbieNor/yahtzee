import java.util.*;
/**
 * Write a description of class ResultChecker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LowStraight extends ResultChecker
{
    private int numberPossibility = 9;
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
        if(diceString.contains("1234") || diceString.contains("2345") || diceString.contains("3456"))
        {
            this.score = 25;
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
