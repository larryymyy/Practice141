# Practice141
Practice for CS141
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
   Simulates pickup stone game 
   CS141 S15 0535
   @since 04/19/2015
   @version 1
 */
public class PickupGameV1
{ 
   public static void main(String[] args)
   {
      Scanner console = new Scanner(System.in);
      
      PickupGameV1 game = new PickupGameV1();
      game.play(console);
      
      System.out.println(game.toString());
   }
public enum PlayMode
   {
      Random,
      Smart,
      Alternating,
      None;
      
      //string of the mode.
      
      public String toString()
      {
         String playmodeStr = "";
         
         if (this == PlayMode.Random)
         {
            playmodeStr = Random_STRING;
         }
         else if (this == PlayMode.Smart)
         {
            playmodeStr = SMART_STRING;
         }
         else if (this == PlayMode.Alternating)
         {
            playmodeStr = ALTERNATING_STRING;
         }
         
         return playmodeStr;
      }
      
      private static final String Random_STRING = "Random, Mode";
      private static final String SMART_STRING = "Smart Mode";
      private static final String ALTERNATING_STRING = "Alternating Mode";
   }
   
   private static final int Random_CODE = 0;
   private static final int SMART_CODE = 1;
   private static final int ALTERNATING_CODE = 2;
   
   private int initialStones;
   private int currentStones;
   
   private PlayMode initialPlaymode;
   private PlayMode currentPlaymode;
   
   private boolean randomizeInitialStones;
   private boolean randomizeInitialPlayMode;
   
   private int randomCount;
   private int smartCount;
   private int alternatingCount;
   
   private int computerRandomWin;
   private int computerSmartWin;
   private int computerAlternatingWin;
   
   private int humanRandomWin;
   private int humanSmartWin;
   private int humanAlternatingWin;
   
   private int turn;
   
   private String history;
   
   private static final int MAX_INITIAL_STONES = 55;
   private static final int MIN_INITIAL_STONES = 8;
   private static final int CHOOSE_RANDOM_INITIAL_STONES = 0;
   
   
   private static final int MIN_STONE_PICKUP = 1;
   
   private static final int COMPUTER_TURN = 0;
   private static final int HUMAN_TURN = 1;
   
   private static final String YES_REPLY = "Y";
   private static final String NO_REPLY = "N";
   private static final String INVALID_INPUT_TRY_AGAIN
      = "Invalid input.";
   private static final String GOODBYE_HUMAN
      = "Good Bye.";
   
   private static final String REPLAY_PROMPT
      = "Play again? (y/n)";
      
   private static final String NEWLINE = "\n";
      
   private static final String STONES_HUMAN_REMOVED_HISTORY
      = "Stones human removed: ";
   private static final String STONES_HUMAN_REMOVED_ECHO
      = "Stones you removed: ";
   private static final String REMOVE_STONES_PROMPT
      = "How many stones you want to remove?";
   private static final String STONES_COMPUTER_REMOVED
      = "Stones computer removed: ";
      
   private static final String STONES_IN_PILE_ECHO
      = "Stones remaining: ";
   private static final String STONES_HUMAN_HAS_ECHO
      = "Your Stones: ";
   private static final String STONES_COMPUTER_HAS_ECHO
      = "Computer stones: ";
      
   private static final String STONE_ECHO = "*";
   private static final String TWO_STONES_ECHO
      = STONE_ECHO + STONE_ECHO + " ";
   private static final int TWO_STONE_COLUMNS
      = 4;
   private static final int STONE_ROW_LENGTH
      =  TWO_STONE_COLUMNS * TWO_STONES_ECHO.length();
      
   private static final String INITAL_STONES_HISTORY
      = "Stones game started with: ";
   private static final String INITAL_MODE_HISTORY
      = "Play mode game started with: ";
      
   private static final String CURRENT_MODE_HISTORY
      = "Current play mode: ";
      
   private static final String HUMAN_WIN_HISTORY
      = "Human won.";
   
   private static final String HUMAN_WIN_ECHO
      = "You win this time game.";
      
