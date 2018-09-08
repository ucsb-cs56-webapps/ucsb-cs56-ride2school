package edu.ucsb.cs56.ride2school.data;

import java.util.List;
import java.util.Random;

import edu.ucsb.cs56.ride2school.config.DatabaseConfig;

public class RandomUserMaker {

	private DatabaseConfig db;
	private Random random;

    //  A list of first names to be used for random name generation
	private static final List<String> firstNamesList = {"James","John","Robert","Michael","William",
	"David","Richard","Charles","Josheph","Thomas","Christopher","Daniel","Paul","Mark","Donald",
	"George","Mary","Patricia","Linda","Barbara","Elizabeth","Jennifer","Maria","Susan","Margaret",
	"Dorothy","Lisa","Nancy","Karen","Betty","Hellen","Sandra"};


	public RandomPostMaker(DatabaseConfig db) {
		this.db = db;
        this.random = new Random();
	}

	public static UserData createRandomUserData() {

        // Determine next available user id
		List<UserData> allUsers = db.getAllPosts();
		long id = allUsers.size() + i;

		// Generate a random name for our poster
		int randomFirstNameIndex = random.nextInt(firstNamesList.size());
		String randomPosterName = firstNamesList.get(randomFirstNameIndex);
		randomPosterName += " " + (char)(random.nextInt(26) + 'a') + ".";
		
        // Create a new UserData object from the randomly generated name and next available id number
        UserData newUserData = new UserData(randomPosterName, id);

        return newUserData
	}

}
