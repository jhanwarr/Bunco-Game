import java.util.Comparator;

public class ScoreCompare implements Comparator<Player>
{
	/**
	 * Function to compare the scores of Two Player objects according to their total scores
	 */
	public int compare(Player p1, Player p2)
	{
		if(p1.getScore() < p2.getScore())
			return 1;
		
		else if(p1.getScore() > p2.getScore())
			return -1;
		
		else
			return 0;
	}
}
