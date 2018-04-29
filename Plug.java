/**
 * This class acts as a vessel for carrying a unique mapping between two characters ...
 *  ... that are held in each end of the plug sockets ('socketEndOne' and 'socketEndTwo')
 */
public class Plug {
	//Variables are assigned to as private for better encapsulation as they are plug unique
	private char socketEndOne;
	private char socketEndTwo;
	
	//SocketEndOne and socketEndTwo are just two ends of the same wire
	public Plug (char socketEndOne, char socketEndTwo) {
		this.socketEndOne = socketEndOne;
		this.socketEndTwo = socketEndTwo;
	}
	
	//Getter returns the value of the first end of the plug
	public char getEndOne() {
		return socketEndOne;
	}
	
	//Getter returns the value of the second end of the plug
	public char getEndTwo() {
		return socketEndTwo;
	}
	
	//Setter sets the first end of the plug to a character
	public void setEndOne(char socketEndOne) {
		this.socketEndOne = socketEndOne;
	}
	
	//Setter sets the second end of the plug to a character
	public void setEndTwo(char socketEndTwo) {
		this.socketEndTwo = socketEndTwo;
	}
	
	//Method checks if either end of plug routes to new character
	public char encode(char letterIn) {
		//Assume no new mapping to remove 'else' statement
		char charOut = letterIn;
		
		//Compares both ends of the plug to the letter in question and applies mapping
		if (letterIn == socketEndOne) {
			charOut = socketEndTwo;
		}
		else if (letterIn == socketEndTwo) {
			charOut = socketEndOne;
		}
		
		return charOut;
	}
	
	//Method checks if a plug end clashes with another plug
	public boolean clashesWith(Plug plugIn) {
		//Assume no clash to remove 'else' statement
		boolean doesClash = false;
		
		//Checks end one of the socket with the plug
		if (plugIn.getEndOne() == socketEndOne || plugIn.getEndTwo() == socketEndOne) {
			doesClash = true;
		}
		//Checks end two wit the socket with the plug
		else if (plugIn.getEndOne() == socketEndTwo || plugIn.getEndTwo() == socketEndTwo){
			doesClash = true;
		}
		
		//Returns the boolean clash value (true if clashed)
		return doesClash;
	}
}
