package edu.ucsb.cs56.ride2school.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.ucsb.cs56.ride2school.config.DatabaseConfig;
import edu.ucsb.cs56.ride2school.data.RandomUserMaker;

public class RandomPostMaker {

	private static final List<String> firstNamesList = {"James","John","Robert","Michael","William",
	"David","Richard","Charles","Josheph","Thomas","Christopher","Daniel","Paul","Mark","Donald",
	"George","Mary","Patricia","Linda","Barbara","Elizabeth","Jennifer","Maria","Susan","Margaret",
	"Dorothy","Lisa","Nancy","Karen","Betty","Hellen","Sandra"};

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


			Date date = new Date();


			// Generate a random name for our poster
			int randomFirstNameIndex = random.nextInt(firstNamesList.size());
			String randomPosterName = firstNamesList.get(randomFirstNameIndex);
			randomPosterName += " " + (char)(random.nextInt(26) + 'a') + ".";
			UserData poster = new UserData(randomPosterName, 1L);


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
	private List<UserData> users = Arrays.asList()
}
