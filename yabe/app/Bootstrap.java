
import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

//Diz ao play que queremos executar esta tarefa de forma sincrona na inicializacao do apicativo
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
    	
        // Checa se o banco esta vazio
        if(User.count() == 0) {
            Fixtures.loadModels("initial-data.yml");
        }
    }
 
}
