package edu.ucsb.cs56.ride2school.config;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ucsb.cs56.ride2school.data.PostData;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class WebConfig {

	private HashMap<String, Object> map;
	private ArrayList<PostData> posts;

	private DatabaseConfig db;

	public WebConfig(DatabaseConfig db) {
		this.db = db;
		SetUpRoutes();

		testDatabase();
	}

	private void SetUpRoutes() {
		UpdatePosts();

		get("/", (rq, rs) -> new ModelAndView(posts, "feed.mustache"), new MustacheTemplateEngine());

		get("/post", (rq, rs) -> new ModelAndView(map, "post.mustache"), new MustacheTemplateEngine());

		post("/login", (rq, rs) -> new ModelAndView(map, "login.mustache"), new MustacheTemplateEngine());
	}

	private void UpdatePosts() {
		posts = db.getAllPosts();
		posts.sort((p1, p2) -> p1.getLastUpdate().compareTo(p2.getLastUpdate()));
	}

	private void testDatabase() {
		System.out.println("Hello");
		System.out.println(posts.get(0).getTitle());
	}
}
