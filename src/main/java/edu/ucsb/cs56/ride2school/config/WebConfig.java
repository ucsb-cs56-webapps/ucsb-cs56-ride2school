package edu.ucsb.cs56.ride2school.config;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsb.cs56.ride2school.data.PostData;
import edu.ucsb.cs56.ride2school.data.RandomPostMaker;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class WebConfig {

	private DatabaseConfig db;

	public WebConfig(DatabaseConfig db) {
		this.db = db;
		newPosts();
		SetUpRoutes();
	}
	
	private void newPosts()
	{
		if(db.getAllPosts().size() > 50)
			return;
		List<PostData> posts = new RandomPostMaker(db).createRandomPosts(50);
		for(int i = 0; i <posts.size(); i++)
		{
			db.addPostToDataBase(posts.get(i));
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
			
			Long id = Long.parseLong(rq.params(":postID"));
			PostData post = getPosts().get(id.intValue());
			
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
