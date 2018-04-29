

/**
 * This class can be tasked with finding a missing number of plugs or rotors
 *  ... a square array passed in with the following features is expected.
 *  	- '0' : Denotes a unknown variable
 *  	- '<Character>' : Denotes a known variable
 *      - 'null' : is used to produce a fully square array
 *      
 * Class uses a recursive technique to find solutions
 */
public class Bombe {

	/*
	 * The Enigma class is defined outside of the class so a recursive algorithm can still work ...
	 *  ... on the same Enigma machine rather than instancing a new one
	 */
	private EnigmaMachine Enigma;
	
	//Constructor sets up any private variables
	public Bombe() {
		//On creation of this class the Enigma machine is instanced only once
		Enigma = new EnigmaMachine();
	}
	
	//Method takes in the square array defined by the user and a boolean value marked 'true' for missing features
	/**
	 * @param knownData The data array with the following design: (Ordered by array number) ..
	 * .. [0]Plugs [1] Rotor Positions [2]Rotor Types [3]Reflector Type [4]Cipher text [5]Keyword
	 * @param dataWidth Takes the side size of the square array 
	 * @param missingPlugFlag Flag denotes missing plugs to search for
	 * @param missingRotorFlag Flag denotes missing rotor types to search for 
	 * @param missingPosFlag Flag denotes missing rotor positions to search for
	 */
	public void parseInput(String knownData[][], int dataWidth, boolean missingPlugFlag,
							boolean missingRotorFlag, boolean missingPosFlag) {
		
		int plugCounter = 0, rotorCounter = 0;
		
		//Collects the number of complete and half plugs input by the user
		for (int i =  0; i < dataWidth; i++) {
			
			//Null checks if the array [0] in the dataArray[][] has been buffered to produce a square array
			if (knownData[0][i] != null) {
				plugCounter += 1;
			}
		}
		
		//Collects the number of rotors input by the user
		for (int i = 0; i < dataWidth; i++) {
			
			//Null checks if the array [0] in the dataArray[][] has been buffered to produce a square array
			if (knownData[1][i] != null) {
				rotorCounter += 1;
			}
		}
		
		/*
		 * Statements check which flag is marked as true, does not handle multiple missing components
		 * Each method takes in the same parameters but processes the data differently, however all solutions are recursive
		 */
		if ((missingPlugFlag == true) && (missingRotorFlag == false) && (missingPosFlag == false)){
			findMissingPlugs(knownData, dataWidth, plugCounter, 0, rotorCounter);
			
		} else if ((missingPlugFlag == false) && (missingRotorFlag == true) && (missingPosFlag == false)) {
			findMissingRotor(knownData, dataWidth, plugCounter, 0);
			
		} else if ((missingPlugFlag == false) && (missingRotorFlag == false) && (missingPosFlag == true)) {
			findMissingPos(knownData, dataWidth, plugCounter, 0);
			
		} else {
			System.err.println("Please select only one Enigma Machine component missing flag!");
		}
		
	}	
	
	/*
	 * Method finds and adds the reflector from user input to the Enigma Machine, this takes in the Enigma machine ...
	 *  ... class so it is more robust in case a solution requires instancing a new Enigma Machine
	 */
	private void addInputReflector(EnigmaMachine Enigma, String knownData[][]) {
		
		//Provided there is no enigma already in place it will execute the solution, this is to safeguard the recursion
		if (Enigma.getReflector() == null ) {
			Reflector enigmaReflector = new Reflector();
		
			//Initialise method applies the mapping for the specific reflector
			enigmaReflector.initialise(knownData[3][0]);
			
			//Reflector is passed in as a component of the Enigma Machine
			Enigma.addReflector(enigmaReflector);
		}
	}
	
	//Method compares the plain text output of the Enigma machine with the known phrase
	private void checkIfMatched(String plainText, String knownData[][]) {		
		
		//.contains() checks if the character array exists in the string
		if (plainText.contains(knownData[5][0])) {
			
			//On finding a matching solution, output all data for the user to know the Enigma Machine settings
			System.out.println("Found: " + plainText);
			System.out.println(Enigma.outputPlugInfo());
			System.out.println(Enigma.outputRotorInfo());
		}
	}
	
