import java.io.*;
import java.util.*;
/**
 * Abstract class Computer - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public class Computer implements Player, Comparable<Player>
{
    private String name;
    private Scorecard scorecard = new Scorecard();
    private Dice dice = new Dice();
    private String filename;
    private String saveString;
    private FileWriter writer;
    private Scorer scorer = new Scorer();
    private Random randomGenerator = new Random();

    public Computer(String name)
    {
        this.name = name;
    }

    public void giveFilename(String filename)
    {
        this.filename = filename;
    }

    public void giveWriter(FileWriter writer)
    {
        this.writer = writer;
    }

    public Scorecard getScorecard()
    {
        return scorecard;
    }

    public Scorer getScorer()
    {
        return scorer;
    }

    public void update()
    {
        scorecard.update();
        scorer.update();
    }

    public void setSaveString(String saveString)
    {
        this.saveString = saveString;
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
        return "Computer";
    }

    public void haveContinuedGo(Dice currentDice, int currentSection, int roundsTaken)
    {       
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
        ArrayList<Integer> diceRoll = new ArrayList<Integer>();
        ArrayList<Integer> bestScore = new ArrayList<Integer>();
        System.out.println("\n\t-------------------------------------------\n\t" + this.name + "'s Go!\n\t-------------------------------------------\n\n");
        while(diceRolls <= 1){
            firstMove = false;
            secondMove = true;
            if(diceRolls == 0){
                diceRoll = dice.getDiceState();
            }
            else if(diceRolls == 1)
            {
                if(currentSection ==1)
                {
                    diceRoll = dice.roll();
                }
                else
                {
                    diceRoll = dice.getDiceState();
                }
                secondMove = false;
                firstMove = true;
            }
            while(!firstMove)
            {
                boolean repeat = false;
                int choice = 0;
                int rowToFill = -1;
                bestScore = findBestScore(diceRoll);
                if(bestScore.get(1) > 16)
                {
                    rowToFill = bestScore.get(0);
                    choice = 0;
                }
                else
                {
                    choice = 1;
                }
                if(choice == 0)
                {
                    scorecard.addCategory(rowToFill,bestScore.get(1));                                
                    diceRolls = 1;
                    firstMove = true;
                    secondMove = true;
                    madeChoice = true;;
                }

                else if(choice == 1)
                {
                    diceRolls++;
                    firstMove = true;
                }

                else
                {
                    System.out.println("Something's gone very wrong, you shouldn't be here...");
                    repeat = true;
                }
            }
            while(!secondMove)
            {
                boolean repeat = false;
                int choice = 0;
                int rowToFill = -1;
                bestScore = findBestScore(diceRoll);
                if(bestScore.get(1) > 16)
                {
                    rowToFill = bestScore.get(0);
                    choice = 0;
                }
                else if(bestScore.get(1) < 7)
                {
                    choice = 1;
                }
                else{
                    choice = 2;
                }
                if(choice == 0)
                {
                    scorecard.addCategory(rowToFill,bestScore.get(1));                                
                    diceRolls = 2;
                    firstMove = true;
                    secondMove = true;
                    madeChoice = true;;
                }
                else if(choice == 1)
                {
                    diceRolls++;
                    secondMove = true;
                    madeChoice = false;
                }
                else if(choice == 2)
                {
                    ArrayList<Boolean> holdings = holdModal(diceRoll);                 
                    dice.setDiceHoldState(holdings);
                    diceRolls++;
                    secondMove = true;
                }
                else
                {
                    System.out.println("Something's gone very wrong, you shouldn't be here...");
                    repeat = true;
                }
            }
        }
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
            boolean repeat = false;
            int choice = 0;
            int rowToFill = -1;
            bestScore = findBestScore(diceRoll);
            scorecard.addCategory(bestScore.get(0),bestScore.get(1)); 
            madeChoice =true;
        }
        System.out.println("\n" + name + " scored " + bestScore.get(1) + " on Row " + bestScore.get(0) + " with " + diceRoll + "\n\n");
        scorecard.printScorecard();
    }

    public void haveGo(int roundsTaken)
    {
        int diceRolls = 0;
        boolean madeChoice = false;
        System.out.println("\n\t-------------------------------------------\n\t" + this.name + "'s Go!\n\t-------------------------------------------");
        ArrayList<Integer> bestScore = new ArrayList<Integer>();
        ArrayList<Integer> diceRoll = new ArrayList<Integer>();
        while(diceRolls <= 1){
            bestScore.clear();
            boolean firstMove = false;
            boolean secondMove = true;
            if(diceRolls == 0){
                diceRoll = dice.cleanRoll();
            }
            else if(diceRolls == 1)
            {
                diceRoll = dice.roll();
                secondMove = false;
                firstMove = true;
            }

            while(!firstMove)
            {
                boolean repeat = false;
                int choice = 0;
                int rowToFill = -1;
                bestScore = findBestScore(diceRoll);
                if(bestScore.get(1) > 17)
                {
                    rowToFill = bestScore.get(0);
                    choice = 0;
                }
                else
                {
                    choice = 1;
                }
                if(choice == 0)
                {
                    scorecard.addCategory(rowToFill,bestScore.get(1));                                
                    diceRolls = 2;
                    firstMove = true;
                    secondMove = true;
                    madeChoice = true;;
                }

                else if(choice == 1)
                {
                    diceRolls++;
                    firstMove = true;
                }
                else
                {
                    System.out.println("Something's gone very wrong, you shouldn't be here...");
                    repeat = true;
                }
            }
            while(!secondMove)
            {
                boolean repeat = false;
                int choice = 0;
                int rowToFill = -1;
                bestScore = findBestScore(diceRoll);
                if(bestScore.get(1) ==25 || bestScore.get(1) == 35)
                {
                    rowToFill = bestScore.get(0);
                    choice = 0;
                }

                else if(bestScore.get(1) > 17 && !(bestScore.get(1) == 25 || bestScore.get(1) == 35))
                {
                    choice = 2;
                }
                else if(bestScore.get(1) < 7)
                {
                    choice = 1;
                }
                else{
                    choice = 2;
                }
                if(choice == 0)
                {
                    scorecard.addCategory(rowToFill,bestScore.get(1));                                
                    diceRolls = 2;
                    firstMove = true;
                    secondMove = true;
                    madeChoice = true;;
                }
                else if(choice == 1)
                {
                    diceRolls++;
                    secondMove = true;
                    madeChoice = false;
                }
                else if(choice == 2)
                {
                    ArrayList<Boolean> holdings = holdModal(diceRoll);
                    dice.setDiceHoldState(holdings);
                    diceRolls++;
                    secondMove = true;
                }
                else
                {
                    System.out.println("Something's gone very wrong, you shouldn't be here...");
                    repeat = true;
                }
            }
        }
        while(!madeChoice)
        {
            diceRoll = dice.roll();   
            bestScore.clear();
            boolean repeat = false;
            int choice = 0;
            int rowToFill = -1;
            bestScore = findBestScore(diceRoll);
            scorecard.addCategory(bestScore.get(0),bestScore.get(1)); 
            madeChoice =true;
        }
        System.out.println("\n********************************************\n" + name + "'s Scorecard\n********************************************");
        scorecard.printScorecard();
        System.out.println("\n" + name + " scored " + bestScore.get(1) + " on Row " + bestScore.get(0) + " with " + diceRoll + "\n");
    }

    public ArrayList<Integer> findBestScore(ArrayList<Integer> diceRollState)
    {
        ArrayList<Integer> bestScore = new ArrayList<Integer>();
        ArrayList<Integer> freeCategories = scorecard.getFreeCategories();
        scorer.findPossibilities(diceRollState, freeCategories);
        int bestScoreKey = 0;
        int bestScoreValue = 0;
        for(int i = 0; i < 13; i++)
        {
            int value = scorer.getScore(i);
            if(value > bestScoreValue && freeCategories.contains(i))
            {
                bestScoreKey = i;
                bestScoreValue = value;
            }
        }
        if(bestScoreValue == 0)
        {
            int randomRowIndex = randomGenerator.nextInt(freeCategories.size());
            int randomRow = freeCategories.get(randomRowIndex);
            bestScore.add(randomRow);
            bestScore.add(0);
        }
        else{
            bestScore.add(bestScoreKey);
            bestScore.add(bestScoreValue);
        }
        return bestScore;
    }

    public ArrayList<Boolean> holdModal(ArrayList<Integer> diceRoll)
    {
        //Make a decision about what dice to hold during this roll
        ArrayList<Boolean> holdings = new ArrayList<Boolean>();
        ArrayList<Integer> tempDice = new ArrayList<Integer>();
        HashMap<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();
        tempDice = diceRoll;
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
        int modalValue = -1;
        int modalQuantity = -1;
        for(int i = 1; i < 7; i++)
        {
            try{
                int quantity = frequencyMap.get(i);
                if(quantity > modalQuantity)
                {
                    modalQuantity = quantity;
                    modalValue = i;
                }
            }
            catch(Exception e)
            {
            }
        }
        ArrayList<Integer> fc = scorecard.getFreeCategories();
        if(modalQuantity == 5 && scorecard.getFreeCategories().contains(12))
        {
            holdings.add(true);
            holdings.add(true);
            holdings.add(true);
            holdings.add(true);
            holdings.add(true);
        }
        else if(modalQuantity >= 4 && scorecard.getFreeCategories().contains(7))
        {
            for(int value : diceRoll)
            {
                if(value == modalValue)
                {
                    holdings.add(true);
                }
                else
                {
                    holdings.add(false);
                }
            }
        }
        else if(modalQuantity == 3 && (scorecard.getFreeCategories().contains(6) || scorecard.getFreeCategories().contains(8) || fc.contains(7) || fc.contains(12)))
        {
            for(int value : diceRoll)
            {
                if(value == modalValue)
                {
                    holdings.add(true);
                }
                else
                {
                    holdings.add(false);
                }
            }
        }
        else if(modalQuantity >=3 && !fc.contains(7) && !fc.contains(12) && (scorecard.getFreeCategories().contains(6) || scorecard.getFreeCategories().contains(8)))
        {
            int countRate =0;
            for(int value: diceRoll)
            {
                if(value == modalValue && countRate < 3)
                {
                    holdings.add(true);
                    countRate++;
                }
                else{
                    holdings.add(false);
                }
            }
        }
        else if(modalQuantity == 2 && (fc.contains(0) || fc.contains(1) || fc.contains(2) || fc.contains(3) || fc.contains(4) || fc.contains(5) || fc.contains(6) || fc.contains(7) || fc.contains(8)))
        {
            for(int value : diceRoll)
            {
                if(value == modalValue)
                {
                    holdings.add(true);
                }
                else
                {
                    holdings.add(false);
                }
            }
        }
        else
        {
            holdings.add(false);
            holdings.add(false);
            holdings.add(false);
            holdings.add(false);
            holdings.add(false);
        }
        return holdings;
    }

    public int compareTo(Player player)
    {
        //Comparison for sortingPlayerList
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