   private static final String COMPUTER_WIN_HISTORY
      = "Computer won.";
   
   private static final String COMPUTER_WIN_ECHO
      = "Computer won.";
      
   private static final String PICKUP_UNDER_MIN
      = "You need pick up at least 1 stone.";
      
   private static final String PICKUP_OVER_MAX
      = "You cannot pick up more than half the stones";
      
   /**
      Random number of stones and play mode.
      The number of stones from 8 to 55. any mode
    */
   public PickupGameV1()
   {  
      this(CHOOSE_RANDOM_INITIAL_STONES);
   }
   public PickupGameV1(int initialStones)
   {
      this(initialStones, PlayMode.None);
   }
   public PickupGameV1(int initialStones, PlayMode playmode)
   {
      setStones(initialStones);
      configureStones();
      
      setPlayMode(playmode);
      configurePlayMode();
      
      randomCount = 0;
      smartCount = 0;
      alternatingCount = 0;
      
      computerRandomWin = 0;
      computerSmartWin = 0;
      computerAlternatingWin = 0;
      
      humanRandomWin = 0;
      humanSmartWin = 0;
      humanAlternatingWin = 0;
      
      // initialize = 0
      // coin will be tossed before match
      turn = Coin.toss();
      
      history = "";
   }
   
   /**
      computer play mode choice.
     */
   public void setPlayMode(PlayMode playmode)
   {
      initialPlaymode = playmode;
      if(playmode == PlayMode.None)
      {
         randomizeInitialPlayMode = true;
      }
      else
      {
         randomizeInitialPlayMode = false;
      }
   }
   
   /**
      computer play mide choice
    */
   public PlayMode getPlayMode()
   {
      return initialPlaymode;
   }
   
   /**
      stones numbers
      range from 8 to 55.
    */
   public void setStones(int stones)
   {
      if(inRange(stones, MIN_INITIAL_STONES, MAX_INITIAL_STONES))
      {
         initialStones = stones;
      }
      
      if(initialStones == 0)
      {
         randomizeInitialStones = true;
      }
      else
      {
         randomizeInitialStones = false;
      }
   }
   
   /**
      How many stones to play.
    */
   public int getStones()
   {
      return initialStones;
   }
   
   /**
      Total number of games that played.
    */
   public int getTotalGames()
   {
      return randomCount
             + smartCount
             + alternatingCount;
            
   }
   
   /**
      Total number that computer wins.
          */
   public int getComputerWins()
   {
      return computerRandomWin
             + computerSmartWin
             + computerAlternatingWin;
   }
   
   /**
     Computer wins in random mode.
     */
   public int getComputerRandomWins()
   {
      return computerRandomWin;
   }
   
   /**
      Computer wins in smart mode.
    */
   public int getComputerSmartWins()
   {
      return computerSmartWin;
   }
   
   /**
      Get the number of computer wins in alternating mode.
      @return The number of computer wins in alternating mode.
    */
   public int getComputerAlternatingWins()
   {
      return computerAlternatingWin;
   }
   
   /**
      Get the total number of human wins.
      @return The total number of human wins.
    */
   public int getHumanWins()
   {
      return humanRandomWin
             + humanSmartWin
             + humanAlternatingWin;
   }
   
   /**
      Get the number of human wins in random mode.
      @return The number of human wins in random mode.
    */
   public int getHumanRandomWins()
   {
      return humanRandomWin;
   }
   
   /**
      Get the number of human wins in smart mode.
      @return The number of human wins in smart mode.
    */
   public int getHumanSmartWins()
   {
      return humanSmartWin;
   }
   
   /**
      Get the number of human wins in alternating mode.
      @return The number of human wins in alternating mode.
    */
   public int getHumanAlternatingWins()
   {
      return humanAlternatingWin;
   }
   
   /**
      Get the percentage of computer wins.
      @return The percentage of computer wins,
              negative 1 when blank.
    */
   public double getComputerWinPercentage()
   {
      if(getTotalGames() == 0) return -1;
      
      return (double) getComputerWins() / getTotalGames();
   }
   
