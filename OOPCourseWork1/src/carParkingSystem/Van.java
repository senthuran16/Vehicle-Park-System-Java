package carParkingSystem;

public class Van extends Vehicle {
	private double cargoVolume;

	// constructor
	public Van(String idPlateNo, String brand, DateTime entryDateTime, double cargoVolume) {
		super(idPlateNo, brand, entryDateTime);
		this.cargoVolume = cargoVolume;
	}

	// methods

	public String getType() {
		return "van";
	}

	public double getCargoVolume() {
		return cargoVolume;
	}

	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume = cargoVolume;
	}

	@Override
	public String toString() {
		return "VAN    " + super.toString() + "Cargo Volume : " + cargoVolume;
	}

	public String toStringFile() {
		return ("VAN" + "\t" + super.toStringFile() + "\t" + cargoVolume);
	}

}
