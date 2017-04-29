package carParkingSystem;

public class Car extends Vehicle {
	private int noOfDoors;
	private String color;

	// constructor
	public Car(String idPlateNo, String brand, DateTime entryDateTime, int noOfDoors, String color) {
		super(idPlateNo, brand, entryDateTime);
		this.noOfDoors = noOfDoors;
		this.color = color;
	}

	// methods

	public String getType() {
		return "car";
	}

	public int getNoOfDoors() {
		return noOfDoors;
	}

	public void setNoOfDoors(int noOfDoors) {
		this.noOfDoors = noOfDoors;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "CAR    " + super.toString() + "Doors : " + noOfDoors + "  Color : " + color;
	}

	public String toStringFile() {
		return ("CAR" + "\t" + super.toStringFile() + "\t" + noOfDoors + "\t" + color);
	}

}