   /**
      Get the percentage of computer wins in random mode.
      @return The percentage of computer wins in random mode,
              negative 1 when blank.
    */
   public double getComputerRandomWinPercentage()
   {
      if(randomCount == 0) return -1;
      return (double) computerRandomWin / randomCount;
   }
   
   /**
      Get the percentage of computer wins in smart mode.
      @return The percentage of computer wins in smart mode,
              negative 1 when blank.
    */
   public double getComputerSmartWinPercentage()
   {
      if(smartCount == 0) return -1;
      return (double) computerSmartWin / smartCount;
   }
   
   /**
      Get the percentage of computer wins in alternating mode.
      @return The percentage of computer wins in alternating mode,
              negative 1 when blank.
    */
   public double getComputerAlternatingWinPercentage()
   {
      if(alternatingCount == 0) return -1;
      return (double) computerAlternatingWin / alternatingCount;
   }
   
   /**
      Get the percentage of human wins.
      @return The percentage of human wins,
              negative 1 when blank.
    */
   public double getHumanWinPercentage()
   {
      if(getTotalGames() == 0) return -1;
      
      return (double) getHumanWins() / getTotalGames();
   }
   
   /**
      Get the percentage of human wins in random mode.
      @return The percentage of human wins in random mode,
              negative 1 when blank.
    */
   public double getHumanRandomWinPercentage()
   {
      if(randomCount == 0) return -1;
      
      return (double) humanRandomWin / randomCount;
   }
   
   /**
      Get the percentage of human wins in smart mode.
      @return The percentage of human wins in smart mode,
              negative 1 when blank.
    */
   public double getHumanSmartWinPercentage()
   {
      if(smartCount == 0) return -1;
      
      return (double) humanSmartWin / smartCount;
   }
   
   /**
      Get the percentage of human wins in alternating mode.
      @return The percentage of human wins in alternating mode,
              negative 1 when blank.
    */
   public double getHumanAlternatingWinPercentage()
   {
      if(alternatingCount == 0) return -1;
      
      return (double) humanAlternatingWin / alternatingCount;
   }
   
   /**
      Play a game from start to finish.
      Usually, along with a constructor call, this method
      is all that is ever needed to run a game of pickup.
      @param in The scanner that recieves user input.
    */
   public void play(Scanner in)
   {
      boolean replay = false;
      
      do
      {
         startMatch();
         
         while(currentStones > 0)
         {
            System.out.print(STONES_IN_PILE_ECHO + currentStones);
            nextTurn(in);
         }
         
         endMatch();
         
         replay = promptReplay(in);
      } while(replay);
      
      System.out.println(GOODBYE_HUMAN);
   }
   
   
   /**
      Sets up the start of the match.
    */   
   private void startMatch()
   {  
      switch(initialPlaymode)
      {
         case Random:
            randomCount++;
            break;
         case Smart:
            smartCount++;
            break;
         case Alternating:
            alternatingCount++;
         default:
      }
      
      history += NEWLINE + INITAL_STONES_HISTORY + initialStones;
      history += NEWLINE + INITAL_MODE_HISTORY + initialPlaymode.toString();
   }
   
   /**
      Cleans up the last match, and sets up for another.
    */
   private void endMatch()
   {
      // Decide who goes first.
      turn = Coin.toss();
      // Reconfigure the number of stones
      configureStones();
      // Reconfigure the play mode.
      configurePlayMode();
   }
   
   /**
      Configures the stones based on the information
      given when the stones were set.
    */
   private void configureStones()
   {
      if(randomizeInitialStones)
      {
         initialStones = pickRandomInitialStones();
         currentStones = initialStones;
      }
      else
      {
         currentStones = initialStones;
      }
   }
   
   /**
      Configures the play mode based on the information
      given when the play mode was set.
    */   
   private void configurePlayMode()
   {
      if(randomizeInitialPlayMode == true)
      {
         initialPlaymode = pickRandomPlayMode();
      }
      
      if(initialPlaymode == PlayMode.Alternating)
      {
         currentPlaymode = pickRandomAlternatePlayMode();
      }
      else
      {
         currentPlaymode = initialPlaymode;
      }
   }
   
