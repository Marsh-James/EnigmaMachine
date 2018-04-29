/**
 * Reflector class increases the cryptographic complexity by modifying letter before... 
 *  ... it is passed back through the rotors
 */
public class Reflector extends Rotor {
	
	//Method sets initial mappings to reflector
	public void initialise(String reflectVersion) {
		//Converts input to upper case so the input is not case sensitive
		reflectVersion = reflectVersion.toUpperCase();
		
		//Checks if the user input matches either possible entries for mappings
		if (reflectVersion.equals("REFLECTORI")) {
			mapping = new int[] {24, 17, 20, 7, 16, 18, 11, 3, 15, 23, 13, 6, 14, 10, 12, 8, 4, 1, 5, 25, 2, 22, 21, 9, 0, 19};
			this.name = reflectVersion;
		}
		else if (reflectVersion.equals("REFLECTORII")) {
			mapping = new int[] {5, 21, 15, 9, 8, 0, 14, 24, 4, 3, 17, 25, 23, 22, 6, 2, 19, 10, 20, 16, 18, 1, 13, 12, 7, 11};
			this.name = reflectVersion;
		}
	}
	
	//Method takes input and reflects new int
	public int substitute(int substituteInt) {
		//Applies generic mapping by searching array location for new mapping
		substituteInt = mapping[substituteInt];
		
		return substituteInt;
	}	
}
