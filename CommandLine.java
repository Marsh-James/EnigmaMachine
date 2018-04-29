import java.util.Scanner;

/**
 * Class allows a user to setup a Enigma machine and encode characters in real time
 */
public class CommandLine {

	//Generate a Enigma machine that a user can define its components for
	private EnigmaMachine Enigma;
	private StringBuilder plainText, cipherText;
	//Boolean array to figure out the progress of the user {plugs set, rotors set, reflector set}
	private boolean[] userInputsMade = {false, false, false};
	
	//Constructor initialises a new Enigma machine and the plain/cipher text string builders
	public CommandLine() {
		Enigma = new EnigmaMachine();
		plainText = new StringBuilder();
		cipherText = new StringBuilder();
		
		//Executes method to load user interface
		userInterface();
	}
	
	//Method takes a user input and outputs the original plain text input as well as the cipher text equivalent
	private void startEncoding() { 
		System.out.println("Please begin typing, press 'ENTER' after each character to confirm button pressed!");
		char encodeChar = ' ';
		
		//Loop till escape key is pressed ('Q', least common letter in the alphabet)
		while (true) {
			String userInput = "";

			//Get a user input of type string for processing (A-Z characters only accepted)
			userInput = getUserStringSelection();	
			encodeChar = userInput.charAt(0);
			
			//Check if key pressed is the escape character
			if (encodeChar == 'Q') {
				//Executes method to exit program
				exitEnigma();
			}
 			
			//Add character to plain text string
			plainText.append(encodeChar);
			//Encode character using user defined Enigma machine
			encodeChar = Enigma.encodeLetter(encodeChar);
			//Store encoded character in a cipher text string 
			cipherText.append(encodeChar);
			
			//Clear console so users can more easily read the current outputs
			cleanConsole();
			
			//Output all relevant data
			System.out.println("Cipher Text: " + cipherText.toString());
			System.out.print("Plain Text: " + plainText.toString());
			System.out.println();
		}
	}
	
	//Method exits the program by mode 0 (no errors)
	private void exitEnigma() {
		System.out.println();
		System.out.println("Good bye!");
		
		//Mode 0 denotes no errors, intentional break in program
		System.exit(0);
	}
	
	//Method manages the user interface when setting up the Enigma machine
	private void userInterface() {
		int userSelection = 0;
		
		//Loop keeps interface active even after Enigma is setup
		while (true) {
			
			//Clean console for better readability and reprint Enigma setup status
			cleanConsole();
			printBanner();
			printOptions();
			
			//Collect user integer input
			userSelection = getUserIntSelection();
			
			//Check if input is within range otherwise reject and demand second attempt at input
			if (userSelection < 1 || userSelection > 5) {
				userSelection = getUserIntSelection();
			} else {
				//Switch statement filters the userSelections so right method can be executed
				switch (userSelection) {
		
				case 1 : setupPlugs();
				 	 	 break;
				case 2 : setupRotors();
					 	 break;
				case 3 : setupReflector();
						 break;
				case 4 : exitEnigma();
						 break;
				}
		
				//If the Enigma machine is setup then option 5 may be attempted
				if (userSelection == 5 && (enigmaReady() == true)) {
					System.out.println();
					System.out.println("Type 'Q' to escape");
					startEncoding();
				} else {
					//If user attempts to select option 5 before it is displayed they will be given this message
					System.out.println("Enigma machine is not fully setup");
				}
			}
		}
	}
	
	//Method prints banner for this class
	private void printBanner() {
		
		System.out.println("//////////////////////////////////////////");
		System.out.println("///Welcome to build your Enigma Machine///");
		System.out.println("//////////////////////////////////////////");
	}
	
