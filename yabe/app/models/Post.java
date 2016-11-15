package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
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

	public Post(String title, String content, User author) {

		//Cria uma nova lista de comentários
		this.comments =  new ArrayList<Comment>();
		this.title = title;
		this.content = content;
		this.author = author;
		
		//Cria uma nova data
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
	
}















