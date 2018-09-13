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

		MongoClientURI uri = new MongoClientURI(requestString);
		client = new MongoClient(uri);
		db = client.getDatabase(uri.getDatabase());

		// Test Connection Works
		try {
			db.runCommand(new Document().append("connectionStatus", 1).append("showPrivileges", false));
			System.out.println("Finished setting up Database");
		} catch (MongoTimeoutException e) {
			/*
			 * If you get this error make sure the env.sh file is set up
			 * correctly
			 * 
			 * Correct Info:
			 * 
			 * export USER_=YOURUSERNAME export PASS_=YOURPASSWORD export
			 * DB_NAME_=YOURDBNAME export HOST_=YOURHOSTURL
			 * 
			 */
			System.err.println("Failed to connect to Database");
			System.err.println("Tried connecting using: " + requestString);
			System.exit(0);
		}
	}

	// Closes Stream on GarbageCollection
	public void finalize() {
		System.out.println("Closing Database");
		client.close();
	}

	// Returns an ArrayList of all the posts
	public ArrayList<PostData> getAllPosts() {
		MongoCollection<Document> posts = db.getCollection("PostData");

		List<Document> documents = (List<Document>) posts.find().into(new ArrayList<Document>());
		ArrayList<PostData> allPosts = new ArrayList<PostData>();

		for (Document d : documents) {
			allPosts.add(new PostData(d));
		}
		return allPosts;
	}

	// Returns an ArrayList of all the users
	public ArrayList<UserData> getAllUsers() {
		MongoCollection<Document> users = db.getCollection("UserData");

		List<Document> documents = (List<Document>) users.find().into(new ArrayList<Document>());
		ArrayList<UserData> allUsers = new ArrayList<UserData>();

		for (Document d : documents) {
			allUsers.add(new UserData(d));
		}
		return allUsers;
	}

	// Returns a user from arraylist
	public UserData getUserByName(String name, ArrayList<UserData> users) {
		System.out.println("Searching for: " + name);
		for(UserData user : users){
			System.out.println("Comparing " + "\"" + name  + "\"" + " with " + "\"" + user.getName() + "\"");
		    if(user.getName().equals(name)){
		    	System.out.println("Name found!");
		    	return user;
		    }
		}
		return null;

	}

	// Returns a post from database based upon id
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

	// Returns a user from database based upon id
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

	// Modifies an object in database
	public void modifyDatabaseObject(StoreableData data) {
		MongoCollection<Document> collection = db.getCollection(data.getCollectionName());
		try {
			collection.findOneAndUpdate(new Document("_id", data.getID()), data.convertToDocument());
		} catch (Exception e) {
			System.out.println("Object with ID: " + data.getID() + " no longer exists");
		}
	}

	// Deletes an object from database
	public void deleteDatabaseObject(StoreableData data) {
		MongoCollection<Document> collection = db.getCollection(data.getCollectionName());
		try {
			collection.findOneAndDelete(new Document("_id", data.getID()));
		} catch (Exception e) {
			System.out.println("Object with ID: " + data.getID() + " no longer exists");
		}
	}

	// Puts the data object into correct Database Collection
	public void addToDatabase(StoreableData data) {
		MongoCollection<Document> collection = db.getCollection(data.getCollectionName());
		collection.insertOne(data.convertToDocument());
	}

	

}
