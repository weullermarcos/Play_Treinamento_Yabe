import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

//    @Test
//    public void aVeryImportantThingToTest() {
//        assertEquals(2, 1 + 1);
//    }
	
	@Before //Indica que este metodo será executado antes dos outros
	//Este metodo será executado antes de qualquer teste
	public void setup(){
		
		//Apaga o BD
		Fixtures.deleteDatabase();
		
		System.out.println("Passsou no Setup");
	}
	
	@Test
	public void createAndRetrieveUser(){
		
		//Criando e salvando um novo usuário
		new User("fulano@email.com", "12312", "Fulano").save();
		
		//Busca usuário passando como parametro o e-mail e pega a primeira ocorrencia
		User fulano = User.find("byEmail", "fulano@email.com").first();
		
		//Verifica se não é nullo o objeto recuperado
		assertNotNull(fulano);
		
		//Verifica se o usuário recuperado tem o nome igual ao do usuário inserido
		assertEquals("Fulano", fulano.fullName);
		
		System.out.println("Passsou no createAndRetrieveUser");

	}
	
	@Test //Marca o método como sendo de teste
	public void tryConnectAsUser(){
		
		//cria um novo usuário
		new User("email.teste.com", "123", "teste").save();
		
		// Teste de conexão
		assertNotNull(User.connect("email.teste.com", "123"));
		//assertNotNull(User.connect("email.teste.com", "1234"));
		//assertNotNull(User.connect("email.testa.com", "123"));
		
		System.out.println("Passsou no tryConnectAsUser");

	}

	@Test
	public void createPost(){
		
		//Criando e salvando um novo usuário
		User bob = new User("bob@email", "12", "Bob").save();
		
		//Criando e salvando um novo Post
		new Post("Titulo", "conteudo", bob).save();
		
		//Verifica se um post foi criado
		assertEquals(1, Post.count());
		
		//Recupera uma lista de Posts por autor
		List<Post> bobPosts = Post.find("byAuthor", bob).fetch();
		
	    // Testes
		//Verifica se o Bob só tem um post
	    assertEquals(1, bobPosts.size());
	    
	    //Recupera o primeiro post do bob
	    Post firstPost = bobPosts.get(0);
	    
	    //veriica se o post não é nullo
	    assertNotNull(firstPost);
	    
	    //Verifica se o bob é o autor
	    assertEquals(bob, firstPost.author);
	    
	    //verifica o titulo
	    assertEquals("Titulo", firstPost.title);
	    
	    //verifica o conteudo
	    assertEquals("conteudo", firstPost.content);
	    
	    //verifica se a data de postagem não é nulla
	    assertNotNull(firstPost.postedAt);
		
	}
	
	@Test
	public void postComments() {
		
	    // Criando e salvando um novo usuario
	    User bob = new User("bob@gmail.com", "secret", "Bob").save();
	 
	    // Criando e salvando um novo Post
	    Post bobPost = new Post("My first post", "Hello world", bob).save();
	 
	    // Criando e salvando comentarios
	    new Comment(bobPost, "Jeff", "Nice post").save();
	    new Comment(bobPost, "Tom", "I knew that !").save();
	 
	    // Recuperando todos os comentarios pelo Post informado
	    List<Comment> bobPostComments = Comment.find("byPost", bobPost).fetch();
	 
	    // Testando se existem 2 Posts
	    assertEquals(2, bobPostComments.size());
	 
	    //Recuperanto e testando o primeiro comentario
	    Comment firstComment = bobPostComments.get(0);
	    assertNotNull(firstComment);
	    assertEquals("Jeff", firstComment.author);
	    assertEquals("Nice post", firstComment.content);
	    assertNotNull(firstComment.postedAt);
	 
	    //Recuperanto e testando o segundo comentario
	    Comment secondComment = bobPostComments.get(1);
	    assertNotNull(secondComment);
	    assertEquals("Tom", secondComment.author);
	    assertEquals("I knew that !", secondComment.content);
	    assertNotNull(secondComment.postedAt);
	}
	
	@Test
	//Testando o usuo do método de criar comentários
	public void useTheCommentsRelation() {
		
	    // Cria e salva um novo usuário
	    User bob = new User("bob@gmail.com", "secret", "Bob").save();
	 
	    // Cria e salva um novo Post
	    Post bobPost = new Post("My first post", "Hello world", bob).save();
	 
	    // Criando dois comentaários usando o método criado de adicionar comentários
	    bobPost.addComment("Jeff", "Nice post");
	    bobPost.addComment("Tom", "I knew that !");
	 
	    // Verifica contagem
	    assertEquals(1, User.count());
	    assertEquals(1, Post.count());
	    assertEquals(2, Comment.count());
	 
	    // Recuperando os Posts para o usuário Bob
	    bobPost = Post.find("byAuthor", bob).first();
	    assertNotNull(bobPost);
	 
	    // Verificano se tem 2 comentários para o Post do Bob
	    assertEquals(2, bobPost.comments.size());
	    
	    //Verificando se o Jeff é o autor do primeiro comentário
	    assertEquals("Jeff", bobPost.comments.get(0).author);
	    
	    // Apagando o Post do Bob
	    bobPost.delete();
	    
	    // Verificando se foi cascateado e os commentários foram apagados
	    assertEquals(1, User.count());
	    assertEquals(0, Post.count());
	    assertEquals(0, Comment.count());
	}
	
}

