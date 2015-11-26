import java.util.ArrayList;
import java.util.List;

import models.Disciplina;
import models.Tema;
import models.User;
import models.dao.GenericDAOImpl;
import models.User;
import models.Dica;
import models.DicaDisciplina;
import models.DicaMaterial;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinaTemas();
				}
				if (dao.findAllByClassName(User.class.getName()).size() < 10) criaUsuarios();
			}
		});
	}
	
	@Override
	public void onStop(Application app){
	    JPA.withTransaction(new play.libs.F.Callback0() {
	    @Override
	    public void invoke() throws Throwable {
	        Logger.info("Aplicação finalizando...");
	        disciplinas = dao.findAllByClassName("Disciplina");

	        for (Disciplina disciplina: disciplinas) {
	        dao.removeById(Disciplina.class, disciplina.getId());
	       } 
	    }}); 
	}
	
	private void criaDisciplinaTemas(){
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		Disciplina prob = new Disciplina("Probabilidade e Estatística");
		Disciplina tc = new Disciplina("Teoria da Computação");
		si1.addTema(new Tema("Análise x Design"));
		si1.addTema(new Tema("Orientação a objetos"));
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(new Tema("Play"));
		si1.addTema(new Tema("JavaScript"));
		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		si1.addTema(new Tema("Minitestes"));
		si1.addTema(new Tema("Projeto"));
		prob.addTema(new Tema("Variancia e desvio padrão"));
		prob.addTema(new Tema("Esperança"));
		prob.addTema(new Tema("Distribuição de Poisson"));
		prob.addTema(new Tema("Modelo Normal"));
		prob.addTema(new Tema("Teorema Central do Limite"));
		tc.addTema(new Tema("Lema do Bombeamento"));
		tc.addTema(new Tema("Maquina de Turing"));
		tc.addTema(new Tema("Automato Finito Deterministico"));
		tc.addTema(new Tema("Automato Finito Não Deterministico"));
		geraDicas();
		dao.persist(si1);
		dao.persist(prob);
		dao.persist(tc);
		dao.flush();
	}
	private void criaUsuarios() {
		for (int i = 0; i < 10; i++) {
			User user = new User("User" + i + "@ccc.ufcg.edu.br", "123", "user" + i);
			user.setNome("User" + i);
			dao.persist(user);
			dao.flush();
		}
	}
	private void geraDicas() {
		disciplinas = dao.findAllByClassName(Disciplina.class.getName());
        for (Disciplina disciplina1 : disciplinas) {
            String disciplina = disciplina1.getNome();
            if (disciplina.equals("Sistemas de Informação 1")) {
                Tema tema = disciplina1.getTemaByNome("Orientação a objetos");
                DicaDisciplina dicaDisc = new DicaDisciplina("Sistemas de Informação 1", "Programa O.O");
                DicaMaterial dicaM = new DicaMaterial("http://www.devmedia.com.br/desenvolvimento-com-qualidade-com-grasp/28704");
                setDicaDisciplinas(tema, dicaDisc, dicaM);
            } else if (disciplina.equals("Probabilidade e Estatística")){
                Tema tema = disciplina1.getTemaByNome("Variancia e desvio padrão");
                DicaDisciplina dicaDisc = new DicaDisciplina("Probabilidade e Estatística", "Melhorar a nota");
                DicaMaterial dicaM = new DicaMaterial("http://http://mundoeducacao.bol.uol.com.br/matematica/variancia-desvio-padrao.htm");
                setDicaDisciplinas(tema, dicaDisc, dicaM);
            }
        }
	}
	private void setDicaDisciplinas(Tema tema, DicaDisciplina dicaDisciplina, DicaMaterial dicaMaterial){
		tema.addDica(dicaDisciplina);
		dicaDisciplina.setTema(tema);
		dicaDisciplina.setUser("User3");
		tema.addDica(dicaMaterial);
		dicaMaterial.setTema(tema);
		dicaMaterial.setUser("User1");

		dao.persist(dicaMaterial);
		dao.persist(dicaDisciplina);
        dao.flush();

        setVoto(dicaDisciplina, dicaMaterial);
	}

    private void setVoto(DicaDisciplina dicaDisciplina, DicaMaterial dicaMaterial) {
        dicaDisciplina.addUsuarioQueVotou("user3");
        dicaDisciplina.incrementaConcordancias();
        dicaDisciplina.addUsuarioQueVotou("user1");
        dicaDisciplina.incrementaConcordancias();

        dicaMaterial.addUsuarioQueVotou("user3");
        dicaMaterial.incrementaConcordancias();
        dicaMaterial.addUsuarioQueVotou("user1");
        dicaMaterial.incrementaConcordancias();

        dao.merge(dicaDisciplina);
        dao.merge(dicaMaterial);
        dao.flush();
    }

}
