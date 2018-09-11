package edu.ucsb.cs56.ride2school;

import static spark.Spark.port;

import org.apache.log4j.Logger;

import edu.ucsb.cs56.ride2school.config.DatabaseConfig;
import edu.ucsb.cs56.ride2school.config.WebConfig;

/**
 * Simple example of using Mustache Templates
 *
 */

public class Ride2School {

	public static final String CLASSNAME = new Object() {
	}.getClass().getEnclosingClass().getName();

	public static final Logger log = Logger.getLogger(CLASSNAME);

	public static void main(String[] args) {

		port(getHerokuAssignedPort());

		System.out.println("http://localhost:" + getHerokuAssignedPort());
		
		new DatabaseConfig();
		
		new WebConfig();

	}

	private static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; // return default port if heroku-port isn't set (i.e. on
				     // localhost)
	}

}
