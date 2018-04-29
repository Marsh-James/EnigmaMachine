/**
 * Turnover rotor inherits from Basic rotor as it has all the same characteristics...
 * ...however has a recursive function that rotates other rotors if the conditions are met
 */
public class TurnoverRotor extends BasicRotor {

	private int turnoverPosition;
	private TurnoverRotor nextRotor;
	
	/**
	 * Constructor calls relevant superclass and applies normal arguments
	 * @param rotorType variable is passed in to for fill the super constructor arguments
	 * @param rightRotor variable is used for the recursive rotate function unique to this class
	 */
	public TurnoverRotor(String rotorType, TurnoverRotor rightRotor) {
		super(rotorType);
		
		//Sets the predefined positions of when the turnover rotor can turnover
		switch (rotorType.toUpperCase()) {
			case "I" : 
				turnoverPosition = 24;
				break;
			case "II" : 
				turnoverPosition = 12;
				break;
			case "III" : 
				turnoverPosition = 3;
				break;
			case "IV" : 
				turnoverPosition = 17;
				break;
			case "V" : 
				turnoverPosition = 7;
				break;
		}
		
		//Assigns the right most rotor to this, passed in as method argument
		this.nextRotor = rightRotor;
	}
	
	//Recursive function that rotates the next rotor if the current rotor is able to turnover
	public void rotateTurnover() {

		//Checks if the position of the rotor is at the point of turnover 
		if (this.position == this.turnoverPosition) {
			
			//If there exists a right rotor, otherwise if there isn't nothing can be turned over
			if (nextRotor != null) {
				//Rotates the next rotor and allows it to check if the next rotor is able to turnover
				nextRotor.rotate();
				nextRotor.rotateTurnover();
			}
		}
	}
	
	//Setter method sets the rightmost rotor for the specific rotor
	public void setNextRotor(TurnoverRotor rightRotor) {
		this.nextRotor = rightRotor;
	}
}
