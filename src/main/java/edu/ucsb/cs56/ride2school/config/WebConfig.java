package edu.ucsb.cs56.ride2school.config;

import static spark.Spark.get;
import static spark.Spark.put;
import static spark.Spark.delete;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import Util.RandomPost;
import Util.RandomUser;
import edu.ucsb.cs56.ride2school.data.PostData;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class WebConfig {

	private boolean testingMode = true;

	public WebConfig() {
		System.out.println("Setting up Pages");
		if (testingMode) {
			newUsers();
			newPosts();
		}
		SetUpRoutes();
		System.out.println("Finished setting up pages");
	}

	private void newUsers() {
		int minUsers = 50;
		System.out.println("Generating Random Users");
		while (DatabaseConfig.instance.getAllUsers().size() < minUsers) {
			DatabaseConfig.instance.addToDatabase(RandomUser.createRandomUser());
		}
		System.out.println("Done Generating Random Users");
	}

	private void newPosts() {
		int minPosts = 50;
		System.out.println("Generating Random Posts");
		while (DatabaseConfig.instance.getAllPosts().size() < minPosts) {
			DatabaseConfig.instance.addToDatabase(RandomPost.createRandomPost(100.00, 12));
		}
		System.out.println("Done Generating Random Posts");
	}

	private void SetUpRoutes() {
		get("/", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();

			List<PostData> posts = DatabaseConfig.instance.getAllPosts();
			posts.sort((p1, p2) -> p1.getLastUpdate().compareTo(p2.getLastUpdate()));

			map.put("posts", posts);

			return new ModelAndView(map, "feed.mustache");
		}, new MustacheTemplateEngine());

		get("/form/post", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			return new ModelAndView(map, "post.mustache");
		}, new MustacheTemplateEngine());

		get("/posts/:postID/edit", (rq, rs) -> {
			System.out.println("hi");
			PostData post = DatabaseConfig.instance.getPostByID(new ObjectId(rq.params(":postID")));

			Map<String, Object> map = new HashMap<>();
			map.put("post", post);

			return new ModelAndView(map, "editpost.mustache");
		}, new MustacheTemplateEngine());

		get("/posts/:postID/delete", (rq, rs) -> {
			try
			{
			DatabaseConfig.instance
					.deleteDatabaseObject(DatabaseConfig.instance.getPostByID(new ObjectId(rq.params(":postID"))));
			} finally {
			rs.redirect("/");
			}
			return "<h2> Post DELETED <h2>";
		});

		get("/posts/:postID/view", (rq, rs) -> {
			PostData post = DatabaseConfig.instance.getPostByID(new ObjectId(rq.params(":postID")));

			Map<String, Object> map = new HashMap<>();
			map.put("post", post);

			return new ModelAndView(map, "viewpost.mustache");
		}, new MustacheTemplateEngine());

		get("/login", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			return new ModelAndView(map, "login.mustache");
		}, new MustacheTemplateEngine());
	}
}
