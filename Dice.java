
public class Dice 
{
	private int dice_name;
	
	
	/**
	 * Constructor for initializing the dice number for the current object
	 * @param num The dice number of the current object 
	 */
	public Dice(int num)
	{
		this.dice_name = num;
	}
	
	
	/**
	 * Method to get the current dice's name
	 * @return name of the current dice object
	 */
	public int getDiceNumber()
	{
		return dice_name;
	}
	
	
	/**
	 * Method to generate a number which a dice can give
	 * @return A number between 1 to 6 (both inclusive)
	 */
	public int getNum()
	{
		int rand = (int)(Math.random() * 6 + 1);
		return rand;
	}
}
