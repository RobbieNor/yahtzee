import java.io.*;
/**
 * Write a description of interface Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Player 
{

    void setName(String name);     

    String getName();

    String getType();

    void haveGo(int roundsTaken);

    Scorecard getScorecard();

    void setSaveString(String saveString);

    void giveWriter(FileWriter writer);

    void giveFilename(String filename);

    void haveContinuedGo(Dice dice, int i, int j);

    void update();

    Scorer getScorer();
}
