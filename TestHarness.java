import java.util.Scanner;

/**
 * This class allows for all the tests to be checked easily by method call
 */
public class TestHarness {
	
	//Method performs test one using specified parameters
	public void testOne() {
		
		EnigmaMachine testEnigma = new EnigmaMachine();
		StringBuilder myStringBuilder = new StringBuilder();
		
		//Setup plugs in the test one format
		testEnigma.addPlug('A', 'M');
		testEnigma.addPlug('G', 'L');
		testEnigma.addPlug('E', 'T');
		
		//Declare and initialise basic rotors a required for test one 
		BasicRotor three = new BasicRotor("III");	
		BasicRotor two = new BasicRotor("II");
		BasicRotor one = new BasicRotor("I");
		
		//Assign basic rotor positions a required for test one
		one.setPosition(6);
		two.setPosition(12);
		three.setPosition(5);
		
		//Add the rotors to the test Enigma machine
		testEnigma.addRotor(one, 0);
		testEnigma.addRotor(two, 1);
		testEnigma.addRotor(three, 2);
		
		//Create a new instance of a reflector component
		Reflector myReflector = new Reflector();
		
		//Assign the type of the reflector required for test one and add it to the Enigma machine
		myReflector.initialise("ReflectorI");
		testEnigma.addReflector(myReflector);
		
		
		//Attempt to decode the word
		String decodeWord = "GFWIQH";
		for (int i = 0; i < decodeWord.length(); i++) {
			char newChar = ' ';
			
			//Encode each character in 'GFWIQH'
			newChar = decodeWord.charAt(i);
			newChar = testEnigma.encodeLetter(newChar);
			
			//For each encoded character append to the string builder
			myStringBuilder.append(newChar);
			
		}
		//Print the built string from the encoded characters
		System.out.println(myStringBuilder.toString());
	}
	
	//Method performs test two using specified parameters
	public void testTwo() {
		
		EnigmaMachine testEnigma = new EnigmaMachine();
		StringBuilder myStringBuilder = new StringBuilder();
		
		//Setup plugs in the test two format
		testEnigma.addPlug('B', 'C');
		testEnigma.addPlug('R', 'I');
		testEnigma.addPlug('S', 'M');
		testEnigma.addPlug('A', 'F');
		
		//Declare and initialise basic rotors a required for test two
		BasicRotor three = new BasicRotor("II");	
		BasicRotor two = new BasicRotor("V");
		BasicRotor one = new BasicRotor("IV");
		
		//Assign basic rotor positions a required for test two
		one.setPosition(23);
		two.setPosition(4);
		three.setPosition(9);

		//Add the rotors to the test Enigma machine
		testEnigma.addRotor(one, 0);
		testEnigma.addRotor(two, 1);
		testEnigma.addRotor(three, 2);
		
		//Create a new instance of a reflector component
		Reflector myReflector = new Reflector();
		
		//Assign the type of the reflector required for test two and add it to the Enigma machine
		myReflector.initialise("ReflectorII");
		testEnigma.addReflector(myReflector);
		
		//Attempt to decode the word
		String decodeWord = "GACIG";
		for (int i = 0; i < decodeWord.length(); i++) {
			char newChar = ' ';
			
			//Encode each character in 'GACIG'
			newChar = decodeWord.charAt(i);
			newChar = testEnigma.encodeLetter(newChar);
			
			//For each encoded character append to the string builder
			myStringBuilder.append(newChar);
	
		}
		//Print the built string from the encoded characters
		System.out.println(myStringBuilder.toString());
	}
	
	//Method performs test three using specified parameters
	public void testThree() {

		EnigmaMachine testEnigma = new EnigmaMachine();
		StringBuilder myStringBuilder = new StringBuilder();
		
		//Setup plugs in the test three format
		testEnigma.addPlug('Q', 'F');
		
		//Declare and initialise turnover rotors a required for test three
		TurnoverRotor three = new TurnoverRotor("III", null);	
		TurnoverRotor two = new TurnoverRotor("II", three);
		TurnoverRotor one = new TurnoverRotor("I", two);
		
		//Assign basic rotor positions a required for test three
		one.setPosition(23);
		two.setPosition(11);
		three.setPosition(7);
		
		//Add the rotors to the test Enigma machine
		testEnigma.addRotor(one, 0);
		testEnigma.addRotor(two, 1);
		testEnigma.addRotor(three, 2);
		
		//Create a new instance of a reflector component
		Reflector myReflector = new Reflector();
		
		//Assign the type of the reflector required for test three and add it to the Enigma machine
		myReflector.initialise("ReflectorI");
		testEnigma.addReflector(myReflector);
		
		//Attempt to decode the word
		String decodeWord = "OJVAYFGUOFIVOTAYRNIWJYQWMXUEJGXYGIFT";
		for (int i = 0; i < decodeWord.length(); i++) {
			char newChar = ' ';
			
			//Encode each character in 'OJVAYFGUOFIVOTAYRNIWJYQWMXUEJGXYGIFT'
			newChar = decodeWord.charAt(i);
			newChar = testEnigma.encodeLetter(newChar);
			
			//For each encoded character append to the string builder
			myStringBuilder.append(newChar);
			
			//Apply turnover which will recursively check if the turnover rotor is ready to turnover
			one.rotateTurnover();	
		}
		//Print the built string from the encoded characters
		System.out.println(myStringBuilder.toString());
	}
	
