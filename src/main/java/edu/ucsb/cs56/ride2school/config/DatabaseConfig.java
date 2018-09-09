package edu.ucsb.cs56.ride2school.config;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
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

		MongoClientURI uri = new MongoClientURI(getRequestString());
		client = new MongoClient(uri);
		db = client.getDatabase(uri.getDatabase());
	}

	// Closes Stream on GarbageCollection
	public void finalize() {
		client.close();
	}

	private String getRequestString() {
		String dbUser = System.getenv().get("USER_");
		String dbPassword = System.getenv().get("PASS_");
		String dbName = System.getenv().get("DB_NAME_");
		String hostName = System.getenv().get("HOST_");

		// Good practice to always obscure sensitive login information

		return "mongodb://" + dbUser + ":" + dbPassword + "@d" + hostName + "/" + dbName;
	}

	// Gets all posts currently in database
	// Returns an ArrayList with all the Posts

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
		MongoCollection<Document> dbData = db.getCollection(data.getCollectionName());
		dbData.insertOne(data.convertToDocument());
	}

}
