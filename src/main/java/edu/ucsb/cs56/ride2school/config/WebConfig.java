package edu.ucsb.cs56.ride2school.config;

import static spark.Spark.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.RandomPost;
import Util.RandomUser;
import edu.ucsb.cs56.ride2school.data.PostData;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class WebConfig {

	private DatabaseConfig db;

	private boolean testingMode = true;

	public WebConfig(DatabaseConfig db) {
		this.db = db;
		if (testingMode) {
			System.out.println("Hi");
			newUsers();
			newPosts();
		}
		SetUpRoutes();
	}

	private void newUsers() {
		int minUsers = 50;
		while (db.getAllUsers().size() < minUsers) {
			System.out.println("New User");
			db.addToDatabase(RandomUser.createRandomUser());
		}
	}

	private void newPosts() {
		int minPosts = 50;
		while (db.getAllPosts().size() < minPosts) {
			System.out.println("New Post");
			db.addToDatabase(RandomPost.createRandomPost(db.getAllUsers(), 100.00, 12));
		}
	}

	private void SetUpRoutes() {

		get("/", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("posts", getPosts());
			return new ModelAndView(map, "feed.mustache");
		}, new MustacheTemplateEngine());

		get("/form/post", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			return new ModelAndView(map, "post.mustache");
		}, new MustacheTemplateEngine());

		get("/form/post/:postID/edit", (rq, rs) -> {
			PostData post = null;
			for (PostData p : getPosts()) {
				if (p.getID().toHexString() == rq.params(":postID")) {
					post = p;
				}
			}

			if (post == null)
				System.out.println("Cannot find any post of ID: " + rq.params(":postID"));

			Map<String, Object> map = new HashMap<>();
			map.put("post", post);

			return new ModelAndView(map, "editpost.mustache");
		}, new MustacheTemplateEngine());

		get("/login", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			return new ModelAndView(map, "login.mustache");
		}, new MustacheTemplateEngine());
	}

	private List<PostData> getPosts() {
		List<PostData> posts = db.getAllPosts();
		posts.sort((p1, p2) -> p1.getLastUpdate().compareTo(p2.getLastUpdate()));
		return posts;
	}
}