   /**
      Prompt the user to play again.
    */
   private boolean promptReplay(Scanner in)
   {
      // Loop until a valid input is given
      while(true)
      {
         System.out.println(REPLAY_PROMPT);
         
         String answerStr = "";
         
         try
         {
            answerStr = in.next();
         }
         catch(NoSuchElementException e)
         {
            System.out.println(GOODBYE_HUMAN);
            System.exit(0);
         }
         catch(IllegalStateException e)
         {
            System.out.println(GOODBYE_HUMAN);
            System.exit(0);
         }
         
         // sanitize the input
         answerStr = answerStr.trim().substring(0,1).toUpperCase();
         
         if (answerStr.equals(YES_REPLY))
         {
            return true;
         }
         else if (answerStr.equals(NO_REPLY))
         {
            return false;
         }
         else
         {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
         }
      }
   }
   
   /**
      Performs the next turn.
      @param in Scanner to get user input from.
    */
   private void nextTurn(Scanner in)
   {
      if(turn == COMPUTER_TURN)
      {
         computerTurn();
         turn = HUMAN_TURN;
      }
      else if (turn == HUMAN_TURN)
      {
         humanTurn(in);
         turn = COMPUTER_TURN;
      }
   }
   
   /**
      Performs one turn for the computer.
      Decides between random, smart, and alternating.
    */
   private void computerTurn()
   {
      int stones = 0;
      
      switch(initialPlaymode)
      {
         case Random:
            stones = computerRandomTurn();
            break;
         case Smart:
            stones = computerSmartTurn();
            break;
         case Alternating:
            stones = computerAlternatingTurn();
            break;
         default:
            return;
      }
      
      currentStones -= stones;
      history += NEWLINE + STONES_COMPUTER_REMOVED + stones;
      System.out.println(NEWLINE + STONES_COMPUTER_REMOVED + stones);
      
      if(currentStones == 0)
      {
         lose();
      }
   }
   
   /**
      Perform a random turn.
      In the random turn, the computer randomly picks
      a legal amount of stones to remove from the pile.
    */
   private int computerRandomTurn()
   {
      return pickRandomLegalMove();
   }
   
   /**
      Perform a smart turn for the computer.
      In the smart turn, the computer attempts
      to make the pile size a power of two
      minus 1. If the size of the pile is already
      that, then it's not possible and the computer
      performs a random legal move instead.
    */
   private int computerSmartTurn()
   {
      int targetStones;
      int power = 5;
      final int base = 2;
      final int offset = 1;
      do
      {
         targetStones = (int)Math.pow(base, power) - offset;
         power--;
      }
      while(targetStones > currentStones);
      
      // The stones to pick up is the difference
      // between the current and the target.
      int stones = currentStones - targetStones;
      
      // When there are no stones to pick up,
      // then pick a random legal move.
      if(stones == 0)
      {
         stones = pickRandomLegalMove();
      }
      
      return stones;
   }
   
   /**
      Computer plays an alternating turn, afterwards changing
      the mode to Random when Smart and vice versa.
    */
   private int computerAlternatingTurn()
   {
      int stones = 0;
      
      if(currentPlaymode == PlayMode.Smart)
      {
         stones = computerSmartTurn();
      }
      else
      {
         stones = computerRandomTurn();
      }
      
      switchModes();
      
      return stones;
   }
   
   /**
      Changes a smart mode to random,
      and a random mode to smart.
    */
   private void switchModes()
   {
      if(currentPlaymode == PlayMode.Smart)
      {
         currentPlaymode = PlayMode.Random;
      }
      else
      {
         currentPlaymode = PlayMode.Smart;
      }
      
      history += NEWLINE + CURRENT_MODE_HISTORY + currentPlaymode.toString();
   }
   
