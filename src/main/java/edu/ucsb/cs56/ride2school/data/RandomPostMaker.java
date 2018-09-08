package edu.ucsb.cs56.ride2school.data;

import java.util.List;

import edu.ucsb.cs56.ride2school.config.DatabaseConfig;

public class RandomPostMaker {
	
	private DatabaseConfig db;
	
	public RandomPostMaker(DatabaseConfig db)
	{
		this.db = db;
	}
	
	public List<PostData> createRandomPosts(int amount)
	{
		List<PostData> posts = new HashList<PostData>();
		return posts;
	}
	
}
