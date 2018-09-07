package edu.ucsb.cs56.ride2school;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConfig {

	private ArrayList<PostData> initDatabase() {
		return null;
	}

	private void createDatabase() {

		// Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname

		String dbUser = System.getenv().get("USER_");
		String dbPassword = System.getenv().get("PASS_");
		String dbName = System.getenv().get("DB_NAME_");
		String hostName = System.getenv().get("HOST_");

		// Good practice to always obscure sensitive login information

		String request = "mongodb://" + dbUser + ":" + dbPassword + "@" + hostName + "/" + dbName;

		MongoClientURI uri = new MongoClientURI(request);
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
	}

	private void addPostToDataBase(PostData post) {
		// Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname

		String dbUser = System.getenv().get("USER_");
		String dbPassword = System.getenv().get("PASS_");
		String dbName = System.getenv().get("DB_NAME_");
		String hostName = System.getenv().get("HOST_");

		// Good practice to always obscure sensitive login information

		String request = "mongodb://" + dbUser + ":" + dbPassword + "@" + hostName + "/" + dbName;

		MongoClientURI uri = new MongoClientURI(request);
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());

		MongoCollection<Document> posts = db.getCollection("songs");

		posts.insertOne(convertPostDataToDocument(post));
	}

	private Document convertPostDataToDocument(PostData post) {
		Document doc = new Document().append("ID", post.getId()).append("Title", post.getTitle())
				.append("ArrivingLocationName", post.getArrivingLocation().getName())
				.append("DepartingLocationName", post.getDepartingLocation().getName());
		return doc;
	}

}
