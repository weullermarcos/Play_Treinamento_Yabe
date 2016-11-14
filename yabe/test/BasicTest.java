import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

//    @Test
//    public void aVeryImportantThingToTest() {
//        assertEquals(2, 1 + 1);
//    }
	
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
		
	}
	
	@Test //Marca o método como sendo de teste
	public void tryConnectAsUser(){
		
		//cria um novo usuário
		new User("email.teste.com", "123", "teste").save();
		
		// Teste de conexão
		assertNotNull(User.connect("email.teste.com", "123"));
		//assertNotNull(User.connect("email.teste.com", "1234"));
		//assertNotNull(User.connect("email.testa.com", "123"));

	}

	
	
	
	
	
	
}

