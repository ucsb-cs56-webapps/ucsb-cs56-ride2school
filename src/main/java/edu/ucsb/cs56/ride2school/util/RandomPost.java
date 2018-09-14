package edu.ucsb.cs56.ride2school.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.lang.Math;

import edu.ucsb.cs56.ride2school.config.DatabaseConfig;
import edu.ucsb.cs56.ride2school.data.Location;
import edu.ucsb.cs56.ride2school.data.PostData;
import edu.ucsb.cs56.ride2school.data.UserData;

public class RandomPost {

	private static Random rand = new Random();

	private static final List<Location> locations = Arrays.asList(new Location("UCSB"), new Location("UCSF"),
			new Location("UCSC"), new Location("UCB"), new Location("UCSD"), new Location("UCD"), new Location("UCLA"),
			new Location("UCI"), new Location("UCR"), new Location("UCM"));

	public static PostData createRandomPost(double maxPrice, int maxSeats) {
		String title = "Finding Ride " + rand.nextInt(100);

		Location departingLocation = locations.get(rand.nextInt(locations.size()));

		Location arrivingLocation = departingLocation;
		while (arrivingLocation.getName() == departingLocation.getName()) {
			arrivingLocation = locations.get(rand.nextInt(locations.size()));
		}

		// Generate a random date
		Date date = new Date(-946771200000L + (Math.abs(rand.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000)));

		// Generate a random name for our poster
		ArrayList<UserData> users = DatabaseConfig.instance.getAllUsers(); 
		UserData poster = users.get(rand.nextInt(users.size()));

		Date lastUpdate = new Date(System.currentTimeMillis());

		double price = Math.round(rand.nextDouble() * maxPrice * 100) / 100.0;

		int rideSeats = rand.nextInt(maxSeats) + 1;
		int seatsTaken = rand.nextInt(rideSeats);

		return new PostData(title, departingLocation, arrivingLocation, date, poster, lastUpdate, price, rideSeats,
				seatsTaken);
	}
}