	//Method keeps rotor values within the 0-25 to return the rotor to its original position after decoding the cipher text
	private void circulariseFirstRotor(EnigmaMachine Enigma, int value) {
		
		/*
		 * If the position has shifted into the negative region when working...
		 *  ... backwards shift it forward into range
		 */
		while (value < 0) {
			value += 26;
		}
		
		//Update the rotor position
		Enigma.getRotor(0).setPosition(value);
	}
	
	//Method applies the decoding for the cipher text
	private String decodeCipherChar(EnigmaMachine Enigma, String knownData[][]) {
		//String builder allows for characters to be appended to produce a string
		StringBuilder myStringBuilder = new StringBuilder();
		String plainText = "";
		char plainChar = ' ';
		
		//Loop allows for each character to be decoded (knownData[4][0].length())
		for (int nextChar = 0; nextChar < knownData[4][0].length(); nextChar++) {
			
			//Decoded plain text character is passed into the plain char then appended, method is easier to read
			plainChar = Enigma.encodeLetter(knownData[4][0].charAt(nextChar));
			myStringBuilder.append(plainChar);
			}
		
		//Get the string from myStringBuilder for easier handling
		plainText = myStringBuilder.toString();
		
		/*
		 * When the plain text is produced reset the rotor position back to its original position (before decoding)
		 * This is done so the user can find out what the original position of the rotor was without ...
		 *  ... waiting memory in storing in many locations
		 */
		circulariseFirstRotor(Enigma, (Enigma.getRotor(0).getPosition() - knownData[4][0].length()));
		
		return plainText;
	}
	
	//Method attempts to complete any incomplete plugs and include any complete plugs
	private void findMissingPlugs(String knownData[][], int dataWidth, int plugCount, int firstPlugIndex, int rotorCount) {
		
		//Loop increments through all characters A-Z for any in complete plug end
		for (int i = 0; i < 26; i++) {

			//Get the number of plugs in the plug board for the current recursion level
			int initialPlugboardSize = Enigma.getNumPlugs();
			String plainText = "";
			
			//Ascertains if the plug is incomplete and if so will add the first character 'A' (from the for loop)
			if (knownData[0][firstPlugIndex + 1].equals("0")) {
				
				//Incomplete plug end, add first end and begin guessing other end
				Enigma.addPlug(knownData[0][firstPlugIndex].charAt(0), (char)(65 + i));
			} else {
				
				//Both plug ends exist therefore add full plug to plug board 
				Enigma.addPlug(knownData[0][firstPlugIndex].charAt(0), knownData[0][firstPlugIndex + 1].charAt(0));
			}
			
			/*
			 * Try method attempts to recurse if there exists another plug in the list. IE for two plugs
			 * Plug1 (if incomplete) -> A-A
			 * 		Plug2 (if incomplete) -> B-Z (Then after B-Z increments Plug1 -> A-B (skips A-B clash) -> A-C
			 */
			try {
				//Check next possible position for a plug if it is not a array buffer then recurse
				if (knownData[0][firstPlugIndex + 2] != null) {
					
					//Calls it's own method incrementing the plug index to start it assuming a position a whole plug unit forward
					findMissingPlugs(knownData, dataWidth, plugCount, firstPlugIndex + 2, rotorCount);
				}
				
			//Do nothing if out of bounds, normal error, recursion exits (no message displayed as would increase output clutter)
			} catch (IndexOutOfBoundsException e) {}
			
			//For all the rotors passed in add each one, including type and position
			for (int newRotors = 0 ; newRotors < rotorCount; newRotors++) {
				
				//Checks if rotor is assigned properly and that there is no buffer
				if ((knownData[1][newRotors] != null) && (knownData[2][newRotors] != null)) {
					
					//Generates rotor to add and includes data
					BasicRotor rotorInstance = new BasicRotor(knownData[2][newRotors]);
					rotorInstance.setPosition(Integer.parseInt(knownData[1][newRotors]));
					
					//Add rotor to the Enigma machine
					Enigma.addRotor(rotorInstance, newRotors);
				}
			}
			
			//Attempt to add a reflector to the Enigma machine, only will work on the first iteration, reflector never changes
			addInputReflector(Enigma, knownData);
			
			//Get the plain text from the given cipher text
			plainText = decodeCipherChar(Enigma, knownData);
			//Check if the plain text contains the key phrase and output if it does
			checkIfMatched(plainText, knownData);
			
			//Debreief the Enigma machine, remove top most plug, will be readded on next iteration of this recurse level
			int numPlugs = Enigma.getNumPlugs();
			/*
			 * If statement checks if any clashes occurred, if so there would be one less plug added...
			 *  ... this stops removing plugs added from different recurse levels
			 */
			if (initialPlugboardSize < numPlugs) {
				Enigma.removeBadPlug();
			}
		}	
	}
	
