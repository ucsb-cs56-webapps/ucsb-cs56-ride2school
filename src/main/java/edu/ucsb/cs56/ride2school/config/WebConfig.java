package edu.ucsb.cs56.ride2school.config;

import static spark.Spark.get;
import static spark.Spark.post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.bson.types.ObjectId;

import Util.RandomPost;
import Util.RandomUser;
import edu.ucsb.cs56.ride2school.data.Location;
import edu.ucsb.cs56.ride2school.data.PostData;
import edu.ucsb.cs56.ride2school.data.UserData;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import org.bson.Document;

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

		post("/posts/add", (rq, rs) -> {
			System.out.println(rq.body());

			ArrayList<UserData> users = DatabaseConfig.instance.getAllUsers();
			UserData poster = users.get(new Random().nextInt(users.size()));

			try {
				Map<String, String> info = rq.params();

				String title = info.get("title");
				Location departingLocation = new Location(info.get("departingLocation"));
				Location arrivingLocation = new Location(info.get("arrivingLocation"));
				SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
				Date date = format.parse(info.get("date"));
				int seats = Integer.parseInt(info.get("seats"));

				PostData post = new PostData(title, departingLocation, arrivingLocation, date, poster, new Date(), 20.0,
						seats, seats);
				DatabaseConfig.instance.addToDatabase(post);
			} catch (Exception e) {
				System.out.println("Improper Format");
				rs.redirect("/form/posts");
			}
			rs.redirect("/");
			return null;
		});

		/*
		 * post("/posts/add", (rq, rs) -> {
		 *
		 * String title = ""; Location departingLocation = null; Location
		 * arrivingLocation = null; Date date = null; int seats = 0; UserData
		 * poster = null; try { title = rq.params("title"); departingLocation =
		 * new Location(rq.params("departingLocation")); arrivingLocation = new
		 * Location(rq.params("arrivingLocation")); date = new
		 * Date(rq.params("date")); seats =
		 * Integer.parseInt(rq.params("seats"));
		 *
		 * ArrayList<UserData> users = DatabaseConfig.instance.getAllUsers();
		 * poster = users.get(new Random().nextInt(users.size())); } catch
		 * (Exception e) { e.printStackTrace(); }
		 *
		 * DatabaseConfig.instance.addToDatabase(new PostData(title,
		 * departingLocation, arrivingLocation, date, poster, new Date(), 20.0,
		 * seats, seats)); System.out.println("Added new Post!");
		 * rs.redirect("/posts/"); return null; });
		 */

		get("/posts/:postID/edit", (rq, rs) -> {
			PostData post = DatabaseConfig.instance.getPostByID(new ObjectId(rq.params(":postID")));
    	Map<String, Object> map = new HashMap<>();
			map.put("post", post);

			return new ModelAndView(map, "editpost.mustache");
		}, new MustacheTemplateEngine());

		post("/posts/:postID/edit", (rq, rs) -> {
			System.out.println("hi");
			System.out.println(rq.body());
			Map<String, String> info = rq.params();
			PostData post = DatabaseConfig.instance.getPostByID(new ObjectId(rq.params(":postID")));
			post.setDepartingLocation(new Location(info.get("departure")));
			post.setArrivingLocation(new Location(info.get("arriving")));
		//	post.setDate(info.get("date"));
			post.setSeatsTaken(Integer.parseInt(info.get("seats taken")));
			post.setRideSeats(Integer.parseInt(info.get("total seats")));
			post.setPrice(Double.parseDouble(info.get("cost")));
			DatabaseConfig.instance.modifyDatabaseObject(post);

			System.out.println("hello");
			rs.redirect("/posts/"+ post.getID()+"/view");
			System.out.println("sup");
			return new Document().append("auth","OK");
		});


		get("/posts/:postID/delete", (rq, rs) -> {
			rs.redirect("/");
			try {
				DatabaseConfig.instance
						.deleteDatabaseObject(DatabaseConfig.instance.getPostByID(new ObjectId(rq.params(":postID"))));
			} catch (Exception e) {
				// USER clicked button twice while other was deleting
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
