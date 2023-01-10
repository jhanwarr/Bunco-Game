import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BuncoGame 
{
	private int round;
	private String name;
	private DiceCup cup;

	/**
	 * The constructor to initialize the instance variables
	 */
	public BuncoGame()
	{
		name = "";
		round = 1;
		cup = new DiceCup();
	}

	/**
	 * Getting the name of the current player
	 * @param s The name of the current player
	 */
	public void setName(String s)
	{
		name = s;
	}

	/**
	 * 
	 * @return The round number
	 */
	public int getRound()
	{
		return round;
	}

	/**
	 * Setter Method for round
	 * @param n The round number required to be assigned
	 */
	public void setRound(int n)
	{
		round = n;
	}

	/**
	 * Incrementing the value of round by 1
	 */
	public void incrementRound()
	{
		round++;
	}

	/**
	 * Checking whether the dices rolled a Bunco!
	 * @param arr Array containing the face values of the three dices
	 * @return boolean variable informing whether the player rolled a Bunco or not
	 */
	public boolean isABunco(int[] arr)
	{
		if( (arr[2] == arr[1]) && (arr[1] == arr[0]) && (arr[0] == round) )
		{
			display(name+" rolled a Bunco!", true);
			return true;
		}

		return false;
	}

	/**
	 * Checking whether the dices rolled a Mini Bunco!
	 * @param arr Array containing the face values of the three dices
	 * @return boolean variable informing whether the player rolled a Mini Bunco or not
	 */
	public boolean isAMiniBunco(int[] arr)
	{
		if( (arr[2] == arr[1]) && (arr[1] == arr[0]) )
		{
			display(name+" rolled a MiniBunco!", true);
			return true;
		}

		return false;
	}

	/**
	 * Method to create a round for each player
	 * @param score The score of the given player in this turn
	 * @return The score for this turn after the last roll
	 */
	public int playRound(int score) throws IOException
	{
		display();
		display("In this round, the score for "+name+" = "+score, true);

		waitForUser();
		

		int[] arr = cup.roll();
		//getting the face value of the three dices
		display(name+" rolled "+arr[0]+"-"+arr[1]+"-"+arr[2], true);

		if(isABunco(arr))
		{
			return ((score + 21) * -1);
		}

		else if(isAMiniBunco(arr))
		{
			return playRound(score+5);
		}

		else
		{
			int check = 0;

			if(arr[0] == round)
				check++;

			if(arr[1] == round)
				check++;

			if(arr[2] == round)
				check++;


			if(check == 0)
				return score;

			return playRound(score+check);
		}
	}

	/**
	 * Overloaded method to print blank lines
	 */
	public void display()
	{
		System.out.println();
	}

	/**
	 * Overloaded method to print the given message
	 * @param s Message to be printed
	 * @param lineFeed Variable informing whether line feed is required after printing the message or not
	 */
	public void display(String s, boolean lineFeed)
	{
		if(lineFeed)
			System.out.println(s);

		else
			System.out.print(s);
	}

	/**
	 * Method to wait for the user until ENTER is pressed
	 */
	public void waitForUser() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		display();
		display("Please press ENTER to continue...", true);
		br.readLine();
	}
	
	/**
	 * Method to roll only one dice
	 * @return the face number of the dice
	 */
	public int rollOne()
	{
		return cup.rollOne();
	}
}
