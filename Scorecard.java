import java.util.*;
/**
 * Write a description of class Scorecard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scorecard
{
    private ArrayList<Integer> free_categories = new ArrayList<Integer>();
    private boolean upperBonus;
    private int upperTotal;
    private int lowerTotal;
    private int grandTotal;
    private HashMap<Integer,Integer> scores = new HashMap<Integer,Integer>();
    private ArrayList<String> types = new ArrayList<String>();

    public Scorecard()
    {
        //Initialise Variables
        for(int i = 0 ; i < 13 ; i++)
        {
            free_categories.add(i);
        }
        upperBonus = false;
        upperTotal = 0;
        lowerTotal = 0;
        grandTotal = 0;
        int loop = 0;
        while(loop < 13)
        {
            scores.put(loop,0);

            loop++;
        }
        types.add("Aces");
        types.add("Twos");
        types.add("Threes");
        types.add("Fours");
        types.add("Fives");
        types.add("Sixes");
        types.add("3 of a kind");
        types.add("4 of a kind");
        types.add("Full House");
        types.add("Lw.Straight");
        types.add("Hg.Straight");
        types.add("YAHTZEE");
        types.add("Chance");
    }

    public void update()
    {
        //Update variables
        GameFinisher.scorecards.add(free_categories);
        GameFinisher.scoresAll.add(scores);
    }
    public void setDetails(ArrayList<Integer> free_categories, HashMap<Integer, Integer> scores)
    {
        //Force Set Variables
        this.free_categories = free_categories;
        this.scores = scores;
    }
    
    public String getDetails()
    {
        //Return information stored by Scorecard
        String scorecardString = "";
        scorecardString += free_categories;
        scorecardString += "\n" + scores;
        return scorecardString;
    }
    public void checkForBonus()
    {
        //Update bonus boolean
        if(upperTotal > 63)
        {
            upperBonus = true;
        }
    }

    public void printScorecard()
    {
        //Make sure information is correct
        calculateTotals();
        checkForBonus();
        //Print Scorecard
        System.out.println("Row\t|Type\t\t|Score\t|Available");
        System.out.println("-------------------------------------------");        
        try{
            for(int i = 0; i < 6; i++)
            {
                System.out.println(i + "\t|" +types.get(i) +"\t\t|" + scores.get(i) +"\t|" + checkFreeCategory(i));
            }
            System.out.println("\t-----------------------------------");                                
            if(upperBonus){
                System.out.println("\t|Bonus:\t\t|25\t") ;
            }
            else {
                System.out.println("\tBonus:\t\t|0\t");
            }
            System.out.println("\t-----------------------------------");
            for(int i = 6; i < 12; i++)
            {
                System.out.println(i + "\t|" +types.get(i) +"\t|" + scores.get(i) +"\t|" + checkFreeCategory(i));
            }
            System.out.println(12 + "\t|" +types.get(12) +"\t\t|" + scores.get(12) +"\t|" + checkFreeCategory(12));
            System.out.println("\t-----------------------------------");
            System.out.println("\t|Upper Total:\t|" + upperTotal);
            System.out.println("\t|Lower Total:\t|" + lowerTotal);
            System.out.println("-------------------------------------------");
            System.out.println("********************************************");
            System.out.println("GRAND TOTAL:\t\t|" + grandTotal); 
            System.out.println("********************************************\n");
        } catch(IndexOutOfBoundsException e)
        {
            System.out.println("Map or Arraylists are not initlised correctly");
        }
    }

    public boolean checkFreeCategory(int category)
    {
        return getFreeCategories().contains(category);
    }

    public ArrayList<Integer> getFreeCategories()
    {
        return free_categories;
    }

    public void addCategory(int category, int score)
    {
        int index = free_categories.indexOf(category);
        if(index != -1)
        {
            free_categories.remove(index);
            scores.put(category, score);
        }
        else
        {
            System.out.println("ERROR: Can't find index of category. Is it present?");
        }
    }

    public void calculateTotals()
    {
        //Calculate all Totals + Bonus
        lowerTotal = 0;
        upperTotal = 0;
        for(int i = 0; i < 6; i++)
        {
            upperTotal += scores.get(i);
        }
        for(int i = 6; i < 13; i++)
        {
            lowerTotal += scores.get(i);
        }
        grandTotal = upperTotal + lowerTotal;
        checkForBonus();
        if(upperBonus)
        {
            grandTotal += 25;
        }
    }

    public int getTotal()
    {
        calculateTotals();
        return grandTotal;
    }
}
