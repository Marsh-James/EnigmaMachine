import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class enables adding of new plugs and manages any duplicates that are...
 *  ... attempted to be added to the class
 * This class also handles outputting of data for private variables
 */
public class Plugboard {
	
	/*
	 * NOTE: Array list of plugs rather than fixed array is more memory efficient ...
	 *  ... even though a maximum of 13 plugs may be added for one plug board if all locations are not used ...
	 *  ... memory would otherwise be wasted
	 */
	private ArrayList<Plug> plugList = new ArrayList<Plug>();
		
	//Method attempts to add a given plug to the plug board
	public void addPlug(char socketEndOne, char socketEndTwo) {
		boolean plugFailed = false;
		
		//Creates instance of plug to validate
		Plug tryPlug = new Plug(socketEndOne, socketEndTwo);
		
		//Iterates through all existing plugs in plug board to compare if ends are duplicates
		for (Plug plugToCheck : plugList) {

			if (plugToCheck.clashesWith(tryPlug)) {
				plugFailed = true;
			}
		}
		
		//If plug did not fail add it to the plugbord
		if (!plugFailed) {
			plugList.add(tryPlug);
		}
	}
	
	//Method uses the .size() method of ArrayList type to return number of plugs
	public int getNumPlugs() {
		int numOfPlugs = 0;
		numOfPlugs = plugList.size();
		
		return numOfPlugs;
	}
	
	//Method uses .clear() method to remove all plugs
	public void clear() {
		plugList.clear();
	}
	
	//Method attempts to check if any plug ends map a letter
	public char substitute(char keyPressed) {
		//Assume there is no matching plug, therefore if fails will output same character
		char outputChar = keyPressed;
		
		//Iterates through all plugs to check if there is a plug end matching the letter in
		for (Plug plugToCheck : plugList) {
			//Reset and update iterator
			if (plugToCheck.encode(keyPressed) != outputChar) {
				outputChar = plugToCheck.encode(keyPressed);
				break;
			 }
		}
		
		return outputChar;
	}
	
	//EXTRA METHODS//
	
	//Getter returns the plug list
	public ArrayList<Plug> getPlugList() {
		return plugList;
	}
	
	//Method removes the last plug on the ordered array list
	public void removeLastPlug() {
		if (plugList.size() > 0 ) {
			//'.size() - 1' is the position of the final element
			plugList.remove(plugList.size() - 1);
		}
	}
	
	
}
