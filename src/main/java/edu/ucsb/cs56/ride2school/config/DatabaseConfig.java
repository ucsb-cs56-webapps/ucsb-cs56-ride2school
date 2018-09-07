package edu.ucsb.cs56.ride2school.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.ucsb.cs56.ride2school.data.Location;
import edu.ucsb.cs56.ride2school.data.PostData;
import edu.ucsb.cs56.ride2school.data.UserData;

public class DatabaseConfig {

	private String getRequestString() {
		String dbUser = System.getenv().get("USER_");
		String dbPassword = System.getenv().get("PASS_");
		String dbName = System.getenv().get("DB_NAME_");
		String hostName = System.getenv().get("HOST_");

		// Good practice to always obscure sensitive login information

		return "mongodb://" + dbUser + ":" + dbPassword + "@d" + hostName + "/" + dbName;
	}

	public ArrayList<PostData> getAllPosts() {
		System.out.println(getRequestString());
		MongoClientURI uri = new MongoClientURI(getRequestString());
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> posts = db.getCollection("posts");

		List<Document> documents = (List<Document>) posts.find().into(new ArrayList<Document>());
		ArrayList<PostData> allPosts = new ArrayList<PostData>();

		for (Document d : documents) {
			allPosts.add(convertDocumentToPostData(d));
		}

		client.close();
		return allPosts;
	}

	public void addPostToDataBase(PostData post) {
		System.out.println(getRequestString());
		MongoClientURI uri = new MongoClientURI(getRequestString());
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());

		MongoCollection<Document> posts = db.getCollection("posts");

		posts.insertOne(convertPostDataToDocument(post));

		client.close();

	}

	private PostData convertDocumentToPostData(Document doc) {
		Long id = doc.getLong("ID");
		String title = doc.getString("Title");
		Location arrivalLocation = new Location(doc.getString("ArrivingLocationName"));
		Location departingLocation = new Location(doc.getString("DepartingLocationName"));
		Date date = doc.getDate("Date");
		UserData poster = new UserData(doc.getString("PosterName"), doc.getLong("PosterID"));
		Date lastUpdate = doc.getDate("lastUpdate");
		double price = doc.getDouble("price");
		int rideSeats = doc.getInteger("rideSeats");
		int seatsTaken = doc.getInteger("seatsTaken");

		return new PostData(id, title, departingLocation, arrivalLocation, date, poster, lastUpdate, price, rideSeats,
				seatsTaken);
	}

	private Document convertPostDataToDocument(PostData post) {
		Document doc = new Document().append("ID", post.getId()).append("Title", post.getTitle())
				.append("ArrivingLocationName", post.getArrivingLocation().getName())
				.append("DepartingLocationName", post.getDepartingLocation().getName()).append("Date", post.getDate())
				.append("PosterID", post.getPoster().getUserId()).append("PosterName", post.getPoster().getName())
				.append("lastUpdate", post.getLastUpdate()).append("price", post.getPrice())
				.append("rideSeats", post.getRideSeats()).append("seatsTaken", post.getSeatsTaken());
		return doc;
	}

}
