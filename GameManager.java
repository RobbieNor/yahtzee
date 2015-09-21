import java.io.*;
import java.util.*;
import java.io.File;
/**
 * Write a description of class GameManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameManager
{
    private BufferedReader reader;
    public static ArrayList<Player> players = new ArrayList<Player>();
    private String filename;

    public void runMainMenu()
    {
        try{
            boolean createDirectory = (new File("GameSaves")).mkdir();
        }
        catch(Exception e)
        {
            //Directory already exists
        }
        System.out.println("Welcome To Yahtzee!");
        System.out.println("********************************************");
        boolean stayAlive = true;
        while(stayAlive){
            players.clear();
            System.out.println("_______________\n");
            System.out.println("Main Menu");
            System.out.println("_______________\n");
            System.out.println("Available Actions:");
            System.out.println("\t--------------------------");
            System.out.println("\t0) Exit to Desktop\n\t1) New Game\n\t2) Load Game\n\t3) Run AI Test");
            System.out.println("\t--------------------------\n");
            int choice = makeValidChoice(-1,4);
            System.out.println("_______________");
            if(choice == 0)
            {
                System.out.println("\nExiting Yahtzee");
                System.out.println("_______________\n");
                System.out.println("\n********************************************");
                System.out.println("-------------------------------------------\nThank you for Playing\n-------------------------------------------");
                System.out.println("********************************************");
                stayAlive = false;                
            }
            else if(choice == 1)
            {
                setupNewGame();
            }
            else if(choice == 2)
            {
                playSavedGame();
            }
            else if(choice == 3)
            {
                System.out.println("\nAI Test");
                System.out.println("_______________\n");
                System.out.println("\nNumber of AI's to Run...");
                int playersToAdd = makeValidChoice(0,2013);
                AITester.testAI(playersToAdd);
            }
            else
            {
                System.out.println("Something's gone very wrong, you shouldn't be here...");
            }
        }
    }

    private void setupNewGame()
    {
        System.out.println("\nGame Setup");
        System.out.println("_______________\n");
        System.out.println("\t--------------------------");        
        boolean newPlayerWanted = true;
        System.out.println("\tCreating Players....");
        System.out.println("\t--------------------------\n");
        while(newPlayerWanted)
        {
            Player player = makePlayer();
            players.add(player);
            System.out.print("\nAdd Another Player?\t[1) Yes\t\t[2) No\n");
            int choice = makeValidChoice(0,3);
            if(choice == 1)
            {
                newPlayerWanted = true;
            }
            else if(choice == 2)
            {
                newPlayerWanted = false;
            }
        }
        System.out.println("\nEnter GameSave Filename...");
        filename = makeValidString();
        boolean saveState = createSaveString(filename);
        if(saveState)
        {
            System.out.println("\n\t--------------------------");                                
            System.out.println( "\tSave Complete");
            System.out.println("\t-----");
            System.out.println("\tStarting Game...");
            System.out.println("\t--------------------------\n");
        }
        else
        {
            System.out.println("\n\t--------------------------");
            System.out.println( "Save Failed");
            System.out.println("\t--------------------------\n");
        }
        System.out.println("_______________\n");
        System.out.println("Game In Progress");
        System.out.println("_______________");
        playGame();
        finishGame(filename);
    }

    public ArrayList<Integer> finishGame(String filename)
    {
        System.out.println("\n_______________");
        System.out.println("\nGame Complete");
        System.out.println("_______________\n");
        System.out.println("********************************************");
        System.out.println("Final Scores");
        System.out.println("********************************************");
        System.out.println("Position\t|Score\t|Player");
        System.out.println("-------------------------------------------");
        ArrayList<Player> sortedPlayerList = new ArrayList<Player>();
        sortedPlayerList = getSortedPlayerList();
        int position = 1;
        ArrayList<Integer> scoreSet = new ArrayList<Integer>();
        for( Player player : sortedPlayerList)
        {
            System.out.println(position + "\t\t " + player.getScorecard().getTotal() + "\t " + player.getName());
            scoreSet.add(player.getScorecard().getTotal());
            position++;
        }
        System.out.println("-------------------------------------------");
        System.out.println("********************************************\n");
        try{
            File file = new File("GameSaves//" + filename + ".txt");
            file.delete();
        }
        catch(Exception e)
        {
            System.out.println("Error: GameSave not deleted");
        }
        System.out.println("\n\t--------------------------");                            
        System.out.println("\tReturning to Main Menu...");
        System.out.println("\t--------------------------\n");
        return scoreSet;
    }

    public ArrayList<Player> getSortedPlayerList()
    {
        ArrayList<Player> sortedPlayerList = new ArrayList<Player>();
        int size = players.size();
        for(int loops = 0; loops < size; loops++)
        {
            int lowestScore = 9999;
            int i = 0;
            for(Player player : players)
            {   
                if(lowestScore > player.getScorecard().getTotal())
                {
                    lowestScore = player.getScorecard().getTotal();
                }
            }
            int indexToRemove =0;
            int index = 0;
            for(Player player : players)
            {

                if(i == 0)
                {
                    if(lowestScore == player.getScorecard().getTotal())
                    {
                        sortedPlayerList.add(player);
                        i = 1;
                        indexToRemove = index;
                    }
                }
                index++;
            }
            players.remove(indexToRemove);
        }
        Collections.reverse(sortedPlayerList);
        return sortedPlayerList;
    }

    public void playGame()
    {
        int noOfPlayers = 0;
        int totalRounds = 0;
        int roundsTaken = 0;
        int rolls = 0; //Get rolls from player.haveGo()
        for(Player player : players)
        {
            noOfPlayers++;
            totalRounds += 13;
        }
        while(roundsTaken != totalRounds)
        {
            for(int i=0; i < 13; i++)
            {
                for(Player player : players)
                {
                    player.haveGo(roundsTaken);
                    roundsTaken++;
                }
            }
        }
    }

    public void addPlayer(Player player)
    {
        players.add(player);
    }

    private boolean createSaveString(String filename)
    {
        boolean success = false;
        String saveString = "";
        for(Player player : players)
        {
            saveString += player.getType();
            saveString += ",";

        }
        saveString += "\n";
        for(Player player : players)
        {
            saveString += player.getName();
            saveString += ",";
        }
        saveString += "\n";
        for( Player player : players)
        {
            player.setSaveString(saveString);
            player.giveFilename(filename);
            //player.giveWriter(writer);
        }
        success = true;
        return success;
    }

    private Player makePlayer()
    {
        System.out.print("\nEnter Player Name...\n");
        String name = makeValidString();
        System.out.println("\nEnter Species ...\t[1) Human\t[2) AI");
        int species = makeValidChoice(0,3);
        if(species == 1)
        {
            Human human = new Human(name);
            return human;
        }
        else if(species == 2)
        {
            Computer computer = new Computer(name);
            return computer;
        }
        else{
            System.out.println("Null!?");
            return null;
        }     
    }

    private void playSavedGame()
    {
        File folder = new File("GameSaves//");
        File[] saves = folder.listFiles();
        System.out.println("\nLoad Game");
        System.out.println("_______________\n");
        System.out.println("\n********************************************");
        System.out.println("Saved Games");
        System.out.println("-------------------------------------------");
        for(int i = 0; i < saves.length; i++)
        {
            if(saves[i].isFile())
            {
                String savename = saves[i].getName();
                savename = savename.substring(0, savename.length() -4);
                System.out.println(savename);
            }
        }
        System.out.println("********************************************\n");
        System.out.println("Select a GameSave");
        boolean goodFilename = false;
        String playersState = "";
        String speciesState ="";
        String gameState = "";
        String freeCategoryState = "";
        String scoresState = "";
        String possibleScoresState = "";
        String saveFilename = "";
        while(!goodFilename)
        {
            saveFilename = makeValidString();
            try
            {
                File file = new File("GameSaves//" + saveFilename + ".txt");
                FileReader reader = new FileReader(file.getAbsolutePath());
                BufferedReader bReader = new BufferedReader(reader);
                speciesState = bReader.readLine();
                playersState = bReader.readLine();
                gameState = bReader.readLine();
                freeCategoryState = bReader.readLine();
                scoresState = bReader.readLine();
                possibleScoresState = bReader.readLine();
                goodFilename = true;
                reader.close();
            }
            catch(FileNotFoundException e)
            {
                System.out.println("GameSave not found...");
            }
            catch(IOException e)
            {
                System.out.println("ERROR: IO Error whilst reading gamesave");
            }
        }
        //Rebuild the state game was previously in
        String[] playersStateArray = playersState.split(","); 
        String[] speciesStateArray = speciesState.split(",");
        String[] gameStateArray = gameState.split(":");        
        int noOfPlayers = playersStateArray.length;
        players.clear();
        //Create Players
        for(int i = 0; i < noOfPlayers; i++)
        {
            if(speciesStateArray[i].equals("Human"))
            {
                Human human = new Human(playersStateArray[i]);
                human.giveFilename(saveFilename);
                players.add(human);
            }
            else if(speciesStateArray[i].equals("Computer"))
            {
                Computer computer = new Computer(playersStateArray[i]);
                computer.giveFilename(saveFilename);
                players.add(computer);
            }
        }
        String continuingPlayerName = gameStateArray[0];
        int indexOfContinuingPlayer = -1;
        for(Player player : players)
        {
            if(player.getName().equals(continuingPlayerName))
            {
                indexOfContinuingPlayer = players.indexOf(player);
            }
        }
        //Reform Array's to rebuild objects with
        String currentDiceStateString = gameStateArray[1];
        List<String> diceStringsArray = Arrays.asList(currentDiceStateString.substring(1, currentDiceStateString.length() - 1).split(", "));
        ArrayList<Integer> diceValues = new ArrayList<Integer>();
        for(String valueString : diceStringsArray)
        {
            int value = Integer.parseInt(valueString);
            diceValues.add(value);
        }
        Dice currentDice = new Dice();
        currentDice.setSavedDiceState(diceValues);
        int currentSection = Integer.parseInt(gameStateArray[2]);
        int roundsTaken = Integer.parseInt(gameStateArray[3]);
        String freeCategoryString = freeCategoryState;        
        List<String> freeCategoriesStringArrays = Arrays.asList(freeCategoryString.substring(2, freeCategoryString.length() - 2).split("], \\["));
        ArrayList<ArrayList<Integer>> freeCategoriesArrays = new ArrayList<ArrayList<Integer>>();
        for(String freeCategoryStringArray : freeCategoriesStringArrays)
        {
            List<String> freeCategoryStringList = Arrays.asList(freeCategoryStringArray.split(", "));
            ArrayList<Integer> freeCategoryIntArray = new ArrayList<Integer>();
            for(String freeCategory : freeCategoryStringList)
            {
                int freeCategoryInt = Integer.parseInt(freeCategory);
                freeCategoryIntArray.add(freeCategoryInt);
            }
            freeCategoriesArrays.add(freeCategoryIntArray);
        }
        String scoresString = scoresState;
        List<String> scoresStringArrays = Arrays.asList(scoresString.substring(2, scoresString.length() - 2).split("}, \\{"));
        ArrayList<HashMap<Integer, Integer>> scoresHashMaps = new ArrayList<HashMap<Integer,Integer>>();
        for(String scoresStringArray : scoresStringArrays)
        {
            HashMap<Integer, Integer> scoreMap = new HashMap<Integer, Integer>();
            List<String> scoresStringList = Arrays.asList(scoresStringArray.split(", "));
            for(String mapping : scoresStringList)
            {
                List<String> mappingStringArray = Arrays.asList(mapping.split("="));
                ArrayList<Integer> mappingIntArray = new ArrayList<Integer>();
                for(String mapString : mappingStringArray)
                {
                    int figure = Integer.parseInt(mapString);
                    mappingIntArray.add(figure);
                }
                scoreMap.put(mappingIntArray.get(0), mappingIntArray.get(1));
            }
            scoresHashMaps.add(scoreMap);
        }
        String possibleScoresString = possibleScoresState;
        ArrayList<HashMap<Integer, Integer>> possibleScoresHashMaps = new ArrayList<HashMap<Integer,Integer>>();
        if(!possibleScoresString.equals("[{}\\]"))
        {
            List<String> possibleScoresStringArrays = Arrays.asList(possibleScoresString.substring(2, possibleScoresString.length() - 2).split("}, \\{"));
            for(String possibleScoresStringArray : possibleScoresStringArrays)
            {
                if(possibleScoresStringArray.contains("{") || possibleScoresStringArray.contains("}"))
                {
                }
                else{
                    HashMap<Integer, Integer> possibleScoreMap = new HashMap<Integer, Integer>();
                    List<String> possibleScoresStringList = Arrays.asList(possibleScoresStringArray.split(", "));
                    for(String mapping : possibleScoresStringList)
                    {
                        List<String> mappingStringArray = Arrays.asList(mapping.split("="));
                        ArrayList<Integer> mappingIntArray = new ArrayList<Integer>();
                        for(String mapString : mappingStringArray)
                        {
                            if(!mapString.equals("")){
                                int figure = Integer.parseInt(mapString);
                                mappingIntArray.add(figure);

                            }
                        }
                        possibleScoreMap.put(mappingIntArray.get(0), mappingIntArray.get(1));
                    }
                    possibleScoresHashMaps.add(possibleScoreMap);
                }
            }
        }
        int freeCategoryIndex = 0;
        int scoresHashMapsIndex =0;
        int possibleScoresHashMapsIndex =0;
        for(Player player : players)
        {
            player.getScorecard().setDetails(freeCategoriesArrays.get(freeCategoryIndex), scoresHashMaps.get(scoresHashMapsIndex));
            player.getScorer().setDetails(possibleScoresHashMaps.get(possibleScoresHashMapsIndex));
            freeCategoryIndex++;
            scoresHashMapsIndex++;
            possibleScoresHashMapsIndex++;
        }
        createSaveString(saveFilename);
        int totalRounds = noOfPlayers*13;
        boolean startPlaying = false;
        boolean continuePlay = false;
        System.out.println("_______________\n");
        System.out.println("Game In Progress");
        System.out.println("_______________");   
        while(roundsTaken < totalRounds){
            for(Player player : players)
            {
                if(startPlaying)
                {
                    continuePlay = true;
                    startPlaying = false;
                }
                if(player == players.get(indexOfContinuingPlayer) && startPlaying == false && continuePlay == false)
                {
                    startPlaying = true;
                }
                if(startPlaying)
                {
                    player.haveContinuedGo(currentDice, currentSection, roundsTaken);
                    roundsTaken++;
                }
                if(continuePlay)
                {
                    player.haveGo(roundsTaken);
                    roundsTaken++;
                }
            }
        }
        finishGame(saveFilename);
    }

    /**
     * RetrieveString input from CLI
     */
    public static String readStringInput() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        return input;
    }

    public static String makeValidString()
    {
        String string = null;
        boolean working = false;
        while(!working)
        {
            try
            {
                boolean valid = false;
                while(!valid)
                {
                    System.out.print(">:");
                    string = readStringInput();
                    if(string.contains(","))
                    {
                        System.out.println("Invalid Character Entered");
                    }
                    else
                    {
                        valid = true;
                    }
                    working = true;
                }
            }
            catch(Exception e)
            {
                System.out.println("Somethings gone wrong here...");
            }
        }
        return string;
    }

    public static int makeValidChoice(int lowerBound, int upperBound)
    {
        boolean madeValidChoice = false;
        int choice = -1;
        while(!madeValidChoice){
            System.out.print(">:");
            boolean madeChoice = false;
            while(!madeChoice){
                try{
                    choice = Integer.parseInt(readStringInput());
                    madeChoice = true;
                }
                catch(Exception e){

                }
            }
            if(choice <= lowerBound || choice >= upperBound)
            {
                System.out.println("Please make a valid choice.");
            }
            else{
                madeValidChoice = true;
            }
        }
        return choice;
    }
}