   /**
      Perform the human's turn.
      @param in The Scanner to recieve input for the turn.
    */
   private void humanTurn(Scanner in)
   {  
      boolean turnOver = false;
      while(!turnOver)
      {
         System.out.println(NEWLINE + REMOVE_STONES_PROMPT);
         
         int stones = 0;
         
         try
         {
            stones = in.nextInt();
            turnOver = pickupStones(stones);
         }
         catch(InputMismatchException e)
         {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            // needs to skip the invalid input
            in.next();
         }
         catch(NoSuchElementException e)
         {
            System.out.println(GOODBYE_HUMAN);
            // needs to skip the invalid input
            System.exit(0);
         }
         catch(IllegalStateException e)
         {
            System.out.println(GOODBYE_HUMAN);
            System.exit(0);
         }
         
         if(turnOver)
         {
            history += NEWLINE + STONES_HUMAN_REMOVED_HISTORY + stones;
            System.out.println(STONES_HUMAN_REMOVED_ECHO + stones);
         }
      }
      
      if(currentStones == 0)
      {
         lose();
      }
   }
   
   /**
      Decides the loser.
      Called when the last stone is removed
      by whoever picked it up.
    */
   public void lose()
   {
      if(turn == HUMAN_TURN)
      {
         computerWin();
      }
      else
      {
         humanWin();
      }
   }
   
   
   /**
      Give the computer a win.
    */
   private void computerWin()
   {
      switch(initialPlaymode)
      {
         case Random:
            computerRandomWin++;
            break;
         case Smart:
            computerSmartWin++;
            break;
         case Alternating:
            computerAlternatingWin++;
            break;
         default:
            return;
      }
      
      history += NEWLINE + COMPUTER_WIN_HISTORY;
      System.out.println(COMPUTER_WIN_ECHO);
   }
   
   /**
      Give the human a win.
    */
   private void humanWin()
   {
      switch(initialPlaymode)
      {
         case Random:
            humanRandomWin++;
            break;
         case Smart:
            humanSmartWin++;
            break;
         case Alternating:
            humanAlternatingWin++;
            break;
         default:
            return;
      }
      
      history += NEWLINE + HUMAN_WIN_HISTORY;
      System.out.println(HUMAN_WIN_ECHO);
   }
   
   /**
      Pick up the given number of stones.
      Insures the stones are between 1 and
      half the current number of stones,
      Echos to the console when not in range,
      and returns false.
      @param stones Number of stones to pick up.
      @return True when stones are picked up,
              false when no stones are picked up.
    */
   public boolean pickupStones(int stones)
   {
      int halfStonesLeft = currentStones / 2;
      if(currentStones == 1)
      {
         halfStonesLeft = 1;
      }
   
      if(stones < MIN_STONE_PICKUP)
      {
         System.out.println(PICKUP_UNDER_MIN);
         return false;
      }
      
      if(stones > halfStonesLeft)
      {
         System.out.println(PICKUP_OVER_MAX);
         return false;
      }
      
      currentStones -= stones;
      return true;
   }
   
   
   /**
      Prints out the current history to the command line.
    */
   public void printHistory()
   {
      System.out.println(history);
   }
   
   /**
      Gets the current history.
      @return The current history.
    */
   public String getHistory()
   {
      return history;
   }
   
   /**
      Picks a random number of initial stones.
      The value can range from 8 to 55.
      @return Random legal number of stones to begin the game with.
    */
   private static int pickRandomInitialStones()
   {
      return pickRandomNumberInRange(MIN_INITIAL_STONES, MAX_INITIAL_STONES);
   }
   
   /**
      Picks a random legal number of stones to remove.
      Can be from 1 to half the number of stones left.
      @return Random legal number of stones to remove.
    */
   private int pickRandomLegalMove()
   {
      // When only one stone is left,
      // the only legal move is to pick up that stone.
      if(currentStones == MIN_STONE_PICKUP)
      {
         return MIN_STONE_PICKUP;
      }
      
      int halfCurrentStones = currentStones / 2;
      
      return pickRandomNumberInRange(MIN_STONE_PICKUP, halfCurrentStones);
   }
   
