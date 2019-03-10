import java.util.*;
/**
 * Write a description of class ResultChecker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ThreeOfAKind extends ResultChecker
{
    private int numberPossibility = 6;
    private int score;
    private ArrayList<Integer> tempDice = new ArrayList<Integer>();
    private HashMap<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

    public void findScore(ArrayList<Integer> diceRoll)
    {
        frequencyMap.clear();
        this.tempDice = diceRoll;
        for(int i = 0; i < 5; i++)
        {
            int value = tempDice.get(i);
            if(frequencyMap.containsKey(value))
            {
                frequencyMap.put(value, frequencyMap.get(value) + 1);
            }
            else
            {
                frequencyMap.put(value, 1);
            }
        }
        boolean threeOfAKind = false;        
        for(int i = 1; i < 7 ; i++)
        {
            if(frequencyMap.containsKey(i))
            {
                if(frequencyMap.get(i) >= 3)
                {
                    threeOfAKind = true;
                   
                }
            }
        }       
        if(threeOfAKind)
        {
           this.score = 20;
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
