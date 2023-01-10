import java.util.Arrays;

public class Player implements Comparable<Player>
{
	private String name;
	private int[] scores;
	private int countWins;
	
	
	/**
	 * Constructor to initialize the instance variables
	 */
	public Player()
	{
		this.name = "Computer";
		scores = new int[6];
		countWins = 0;
	}
	
	/**
	 * Constructor to initialize the instance variables
	 * @param name The name of the player
	 */
	public Player(String name)
	{
		this.name = name;
		scores = new int[6];
		countWins = 0;
	}
	
	/**
	 * Method to return the name of the current player
	 * @return The name of the current player
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Method to send the score of the current player
	 * @return The total score of the current player
	 */
	public int getScore()
	{
		int sum = 0;
		
		for(int score: scores)
			sum+=score;
		
		return sum;
	}
	
	/**
	 * Setter method for score array
	 * @param n The variable which has to be filled at every index of scores
	 */
	public void setScore(int n)
	{
		Arrays.fill(scores, n);
	}
	
	/**
	 * Getter Method for countWins
	 * @return countWins value
	 */
	public int getWins()
	{
		return countWins;
	}
	
	/**
	 * Setter Method for countWins
	 * @param wins The value required to be assigned to countWins
	 */
	public void setWins(int wins)
	{
		countWins = wins;
	}
	
	/**
	 * incrementing countWins by 1
	 */
	public void incrementWins()
	{
		countWins++;
	}
	
	/**
	 * Method to return the score of current player in any given round
	 * @param round the round who's score is desired
	 * @return the score in the given round
	 */
	public int getRoundScore(int round)
	{
		return scores[round];
	}
	
	/**
	 * Adding the score of the given round in the scores array
	 * @param round the last round's number
	 * @param score the score of the current player in the given round
	 */
	public void setRoundScore(int round, int score)
	{
		scores[round-1] = score;
	}
	
	/**
	 * The toString() method for printing the object details
	 */
	public String toString()
	{
		return name+" has "+this.getScore()+" points.";
	}

	/**
	 * compareTo() method which helps us to compare two Player objects according to their names
	 */
	@Override
	public int compareTo(Player obj) 
	{
		return name.compareTo(obj.name);
	}
}