   /**
      Picks a random play mode. Can be any of the three play modes.
      @return One of the three play modes, chosen randomly.
    */
   private static PlayMode pickRandomPlayMode()
   {
      Random gen = new Random();
      int modeValue = gen.nextInt(3);
      
      PlayMode randomMode;
      switch(modeValue)
      {
         case Random_CODE:
            randomMode = PlayMode.Random;
            break;
         case SMART_CODE:
            randomMode = PlayMode.Smart;
            break;
         case ALTERNATING_CODE:
            randomMode = PlayMode.Alternating;
            break;
         default:
            return null;
      }
      
      return randomMode;
   }
   
   /**
      Randomly picks between Smart and Random mode.
      Intended for use during the initialization
      of a game in alternate mode.
      @return Randomly chooses between PlayMode.Smart and PlayMode.Random.
    */
   private static PlayMode pickRandomAlternatePlayMode()
   {
      if(Coin.toss() == Coin.HEADS)
      {
         return PlayMode.Smart;
      }
      
      return PlayMode.Random;
   }
   
   /**
      Picks a random number in the given range, inclusively.
      @param min The lowest number in the range.
      @param max The highest number in the range.
      @exception OutOfRangeException Min must be higher than max.
    */
   private static int pickRandomNumberInRange(int min, int max)
   {
      Random gen = new Random();
      int genMax = max - min + 1;
      int stones = gen.nextInt(genMax) + min;
      
      return stones;
   }
   
   /**
      Tests whether a give value is inside the given range.
      @param x The value to test.
      @param a The start of the range.
      @param b The end of the range.
      @return True when in range, false otherwise.
    */
   private static boolean inRange(int x, int a, int b)
   {
      // When a is less than b
      if(a < b)
      {
         return ((a <= x) && (x <= b));
      }
      
      // When b is less than a.
      return ((b <= x) && (x <= a));
   }
   
   private final static String PERCENT_FORMAT = "%.2f%%";
   
   private final static String HUMAN_PERCENT_WIN
      = "Percent won by human: ";
   private final static String HUMAN_STUPID_PERCENT_WIN
      = "Percent random mode won by human: ";
   private final static String HUMAN_SMART_PERCENT_WIN
      = "Percent smart mode won by human: ";
   private final static String HUMAN_ALTERNATING_PERCENT_WIN
      = "Percent alternating mode won by human: ";
      
   private final static String COMPUTER_PERCENT_WIN
      = "Percent won by computer: ";
   private final static String COMPUTER_STUPID_PERCENT_WIN
      = "Percent random mode won by computer ";
   private final static String COMPUTER_SMART_PERCENT_WIN
      = "Percent smart mode won by computer: ";
   private final static String COMPUTER_ALTERNATING_PERCENT_WIN
      = "Percent alternating mode won by computer: ";
      
   private final static String INITIAL_STONES_STR
      = "Initial Stones: ";
   private final static String CURRENT_STONES_STR
      = "Current stones: ";
   
   private final static String INITIAL_PLAYMODE_STR
      = "Initial play mode: ";
   private final static String CURRENT_PLAYMODE_STR
      = "Current play mode: ";
   
   private final static String RANDOMIZE_INITIAL_PLAYMODE_STR
      = "Randomize play mode: ";
   private final static String RANDOMIZE_INITIAL_STONES_STR
      = "Randomize initial stones: ";
   
   private final static String STUPID_COUNT_STR
      = "Games played on random mode: ";
   private final static String SMART_COUNT_STR
      = "Games played on smart mode: ";
   private final static String ALTERNATING_COUNT_STR
      = "Games played on alternating mode: ";
   
   private final static String COMPUTER_STUPID_WIN_STR
      = "Computer wins on random mode: ";
   private final static String COMPUTER_SMART_WIN_STR
      = "Computer wins on smart mode: ";
   private final static String COMPUTER_ALTERNATING_WIN_STR
      = "Computer wins on alternating mode: ";
   
