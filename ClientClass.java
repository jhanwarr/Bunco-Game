import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//importing the classes from different packages that are required


public class ClientClass 
{
	private static Player[] players;

	/**
	 * The main method for calling other functions and ensuring a smooth flow of operations for the game
	 * @param args The array variable to store the arguments 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		//Objects for taking inputs from the user
		
		BuncoGame play = new BuncoGame();
		boolean samePlayers = false;
		//to check whether the user wants to play with the same players
		
		int countNumGames = 0;
		//number of games played in this run

		infinite: //label for the infinite loop
			while(true)
			{
				//SETUP for this GAME

				if(!samePlayers) //checking if we want new players or not
				{
					play.display("Please enter the number of players: ", false);
					int num = sc.nextInt();

					//validating the number of players
					while( (num > 4) || (num < 1) )
					{
						play.display("Invalid response. Number of players should be more than 0 and less than 4.", true);

						play.display("Enter the number of players: ", false);
						num = sc.nextInt();
					}

					if(num == 1) //setting up a single player game
					{
						players = new Player[2];

						players[0] = new Player(); //storing Computer as a player

						play.display("Enter the name of the player: ", false);
						String name = br.readLine();
						//the name of the player

						//validating the player's name
						while(name.equalsIgnoreCase("Computer"))
						{
							play.display("Players cannot have the same name. Please use a different name.", true);
							play.display("Enter the name of the player: ", false);
							name = sc.nextLine();
						}

						players[1] = new Player(name);
						//adding the new player to the game
						
						setOrder(play);
					}

					else
					{
						players = new Player[num]; //initializing the players array
						setUpPlayers(num, play, br);
					}
				}

				else
				{
					setOrder(play);
					//if the players are the same 
				}


				//PLAYING the GAME Starts here

				for(int i=0; i<6; i++)
				{
					boolean rolledBunco = false; //to check whether a Bunco was rolled or not

					play.display();
					play.display("Round : "+(i+1), true);

					for(Player p: players)
					{
						if(rolledBunco) //if Bunco was rolled, setting other players' score to 0
						{
							p.setRoundScore(i+1, 0);
							continue;
						}

						play.setName(p.getName()); //getting the name of the player playing this turn

						int score = play.playRound(0); //getting the score of this player for this round

						if(score < 0) //this is possible only if a Bunco is rolled
						{
							score *= -1; //getting the actual score of the player in the current round
							rolledBunco = true;
							play.display();

							p.setRoundScore(i+1, score);

							play.display();
							play.display(p.getName()+" scored "+score+" points in round "+(i+1)+".", true);
							play.display(p.getName()+" has a total score of "+p.getScore()+" points.", true);

							play.display("Skipping the rest of the round.", true);
							play.waitForUser();

							continue; //Skipping the rest of the turns in this round
						}

						p.setRoundScore(i+1, score); //updating the round score

						play.display();
						play.display(p.getName()+" scored "+score+" points in round "+(i+1)+".", true);
						play.display(p.getName()+" has a total score of "+p.getScore()+" points.", true);

						play.waitForUser();
					}

					play.display();
					printRank(play, i+1);

					play.incrementRound(); //updating the round
				}


				//ENDING this GAME

				play.setRound(1); //setting the round to be 1 again
				countNumGames++; //incrementing number of games played
				addWin(); //updating the win count of the winning player(s)


				//STARTING a NEW GAME

				play.display();
				play.display("Do you want to play again? ", true);
				play.display("Enter no/any other key: ", false);
				String ans = br.readLine().toLowerCase();

				if(ans.equals("no"))
					break infinite;

				play.display();
				play.display("Do you want to play with the same players? ", true);
				play.display("Enter yes/any other key: ", false);
				ans = br.readLine().toLowerCase();

				if(ans.equals("yes"))
					samePlayers = true;

				else //for printing the statistics if the user doesn't want to play
				{
					play.display();
					play.display("Number of games played: "+countNumGames, true);
					play.display();
					play.display("Player -- Wins", true);
					play.display();

					for(Player p: players)
					{
						play.display(p.getName()+" -- "+p.getWins(), true);
					}

					samePlayers = false;
					play.display();
				}


				for(Player p: players)
				{
					p.setScore(0);
				}
			}

		sc.close();
		br.close();
		//closing the Scanner and BufferedReader Objects that were created

		play.display();
		play.display("Number of games played: "+countNumGames, true);
		play.display();
		play.display("Player -- Wins", true);
		play.display();

		for(Player p: players)
		{
			play.display(p.getName()+" -- "+p.getWins(), true);
		}

		play.display("Thank you! I hope you had fun.", true);
	}


	/**
	 * Setting up the players for a multi-player game
	 * @param num The number of players that will be playing
	 * @param play BuncoGame objects for calling the BuncoGame methods
	 * @param br BufferedReader Object to take user inputs
	 * @throws IOException
	 */
	public static void setUpPlayers(int num, BuncoGame play, BufferedReader br) throws IOException
	{
		for(int i=0; i<num; i++)
		{
			play.display("Enter the name of the player "+(i+1)+": ", false);

			String name = br.readLine();

			while(i!=0 && repeatName(name, i))
			{
				play.display("Players cannot have the same name. Please use a different name.", true);
				play.display("Enter the name of the player "+(i+1)+": ", false);
				name = br.readLine();
			}

			players[i] = new Player(name);
		}

		setOrder(play);
	}