	//Method encodes text in a plain text file to a cipher text file
	public void testEnigmaFile() {
		EnigmaFile myTestEnigmaFile = new EnigmaFile();
	}
	
	//Method finds the incomplete plug combinations for some given cipher text and keyword
	public void challengeOne() {
		Bombe myBombe = new Bombe();
		
		//Setup square string array that is used to to determine the missing component information
		String[][] testData = {{"D", "0", "S", "0"},
				   {"8", "4", "21", null},
				   {"IV", "III", "II", null},
				   {"ReflectorI", null, null, null},
				   {"JBZAQVEBRPEVPUOBXFLCPJQSYFJI", null, null, null},
				   {"ANSWER", null, null, null}};

		//Attempt to parse the testData marking the incomplete plug ends flag
		myBombe.parseInput(testData, 4, true, false, false);
	}
		
	//Method finds the incomplete rotor positions for some given cipher text and keyword
	public void challengeTwo() {
		Bombe myBombe = new Bombe();
		
		//Setup square string array that is used to to determine the missing component information
		String[][] testData = {{"H", "L", "G", "P"},
				{"0", "0", "0", null},
				{"V", "III", "II", null},
				{"ReflectorI", null, null, null},
				{"AVPBLOGHFRLTFELQEZQINUAXHTJMXDWERTTCHLZTGBFUPORNHZSLGZMJNEINTBSTBPPQFPMLSVKPETWFD", null, null, null},
				{"ELECTRIC", null, null, null}};

		//Attempt to parse the testData marking the missing rotor positions flag
		myBombe.parseInput(testData, 4, false, false, true);
	}
	
	//Method finds the incomplete rotor types for some given cipher text and keyword
	public void challengeThree() {
		Bombe myBombe = new Bombe();
		
		//Setup square string array that is used to to determine the missing component information
		String[][] testData = {{"M", "F", "O", "I"},
				{"22", "24", "23", null},
				{"0", "0", "0", null},
				{"ReflectorI", null, null, null},
				{"WMTIOMNXDKUCQCGLNOIBUYLHSFQSVIWYQCLRAAKZNJBOYWW", null, null, null},
				{"JAVA", null, null, null}};

		//Attempt to parse the testData marking the missing rotor types flag
		myBombe.parseInput(testData, 4, false, true, false);	
	}
	
	//Method allows users to key by key encode their plain text message
	public void extensionOne() {
		EnigmaMachine testEnigma = new EnigmaMachine();
		StringBuilder plainText = new StringBuilder();
		StringBuilder cipherText = new StringBuilder();
		
		//The test Enigma is setup with the plugs of test three for proof
		testEnigma.addPlug('Q', 'F');
		
		//The test Enigma is setup with the turnover Rotor combinations of part three
		TurnoverRotor three = new TurnoverRotor("III", null);	
		TurnoverRotor two = new TurnoverRotor("II", three);
		TurnoverRotor one = new TurnoverRotor("I", two);
		
		//The test Enigma is setup with the initial rotor positions of part three
		one.setPosition(23);
		two.setPosition(11);
		three.setPosition(7);
		
		//Turnover rotors are added to the enigma rotor array
		testEnigma.addRotor(one, 0);
		testEnigma.addRotor(two, 1);
		testEnigma.addRotor(three, 2);
		
		//Creates a new reflector component
		Reflector myReflector = new Reflector();
		
		//Assign the type of the reflector as in test three and add it to the Enigma machine
		myReflector.initialise("ReflectorI");
		testEnigma.addReflector(myReflector);
		
		//User instructions output
		System.out.println("Please begin typing, press 'ENTER' after each character to confirm button pressed!");
		System.out.println("Press 'Q' to escpae this program");
		char encodeChar = ' ';
		
		//Begin encoding until escape character is pressed 
		while (true) {
			String userInput = "";
			
			//Reading from System.in gets the user input
			Scanner reader = new Scanner(System.in);  

			//Converts user input to upper case to allow for lower case inputs (more robust)
			userInput = (reader.next()).toUpperCase();
			encodeChar = userInput.charAt(0);
			
			//Check if the character pressed is the escape character (the least common character)
			if (encodeChar == 'Q') {
				//Executes method to exit program
				System.out.println("Good bye!");
				System.exit(0);
			}
			
			//Adds current user input to plain text string
			plainText.append(encodeChar);
			
			//Character is encoded by the Enigma machine using defined parts
			encodeChar = testEnigma.encodeLetter(encodeChar);
			
			//Adds most recent encoded character to the cipher text string
			cipherText.append(encodeChar);
			
			//Many white spaces are printed to allow for better user readability
			for (int i = 0; i < 20; i++) {
				System.out.println("\n");
			}
			
			//Output of current user plain text input and the cipher text output
			System.out.println("Cipher Text: " + cipherText);
			System.out.print("Plain Text: " + plainText);

			//Apply turnover which will recursively check if the turnover rotor is ready to turnover
			one.rotateTurnover();	
		}
	}
	
	/*
	 * Method is a further extension of extensionTwo with user validation and allows a user defined Enigma machine. It...
	 *  ... implements a command line interface and prompts to keep the user updated about the definition of the 
	 *  ... Enigma machine
	 */
	public void extensionTwo() {
		CommandLine myCommand = new CommandLine();
	}
}

