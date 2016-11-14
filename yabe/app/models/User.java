package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

//Marca a classe como uma entidade JPA
@Entity
//@Table(name="NomeDaTabela") //Notação dada caso queira dar um nome para a tabela diferente do nome da classe
public class User extends Model {
	
	public String email;
	public String password;
	public String fullName;
	public boolean isAdmin;
	
	
	public User(String email, String password, String fullName) {

		this.email = email;
		this.password = password;
		this.fullName = fullName;
	}
	
	//Metodo para buscar verificar se usuário e senha fornecidos batem
	public static User connect(String email, String password){
		
		//Busca por email e senha e retorna a primeira ocorrencia
		return find("byEmailAndPassword", email, password).first();
	}

}
