import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Class allows a user to read from a PlainText file and output to a CipherText file
 * Class allows for long strings to be processed quickly and in an understandable language
 */
public class EnigmaFile {

	//Setup two strings that will take a file input (plainText)
	private String plainText;
	
	//Constructor initialises plainText string as the file contents
	public EnigmaFile() {
		plainText = readFile();
		
		//Executes the Enigma machine to encode the file contents
		runEnigmaMachine();
	}
	
	//Method encodes the plainText file and facilitates cipher text output to file
	private void runEnigmaMachine() {
		
		EnigmaMachine enigma = new EnigmaMachine();
		StringBuilder myStringBuilder = new StringBuilder();
		
		//Setup predefined plugs to use in the Enigma machine
		enigma.addPlug('A', 'M');
		enigma.addPlug('G', 'L');
		enigma.addPlug('E', 'T');
		
		//Setup predefined rotors by instancing new rotors to use in the Enigma machine
		BasicRotor three = new BasicRotor("III");	
		BasicRotor two = new BasicRotor("II");
		BasicRotor one = new BasicRotor("I");
		
		//Setup predefined rotor positions for each rotor
		one.setPosition(6);
		two.setPosition(12);
		three.setPosition(5);
		
		//Add rotors to the Enigma machine
		enigma.addRotor(one, 0);
		enigma.addRotor(two, 1);
		enigma.addRotor(three, 2);
		
		//Instance a new reflector to be added
		Reflector myReflector = new Reflector();
		
		//Initialise with mapping from ReflectorI, this is predefined
		myReflector.initialise("ReflectorI");
		
		//Add reflector to the Enigma machine
		enigma.addReflector(myReflector);
		
		//Store the painText in the method scope so it can not be accessed incorrectly
		String encodeWord = plainText;
		
		//Iterate through each character 
		for (int i = 0; i < plainText.length(); i++) {
			char newChar = ' ';
			
			//Take the current character in the plainText string
			newChar = encodeWord.charAt(i);
			//Encode character using user defined Enigma machine
			newChar = enigma.encodeLetter(newChar);
			
			//Add character to cipher text string
			myStringBuilder.append(newChar);
		}
		
		//Store cipher text string into file
		inputFile(myStringBuilder.toString());
		
	}
	
	//Method reads contents of specified file
	private String readFile() {
		String output = "";
		
		//Attempt to access the plain text containing file
        try {
            File file = new File("PlainText.txt");
            Scanner fileInput = new Scanner(file);
            StringBuilder myStringBuilder = new StringBuilder();

            /*
             * If there is a next line to be input by the file append it to the string ...
             *  ... this means all contents of the file will be moved in as one long continuous string
             */
            while (fileInput.hasNextLine()) {
                myStringBuilder.append(fileInput.nextLine());
            }
            
            //Once all data is collected from the file close the accessed file
            fileInput.close();
            //Assign the output string for easier handling rather than moving the StringBuilder
            output = myStringBuilder.toString();
            
        } catch (Exception e) {
        	//Catch errors and print stack trace
            e.printStackTrace();
        }
        
        //Validation to convert all input characters into their upper case
        output = output.toUpperCase();
		return output;
	}
	
	//Method attempts to write cipher text to file
	private void inputFile(String data) {
		PrintStream ps;
		
		//Attempt to access the cipher text file
		try {
			ps = new PrintStream("CipherText.txt");
			//If print stream is opened output the cipher text lint to file
			ps.println(data);
			
			//Print message to notify user
			System.out.println("Encoded to CipherText file!");
			
			//Once all data is posted to the file close the accessed file
			ps.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
