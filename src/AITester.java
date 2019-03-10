import java.lang.Math;
import java.util.*;
/**
 * Write a description of class AITester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AITester
{
    public static void testAI(int playersToAdd)
    {
        //Setup AI Players
        GameManager gm = new GameManager();     
        for(int i = 0; i < playersToAdd; i++)
        {
            Computer ai = new Computer("AI");
            ai.giveFilename("AITEST");
            gm.addPlayer(ai);
        }
        gm.playGame();
        //Statistical Analysis of Results
        ArrayList<Integer> scoreSet = gm.finishGame("AITest");
        int scoresSquaredTotal =0;
        double scoreTotal = 0;
        double playerCount = 0;
        for(double score : scoreSet)
        {
            scoreTotal += score;
            playerCount++;
            scoresSquaredTotal += score*score;
        }
        double averageScore = scoreTotal / playerCount;
        double standardDeviation = Math.sqrt((scoresSquaredTotal - (playerCount*averageScore*averageScore))/(playerCount-1));
        double standardError = standardDeviation / (Math.sqrt(playerCount));  
        //Print Results To screen
        System.out.println("********************************************");
        System.out.println("Test Results");
        System.out.println("********************************************");
        System.out.println("Test Sample:\t\t" + playerCount);
        System.out.println("Average Score:\t\t" + averageScore);
        System.out.println("Standard Deviation:\t" + standardDeviation);
        System.out.println("Standard Error:\t\t" + standardError);
        System.out.println("---------------------------------------");
        System.out.println("********************************************\n\n");
    }
}
