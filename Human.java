import java.util.*;
import java.io.*;
/**
 * Abstract class Human - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public class Human implements Player, Comparable<Player>
{
    private String name;
    private Dice dice = new Dice();
    private Scorecard scorecard = new Scorecard();
    private Scorer scorer = new Scorer();
    private String filename;
    private String saveString;

    private GameFinisher gameFinisher;

    public Human(String name)
    {
        setName(name);
        gameFinisher = new GameFinisher();
        scorecard.calculateTotals();
    }

    public void giveFilename(String filename)
    {
        this.filename = filename;
    }

    public void setSaveString(String saveString)
    {
        this.saveString = saveString;
    }

    public Scorecard getScorecard()
    {
        return scorecard;
    }

    public Scorer getScorer()
    {
        return scorer;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public String getType()
    {
        return "Human";
    }

    public void update()
    {
        //Update Scorecard and Scorer ready for saving game
        scorecard.update();
        scorer.update();
    }

    public void haveGo(int roundsTaken)
    {
        //Initialise boolean loop variables
        int diceRolls = 0;
        boolean madeChoice = false;

        while(diceRolls <= 1){
            ArrayList<Integer> diceRoll = new ArrayList<Integer>();
            boolean firstMove = false;
            boolean secondMove = true;

            if(diceRolls == 0){
                //Roll dice cleanly and print related information
                System.out.println("\n\t--------------------------\n\t" + this.name + "'s Go!\n\t--------------------------\n\nRoll One...");
                diceRoll = dice.cleanRoll();
                System.out.println("\t" + diceRoll + "\n");

            }
            else if(diceRolls == 1)
            {
                //Roll dice based on previous preferences
                System.out.println("\nRoll Two...");
                diceRoll = dice.roll();
                System.out.println("\t" + diceRoll + "\n");
                secondMove = false;
                firstMove = true;
            }
            //Make first action
            while(!firstMove)
            {
                boolean repeat = false;
                //Print Menu
                System.out.println("Available Actions:");
                System.out.println("\t--------------------------");
                System.out.println("\t0) Exit to Desktop\n\t1) Fill a Row\n\t2) Possible Scores\n\t3) Check Scorecard\n\t4) Clean Roll");
                System.out.println("\t--------------------------\n");
                System.out.println("Enter Choice...");
                int choice = GameManager.makeValidChoice(-1,5);
                if(choice == 0)
                {
                    //Update and send GameFinisher variables to save
                    scorecard.update();
                    scorer.update();
                    gameFinisher.exitSequence(filename,name, diceRoll, saveString,roundsTaken, 1);
                }
                else if(choice == 3)
                {
                    //Print Scorecard
                    System.out.println("\n*********************************************\n" + name + "'s Scorecard\n********************************************");
                    scorecard.printScorecard();
                    repeat = true;
                }

                else if(choice == 4)
                {
                    //Cleanly Roll Dice
                    diceRolls++;
                    firstMove = true;
                }
                else if(choice == 2)
                {
                    //Print Possibilities
                    scorer.printPossibilities(diceRoll, scorecard.getFreeCategories());
                    repeat =true;
                }
                else if(choice == 1)
                {
                    //Fill a Row
                    boolean categoryUsed = true;
                    while(categoryUsed)
                    {
                        System.out.println("\nRow to Fill...");
                        int category = GameManager.makeValidChoice(-1,14);
                        scorer.findPossibilities(diceRoll, scorecard.getFreeCategories());
                        //Check category isnt already filled
                        try{
                            if(scorecard.getFreeCategories().contains(category)){
                                int score = scorer.getScore(category);
                                //Set row to be filled and add score
                                scorecard.addCategory(category,score);
                                madeChoice = true;
                                categoryUsed = false;
                            }
                            else
                            {
                                System.out.println("Row is already filled. Please choose another...");
                            }
                        } catch(NullPointerException e) 
                        {
                            System.out.println("Program Crash: Free categories doesnt exist.");
                        }

                    }
                    diceRolls = 2;
                    firstMove = true;
                    secondMove = true;
                    madeChoice = true;
                }
                else
                {
                    System.out.println("Something's gone very wrong, you shouldn't be here...");
                    repeat = true;
                }
                if(repeat){
                    if(diceRolls == 0){
                        System.out.println("Roll One...");
                        System.out.println("\t" + diceRoll + "\n");
                    }
                    else if(diceRolls == 1)
                    {
                        System.out.println("Roll Two...");
                        System.out.println("\t" + diceRoll + "\n");
                    }
                }
            }

            while(!secondMove)
            {
                 boolean repeat = false;
                //Print Menu               
                System.out.println("Available Actions:");
                System.out.println("\t--------------");
                System.out.println("\t0) Exit to Desktop\n\t1) Fill a Row\n\t2) Possible Scores\n\t3) Check Scorecard\n\t4) Clean Roll\n\t5) Hold and Roll Dice");
                System.out.println("\t--------------\n");
                System.out.println("Enter Choice...");

                int choice = GameManager.makeValidChoice(-1,6);

                if(choice == 0)
                {
                    //Update and send GameFinisher variables to save
                    gameFinisher.exitSequence(filename, name, diceRoll, saveString,roundsTaken, 2);
                }
                else if(choice == 3)
                {
                    //Print Scorecard
                    System.out.println("\n********************************************\n" + name + "'s Scorecard\n********************************************");
                    scorecard.printScorecard();
                    repeat = true;
                }
                else if(choice == 5)
                {
                    //Get Holdings preferences from Player
                    System.out.println("\n\t--------------------------");
                    System.out.println("\tDice Holder\n\t-----\n\t[0) Leave [1) Hold");
                    System.out.println("\t--------------------------\n");
                    System.out.println(diceRoll);
                    System.out.println("---------------");
                    System.out.print("[" + diceRoll.get(0) + "]");
                    boolean one = toHold();
                    System.out.print("[" + diceRoll.get(1) + "]");
                    boolean two = toHold();
                    System.out.print("[" + diceRoll.get(2) + "]");
                    boolean three = toHold();
                    System.out.print("[" + diceRoll.get(3) + "]");
                    boolean four = toHold();
                    System.out.print("[" + diceRoll.get(4) + "]");
                    boolean five = toHold();
                    //Send Preferences to Dice()
                    ArrayList<Boolean> newState = new ArrayList<Boolean>();
                    newState.add(one);
                    newState.add(two);
                    newState.add(three);
                    newState.add(four);
                    newState.add(five);
                    dice.setDiceHoldState(newState);
                    System.out.println("\n\t--------------------------\n\tDice Held\n\t-----\n\tRolling Dice...\n\t--------------------------");
                    diceRolls++;
                    secondMove = true;
                }
                else if(choice == 4)
                {
                    //Cleanly Roll Dice
                    diceRolls++;
                    secondMove = true;
                    madeChoice = false;
                }
                else if(choice == 2)
                {
                    //Print Possibilities
                    scorer.printPossibilities(diceRoll, scorecard.getFreeCategories());
                    repeat =true;
                }
                else if(choice == 1)
                {
                    //Fill a Row
                    boolean categoryUsed = true;
                    while(categoryUsed)
                    {
                        System.out.println("What row would you like to fill?");
                        int category = GameManager.makeValidChoice(-1,14);
                        //Check category isnt already filled
                        try{
                            if(scorecard.getFreeCategories().contains(category)){
                                int score = scorer.getScore(category);
                                //Set row to be filled and add score
                                scorecard.addCategory(category,score);
                                madeChoice = true;
                                categoryUsed = false;
                            }
                            else
                            {
                                System.out.println("Row is already filled. Please choose another...");
                            }
                        } catch(NullPointerException e) {
                            System.out.println("Program Crash: Free categories doesnt exist.");
                        }
                    }
                    diceRolls = 2;
                    repeat = false;
                    secondMove = true;
                    madeChoice = true;
                }
                else
                {
                    System.out.println("Something's gone very wrong, you shouldn't be here...");
                    repeat = true;
                }

                if(repeat){
                    if(diceRolls == 0){
                        System.out.println("Roll One...");
                        System.out.println("\t" + diceRoll + "\n");
                    }
                    else if(diceRolls == 1)
                    {
                        System.out.println("Roll Two...");
                        System.out.println("\t" + diceRoll + "\n");
                    }

                }
            }
        }
        ArrayList<Integer> diceRoll = dice.roll();        
        while(!madeChoice)
        {
            System.out.println("\nFinal Roll...");
            System.out.println("\t" + diceRoll + "\n");
            //Print Menu
            System.out.println("Available Actions:");
            System.out.println("\t--------------------------");
            System.out.println("\t0) Exit to Desktop\n\t1) Fill a Row\n\t2) Possible Scores\n\t3) Check Scorecard");
            System.out.println("\t--------------------------");
            System.out.println("Enter Choice...");
            int choice = GameManager.makeValidChoice(-1,4);
            if(choice == 0)
            {
                //Update and send GameFinisher variables to save
                gameFinisher.exitSequence(filename, name, diceRoll, saveString, roundsTaken, 3);
            }
            else if(choice == 3)
            {
                //Print Scorecard
                System.out.println("\n********************************************\n" + name + "'s Scorecard\n********************************************");
                scorecard.printScorecard();
            }

            else if(choice == 2)
            {
                //Print Possibilities
                scorer.printPossibilities(diceRoll, scorecard.getFreeCategories());
            }
            else if(choice == 1)
            {
                //Fill a Row
                boolean categoryUsed = true;
                while(categoryUsed)
                {
                    System.out.println("What row would you like to fill?");
                    int category = GameManager.makeValidChoice(-1,14);
                    //Check category isnt already filled
                    try{
                        if(scorecard.getFreeCategories().contains(category)){
                            int score = scorer.getScore(category);
                            //Set row to be filled and add score
                            scorecard.addCategory(category,score);
                            madeChoice = true;
                            categoryUsed = false;
                        }
                        else
                        {
                            System.out.println("Row is already filled. Please choose another...");
                        }
                    } catch(NullPointerException e) {
                        System.out.println("Program Crash: Free categories doesnt exist.");
                    }
                }
            }
        }
    }

    public void haveContinuedGo(Dice currentDice, int currentSection, int roundsTaken)
    {
        //Initialise Variables from GameSave
        dice = currentDice;
        boolean madeChoice = false;
        boolean firstMove = false;
        boolean secondMove = true;
        int diceRolls = 0;
        if(currentSection == 1)
        {
            firstMove = false;
            secondMove = true;
            diceRolls = 0;
        }
        if(currentSection ==2)
        {
            diceRolls = 1;
        }
        if(currentSection == 3)
        {
            diceRolls =2;
        }
        //Proceed to haveGo() whilst taking account of different initial variable scenarios
        while(diceRolls <= 1){
            ArrayList<Integer> diceRoll = new ArrayList<Integer>();
            if(diceRolls == 0){
                System.out.println("\n\t--------------------------\n\t" + this.name + "'s Go!\n\t--------------------------\n\nRoll One...");
                diceRoll = dice.getDiceState();
                System.out.println("\t" + diceRoll + "\n");
            }
            else if(diceRolls == 1)
            {
                System.out.println("\nRoll Two...");
                if(currentSection == 1)
                {
                    diceRoll = dice.roll();
                }
                else
                {
                    diceRoll = dice.getDiceState();
                }
                System.out.println("\t" + diceRoll + "\n");
                secondMove = false;
                firstMove = true;
            }
            //CURRENTLY MOVING REPEATS
            while(!firstMove)
            {
                boolean repeat = false;
                System.out.println("Available Actions:");
                System.out.println("\t--------------------------");
                System.out.println("\t0) Exit to Desktop\n\t1) Fill a Row\n\t2) Possible Scores\n\t3) Check Scorecard\n\t4) Clean Roll");
                System.out.println("\t--------------------------\n");
                System.out.println("Enter Choice...");
                int choice = GameManager.makeValidChoice(-1,5);
                if(choice == 0)
                {
                    gameFinisher.exitSequence(filename,name, diceRoll, saveString,roundsTaken, 1);
                }
                else if(choice == 3)
                {
                    System.out.println("\n********************************************\n" + name + "'s Scorecard\n********************************************");
                    scorecard.printScorecard();
                    repeat = true;
                }

                else if(choice == 4)
                {
                    diceRolls++;
                    firstMove = true;
                }
                else if(choice == 2)
                {
                    scorer.printPossibilities(diceRoll, scorecard.getFreeCategories());
                    repeat =true;
                }
                else if(choice == 1)
                {
                    boolean categoryUsed = true;
                    while(categoryUsed)
                    {
                        System.out.println("Which row would you like to fill?");
                        int category = GameManager.makeValidChoice(-1,14);
                        scorer.findPossibilities(diceRoll, scorecard.getFreeCategories());
                        try{
                            if(scorecard.getFreeCategories().contains(category)){
                                int score = scorer.getScore(category);
                                scorecard.addCategory(category,score);
                                madeChoice = true;
                                categoryUsed = false;
                            }
                            else
                            {
                                System.out.println("Row is already filled. Please choose another...");
                            }
                        } catch(NullPointerException e) {
                            System.out.println("Program Crash: Free categories doesnt exist.");
                        }
                    }
                    diceRolls = 2;
                    firstMove = true;
                    secondMove = true;
                    madeChoice = true;
                }
                else
                {
                    System.out.println("Something's gone very wrong, you shouldn't be here...");
                    repeat = true;
                }
                if(repeat){
                    if(diceRolls == 0){
                        System.out.println("Roll One...");
                        System.out.println("\t" + diceRoll + "\n");
                    }
                    else if(diceRolls == 1)
                    {
                        System.out.println("Roll Two...");
                        System.out.println("\t" + diceRoll + "\n");
                    }
                }
            }
            while(!secondMove)
            {
                boolean repeat = false;
                System.out.println("Available Actions:");
                System.out.println("\t--------------------------");
                System.out.println("\t0) Exit to Desktop\n\t1) Fill a Row\n\t2) Possible Scores\n\t3) Check Scorecard\n\t4) Clean Roll\n\t5) Hold and Roll Dice");
                System.out.println("\t--------------------------\n");
                System.out.println("Enter Choice...");
                int choice = GameManager.makeValidChoice(-1,6);
                if(choice == 0)
                {
                    gameFinisher.exitSequence(filename, name, diceRoll, saveString,roundsTaken, 2);
                }
                else if(choice == 3)
                {
                    System.out.println("\n********************************************\n" + name + "'s Scorecard\n********************************************");
                    scorecard.printScorecard();
                    repeat = true;
                }
                else if(choice == 5)
                {
                    System.out.println("\n\t--------------------------");
                    System.out.println("\tDice Holder\n\t-----\n\t[0) Leave [1) Hold");
                    System.out.println("\t--------------\n");
                    System.out.println(diceRoll);
                    System.out.println("--------------------------");
                    System.out.print("[" + diceRoll.get(0) + "]");
                    boolean one = toHold();
                    System.out.print("[" + diceRoll.get(1) + "]");
                    boolean two = toHold();
                    System.out.print("[" + diceRoll.get(2) + "]");
                    boolean three = toHold();
                    System.out.print("[" + diceRoll.get(3) + "]");
                    boolean four = toHold();
                    System.out.print("[" + diceRoll.get(4) + "]");
                    boolean five = toHold();
                    ArrayList<Boolean> newState = new ArrayList<Boolean>();
                    newState.add(one);
                    newState.add(two);
                    newState.add(three);
                    newState.add(four);
                    newState.add(five);
                    dice.setDiceHoldState(newState);
                    System.out.println("\n\t--------------------------\n\tDice Held\n\t-----\n\tRolling Dice...\n\t--------------------------");
                    diceRolls++;
                    secondMove = true;
                }
                else if(choice == 4)
                {
                    diceRolls++;
                    secondMove = true;
                    madeChoice = false;
                }
                else if(choice == 2)
                {
                    scorer.printPossibilities(diceRoll, scorecard.getFreeCategories());
                    repeat =true;
                }
                else if(choice == 1)
                {
                    boolean categoryUsed = true;
                    while(categoryUsed)
                    {
                        System.out.println("What row would you like to fill?");
                        int category = GameManager.makeValidChoice(-1,14);
                        try{
                            if(scorecard.getFreeCategories().contains(category)){
                                int score = scorer.getScore(category);
                                scorecard.addCategory(category,score);
                                madeChoice = true;
                                categoryUsed = false;
                            }
                            else
                            {
                                System.out.println("Row is already filled. Please choose another...");
                            }
                        } catch(NullPointerException e) {
                            System.out.println("Program Crash: Free categories doesnt exist.");
                        }
                    }
                    diceRolls = 2;
                    repeat = false;
                    secondMove = true;
                    madeChoice = true;
                }
                else
                {
                    System.out.println("Something's gone very wrong, you shouldn't be here...");
                    repeat = true;
                }

                if(repeat){
                    if(diceRolls == 0){
                        System.out.println("Roll One...");
                        System.out.println("\t" + diceRoll + "\n");
                    }
                    else if(diceRolls == 1)
                    {
                        System.out.println("Roll Two...");
                        System.out.println("\t" + diceRoll + "\n");
                    }

                }
            }
        }
        ArrayList<Integer> diceRoll = new ArrayList<Integer>();
        if(currentSection != 3)
        {
            diceRoll = dice.roll();  
        }
        else if(currentSection == 3)
        {
            diceRoll = dice.getDiceState();  
        }
        while(!madeChoice)
        {
            System.out.println("\nFinal Roll...");
            System.out.println("\t" + diceRoll + "\n");
            System.out.println("Available Actions:");
            System.out.println("\t--------------------------");
            System.out.println("\t0) Exit to Desktop\n\t1) Fill a Row\n\t2) Possible Scores\n\t3) Check Scorecard");
            System.out.println("\t--------------------------");
            System.out.println("Enter Choice...");
            int choice = GameManager.makeValidChoice(-1,4);
            if(choice == 0)
            {
                gameFinisher.exitSequence(filename, name, diceRoll, saveString, roundsTaken, 2);
            }
            else if(choice == 3)
            {
                System.out.println("\n********************************************\n" + name + "'s Scorecard\n********************************************");
                scorecard.printScorecard();
            }

            else if(choice == 2)
            {
                scorer.printPossibilities(diceRoll, scorecard.getFreeCategories());
            }
            else if(choice == 1)
            {
                boolean categoryUsed = true;
                while(categoryUsed)
                {
                    System.out.println("What row would you like to fill?");
                    int category = GameManager.makeValidChoice(-1,14);
                    try{
                        if(scorecard.getFreeCategories().contains(category)){
                            int score = scorer.getScore(category);
                            scorecard.addCategory(category,score);
                            madeChoice = true;
                            categoryUsed = false;
                        }
                        else
                        {
                            System.out.println("Row is already filled. Please choose another...");
                        }
                    } catch(NullPointerException e) {
                        System.out.println("Program Crash: Free categories doesnt exist.");
                    }
                }
            }
        }
    }

    public void giveWriter(FileWriter writer){

    }

    private boolean toHold()
    {
        int hold = GameManager.makeValidChoice(-1,2);
        if(hold == 0)
        {
            return false;
        }
        else if(hold ==1)
        {
            return true;
        }
        else
        {
            System.out.println("Help!!!!");
            return true;            
        }
    }

    public int compareTo(Player player)
    {
        if(this.getScorecard().getTotal() < player.getScorecard().getTotal())
        {
            return -1;
        } else if (this.getScorecard().getTotal() > player.getScorecard().getTotal())
        {
            return 1;
        }
        else {
            return 0;
        }
    }
}
