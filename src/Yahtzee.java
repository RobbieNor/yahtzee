import java.util.*;
/**
 * Write a description of class ResultChecker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Yahtzee extends ResultChecker
{
    private int numberPossibility = 11;
    private int score;
    private ArrayList<Integer> tempDice = new ArrayList<Integer>();

    public void findScore(ArrayList<Integer> diceRoll)
    {
        this.tempDice = diceRoll;
        boolean yahtzee = true;
        int referenceValue = 0;
        try{
            referenceValue = tempDice.get(0);
        }
        catch(NullPointerException e)
        {
            System.out.println("ERROR: tempDice is null");
        }
        finally
        {
            for(int diceValue : tempDice)
            {
                if(diceValue != referenceValue)
                {
                    yahtzee = false;
                }
            }
        }
        if(yahtzee)
        {
            score = 50;
        }
        else{
            score = 0;
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
