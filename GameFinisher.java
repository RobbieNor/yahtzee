import java.io.*;
import java.util.*;
import java.io.File;
/**
 * Write a description of class GameFinisher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameFinisher
{
    public static ArrayList<ArrayList<Integer>> scorecards = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<HashMap<Integer, Integer>> scoresAll = new ArrayList<HashMap<Integer, Integer>>();
    public static ArrayList<HashMap<Integer, Integer>> possibleScoresAll = new ArrayList<HashMap<Integer, Integer>>();

    public static void deleteFile(String filename)
    {
        File file = new File(filename);
        file.delete();
    }

    public void exitSequence(String filename, String name, ArrayList<Integer> diceRoll, String saveString, int roundsTaken, int section)
    {
        System.out.println("\nExit Without Saving? [0) Yes [1) No");
        //Update information ready for saving
        for(Player player : GameManager.players)
        {
            player.update();
        }
        int choice = GameManager.makeValidChoice(-1,2);        
        System.out.println("\n_______________");
        System.out.println("\nExiting Yahtzee");
        System.out.println("_______________\n");
        if(choice == 0)
        {
            //Exit without Saving
            System.out.println("\n********************************************");
            System.out.println("-------------------------------------------\nThank you for Playing\n-------------------------------------------");
            System.out.println("********************************************");
            System.exit(0);
        }
        else if(choice == 1)
        {
            try{
                //Write information to file
                File file = new File("GameSaves//" + filename + ".txt");
                file.setWritable(true);
                FileWriter writer = new FileWriter(file.getAbsolutePath());
                BufferedWriter bWriter = new BufferedWriter(writer);
                //Concatenate and format information to be written
                saveString = saveString + name + ":" + diceRoll + ":" + section + ":"  + roundsTaken + ":";
                saveString = saveString + "\n" + scorecards + "\n" + scoresAll + "\n" + possibleScoresAll;
                bWriter.write(saveString);
                bWriter.close();
            }
            catch(Exception e)
            {
                //Print is Save Failed
                System.out.println(e);
                System.out.println("\n\t---------------------------------------");

                System.out.println( "\tSave Failed");
                System.out.println("\t-----");
                System.out.println("\tExiting Yahtzee...");
                System.out.println("\t---------------------------------------\n");
            }
            finally{
                //Print is save succes
                System.out.println("\n\t--------------------");
                System.out.println( "\tSave Complete");
                System.out.println("\t-----");
                System.out.println("\tExiting Yahtzee...");
                System.out.println("\t--------------------\n");
            }
            System.out.println("\n********************************************");
            System.out.println("-------------------------------------------\nThank you for Playing\n-------------------------------------------");
            System.out.println("********************************************");
            System.exit(0);
        }
        else
        {
            System.out.println("Somethings gone very wrong");
        }
    }
}
