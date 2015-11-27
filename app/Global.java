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

		for (int i = 0; i < 10; i++) {
			User user = new User("User" + i + "@ccc.ufcg.edu.br", "123", "user" + i);
			user.setNome("User" + i);
			dao.persist(user);
		}

		DicaMaterial dica1 = new DicaMaterial("https://sites.google.com/site/prog2ufcg/p2/programa");
		si1.getTemaByNome("Orientação a objetos").addDica(dica1);
		dica1.setTema(si1.getTemaByNome("Orientação a objetos"));
		dica1.setUser("user2");

		DicaConselho dica2 = new DicaConselho("Java, play, html, css, javascript");
		si1.getTemaByNome("Projeto").addDica(dica2);
		dica2.setTema(si1.getTemaByNome("Projeto"));
		dica2.setUser("user5");

		DicaAssunto dica3 = new DicaAssunto("Maquina de Turing");
		tc.getTemaByNome("Maquina de Turing").addDica(dica3);
		dica3.setTema(tc.getTemaByNome("Maquina de Turing"));
		dica3.setUser("user7");

		DicaDisciplina dica4 = new DicaDisciplina("Probabilidade e Estatística", "Conhecimentos em estatística necessários");
		prob.getTemaByNome("Distribuição de Poisson").addDica(dica4);
		dica4.setTema(prob.getTemaByNome("Distribuição de Poisson"));
		dica4.setUser("user5");

		DicaMaterial dica5 = new DicaMaterial("http://leg.ufpr.br/~silvia/CE001/node38.html");
		prob.getTemaByNome("Teorema Central do Limite").addDica(dica5);
		dica5.setTema(prob.getTemaByNome("Teorema Central do Limite"));
		dica5.setUser("user4");

		//insere os votos nas dicas
		dica1.incrementaConcordancias();
		dica1.incrementaConcordancias();
		dica2.incrementaConcordancias();
		dica2.incrementaConcordancias();
		dica3.incrementaConcordancias();
		dica3.incrementaConcordancias();
		dica4.incrementaConcordancias();
		dica4.incrementaConcordancias();
		dica5.incrementaConcordancias();
		dica5.incrementaConcordancias();

		dao.persist(dica1);
		dao.persist(dica2);
		dao.persist(dica3);
		dao.persist(dica4);
		dao.persist(dica5);
		dao.flush();
		dao.flush();
	}
}