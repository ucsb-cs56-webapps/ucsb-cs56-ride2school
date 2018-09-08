package edu.ucsb.cs56.ride2school.data;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.ucsb.cs56.ride2school.config.DatabaseConfig;

public class RandomUserMaker {

	//For Later
	private DatabaseConfig db;
	private Random random;

	// A list of first names to be used for random name generation
	private static final List<String> FIRST_NAMES_LIST = Arrays.asList("James", "John", "Robert", "Michael", "William",
			"David", "Richard", "Charles", "Josheph", "Thomas", "Christopher", "Daniel", "Paul", "Mark", "Donald",
			"George", "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer", "Maria", "Susan", "Margaret",
			"Dorothy", "Lisa", "Nancy", "Karen", "Betty", "Hellen", "Sandra");

	public RandomUserMaker(DatabaseConfig db) {
		this.db = db;
		this.random = new Random();
	}

	public UserData createRandomUserData() {

		// Determine next available user id
		long id = System.currentTimeMillis();

		// Generate a random name for our poster
		int randomFirstNameIndex = random.nextInt(FIRST_NAMES_LIST.size());
		String randomPosterName = FIRST_NAMES_LIST.get(randomFirstNameIndex);
		randomPosterName += " " + (char) (random.nextInt(26) + 'a') + ".";

		// Create a new UserData object from the randomly generated name and
		// next available id number
		UserData newUserData = new UserData(randomPosterName, id);

		return newUserData;
	}

}
