import java.lang.Math;
import java.util.Random;
import java.util.*;
/**
 * Write a description of class Dice here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dice
{
    private int one;
    private int two;
    private int three;
    private int four;
    private int five;
    private ArrayList<Integer> diceState;
    private Random randomGenerator;
    private ArrayList<Boolean> holdings = new ArrayList<Boolean>();

    public Dice()
    {
        //Initialise Dice
        diceState = new ArrayList<Integer>();
        randomGenerator = new Random();
        diceState.add(one);
        diceState.add(two);
        diceState.add(three);
        diceState.add(four);
        diceState.add(five);
        resetHoldings();
    }

    
    public ArrayList<Integer> getDiceState()
    {
        return diceState;
    }
    
    public void setSavedDiceState(ArrayList<Integer> currentDice)
    {
        //Recover DiceState from GameSave's currentDice
        diceState.clear();
        for(int value : currentDice)
        {
            diceState.add(value);
        }
        resetHoldings();
    }

    public void setDiceHoldState(ArrayList<Boolean> holdState)
    {
        //Apply given holdings to Dice()
        this.holdings.clear();
        for(Boolean element : holdState)
        {
            this.holdings.add(element);
        }
    }

    public ArrayList<Integer> cleanRoll()
    {
        //Roll Dice with no holdings
        one = generateRoll();
        two = generateRoll();
        three = generateRoll();
        four = generateRoll();
        five = generateRoll();
        diceState.clear();
        diceState.add(one);
        diceState.add(two);
        diceState.add(three);
        diceState.add(four);
        diceState.add(five);
        return diceState;
    }

    public ArrayList<Integer> roll()
    {
        //Roll Dice with holdings
        if(!holdings.get(0))
        {
            one = generateRoll();
        }
        if(!holdings.get(1))
        {
            two = generateRoll();
        }
        if(!holdings.get(2))
        {
            three = generateRoll();
        }
        if(!holdings.get(3))
        {
            four = generateRoll();
        }
        if(!holdings.get(4))
        {
            five = generateRoll();
        }
        diceState.clear();
        diceState.add(one);
        diceState.add(two);
        diceState.add(three);
        diceState.add(four);
        diceState.add(five);
        resetHoldings();
        return diceState;
    }

    private void resetHoldings()
    {
        holdings.clear();
        holdings.add(false);
        holdings.add(false);
        holdings.add(false);
        holdings.add(false);
        holdings.add(false);
    }

    private int generateRoll()
    {
        //Generate Random values for dice
        int value = 0;
        while(value ==0)
        {
            value = randomGenerator.nextInt(7);
        }
        return value;
    }

    public void printDice()
    {
        System.out.println(diceState);
    }
}
