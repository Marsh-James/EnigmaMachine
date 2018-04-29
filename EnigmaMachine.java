/**
 * EnigmaMachine class includes all functionality to create an Enigma machine
 * Includes all parts and their necessary methods for usage
 */
public class EnigmaMachine {

	//All parts are defined as private and can not be accessed for better encapsulation
	private Plugboard enigmaPlugboard;
	private Reflector enigmaReflector;
	private BasicRotor[] enigmaRotorStack = new BasicRotor[3];
	
	//Method instantiates a new plugBoard which contains no plugs
	public EnigmaMachine() {
		enigmaPlugboard = new Plugboard();
	}
	
	//Method attempts to add a plug, clashes are delt with by the plugboard not the Enigma machine
	public void addPlug(char socketEndOne, char socketEndTwo) {
		enigmaPlugboard.addPlug(socketEndOne, socketEndTwo);
	}
		
	//Method removes all plugs from a plugboard
	public void clearPlugboard() {
		enigmaPlugboard.clear();
	}
	
	//Setter adds a new rotor, which includes its mappings, to the rotor array
	public void addRotor(BasicRotor newRotor, int slot) {
		enigmaRotorStack[slot] = newRotor;
	}
	
	//Getter returns the unique rotor in the position in the rotor array
	public BasicRotor getRotor(int slot) {
		return enigmaRotorStack[slot];
	}
	
	//Setter allows for a user defined reflector with a mapping to be added to the enigma machine
	public void addReflector(Reflector newReflector) {
		enigmaReflector = newReflector;
	}
	
	//Getter returns the enigma machine reflector
	public Reflector getReflector() {
		return enigmaReflector;
	}
	
	//Method allows for a user to update the position of a specific Enigma machine rotor
	public void setPosition(int slot, int position) {
		enigmaRotorStack[slot].setPosition(position);
	}
	
	/*
	 * Method takes in a plain text character and outputs cipher text character
	 * Breakdown order: Plug mapping check -> Map through rotors 0-2 -> Reflect -> Map through rotors 2-0 -> plug mapping check -> output
	 */
	public char encodeLetter(char letter) {
		int encodedLetter = 0;
		int numOfRotors = enigmaRotorStack.length;
		
		//Attempts to find a new character if a plug applies to this letter
		letter = enigmaPlugboard.substitute(letter);
		//Updated letter is converted into its ASCII value and normalised into the 0-25 range for rotor mapping
		encodedLetter = (int)(letter - 65);
		
		//Each rotor from positions 0->2 attempt to map the letter
		for (BasicRotor nextRotor : enigmaRotorStack) {
			
			//Error safeguard in case a rotor is missing
			if (nextRotor != null ) {
				encodedLetter = nextRotor.substitute(encodedLetter);
			}
		}
		
		//First pass encoded letter is sent through the reflector for remapping
		encodedLetter = enigmaReflector.substitute(encodedLetter);
		
		//The remapped value is sent back through the original set of rotors in the opposite direction 2->0
		for (int i = 0; i < numOfRotors; i++) {
			//'numOfRotors - (i + 1)' allows for counting backwards using a forward moving array
			encodedLetter = enigmaRotorStack[numOfRotors - (i + 1)].substituteBack(encodedLetter);
		}

		//After each character the left most rotor is incremented by one position
		enigmaRotorStack[0].rotate();
		
		//Letter is pushed back into the ASCII value range 65->90 so it can be converted back to alphabetic characters
		encodedLetter = (encodedLetter + 65);
		letter = (char)encodedLetter;
		
		//Final substitution through a plug if the encoded letter matches any end of a plug
		letter = enigmaPlugboard.substitute(letter);
		
		//Returns the encoded letter
		return letter;
	}
	
	//EXTRA METHOD
	public String outputPlugInfo() {
		StringBuilder plugData = new StringBuilder();
		
		for (Plug nextPlug : enigmaPlugboard.getPlugList()) {
			
			plugData.append("Plug end one: " + nextPlug.getEndOne() + " & plug end two: " + nextPlug.getEndTwo());
			plugData.append("\n");
		}
		return plugData.toString();
	}
	
	public String outputRotorInfo() {
		StringBuilder rotorData = new StringBuilder();
		
		for (BasicRotor nextRotor : enigmaRotorStack) {

			rotorData.append("Rotor position: " + nextRotor.getPosition() + " & Rotor Type: " + nextRotor.getName());
			rotorData.append("\n");

		}	
		return rotorData.toString();
	}
		
	public void removeBadPlug() {
		this.enigmaPlugboard.removeLastPlug();
	}
	
	public int getNumPlugs() {
		return this.enigmaPlugboard.getNumPlugs();
	}
}
