package edu.ucsb.cs56.ride2school.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.ucsb.cs56.ride2school.config.DatabaseConfig;

public class RandomPostMaker {

	private DatabaseConfig db;
	private Random random = new Random();

	private int maxSeats;
	private double maxPrice;

	public RandomPostMaker(DatabaseConfig db) {
		this.db = db;
	}

	public List<PostData> createRandomPosts(int amount) {
		List<PostData> posts = new ArrayList<>();
		List<PostData> allPosts = db.getAllPosts();
		for (int i = 0; i < amount; i++) {
			long id = allPosts.size() + i;
			String title = "Test " + id;

			Location departingLocation = locations.get(random.nextInt(locations.size()));

			Location arrivingLocation = departingLocation;
			while (arrivingLocation.getName() == departingLocation.getName()) {
				locations.get(random.nextInt(locations.size()));
			}


			// Generate a random date
			Date date = new Date(-946771200000L + (Math.abs(random.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000)));


			// Generate a random name for our poster
			UserData poster = new RandomUserMaker(db).createRandomUserData();


			//Generate a random date for our post

			Date lastUpdate = new Date(System.currentTimeMillis());

			double price = random.nextDouble() * maxPrice;
			int rideSeats = random.nextInt(maxSeats + 1);
			int seatsTaken = random.nextInt(rideSeats);

			posts.add(new PostData(id, title, departingLocation, arrivingLocation, date, poster, lastUpdate, price,
					rideSeats, seatsTaken));
		}
		return posts;
	}

	private List<Location> locations = Arrays.asList(new Location("UCSB"), new Location("UCSF"));
}