   private final static String HUMAN_STUPID_WIN_STR
      = "Human wins on random mode: ";
   private final static String HUMAN_SMART_WIN_STR
      = "Human wins on smart mode: ";
   private final static String HUMAN_ALTERNATING_WIN_STR
      = "Human wins on alternating mode: ";
     
   private final static String NEXT_TURN_STR
      = "First turn next game: ";
   
   private final static String HUMAN_STR = "Human";
   private final static String COMPUTER_STR = "Computer";
   
   private final static String HISTORY_STR
      = NEWLINE + "History:";
      
   private final static String BLANK = " - ";
   
   /**
      Returns a formatted string representing a PickupGame object.
      Details the instance variables, history, and win percents.
      @return The formatted string representing a
              PickupGame object.
    */
   @Override
   public String toString()
   {
      
      String output = "";
      
      output += INITIAL_STONES_STR + initialStones + NEWLINE;
      
      output += CURRENT_STONES_STR + currentStones + NEWLINE;
      output += INITIAL_PLAYMODE_STR + initialPlaymode.toString() + NEWLINE;
      output += CURRENT_PLAYMODE_STR + currentPlaymode.toString() + NEWLINE;
      output += RANDOMIZE_INITIAL_PLAYMODE_STR + randomizeInitialPlayMode + NEWLINE;
      output += RANDOMIZE_INITIAL_STONES_STR + randomizeInitialStones + NEWLINE;
      output += STUPID_COUNT_STR + randomCount + NEWLINE;
      output += SMART_COUNT_STR + smartCount + NEWLINE;
      output += ALTERNATING_COUNT_STR + alternatingCount + NEWLINE;
      output += COMPUTER_STUPID_WIN_STR + computerRandomWin + NEWLINE;
      output += COMPUTER_SMART_WIN_STR + computerSmartWin + NEWLINE;
      output += COMPUTER_ALTERNATING_WIN_STR + computerAlternatingWin + NEWLINE;
      output += HUMAN_STUPID_WIN_STR + humanRandomWin + NEWLINE;
      output += HUMAN_SMART_WIN_STR + humanSmartWin + NEWLINE;
      output += HUMAN_ALTERNATING_WIN_STR + humanAlternatingWin + NEWLINE;
      
      output += NEXT_TURN_STR
             + (turn == COMPUTER_TURN ? COMPUTER_STR : HUMAN_STR)
             + NEWLINE;
      
      output += HISTORY_STR + history + NEWLINE + NEWLINE;
      
      output += formatWinPercent(HUMAN_PERCENT_WIN,
                                 getHumanWinPercentage());
      output += formatWinPercent(HUMAN_STUPID_PERCENT_WIN,
                                 getHumanRandomWinPercentage());
      output += formatWinPercent(HUMAN_SMART_PERCENT_WIN,
                                 getHumanSmartWinPercentage());
      output += formatWinPercent(HUMAN_ALTERNATING_PERCENT_WIN,
                                 getHumanAlternatingWinPercentage());
      
      
      output += formatWinPercent(COMPUTER_PERCENT_WIN,
                                 getComputerWinPercentage());
      output += formatWinPercent(COMPUTER_STUPID_PERCENT_WIN,
                                 getComputerRandomWinPercentage());
      output += formatWinPercent(COMPUTER_SMART_PERCENT_WIN,
                                 getComputerSmartWinPercentage());
      output += formatWinPercent(COMPUTER_ALTERNATING_PERCENT_WIN,
                                 getComputerAlternatingWinPercentage());
      
      
      return output;
   }
   
   private final static double PERCENT_FACTOR = 100.0;
   
   /**
      Takes a label and a percentage, and create an output.
      The percentage is formatted to two decimal places.
      @param label The label of the output.
      @param percent The percentage to output, in decimal form.
      @return Formatted string for displaying a win percent.
    */
   private static String formatWinPercent(String label, double percent)
   {
      String output = "";
      output += label;
      output += (percent != -1)
                  ? String.format(PERCENT_FORMAT, percent*PERCENT_FACTOR)
                  : BLANK;
      output += NEWLINE;
      
      return output;
   }
}
