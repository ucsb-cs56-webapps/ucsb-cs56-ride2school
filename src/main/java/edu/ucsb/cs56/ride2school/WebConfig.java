package edu.ucsb.cs56.ride2school;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class WebConfig {

	private ArrayList<PostData> posts;
	private HashMap<String, Object> map;
	
	public WebConfig() {
		SetUpRoutes();
	}

	private void SetUpRoutes() {
		get("/", (rq, rs) -> new ModelAndView(map, "feed.mustache"), new MustacheTemplateEngine());

		get("/form/post", (rq, rs) -> new ModelAndView(posts, "post.mustache"), new MustacheTemplateEngine());

		post("/login", (rq, rs) -> new ModelAndView(map, "login.mustache"), new MustacheTemplateEngine());
	}
}