	//Method attempts to complete any incomplete rotor types from the user input
	private void findMissingRotor(String knownData[][], int dataWidth, int plugCount, int firstTypeIndex) {
		//Array is used to iterate through all possible rotor types
		String[] rotorTypes = {"I", "II", "III", "IV", "V"};
		
		//Used to iterate through all position of the rotorTypes array
		for (int i = 0; i < 5; i++) {
			
			String plainText = "";	

			//Attempt to add the rotor with the first rotor type and add its known position
			BasicRotor rotorInstance = new BasicRotor(rotorTypes[i]);
			rotorInstance.setPosition(Integer.parseInt(knownData[1][firstTypeIndex]));
			Enigma.addRotor(rotorInstance, firstTypeIndex);
			
			//Check if there is another rotor to be recursed through, by checking if space is not buffer space
			try {
				if (knownData[2][firstTypeIndex + 1] != null) {
					
					/*
					 * If there exists a rotor that needs to be recursed through, increment the starting ...
					 *  ... index by one for the next recurse level
					 */
					findMissingRotor(knownData, dataWidth, plugCount, firstTypeIndex + 1);
				}
				
			//Do nothing if out of bounds, normal error, recursion exits (no message displayed as would increase output clutter)
			} catch (IndexOutOfBoundsException e) {}
			
			//For loop to iterate through all known plugs and add them to the plugboard
			for (int newPlugs = 0 ; newPlugs < (plugCount); newPlugs += 2) {
				
				Enigma.addPlug(knownData[0][newPlugs].charAt(0),knownData[0][newPlugs+1].charAt(0));
			}
			
			//Attempt to add a reflector to the Enigma machine, only will work on the first iteration, reflector never changes
			addInputReflector(Enigma, knownData);	
			
			//Get the plain text from the given cipher text
			plainText = decodeCipherChar(Enigma, knownData);
			//Check if the plain text contains the key phrase and output if it does
			checkIfMatched(plainText, knownData);
			
			//Clear the plug board for the next iteration so no clashes or errors will occur
			Enigma.clearPlugboard();
		}
	}
	
	//Method attempts to complete any incomplete rotor positions from the user input
	private void findMissingPos(String knownData[][], int dataWidth, int plugCount, int firstRotorIndex) {
		//Iterate through all allowed rotor positions from 0 to 25
		for (int i = 0; i < 26; i++) {
			String plainText = "";
			
			//Attempt to add the rotor with the first position (i) and add its known type
			BasicRotor rotorInstance = new BasicRotor(knownData[2][firstRotorIndex]);
			rotorInstance.setPosition(i);
			Enigma.addRotor(rotorInstance, firstRotorIndex);
			
			//Check if there is another rotor to be recursed through, by checking if space is not buffer space
			try {
				if (knownData[1][firstRotorIndex + 1] != null) {
					
					/*
					 * If there exists a rotor that needs to be recursed through, increment the starting ...
					 *  ... index by one for the next recurse level
					 */
					findMissingPos(knownData, dataWidth, plugCount, firstRotorIndex + 1);
				}
				
			//Do nothing if out of bounds, normal error, recursion exits (no message displayed as would increase output clutter)
			} catch (IndexOutOfBoundsException e) {}
			
			//For loop to iterate through all known plugs and add them to the plug board
			for (int newPlugs = 0 ; newPlugs < (plugCount); newPlugs += 2) {
				
				Enigma.addPlug(knownData[0][newPlugs].charAt(0),knownData[0][newPlugs+1].charAt(0));
			}
			
			//Attempt to add a reflector to the Enigma machine, only will work on the first iteration, reflector never changes
			addInputReflector(Enigma, knownData);	
			
			//Get the plain text from the given cipher text
			plainText = decodeCipherChar(Enigma, knownData);
			//Check if the plain text contains the key phrase and output if it does
			checkIfMatched(plainText, knownData);
			
			//Clear the plug board for the next iteration so no clashes or errors will occur
			Enigma.clearPlugboard();
			
		}
	}
}
