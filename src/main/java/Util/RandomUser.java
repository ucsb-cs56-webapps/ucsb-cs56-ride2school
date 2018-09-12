package Util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.ucsb.cs56.ride2school.data.UserData;

public class RandomUser {

	private static Random rand = new Random();

	private static final List<String> FIRST_NAMES_LIST = Arrays.asList("James", "John", "Robert", "Michael", "William",
			"David", "Richard", "Charles", "Josheph", "Thomas", "Christopher", "Daniel", "Paul", "Mark", "Donald",
			"George", "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer", "Maria", "Susan", "Margaret",
			"Dorothy", "Lisa", "Nancy", "Karen", "Betty", "Hellen", "Sandra");

	public static UserData createRandomUser() {
		// Create a new UserData object from the randomly generated name and set password.
		char lastNameCharacter = (char) Character.toUpperCase(rand.nextInt(26) + 'a');
		return new UserData(
				FIRST_NAMES_LIST.get(rand.nextInt(FIRST_NAMES_LIST.size())) + " " + lastNameCharacter + ".", "pass");
	}
}
