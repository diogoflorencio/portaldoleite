import java.util.ArrayList;
import java.util.List;

import models.*;
import models.dao.GenericDAOImpl;

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
				if (dao.findAllByClassName(User.class.getName()).size() < 10) {
					criaUser();
				}
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinaTemas();
				}
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

	private void criaUser() {
		for (int i = 0; i < 10; i++) {
			User user = new User("User" + i + "@ccc.ufcg.edu.br", "123", "user" + i, "User" + i);
			dao.persist(user);
		}
	}

	private void criaDisciplinaTemas() {
		Disciplina tc = new Disciplina("Teoria da Computação");
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		Disciplina prob = new Disciplina("Probabilidade e Estatística");

		si1.addTema(new Tema("Análise x Design", si1));
		si1.addTema(new Tema("Orientação a objetos", si1));
		si1.addTema(new Tema("GRASP", si1));
		si1.addTema(new Tema("GoF", si1));
		si1.addTema(new Tema("Arquitetura", si1));
		si1.addTema(new Tema("Play", si1));
		si1.addTema(new Tema("JavaScript", si1));
		si1.addTema(new Tema("HTML / CSS / Bootstrap", si1));
		si1.addTema(new Tema("Heroku", si1));
		si1.addTema(new Tema("Labs", si1));
		si1.addTema(new Tema("Minitestes", si1));
		si1.addTema(new Tema("Projeto", si1));
		prob.addTema(new Tema("Variancia e desvio padrão",prob));
		prob.addTema(new Tema("Esperança e propriedades",prob));
		prob.addTema(new Tema("Distribuição de Poisson",prob));
		prob.addTema(new Tema("Modelo Normal"));
		prob.addTema(new Tema("Teorema Central do Limite",prob));
		tc.addTema(new Tema("Lema do Bombeamento",tc));
		tc.addTema(new Tema("Maquina de Turing",tc));
		tc.addTema(new Tema("Automato Finito Deterministico",tc));
		tc.addTema(new Tema("Automato Finito Não Deterministico",tc));

		dao.persist(si1);
		dao.persist(prob);
		dao.persist(tc);
		dao.flush();
	}

	private void geraDicas() {
		Tema tema;
		DicaMaterial dicaM;
		DicaDisciplina dicaD;
		disciplinas = dao.findAllByClassName("Disciplina");

		for (int i = 0; i < disciplinas.size(); i++) {
			String disciplina = disciplinas.get(i).getNome();
			switch (disciplina){
				case "Sistemas de Informação 1":
					tema = disciplinas.get(i).getTemaByNome("Orientação a objetos");
					dicaM = new DicaMaterial("http://www.devmedia.com.br/os-4-pilares-da-programacao-orientada-a-objetos/9264");
					dicaD = new DicaDisciplina("Programação 2", "para aprender tecnicas voltadas a OO");
					setDicas(tema,dicaD,dicaM);
					break;
				case "Probabilidade e Estatística":
					tema = disciplinas.get(i).getTemaByNome("Esperança e propriedades");
					dicaD = new DicaDisciplina("Cálculo 2", "para dominar series e integrais");
					dicaM = new DicaMaterial("https://pt.wikipedia.org/wiki/Valor_esperado");
					setDicas(tema,dicaD,dicaM);
					break;
				case "Teoria da Computação":
					tema = disciplinas.get(i).getTemaByNome("Automato Finito Deterministico");
					dicaM = new DicaMaterial("http://www.decom.ufop.br/anderson/BCC242/AFD.pdf");
					dicaD = new DicaDisciplina("Grafos", "pq tem q saber msm");
					setDicas(tema,dicaD,dicaM);
					break;
			}
		}
	}

	private void setDicas(Tema tema, DicaDisciplina dicaD, DicaMaterial dicaM){
		tema.addDica(dicaD);
		dicaD.setTema(tema);
		dicaD.setUser("User0");
		tema.addDica(dicaM);
		dicaM.setTema(tema);
		dicaM.setUser("User1");

		dao.persist(dicaM);
		dao.persist(dicaD);
		dao.flush();

		setVotos("user2",dicaD);
		setVotos("user2",dicaD);
		setVotos("user2",dicaM);
		setVotos("user2",dicaM);
	}

	private void setVotos(String user, Dica dica){
		dica.addUsuarioQueVotou(user);
		dica.incrementaConcordancias();
		dao.merge(dica);
		dao.flush();
	}

}