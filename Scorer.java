import java.io.*;
import java.util.*;
/**
 * Write a description of class Scorer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scorer
{
    private int score;
    private HashMap<Integer, Integer> possibleScores = new HashMap<Integer, Integer>();
    private int findScore;
    private ArrayList<ResultChecker> checkers = new ArrayList<ResultChecker>();
    private ArrayList<String> types = new ArrayList<String>();
    public Scorer()
    {
        //Initialise Variables
        types.add("Aces");
        types.add("Twos");
        types.add("Threes");
        types.add("Fours");
        types.add("Fives");
        types.add("Sixes");
        types.add("3 of a kind");
        types.add("4 of a kind");
        types.add("Full House");
        types.add("Sm.Straight");
        types.add("Lg.Straight");
        types.add("YAHTZEE");
        types.add("Chance");
        ArrayList<Integer> fakeRoll = new ArrayList<Integer>();
        fakeRoll.add(0);
        fakeRoll.add(0);
        fakeRoll.add(0);
        fakeRoll.add(0);
        fakeRoll.add(0);
        ArrayList<Integer> fakeCategories = new ArrayList<Integer>();
        for(int i = 0 ; i < 13; i++)
        {
            fakeCategories.add(i);
        }
        findPossibilities(fakeRoll, fakeCategories);
    }

    public void setDetails(HashMap<Integer, Integer> possibleScores)
    {
        this.possibleScores = possibleScores;
    }

    public void update()
    {
        //Update for GameSave
        GameFinisher.possibleScoresAll.add(possibleScores);
    }

    public String getDetails()
    {
        //Return information about Scorer
        String scorerString = "";
        scorerString += possibleScores;
        return scorerString;
    }

    public void printPossibilities(ArrayList<Integer> diceRoll, ArrayList<Integer> free_categories)
    {
        findPossibilities(diceRoll, free_categories);
        //Print Table
        System.out.println("\n********************************************");
        System.out.println("Possible Outcomes");
        System.out.println("********************************************");
        System.out.println("Row\t|Outcome\t|Potential Score");
        System.out.println("-------------------------------------------");
        for(int i = 0; i <6; i++)
        {
            if(free_categories.contains(i)){
                System.out.println(i +"\t|" + types.get(i) + "\t\t|" + possibleScores.get(i));
            }
            else
            {
                System.out.println(i +"\t|" + types.get(i) + "\t\t|-");
            }
        }
        for(int i = 6; i< 12; i++)
        {
            if(free_categories.contains(i)){
                System.out.println(i +"\t|" + types.get(i) + "\t|" + possibleScores.get(i));
            }
            else{
                System.out.println(i +"\t|" + types.get(i) + "\t|-");
            }
        }
        if(free_categories.contains(12)){
            System.out.println(12 +"\t|" + types.get(12) + "\t\t|" + possibleScores.get(12));
        }
        else
        {
            System.out.println(12 +"\t|" + types.get(12) + "\t\t|-");
        }
        System.out.println("-------------------------------------------");
        System.out.println("********************************************\n");
    }

    public int getScore(int category)
    {
        //Always run findPossibilities first
        try{
            return possibleScores.get(category);
        }
        catch(Exception e)
        {
            System.out.println("Please provide a valid No.");
        }
        return -1;
    }

    public void findPossibilities(ArrayList<Integer> diceRoll, ArrayList<Integer> free_categories)
    {
        //Initlialise checkers;
        Yahtzee yahtzee = new Yahtzee();
        Chance chance = new Chance();
        FourOfAKind fourofakind = new FourOfAKind();
        LowStraight lowstraight = new LowStraight();
        HighStraight highstraight = new HighStraight();
        ThreeOfAKind threeofakind = new ThreeOfAKind();
        FullHouse fullhouse = new FullHouse();
        Numbers numbers = new Numbers();
        checkers.add(yahtzee);
        checkers.add(chance);
        checkers.add(fourofakind);
        checkers.add(lowstraight);
        checkers.add(highstraight);
        checkers.add(fullhouse);
        checkers.add(threeofakind);
        //Loop through and check for each possibility
        for(ResultChecker checker : checkers)
        {
            int position = checker.getPossibilityNumber();
            int possibleScore = checker.getScore(diceRoll);
            possibleScores.put(position, possibleScore);
        }
        for(int i = 1; i < 7; i++)
        {
            numbers.setValueToTally(i);
            int position = numbers.getPossibilityNumber();
            int possibleScore = numbers.getScore(diceRoll);
            possibleScores.put(position, possibleScore);
        }
    }
}
