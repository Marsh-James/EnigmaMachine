/**
 * Abstract class is used for defining BasicRotor/TurnoverRotor/Reflector
 * Class also contains maximum rotor size constant
 */
public abstract class Rotor {
	protected static final int ROTORSIZE = 26;
	
	//Vairables are protected so they can be accessed by the inhering classes
	protected String name;
	protected int position;
	protected int mapping[];

	//Setter sets initial rotor position
	public void setPosition(int rotorPosition) {
		position = rotorPosition;
	}
	
	//Getter returns the current rotor positions
	public int getPosition() {
		return position;
	}
	
	//Following definitions for overridable classes
	public abstract void initialise(String reflectVersion);
	public abstract int substitute(int substituteInt);
	
	//EXTRA METHOD
	public String getName() {
		return this.name;
	}
}