	/**
	 * Checking whether the name has been repeated or not
	 * @param name The name of the player to be checked
	 * @param limit The Players already inserted in the array 
	 * @return boolean variable to confirm whether or not the name is already used
	 */
	public static boolean repeatName(String name, int limit)
	{
		for(int i=0; i<limit; i++)
		{
			if( (players[i].getName()).equalsIgnoreCase(name) )
				return true;
		}

		return false;
	}

	/**
	 * Function to decide the order in which the players will play
	 * @param play BuncoGame objects for calling the BuncoGame methods
	 * @throws IOException
	 */
	public static void setOrder(BuncoGame play) throws IOException
	{
		play.display("To decide who move's first, lets roll the dice.", true);
		play.waitForUser();

		int max = 0, index = 0;

		for(int i=0; i<players.length; i++)
		{
			Player p = players[i];

			int u1 = play.rollOne();
			play.display(p.getName()+" rolled "+u1, true);

			if(u1 > max)
			{
				max = u1;
				index = i;
			}
		}

		Player[] temp = players.clone();

		for(int i=0; i<temp.length; i++)
		{
			if(index == temp.length)
				index = 0;

			players[i] = temp[index];
			index++;
		}

		play.display();
		play.display("The order of players is as follows - ", true);

		for(Player p: players)
		{
			play.display(p.getName(), true);
		}
	}

	/**
	 * Function to increase the win of the player(s) who came first
	 */
	public static void addWin()
	{
		int max = 0;

		for(Player p: players)
		{
			if(p.getScore() > max)
				max = p.getScore();
		}

		for(int i=0; i<players.length; i++)
		{
			if(players[i].getScore() == max)
				players[i].incrementWins();
		}
	}

	/**
	 * printing the ranks of the player after each round based on their score
	 * @param play BuncoGame objects for calling the BuncoGame methods
	 * @param round The last round number
	 */
	public static void printRank(BuncoGame play, int round)
	{
		ScoreCompare checkScore = new ScoreCompare();

		Player[] temp = players.clone();
		List<Player> list = Arrays.asList(temp);

		Collections.sort(list, checkScore);
		int rank = 1;

		play.display("Round - "+round, true);
		play.display();

		play.display("Rank\t Name - Score", true);
		play.display();

		for(Player p: list)
		{
			play.display(rank+"\t "+p.getName()+" - "+p.getScore(), true);
			rank++;
		}
	}
}
