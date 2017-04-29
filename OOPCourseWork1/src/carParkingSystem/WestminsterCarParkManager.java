package carParkingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WestminsterCarParkManager implements CarParkManager {

	// lots in which vehicles are parked in
	Vehicle[] lots = new Vehicle[20];

	// log of all the entered vehicles
	ArrayList<Vehicle> logVehicles = new ArrayList<Vehicle>();

	// main method
	public static void main(String[] args) {

		// instantiation of WestMinsterCarParkManager
		WestminsterCarParkManager myPark = new WestminsterCarParkManager();

		// show menu
		try {
			myPark.menu();
		} catch (StringIndexOutOfBoundsException e1) {
			System.out.println("B");
			System.exit(0);
		}

	}

	/**
	 * Displays the menu
	 */
	public void menu() {
		while (true) {
			Scanner s = new Scanner(System.in);
			System.out.println("");
			System.out.println("=================================");
			System.out.println("  WESTMINSTER CAR PARK MANAGER");
			System.out.println("=================================");
			System.out.println("");
			System.out.println("[A] Add vehicle");
			System.out.println("[D] Delete vehicle");
			System.out.println("[v] View all slots");
			System.out.println("[C] Display Charges");
			System.out.println("[S] View Statistics");
			System.out.println("[F] Display Parked Vehicles");
			System.out.println("[Y] Vehicles entered in a specific day");
			System.out.println("[W] Save lot data to file");
			System.out.println("[L] Load lot data from file");
			System.out.println("[E] Exit");
			System.out.println("");
			System.out.println("Enter your choice : ");
			char menuChoice = s.nextLine().charAt(0);
			menuChoice = Character.toLowerCase(menuChoice);
			switch (menuChoice) {
			case 'a':
				addVehicle();
				break;
			case 'c':
				displayCharge();
				break;
			case 'd':
				deleteVehicle();
				break;
			case 'v':
				listOfVehicles();
				break;
			case 's':
				stats();
				break;
			case 'f':
				displayParkedVehicles();
				break;
			case 'y':
				vehiclesEntered();
				break;
			case 'e':
				System.exit(0);
				break;
			case 'w':
				storeFile();
				break;
			case 'l':
				loadFile();
				break;
			default:
				System.err.println("Invalid Selection !");
				break;
			}
		}

	}

	/**
	 * returns number of free lots called within freeLotsMessage(), addVehicle()
	 */
	@Override
	public int freeLots() {
		int counter = 0;
		for (Vehicle v : lots) {
			if (v == null) {
				counter++;
			}
		}
		return counter;
	}

	/**
	 * displays message regarding free lots
	 */
	@Override
	public void freeLotsMessage() {
		if (freeLots() == 0) {
			System.out.println("No more free lots remaining");
		} else {
			System.out.println(freeLots() + " free lots remaining");
		}

	}

	/**
	 * returns a vehicle object after prompting necessary details called within
	 * addVehicle()
	 */
	@Override
	public Vehicle chooseVehicle() {
		Scanner sc = new Scanner(System.in); // String scanner
		Scanner scI = new Scanner(System.in); // int scanner

		// prompt
		System.out.println("Please choose the vehicle or Exit");
		System.out.print("[C]-Car [V]-Van [M]-Motorbike [E]-Exit : ");

		// store choice as simple letter
		char choice = Character.toLowerCase(sc.nextLine().charAt(0));

		// check exit
		if (choice == 'e') {
			System.exit(0);
		}

		// validate input
		while (!(choice == 'c' || choice == 'v' || choice == 'm')) {
			System.out.println("Invalid input! Please enter on of the following inputs.");
			System.out.print("[C]-Car [V]-Van [M]-Motorbike [E]-Exit : ");
			choice = Character.toLowerCase(sc.nextLine().charAt(0));

			// check exit
			if (choice == 'e') {
				System.exit(0);
			}
		}

		// prompt for ID plate no
		System.out.print("Enter ID plate no : ");
		String idPlateNo = sc.nextLine();

		// validate ID plate no
		// if ID no is already parked in, re prompt

		boolean isFound = true;
		while (isFound) {
			isFound = false;
			for (Vehicle v : lots) {
				if (!(v == null)) {
					if (v.getIdPlateNo().equalsIgnoreCase(idPlateNo)) {
						isFound = true;
					}
				}
			}
			if (isFound) {
				System.out.println("There is already a vehicle in this ID plate no!");
				System.out.print("Please re-enter ID plate no : ");
				idPlateNo = sc.nextLine();

			}
		}

		// prompt for common details
		System.out.print("Enter brand of the vehicle : ");
		String brand = sc.nextLine();

		// create a temporary DateTime object
		DateTime tempDateTime = new DateTime(-1, -1, -1, -1, -1);

		// modifying the tempDateTime object and finalizing it as the entry date

		System.out.println("Enter the entry date ..................");

		// year
		tempDateTime.setYear(scI);

		// month
		tempDateTime.setMonth(scI);

		// day
		tempDateTime.setDay(scI);

		System.out.println("Enter the entry time ..................");

		// hours
		tempDateTime.setHours(scI);

		// minutes
		tempDateTime.setMinutes(scI);

		System.out.println("Entry on : " + tempDateTime.getDate() + " " + tempDateTime.getTime());

		// prompt appropriate vehicle details and
		// return appropriate vehicle object
		switch (choice) {
		case 'c':
			// car

			// no of Doors
			int noOfDoors = validate(scI, "number of doors", 0);

			// color
			System.out.print("Enter color of the car : ");
			String color = sc.nextLine();

			// return
			return new Car(idPlateNo, brand, tempDateTime, noOfDoors, color);
		case 'v':
			// van

			// cargo volume
			double cargoVolume = validate(scI, "cargo volume", 0.0);

			// return
			return new Van(idPlateNo, brand, tempDateTime, cargoVolume);
		case 'm':
			// motor bike

			// size of engine
			int sizeOfEngine = validate(scI, "size of engine", 0);

			// return
			return new Motorbike(idPlateNo, brand, tempDateTime, sizeOfEngine);
		default:
			// anyhow this will not be executed
			// just for the completion of the method
			return null;

		}

	}

	/**
	 * adds the next vehicle to the next empty slot
	 */
	@Override
	public void addVehicle() {
		if (freeLots() == 0) {
			// no lots are available
			System.err.print("Sorry! No more free lots remaining");
		} else {
			// at least one free slot is there

			// choosing the vehicle to add
			Vehicle v = chooseVehicle();

			if (v.getType().equals("van")) {
				// this is a van
				// should employ occupy two lots
				if (freeLots() > 2) {
					// there are at least two lots to park this van

					int tempCounter = -1;
					boolean isVanSlotFound = false;
					for (Vehicle lot : lots) {
						tempCounter++;
						if (lot == null) {
							// current lot is null
							if (tempCounter != lots.length - 1) {
								// this is not the last lot
								if (lots[tempCounter + 1] == null) {
									// the next slot is also null
									// there are two adjacent lots available
									// assign van to the two lots
									lots[tempCounter++] = v;
									lots[tempCounter] = v; // the second lot

									// adding to the log
									logVehicles.add(v);

									isVanSlotFound = true;
									System.out.println("Vehicle " + v.getIdPlateNo() + " added successfully.");

									// show no of available lots
									freeLotsMessage();
									break;
								}
								// else : next slot is not null

							}

						}
					}
					if (!isVanSlotFound) {
						// no two adjacent slots are found
						// the van can not be assigned
						System.err.print("Sorry! There is no slot for this van available.");
					}

				} else {
					// no place to park the van
					System.out.print("Sorry! There is no slot for this van available.");
				}
			} else {
				// not a van
				// check for available slots
				if (freeLots() != 0) {

					// free lots are there
					int index = 0;
					for (Vehicle ve : lots) {

						if (ve == null) {
							// null object
							break;
						} else {
							// other vehicle
							index++; // increase counter for the next slot
						}
					}

					// add vehicle
					lots[index] = v;

					// adding to the log
					logVehicles.add(v);

					System.out.println("Vehicle " + v.getIdPlateNo() + " added successfully.");
					// show no of available lots
					freeLotsMessage();
				} else {
					// no lots available
					System.err.print("Sorry! No more free lots remaining");
				}

			}
		}

	}

	/**
	 * displays the full car park details
	 */
	@Override
	public void listOfVehicles() {
		// show list of lots
		for (Vehicle v : lots) {
			if (v == null) {
				System.out.println("<EMPTY SLOT>");
			} else {
				System.out.println(v);
			}
		}

		// show no of available lots
		freeLotsMessage();

	}

	@Override
	/**
	 * deletes the vehicle
	 * 
	 */
	public Vehicle deleteVehicle() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter ID Plate number of the vehicle to delete : ");
		String idPlateNo = sc.nextLine();

		Vehicle returnObject = null;
		int counter = -1;
		boolean isFound = true;

		isFound = false;
		for (Vehicle v : lots) {
			counter++;
			if (!(v == null)) {
				if (v.getIdPlateNo().equalsIgnoreCase(idPlateNo)) {
					isFound = true;
					returnObject = v;
					break;
				}
			}
		}

		if (isFound) {
			// delete vehicle
			String type = lots[counter].getType();
			System.out.println("One " + type + " left the park.");
			lots[counter] = null;
			// van employs 2 lots
			// should delete the next lot too
			if (type.equals("van")) {
				lots[counter + 1] = null;
			}

		} else {
			// no vehicle found
			System.err.println("No vehicle found with the ID Plate number.");
		}

		return returnObject;
	}

	@Override
	public Vehicle removedVehicle(Vehicle v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removedVehicleType(Vehicle v) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * displays only parked vehicles
	 */
	public void displayParkedVehicles() {

		if (freeLots() != lots.length) {
			// at least one parked vehicle is there

			for (Vehicle v : orderChronologically()) {
				// for (Vehicle v : lots) {
				System.out.println(v);

			}
		} else {
			System.out.println("There are no vehicles parked in.");
		}

	}

	@Override
	/**
	 * orders vehicles chronologically recently parked vehicle first
	 * 
	 * @return sorted array in chronological order. Latest parked vehicle first
	 */
	public Vehicle[] orderChronologically() {

		List<Vehicle> sortedList = new ArrayList<Vehicle>();

		// add only parked vehicles in List
		for (Vehicle v : lots) {
			if (v != null) {
				sortedList.add(v);
			}
		}

		Vehicle[] sortedArray = sortedList.toArray(new Vehicle[sortedList.size()]);

		for (int i = 0; i < sortedArray.length; i++) {
			for (int j = 0; j < sortedArray.length - 1; j++) {
				if (sortedArray[j].getEntryDateTime().isEarlierThan(sortedArray[j + 1].getEntryDateTime())) {
					// swap
					Vehicle temp;
					temp = sortedArray[j];
					sortedArray[j] = sortedArray[j + 1];
					sortedArray[j + 1] = temp;
				}

			}
		}

		return sortedArray;

	}

	@Override
	/**
	 * Prints the complete stats
	 */
	public void stats() {
		System.out.println("          Statistics");
		System.out.println("-------------------------------");

		// print percentages of each vehicle
		percentageSummary();

		System.out.println("");
		longestParkedVehicle();
		lastParkedVehicle();
	}

	@Override
	/**
	 * Returns the percentage of a single vehicle
	 * 
	 * @param s
	 *            Vehicle name
	 * @return
	 */
	public double percentage(String s) {
		int c = 0;
		int vanCounter = 0;
		int otherCounter = 0;
		double percentage = 0.0;

		for (Vehicle ve : lots) {
			if (ve != null) {
				// count total of vans and other vehicles parked in
				if (ve.getType().equalsIgnoreCase("van")) {
					vanCounter++;
				} else {
					otherCounter++;
				}
				// count total of the sent parameter vehicle
				if (ve.getType().equalsIgnoreCase(s)) {
					c++;
				}
			}

		}

		int totalCounter = (vanCounter / 2) + otherCounter;

		if (s.equalsIgnoreCase("van")) {
			c = c / 2;
		}

		if (totalCounter != 0) {
			// at least one vehicle is parked in
			percentage = (c / (double) totalCounter) * 100.0;
		}

		return percentage;

	}

	@Override
	/**
	 * Displays percentages of all the vehicles together
	 */
	public void percentageSummary() {
		String[] vehicleNames = { "Car", "Van", "Motorbike" };

		System.out.println("Percentages of vehicles in the car park");
		System.out.println("");

		for (String v : vehicleNames) {
			System.out.println(v + " : " + percentage(v) + "%");
		}

	}

	@Override
	/**
	 * Returns the longest parked vehicle object
	 * 
	 * @return the first parked vehicle
	 */
	public void longestParkedVehicle() {
		if (freeLots() != 20) {
			Vehicle longestParked = orderChronologically()[orderChronologically().length - 1];
			System.out.println("The vehicle parked for the longest time is : " + longestParked);
		} else {
			System.out.println("No vehicles are parked!");
		}

	}

	@Override
	/**
	 * Returns the latest parked vehicle object
	 * 
	 * @return the last parked vehicle
	 */
	public void lastParkedVehicle() {
		if (freeLots() != 20) {
			Vehicle latestParked = orderChronologically()[0];
			System.out.println("The latest parked vehicle is : " + latestParked);
		} else {
			System.out.println("No vehicles are parked!");
		}

	}

	@Override
	/**
	 * Displays all the vehicles entered the park in the specified day
	 */
	public void vehiclesEntered() {

		Vehicle[] logArr = logVehicles.toArray(new Vehicle[logVehicles.size()]);

		if (freeLots() != 20) {
			// at least one vehicle is parked
			Scanner sc = new Scanner(System.in);

			boolean isFound = false;

			// to avoid the same van being displayed twice
			String tempID = "";

			// create a temporary DateTime object
			/*
			 * hours and minutes are not going to be considered - hours and
			 * minutes both set to 0
			 */
			DateTime inputDateTime = new DateTime(-1, -1, -1, 0, 0);

			// modifying the tempDateTime object and finalizing it as the entry
			// date

			System.out.println("Enter the date ..................");

			// year
			inputDateTime.setYear(sc);

			// month
			inputDateTime.setMonth(sc);

			// day
			inputDateTime.setDay(sc);

			System.out.println("");

			// display vehicles with the same date
			for (Vehicle v : logArr) {
				if (v != null) {
					if (v.getEntryDateTime().getDate().equals(inputDateTime.getDate())) {
						// if dates are equal

						// at least one vehicle is found
						isFound = true;

						if (!(v.getIdPlateNo().equals(tempID))) {
							// if idPlateNo is not equal to tempID
							// print vehicle
							System.out.println(v);

							// if van
							if (v.getType().equalsIgnoreCase("van")) {
								// assign the idPlateNo to tempID
								/*
								 * This prevents printing for the second time
								 * since no two vehicles have same idPlateNo
								 * other than a van
								 */
								tempID = v.getIdPlateNo();
							}
						}

					}
				}
			}

			if (!isFound) {
				System.out.println("No vehicles entered the park on this day.");
			}

		} else {
			System.out.println("No vehicles are parked.");
		}

	}

	@Override
	public double calculateCharge(Vehicle v, DateTime outDateTime) {

		// get in DateTime
		DateTime inDateTime = v.getEntryDateTime();

		double cost = 0;

		// parked time in minutes
		int mins = (int) inDateTime.subtractFrom(outDateTime);

		if (mins >= (24 * 60)) {
			// parked for more than one day

			// count total FULL days parked in
			int fullDays = (int) Math.floor((mins / 60) / 24.0);

			// 30E per full day
			cost += (30 * fullDays);

			// count additional minutes after full days
			int addMins = mins - (fullDays * 24 * 60);

			// additional minutes are anyhow within one day

			// if time is more than three hours
			if (addMins > (3 * 60)) {
				// 3 hours cost is calculated
				// 3 per hour * 3
				cost += 9;

				// calculate additional minutes after 3 hours time
				addMins -= 180;

				// calculate round hours (ceil division)
				int hrs = (int) Math.ceil(addMins / 60.0);

				// 4 per hour
				cost += (4 * hrs);

			} else {
				// within three hours
				// calculate round hours (ceil division)
				int hrs = (int) Math.ceil(addMins / 60.0);

				// 3 per hour
				cost += (3 * hrs);
			}

		} else {
			// parked for less than one day
			// if time is more than three hours
			if (mins > (3 * 60)) {
				// 3 hours cost is calculated
				// 3 per hour * 3
				cost += 9;

				// calculate additional minutes after 3 hours time
				mins -= 180;

				// calculate round hours (ceil division)
				int hrs = (int) Math.ceil(mins / 60.0);

				// 4 per hour
				cost += (4 * hrs);

			} else {
				// within three hours
				// calculate round hours (ceil division)
				int hrs = (int) Math.ceil(mins / 60.0);

				// 3 per hour
				cost += (3 * hrs);
			}
		}

		return cost;
	}

	@Override
	public void displayCharge() {
		if (freeLots() < 20) {
			// there is at least one vehicle
			Scanner sc = new Scanner(System.in);

			// out time date object
			DateTime outDateTime = new DateTime(-1, -1, -1, -1, -1);

			System.out.println("Enter the current date ..................");

			// year
			outDateTime.setYear(sc);

			// month
			outDateTime.setMonth(sc);

			// day
			outDateTime.setDay(sc);

			System.out.println("Enter the current time ..................");

			// hours
			outDateTime.setHours(sc);

			// minutes
			outDateTime.setMinutes(sc);

			// check for each vehicle
			for (Vehicle v : orderChronologically()) {
				// for each parked vehicle
				System.out.println(v.getIdPlateNo() + " Payment : " + calculateCharge(v, outDateTime) + " Euros");
			}
		} else {
			// no vehicles are parked
			System.out.println("There are no vehicles parked in");
		}

	}

	@Override
	/**
	 * Validates int values
	 */
	public int validate(Scanner s, String valElement, int lowEnd) {
		int val = -1;
		boolean isFirstIteration = true;
		do {
			if (isFirstIteration) {
				// first iteration - no error. prompt only
				System.out.print("Enter " + valElement + " : ");
			} else {
				// invalid range of number
				System.err.print(valElement + " should be a positive integer. Please re-enter : ");
			}
			while (!s.hasNextInt()) {
				// not a number
				System.err.print(valElement + " should be a number. Please re-enter : ");
				s.next();
			}
			val = s.nextInt();
			isFirstIteration = false;
		} while (!(val > lowEnd));
		return val;
	}

	@Override
	/**
	 * Validates double values
	 */
	public double validate(Scanner s, String valElement, double lowEnd) {
		double val = -1.0;
		boolean isFirstIteration = true;
		do {
			if (isFirstIteration) {
				// first iteration - no error. prompt only
				System.out.print("Enter " + valElement + " : ");
			} else {
				// invalid range of number
				System.err.print(valElement + " should be a positive integer. Please re-enter : ");
			}
			while (!s.hasNextDouble()) {
				// not a number
				System.err.print(valElement + " should be a number. Please re-enter : ");
				s.next();
			}
			val = s.nextDouble();
			isFirstIteration = false;
		} while (!(val > lowEnd));
		return val;
	}

	/**
	 * Saves array details into a file
	 */

	public void storeFile() {
		// creating file object
		File file = new File("input.txt");
		PrintWriter pw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(file);
			pw = new PrintWriter(fw, true);

			for (Vehicle v : lots) {

				// for each vehicle
				if (v != null) {
					// a vehicle is parked

					// write to file
					pw.println(v.toStringFile());
				} else {
					// an empty lot

					// write to file
					pw.println("EMPTY");
				}

			}

			System.out.println(" > File stored successfully");
		} catch (Exception e) {

			System.err.println("File storing failed. File can not be found in the appropriate location");

		} finally {

			pw.close();

			try {
				fw.close();
			} catch (Exception e) {
				System.err.println("File storing failed. File can not be found in the appropriate location");
			}
		}

	}

	/**
	 * Loads file
	 */

	public void loadFile() {
		// creating file object
		File txtFile = new File("input.txt");

		Scanner sc1;
		try {
			sc1 = new Scanner(txtFile);

			// index of array 'lots'
			int index = 0;

			while (sc1.hasNextLine()) {
				String lineRead = sc1.nextLine();

				if (!(lineRead.equals("EMPTY"))) {
					// a slot's details
					// which has vehicle

					// temporary line elements array
					String[] tempLine = lineRead.split("\t");

					// dateTime is in the String's 4th splitted element
					// splitting further : date, time
					String[] dateTime = tempLine[3].split(" ");

					// split further into individual elements
					// date
					// day, month, year
					String[] date = dateTime[0].split("/");

					int day = Integer.parseInt(date[0]);
					int month = Integer.parseInt(date[1]);
					int year = Integer.parseInt(date[2]);

					// time
					// hour, minutes
					String[] time = dateTime[1].split(":");

					int hour = Integer.parseInt(time[0]);
					int minutes = Integer.parseInt(time[1]);

					// create a dateTime object
					DateTime tempDateTime = new DateTime(day, month, year, hour, minutes);

					// crate common variables for every vehicle
					String idPlateNo = tempLine[1];
					String brand = tempLine[2];
					// [3] DateTime

					if (tempLine[0].equalsIgnoreCase("car")) {
						// car
						int noOfDoors = Integer.parseInt(tempLine[4]);
						String color = tempLine[5];

						// write can to array
						Car c = new Car(idPlateNo, brand, tempDateTime, noOfDoors, color);
						lots[index] = c;

					} else if (tempLine[0].equalsIgnoreCase("van")) {
						// van
						double cargoVolume = Double.parseDouble(tempLine[4]);

						// write van to array
						Van v = new Van(idPlateNo, brand, tempDateTime, cargoVolume);
						lots[index] = v;

					} else {
						// motorbike
						int sizeOfEngine = Integer.parseInt(tempLine[4]);

						// write motorbike to array
						Motorbike m = new Motorbike(idPlateNo, brand, tempDateTime, sizeOfEngine);
						lots[index] = m;
					}

				} else {
					lots[index] = null;
				}
				index++;
			}
			// numOfRooms = index;
			System.out.println(" > File loaded successfully");
		} catch (FileNotFoundException e) {
			System.err.println("File can not be found in appropriate directory");
		}

	}

}
