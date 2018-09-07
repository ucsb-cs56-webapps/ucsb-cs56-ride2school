package edu.ucsb.cs56.ride2school;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.HashMap;

import org.apache.log4j.Logger;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

/**
 * Simple example of using Mustache Templates
 *
 */

public class Ride2School {

	public static final String CLASSNAME = new Object() {
	}.getClass().getEnclosingClass().getName();;

	public static final Logger log = Logger.getLogger(CLASSNAME);

	public static void main(String[] args) {

		port(getHerokuAssignedPort());

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Sam");

		// hello.mustache file is in resources/templates directory
		get("/", (rq, rs) -> new ModelAndView(map, "feed.mustache"), new MustacheTemplateEngine());

		get("/form/post", (rq, rs) -> new ModelAndView(map, "post.mustache"), new MustacheTemplateEngine());

		post("/login", (rq, rs) -> new ModelAndView(map, "login.mustache"), new MustacheTemplateEngine());

	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; // return default port if heroku-port isn't set (i.e. on
						// localhost)
	}

}
