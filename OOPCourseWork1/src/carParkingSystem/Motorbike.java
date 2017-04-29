package carParkingSystem;

public class Motorbike extends Vehicle {
	private int sizeOfEngine;

	// constructor
	public Motorbike(String idPlateNo, String brand, DateTime entryDateTime, int sizeOfEngine) {
		super(idPlateNo, brand, entryDateTime);
		this.sizeOfEngine = sizeOfEngine;
	}

	// methods

	public String getType() {
		return "motorbike";
	}

	public int getSizeOfEngine() {
		return sizeOfEngine;
	}

	public void setSizeOfEngine(int sizeOfEngine) {
		this.sizeOfEngine = sizeOfEngine;
	}

	@Override
	public String toString() {
		return "MOTORBIKE    " + super.toString() + "Size of engine : " + sizeOfEngine;
	}

	public String toStringFile() {
		return ("MOTORBIKE" + "\t" + super.toStringFile() + "\t" + sizeOfEngine);
	}

}
