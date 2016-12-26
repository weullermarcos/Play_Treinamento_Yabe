package controllers;

import java.util.List;

import models.Post;
import play.Play;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

//	Original Index method
//    public static void index() {
//    	
//        render();
//    }
	
	@Before
	static void addDefaults() {
	    renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
	    renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
	}
	
	public static void index() {
		
		//Recuperando o post mais atual
        Post frontPost = Post.find("order by postedAt desc").first();
        
        //recuperando lista de posts e ordenando de forma decrescente
        List<Post> olderPosts = Post.find( "order by postedAt desc").from(1).fetch(10);
        
        render(frontPost, olderPosts);
    }

}