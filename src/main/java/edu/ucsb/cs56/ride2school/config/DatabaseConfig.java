package edu.ucsb.cs56.ride2school.config;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.ucsb.cs56.ride2school.data.PostData;
import edu.ucsb.cs56.ride2school.data.StoreableData;
import edu.ucsb.cs56.ride2school.data.UserData;

public class DatabaseConfig {

	public static DatabaseConfig instance = null;

	private MongoClient client;
	private MongoDatabase db;

	public DatabaseConfig() {
		if (DatabaseConfig.instance != null)
			System.out.println("You can't have two instances of DatabaseConfig");
		DatabaseConfig.instance = this;
		setUpDatabase();
	}

	private void setUpDatabase() {
		System.out.println("Setting up Database");

		String dbUser = System.getenv().get("USER_");
		String dbPassword = System.getenv().get("PASS_");
		String dbName = System.getenv().get("DB_NAME_");
		String hostName = System.getenv().get("HOST_");

		String requestString = "mongodb://" + dbUser + ":" + dbPassword + "@d" + hostName + "/" + dbName;
		try {
			MongoClientURI uri = new MongoClientURI(requestString);
			client = new MongoClient(uri);
			db = client.getDatabase(uri.getDatabase());
			System.out.println("Finished setting up Database");
			db.listCollectionNames();
		} catch (MongoTimeoutException e) {
			System.err.println("Failed to connect to Database");
			System.out.println("Tried to connect using: " + requestString);
		}
	}

	// Closes Stream on GarbageCollection
	public void finalize() {
		System.out.println("Closing Database");
		client.close();
	}

	public ArrayList<PostData> getAllPosts() {
		MongoCollection<Document> posts = db.getCollection("PostData");

		List<Document> documents = (List<Document>) posts.find().into(new ArrayList<Document>());
		ArrayList<PostData> allPosts = new ArrayList<PostData>();

		for (Document d : documents) {
			allPosts.add(new PostData(d));
		}
		return allPosts;
	}

	public ArrayList<UserData> getAllUsers() {
		MongoCollection<Document> users = db.getCollection("UserData");

		List<Document> documents = (List<Document>) users.find().into(new ArrayList<Document>());
		ArrayList<UserData> allUsers = new ArrayList<UserData>();

		for (Document d : documents) {
			allUsers.add(new UserData(d));
		}
		return allUsers;
	}

	public PostData getPostByID(ObjectId id) {
		MongoCollection<Document> posts = db.getCollection("PostData");

		PostData post = null;
		try {
			post = new PostData(posts.find(new Document("_id", id)).first());
		} catch (Exception e) {
			System.out.println("Post with ID: " + id + " no longer exists");
		}
		return post;
	}

	public UserData getUserByID(ObjectId id) {
		MongoCollection<Document> users = db.getCollection("UserData");
		UserData user = null;
		try {
			user = new UserData(users.find(new Document("_id", id)).first());
		} catch (Exception e) {
			System.out.println("User with ID: " + id + " no longer exists");
		}
		return user;
	}

	// Puts the data object into correct Database Collection
	public void addToDatabase(StoreableData data) {
		MongoCollection<Document> collection = db.getCollection(data.getCollectionName());
		collection.insertOne(data.convertToDocument());
	}

}
