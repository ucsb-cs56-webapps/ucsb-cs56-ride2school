package edu.ucsb.cs56.ride2school.config;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;

import edu.ucsb.cs56.ride2school.data.PostData;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class WebConfig {

	private HashMap<String, Object> map;

	private DatabaseConfig db;

	public WebConfig(DatabaseConfig db) {
		this.db = db;
		SetUpRoutes();

		testDatabase();
	}

	private void SetUpRoutes() {
		get("/", (rq, rs) -> new ModelAndView(db.getAllPosts(), "feed.mustache"), new MustacheTemplateEngine());

		post("/form/post", (rq, rs) -> new ModelAndView(map, "post.mustache"), new MustacheTemplateEngine());

		post("/login", (rq, rs) -> new ModelAndView(map, "login.mustache"), new MustacheTemplateEngine());
	}

	private void testDatabase() {
		db.addPostToDataBase(new PostData(1L));
		
		System.out.println("Hello");
		System.out.println(db.getAllPosts().get(0).getTitle());
	}
}
