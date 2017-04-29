package carParkingSystem;

import java.util.Scanner;

public class DateTime {
	// date related variables
	private int day;
	private int month;
	private int year;

	// time related variables
	private int hours;
	private int minutes;

	// constructor
	public DateTime(int day, int month, int year, int hour, int minutes) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
		this.hours = hour;
		this.minutes = minutes;
	}

	/*
	 * // TEST public static void main(String[] args) { DateTime r = new
	 * DateTime(31, 12, 2015, 23, 45); DateTime s = new DateTime(1, 1, 2016, 0,
	 * 01);
	 * 
	 * // Returns minutes System.out.println(r.subtractFrom(s)); }
	 */

	// methods

	public int getDay() {
		return day;
	}

	public void setDay(Scanner s) {
		// year and month is anyhow set before

		// month
		int m = this.month;

		// end date of month
		int e = 31;

		if (m == 2) {
			// february
			if (this.year % 4 == 0) {
				// leap : 29 days
				e = 29;

			} else {
				// non-leap : 28 days
				e = 28;
			}

		} else if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
			// 31 days
			e = 31;

		} else {
			// 30 days
			e = 30;

		}
		this.day = validate(s, "Day", 1, e);
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(Scanner s) {
		this.month = validate(s, "Month", 1, 12);
	}

	public int getYear() {
		return year;
	}

	public void setYear(Scanner s) {
		this.year = validate(s, "Year", 1900, 2016);
	}

	public int getHours() {
		return hours;
	}

	public void setHours(Scanner s) {
		this.hours = validate(s, "Hours", 0, 23);
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(Scanner s) {
		this.minutes = validate(s, "Minutes", 0, 59);
	}

	// setters when values are not gained through input
	// no validation

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setHour(int hours) {
		this.hours = hours;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * validates day / month / year with specific ranges scanner is to input
	 */
	public int validate(Scanner s, String valElement, int lowEnd, int highEnd) {
		int val = -1;
		boolean isFirstIteration = true;
		do {
			if (isFirstIteration) {
				// first iteration - no error. prompt only
				System.out.print("Enter " + valElement + " : ");
			} else {
				// invalid range of number
				System.err.print(
						valElement + " should be in range of " + lowEnd + "-" + highEnd + ". Please re-enter : ");
			}
			while (!s.hasNextInt()) {
				// not a number
				System.err.print(valElement + " should be a number. Please re-enter : ");
				s.next();
			}
			val = s.nextInt();
			isFirstIteration = false;
		} while (!(val >= lowEnd && val <= highEnd));
		return val;

	}

	public String getDate() {
		// return date in format DD/MM/YYYY
		return String.format("%02d/%02d/%04d", day, month, year);
	}

	public String getTime() {
		// return time in format HH:MM
		return String.format("%02d:%02d", hours, minutes);
	}

	public boolean isEarlierThan(DateTime d) {
		// if THIS date is earlier than the arguement date =
		// then return true

		if (this.year == d.year) {
			// years are same. go for month
			if (this.month == d.month) {
				// months are same. go for days
				if (this.day == d.day) {
					// days are same. go for hours
					if (this.hours == d.hours) {
						// hours are same. go for minutes
						if (this.minutes == d.minutes) {
							// minutes are same
							// same time
							// either true or false is acceptable for return
							return false;

						} else {
							return (this.minutes < d.minutes);
						}

					} else {
						return (this.hours < d.hours);
					}

				} else {
					return (this.day < d.day);
				}

			} else {
				return (this.month < d.month);
			}

		} else {
			return (this.year < d.year);
		}
	}

	/**
	 * Converts time to minutes from 00/00/0000 00:00
	 * 
	 * @return
	 */
	public long convertToMinutes() {
		long retMinutes = 0;

		// minutes
		retMinutes += this.minutes;

		// hours
		retMinutes += this.hours * 60;

		// year
		// leap year = 527040 minutes
		// non-leap year = 525600 minutes
		for (int y = 0; y < this.year; y++) {
			if (y % 4 == 0) {
				// leap year
				retMinutes += 527040;
			} else {
				// non-leap year
				retMinutes += 525600;
			}
		}

		// day
		retMinutes += (this.day - 1) * 1440;

		// MONTH [m]
		// JAN[1] , FEB[2] , MAR[3] , APR[4] , MAY[5] , JUN[6] , JUL[7] , AUG[8]
		// , SEP[9] , OCT[10] , NOV[11] , DEC[12]
		// 31 , 28/29 , 31 , 30 , 31 , 30 , 31 , 31 , 30 , 31 , 30 , 31

		// month

		for (int m = 1; m < this.month; m++) {
			if (m == 2) {
				// february
				if (this.year % 4 == 0) {
					// leap : 29 days
					retMinutes += 29 * 1440;
				} else {
					// non-leap : 28 days
					retMinutes += 28 * 1440;
				}

			} else if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
				// 31 days
				retMinutes += 31 * 1440;
			} else {
				// 30 days
				retMinutes += 30 * 1440;
			}
		}

		return retMinutes;
	}

	/**
	 * Returns subtracted minutes
	 * 
	 * @param d
	 *            Later DateTime
	 * @return Time difference in minutes
	 */
	public long subtractFrom(DateTime d) {

		return d.convertToMinutes() - this.convertToMinutes();

	}

	@Override
	public String toString() {
		return (getDate() + " " + getTime());
	}

}
