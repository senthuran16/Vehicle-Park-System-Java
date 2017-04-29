package carParkingSystem;

import java.util.Scanner;

public interface CarParkManager {
	// return free lots
	public int freeLots();

	// display free lots as message
	public void freeLotsMessage();

	// prompt and enter vehicle details
	public Vehicle chooseVehicle();

	// add a vehicle
	public void addVehicle();

	// show parked vehicle idPlateNo's
	public void listOfVehicles();

	// delete a vehicle
	public Vehicle deleteVehicle();

	// return vehicle instance when leaving
	public Vehicle removedVehicle(Vehicle v);

	// display type of vehicle, leaving the park
	public void removedVehicleType(Vehicle v);

	// print list of currently parked vehicles
	public void displayParkedVehicles();

	// order the list chronologically
	public Vehicle[] orderChronologically();

	// statistics ------------

	// display all statistics
	public void stats();

	// return percentage of a specific vehicle
	public double percentage(String s);

	// display percentage of all vehicles
	public void percentageSummary();

	// display vehicle parked for the longest time
	public void longestParkedVehicle();

	// display the last parked vehicle
	public void lastParkedVehicle();

	// display list of vehicles entered in on specific day
	public void vehiclesEntered();

	// calculate charges
	public double calculateCharge(Vehicle v, DateTime outDateTime);

	// display charges
	public void displayCharge();

	// validation for numerical values (int)
	public int validate(Scanner s, String valElement, int lowEnd);

	// validation for numerical values (int)
	public double validate(Scanner s, String valElement, double lowEnd);

}