	//Method prints available options and their status 
	private void printOptions() {
		
		//Simple print of all options available
		System.out.println();
		System.out.println("[1] Setup plugs - Completed: " + userInputsMade[0]);
		System.out.println("[2] Setup rotors - Completed: " + userInputsMade[1]);
		System.out.println("[3] Setup reflector - Completed: " + userInputsMade[2]);
		System.out.println("[4] Exit");
		
		//When the Enigma machine is setup a 5th encoding option is displayed as all parts are setup
		if (enigmaReady() == true) {
			System.out.println("[5] Start encoding");
		}
	}
	
	
	//Method allows user to setup a number of user defined plugs
	private void setupPlugs() {
		int numberOfPlugs = -1;
				
		//Clear plugboard in case the user is trying to setup more than once, prevents clashes and unwanted plugs
		Enigma.clearPlugboard();
		
		/*
		 * Assuming that the number of plugs is in the allowed maximum range ...
		 *  ... (even though it is handled by a plug method to check if there are any clashes, defensive programming)
		 */
		while (numberOfPlugs < 0 || numberOfPlugs > 13) {
			System.out.println();
			System.out.println("Enter the number of plugs");
			
			//Get number of plugs from the user if the current value is outside the range
			numberOfPlugs = getUserIntSelection();
			
			//If outside of range again, output error
			if (numberOfPlugs > 13)  {
				System.err.println("There are not enough locations in the plug board for this quantity of plugs \n");
			}
		}
		
		
		//For a user defined number of plugs iterate and fill each one, any failed plugs will not be counted
		for (int nextPlug = 0; nextPlug < numberOfPlugs; nextPlug++) {
			//Used to check if a plug had failed or not so the user can attempt to add another plug
			int initialPlugCount = Enigma.getNumPlugs();
			char endOne, endTwo;
			
			//Output so user knows what plug end to input and when along with the current plug count
			System.out.println("Plug end one of plug " + nextPlug + " in to a key (input char): ");
			endOne = getUserStringSelection().charAt(0);
			System.out.println("Plug end two of plug " + nextPlug + " in to a key (input char): ");
			endTwo = getUserStringSelection().charAt(0);
			
			//Attempt to add plug
			Enigma.addPlug(endOne, endTwo);
			
			//Check if the plug had failed and the count stayed the same, if so output error and allow iteration to occur again
			if (Enigma.getNumPlugs() == initialPlugCount) {
				System.err.println("Plug failed:  " + nextPlug + " a mapping to these keys already exist \n");
				nextPlug--;
			}
		}		
		
		//Update the setup array so that he plugs are now setup
		userInputsMade[0] = true;
	}
	
	//Method allows user to setup a number of user defined rotors
	private void setupRotors() {
		
		//Enigma machine must contain three rotors therefore iterate through
		for (int nextRotor = 0; nextRotor < 3; nextRotor++) {
			
			//Rotor types are fixed therefore a comparison array is setup
			String[] rotorTypes = {"I", "II", "III", "IV", "V"};
			String userRotorType = null;
			int userRotorPos = 0;
			
			//Validation booleans to check if the rotor position and type are within valid range
			boolean isRealType = false, isRealPos = false;
			
			//Loop is forced to occur once as real type is initialised as false
			while (!isRealType) {
				//Request rotor type to be input by user and it from a string inputs
				System.out.print("Rotor " + (nextRotor + 1) + " type? ");
				userRotorType = getUserStringSelection();
				
				
				//Assume that the type does not exist, however if there is a match, switch the boolean value to true
				for (String availableRotorType : rotorTypes) {
					
					//String comparison between input and the string array
					if (userRotorType.equals(availableRotorType)) {
						isRealType = true;
					}			
				}
				
				//If invalid type is still detected clear the console and print error message
				if (!isRealType) { 
					cleanConsole();
					System.out.println("This is an inalid type for this rotor, try again");
				}
			}
			
			//Once correct type has been input generate a new rotor instance of that type
			BasicRotor userRotor = new BasicRotor(userRotorType);
			
			//Loop is forced to occur as real position is initialised as false
			while (isRealPos == false ) {
				
				//Print instructions and get user 'int' input
				System.out.print("Rotor " + (nextRotor + 1) + " position? ");
				userRotorPos = getUserIntSelection();
				
				//Check if input lies within range, otherwise keep as invalid input and print notification
				if (userRotorPos > 25 || userRotorPos < 0 ) {
					cleanConsole();
					isRealPos = false;
					System.out.println("This is an invalid position for this rotor, try again");
						
				} else {
					//If valid input is input within 0-25 range then pass in the input
					isRealPos = true;
				}
			}
			
			//Using the validated user position set the instance of the rotor position
			userRotor.setPosition(userRotorPos);
			
			//Fully validated user input rotor is then added to the Enigma part array
			Enigma.addRotor(userRotor, nextRotor);
		}
		userInputsMade[1] = true;
	}
	
