package carParkingSystem;

public abstract class Vehicle {
	private String type;
	private String idPlateNo;
	private String brand;
	private DateTime entryDateTime;

	// constructor
	public Vehicle(String idPlateNo, String brand, DateTime entryDateTime) {
		//super();
		this.idPlateNo = idPlateNo;
		this.brand = brand;
		this.entryDateTime = entryDateTime;
	}

	// methods

	public abstract String getType();
	/*
	 * public String getType() { return type; }
	 */

	public String getIdPlateNo() {
		return idPlateNo;
	}

	public void setIdPlateNo(String idPlateNo) {
		this.idPlateNo = idPlateNo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public DateTime getEntryDateTime() {
		return entryDateTime;
	}

	public void setEntryDateTime(DateTime entryDateTime) {
		this.entryDateTime = entryDateTime;
	}

	@Override
	public String toString() {
		return idPlateNo + "  Brand : " + brand + "  Entry on : " + entryDateTime + "  ";
	}

	public String toStringFile() {
		return (idPlateNo + "\t" + brand + "\t" + entryDateTime);
	}

}
