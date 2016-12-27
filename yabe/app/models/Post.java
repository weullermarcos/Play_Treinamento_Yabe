package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;


@Entity
public class Post extends Model {
	
	public String title;
	public Date postedAt;
	
	@Lob //Inidica que será um texto grande
	public String content;
	
	@ManyToOne //Muitos post para um único autor
	public User author;
	
	//Associando um Post a uma lista de comentários
	//Importante: Se um Post for apagado seus comentários serão apagado em cascata
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL)
	public List<Comment> comments;
	//Criando a lista de comentários do Post

	//Cria a relacao de muitos para muitos entre Post e Tag
	@ManyToMany(cascade=CascadeType.PERSIST)
	public Set<Tag> tags;
	 
	public Post(User author, String title, String content) {
		
	    this.comments = new ArrayList<Comment>();
	    this.tags = new TreeSet<Tag>();
	    this.author = author;
	    this.title = title;
	    this.content = content;
	    this.postedAt = new Date();
	}
	
	//Metodo para adicionar comentários
	public Post addComment(String author, String content) {
		
		//cria e salva o novo comentario associado ao Post corrente
	    Comment newComment = new Comment(this, author, content).save();
	    
	    //Adiciona o Post a lista de Posts
	    this.comments.add(newComment);
	    
	    //Salva o Post
	    this.save();
	    return this;
	}
	
	//Adiciona paginacao
	public Post previous() {
	    return Post.find("postedAt < ? order by postedAt desc", postedAt).first();
	}
	 
	public Post next() {
	    return Post.find("postedAt > ? order by postedAt asc", postedAt).first();
	}
	
	
	public Post tagItWith(String name) {
	    tags.add(Tag.findOrCreateByName(name));
	    return this;
	}
	
	public static List<Post> findTaggedWith(String tag) {
	    return Post.find(
	        "select distinct p from Post p join p.tags as t where t.name = ?", tag
	    ).fetch();
	}
	
	
	public static List<Post> findTaggedWith(String... tags) {
	    return Post.find(
	            "select distinct p from Post p join p.tags as t where t.name in (:tags) group by p.id, p.author, p.title, p.content,p.postedAt having count(t.id) = :size"
	    ).bind("tags", tags).bind("size", tags.length).fetch();
	}
	
	
}