	//Method allows a user to define the rotor type for the Enigma machine
	private void setupReflector() { 
		//Reflector types are fixed therefore a comparison array is setup
		String[] reflectorTypes = {"REFLECTORI", "REFLECTORII"};
		String userReflectorType = null;
		boolean isRealType = false;
		
		//Loop is forced to occur as real type is initialised as false
		while (!isRealType) {
			System.out.print("Reflector type? ");
			userReflectorType = getUserStringSelection();
			
			//Checks user input against all types of reflector, is not case sensitive, handled by string return
			for (String availableReflectorType : reflectorTypes) {
				
				if (userReflectorType.equals(availableReflectorType)) {
					//If contains valid type will exit loop on next iteration as failed condition is reversed
					isRealType = true;
				}			
			}
			
			//If input failed output notification 
			if (!isRealType) { 
				System.out.println("This is an inalid type for this reflector, try again");
			}
		}
				
		//Create a new instance of a reflector
		Reflector myReflector = new Reflector();
		
		//Initialise the new reflector instance with the type so the mappings are bound and add part to the Enigma Machine
		myReflector.initialise(userReflectorType);
		Enigma.addReflector(myReflector);
		
		userInputsMade[2] = true;
	}
	
	//Method allows for a valid user integer to be passed back
	private int getUserIntSelection() {
		//Creates Scanner object to read console input
		Scanner reader = new Scanner(System.in);
		
		boolean invalidInput = true;
		int userSelection = -1;
		
		//Loop is forced to occur as invalidInput is initialised as true
		while (invalidInput) {
			//Attempt to get int input from the user
			try {
				System.out.print("Please enter an integer: ");
				
				userSelection = Integer.parseInt(reader.next());
				
				//Validate input as only positive values can be put through the Enigma machine
				if (userSelection < 0) {
					System.err.println("This system does not allow negative inputs, please try again \n");
					invalidInput = true;
				} else {
					invalidInput = false;
				}
				
			//If input is not of int type catch exception and print error message
			} catch (NumberFormatException e) {
				System.err.println("ERROR: Input is invalid \n");
				invalidInput = true;
			}
		}
		
		//Return non-negative, int data type input
		return userSelection;
	}
	
	//Method allows for a valid user integer to be passed back
	private String getUserStringSelection() {
		//Creates Scanner object to read console input
		Scanner reader = new Scanner(System.in);
		
		boolean invalidInput = true;
		String userSelection = "";
		
		//Loop is forced to occur as invalidInput is initialised as true
		while (invalidInput) {
			
			System.out.print("Please enter an alphabetical character string: ");
			userSelection = reader.next();
			
			//Allows for users to enter both upper and lower case characters
			userSelection = userSelection.toUpperCase();
			
			//Make sure the input is a letter of at least length one
			if (userSelection.matches("[A-Z]+")) {
				invalidInput = false;
			} else {
				//If input does not meet conditions output error message and force user input again
				System.err.println("\nThis system expects a alphabetical character string, please try again");
				invalidInput = true;
			}
		}
		
		//Return upper case character within A-Z range
		return userSelection;
	}
	
	//Method checks if all parts of the Enigma machine are setup
	private boolean enigmaReady() {
		//Assumes the parts are all setup
		boolean allReady = true;
		
		//Iterates through all array positions and if a part is not setup it updates the allReady state to false 
		for (boolean enigmaPartReady : userInputsMade) {
			
			//Condition that checks if the part in question is ready or not
			if (enigmaPartReady == false) { 
				allReady = false;
			}
		}
		
		return allReady;
	}
	
	//Method cleans console screen for easier readability
	private void cleanConsole() {
		
		//For loop outputs many new lines to push all current data on the screen out of view
		for (int i = 0; i < 30; i++) {
			System.out.println("\n");
		}
	}
}
