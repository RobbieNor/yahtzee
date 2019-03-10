import java.util.*;
/**
 * Write a description of class ResultChecker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class ResultChecker
{

    public abstract void findScore(ArrayList<Integer> diceRoll);

    public abstract int getScore(ArrayList<Integer> diceRoll);

    public abstract int getPossibilityNumber();
}
