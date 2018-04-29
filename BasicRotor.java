/**
 * Class provides the mappings and functions for a BasicRotor
 * This includes the functionality to setup any Basic rotor as well as...
 *  ... apply two separate mapping functions and increment its position by rotation
 */
public class BasicRotor extends Rotor{

	/*
	 * BasicRotor mappings are provided as protected so all subs classes can access these variables
	 * So that they do not need to be redeclared
	 */
	protected int[] mappingI = {4, 10, 12, 5, 11, 6, 3, 16, 21, 25, 13, 19, 14, 22, 24, 7, 23, 20, 18, 15, 0, 8, 1, 17, 2, 9};
	protected int[] mappingII = {0, 9, 3, 10, 18, 8, 17, 20, 23, 1, 11, 7, 22, 19, 12, 2, 16, 6, 25, 13, 15, 24, 5, 21, 14, 4};
	protected int[] mappingIII = {1, 3, 5, 7, 9, 11, 2, 15, 17, 19, 23, 21, 25, 13, 24, 4, 8, 22, 6, 0, 10, 12, 20, 18, 16, 14};
	protected int[] mappingIV = {4, 18, 14, 21, 15, 25, 9, 0, 24, 16, 20, 8, 17, 7, 23, 11, 13, 5, 19, 6, 10, 3, 2, 12, 22, 1};
	protected int[] mappingV = {21, 25, 1, 17, 6, 8, 19, 24, 20, 15, 18, 3, 13, 7, 11, 23, 0, 22, 12, 9, 16, 14, 5, 4, 2, 10};
	
	//Method standardises the user input and passes it on to be mapped
	public BasicRotor(String rotorType) {
		rotorType = rotorType.toUpperCase();
		initialise(rotorType);
	}
	
	//Method attempts to map the specified type to a declared mapping
	public void initialise(String rotorType) {
		//Case only contains conditions relevant to the basic rotor
		switch (rotorType) {
		case "I" :
			mapping = mappingI;
			this.name = "I";
			break;
		case "II" :
			mapping = mappingII;
			this.name = "II";
			break;
		case "III" :
			mapping = mappingIII;
			this.name = "III";
			break;
		case "IV" :
			mapping = mappingIV;
			this.name = "IV";
			break;
		case "V" :
			mapping = mappingV;
			this.name = "V";
			break;
		}
	}

	//Method allows for integer to be mapped using the given rotor mapping
	public int substitute(int substituteInt) {
		substituteInt -= position;
		
		/*
		 * If the value after the position taken is negative the value...
		 *  ... is 'circularised' so it lies within the 0-25 range
		 */
		if (substituteInt < 0) {
			substituteInt += 26;
		}
		
		//Rotor specific mapping is applied to the value, returns the value at the position of the mapping
		substituteInt = this.mapping[substituteInt];
		substituteInt += position;
		
		/*
		 * If the value after the position is readded is greater than 25 ('Z' in the alphabet)...
		 *  ... the remainder (MODULO) function is applied so it would circularise the value back into ...
		 *  ... the 0-25 range
		 */
		if (substituteInt > 25) {
			substituteInt = substituteInt % ROTORSIZE;
		}

		return substituteInt;
	}
	
	public int substituteBack(int substituteInt) {
		substituteInt -= position;
		
		//Value is 'circularised' to remain in the 0-25 range
		if (substituteInt < 0) {
			substituteInt += 26;
		}
		
		/*
		 * The value to find (that lies within it 0-25 range) is searched for in the mapping array ...
		 *  ... the position that contains this value is returned and used as the mapped value
		 */
		for (int i = 0; i < ROTORSIZE; i++) {
			if (substituteInt == this.mapping[i]) {
				substituteInt = i;
				i = ROTORSIZE;
			}
		}
		
		substituteInt += position;
				
		//Value is 'circularised' by use of the MODULO function which keeps it in the 0-25 range
		if (substituteInt > 25) {
			substituteInt = substituteInt % ROTORSIZE;
		}

		return substituteInt;
	}
	
	//Method increases the rotor position, essentially rotating it one position in mapping
	public void rotate() {
		position += 1;
		
		//Value is 'circularised' by use of the MODULO function which keeps it in the 0-25 range
		if (position > 25) {
			position = position % ROTORSIZE;
		}
	}
}
