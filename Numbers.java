import java.util.*;
/**
 * Write a description of class ResultChecker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Numbers extends ResultChecker
{
    private int numberPossibility;
    private int score;
    private ArrayList<Integer> tempDice = new ArrayList<Integer>();
    private int valueToTally;
    
    public void findScore(ArrayList<Integer> diceRoll)
    {
        this.score = 0;
        this.tempDice = diceRoll;
        int runningTotal = 0;
        for(int value: tempDice)
        {
            if(value == valueToTally)
            {
                runningTotal += valueToTally;
            }
        }
        this.score = runningTotal;
    }

    public int getScore(ArrayList<Integer> diceRoll)
    {
        findScore(diceRoll);
        return score;
    }
    
    public void setValueToTally(int valueToTally)
    {
        this.valueToTally = valueToTally;
    }
    
     public int getPossibilityNumber()
    {
        numberPossibility = valueToTally -1;
        return numberPossibility;
    }
}
