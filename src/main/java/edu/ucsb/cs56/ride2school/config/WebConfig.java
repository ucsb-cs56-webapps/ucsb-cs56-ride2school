package edu.ucsb.cs56.ride2school.config;

import static spark.Spark.get;
import static spark.Spark.post;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Random;

import org.bson.types.ObjectId;

import edu.ucsb.cs56.ride2school.data.Location;
import edu.ucsb.cs56.ride2school.data.PostData;
import edu.ucsb.cs56.ride2school.data.UserData;
import edu.ucsb.cs56.ride2school.util.RandomPost;
import edu.ucsb.cs56.ride2school.util.RandomUser;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import org.bson.Document;

public class WebConfig {

	private boolean testingMode = true;

	public WebConfig() {
		System.out.println("Setting up Pages");
		if (testingMode) {
			deleteAllPostsAndUsers();
			newUsers();
			newPosts();
		}
		SetUpRoutes();
		System.out.println("Finished setting up pages");
	}

	private void deleteAllPostsAndUsers(){

		for(UserData ud : DatabaseConfig.instance.getAllUsers()){
			//System.out.println("Deleting user: " + ud.getID());
			DatabaseConfig.instance.deleteDatabaseObject(ud);
		}

		for(PostData pd : DatabaseConfig.instance.getAllPosts()){
			//System.out.println("Deleting post: " + pd.getID());
			DatabaseConfig.instance.deleteDatabaseObject(pd);
		}

		System.out.println("Done deleting old posts and users");

	}

	private void newUsers() {
		int minUsers = 10;
		System.out.println("Generating Random Users");
		while (DatabaseConfig.instance.getAllUsers().size() < minUsers) {
			DatabaseConfig.instance.addToDatabase(RandomUser.createRandomUser());
		}
		System.out.println("Done Generating Random Users");
	}

	private void newPosts() {
		int minPosts = 10;
		System.out.println("Generating Random Posts");
		while (DatabaseConfig.instance.getAllPosts().size() < minPosts) {
			DatabaseConfig.instance.addToDatabase(RandomPost.createRandomPost(100.00, 4));
		}
		System.out.println("Done Generating Random Posts");
	}

	private void SetUpRoutes() {
		get("/", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();

			List<PostData> posts = DatabaseConfig.instance.getAllPosts();
			posts.sort((p1, p2) -> p2.getLastUpdate().compareTo(p1.getLastUpdate()));

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
				SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
				Date date = format.parse(info.get("date"));
				int seats = Integer.parseInt(info.get("seats"));

				PostData post = new PostData(title, departingLocation, arrivingLocation, date, poster, new Date(), 20.0,
						seats, seats);
				DatabaseConfig.instance.addToDatabase(post);
			} catch (Exception e) {
				System.out.println("Improper Format");
				rs.redirect("/form/post");
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
			System.out.println("yo");
			PostData post = DatabaseConfig.instance.getPostByID(new ObjectId(rq.params(":postID")));
    		Map<String, Object> map = new HashMap<>();
			map.put("post", post);

			return new ModelAndView(map, "editpost.mustache");
		}, new MustacheTemplateEngine());

		post("/posts/:postID/edit", (rq, rs) -> {
			rq.queryParams(); // Initial call here seems necessary or else queryParams() appears null
			System.out.println("Editing function...");
			// System.out.println("rq.body(): " + rq.body());
			// System.out.println("rq.params(): " + rq.params());
			System.out.println("rq.queryParams(): " + rq.queryParams());
			// System.out.println("rq.queryParams(seatstaken): " + rq.queryParams("seatstaken"));
			
			PostData post = DatabaseConfig.instance.getPostByID(new ObjectId(rq.params(":postID")));
			//System.out.println("Converted document before updating values with set: " + post.convertToDocument());
			post.setDepartingLocation(new Location(rq.queryParams("departure")));
			post.setArrivingLocation(new Location(rq.queryParams("arriving")));

			//Consider changing the data formats to be more user friendly for editing. Or show a clickable calendar.
			SimpleDateFormat format = new SimpleDateFormat("EEE MMMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
			post.setDate(format.parse(rq.queryParams("date")));
			post.setSeatsTaken(Integer.parseInt(rq.queryParams("seats taken")));
			post.setRideSeats(Integer.parseInt(rq.queryParams("total seats")));			
			post.setPrice(Double.parseDouble(rq.queryParams("cost")));
			post.setLastUpdate(new Date());
			System.out.println("Document after setting: " + post.convertToDocument());
			
			// To do: get this modifyDatabaseObject method working instead of deleting and readding.
			// DatabaseConfig.instance.modifyDatabaseObject(post);

			System.out.println("deleting");
			DatabaseConfig.instance.deleteDatabaseObject(post);
			System.out.println("readding");
			DatabaseConfig.instance.addToDatabase(post);

			//rs.redirect("/");
			rs.redirect("/posts/"+ post.getID()+"/view");

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
		get("/recovery", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			return new ModelAndView(map, "recovery.mustache");
		}, new MustacheTemplateEngine());
		get("/signup", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			return new ModelAndView(map, "signup.mustache");
		}, new MustacheTemplateEngine());
		post("/signup/newUser", (rq, rs) ->{
			UserData newUser = new UserData(rq.queryParams("name"),rq.queryParams("tempPassword"));
			DatabaseConfig.instance.addToDatabase(newUser);
			rs.redirect("/login");
			return null;
		});
		//This route is a stub for testing authentication
		get("/login/authentication/authenticated", (rq,rs) -> {
			Map<String, Object> map = new HashMap<>();
			return new ModelAndView(map, "authenticated.mustache");
		}, new MustacheTemplateEngine());
		post("/login/authentication", (rq, rs) ->{
			ArrayList<UserData> users = DatabaseConfig.instance.getAllUsers();
			String enteredName = rq.queryParams("username");
			String enteredPassword = rq.queryParams("password");
			UserData user = DatabaseConfig.instance.getUserByName(enteredName, users);
			if(user.getTempPassword() == enteredPassword){
				
			}
			else{
				rs.redirect("/login/authentication/authenticated");
			}
			return null;
		});
	}
}
