import java.util.*;
/**
 * Write a description of class ResultChecker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chance extends ResultChecker
{
    private int numberPossibility = 12;
    private int score;
    private ArrayList<Integer> tempDice = new ArrayList<Integer>();
    
    public void findScore(ArrayList<Integer> diceRoll)
    {
        this.tempDice = diceRoll;
        int runningTotal = 0;
        for( int diceValue : tempDice)
        {
            runningTotal += diceValue;
        }
        this.score = runningTotal;
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
