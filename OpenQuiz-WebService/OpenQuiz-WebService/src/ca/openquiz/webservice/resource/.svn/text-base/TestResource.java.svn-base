package ca.openquiz.webservice.resource;

import javax.ws.rs.Path;
/*
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.openquiz.webservice.enums.CategoryType;
import ca.openquiz.webservice.enums.Degree;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.QuestionTarget;
import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Category;
import ca.openquiz.webservice.model.Choice;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.QuestionAnagram;
import ca.openquiz.webservice.model.QuestionAssociation;
import ca.openquiz.webservice.model.QuestionGeneral;
import ca.openquiz.webservice.model.QuestionIdentification;
import ca.openquiz.webservice.model.QuestionIntru;
import ca.openquiz.webservice.model.QuestionSet;
import ca.openquiz.webservice.model.QuestionSetSection;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.tools.ExceptionLogger;
*/

@Path("/tests")
public class TestResource {
	/*
	@GET
	@Path("/addData")
	public Response addTestData() 
	{
		Date now = new Date();

		User u111 = new User();
		u111.setAdmin(false);
		u111.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u111.setCreationDate(now);
		u111.setEmail("testGroups111@gmail.com");
		u111.setFirstName("Group1 Team1");
		u111.setLanguage(Language.fr);
		u111.setLastName("User1");
		u111.setPassword("test");
		DBManager.save(u111);

		User u112 = new User();
		u112.setAdmin(false);
		u112.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u112.setCreationDate(now);
		u112.setEmail("testGroups112@gmail.com");
		u112.setFirstName("Group1 Team1");
		u112.setLanguage(Language.fr);
		u112.setLastName("User2");
		u112.setPassword("test");
		DBManager.save(u112);

		User u113 = new User();
		u113.setAdmin(false);
		u113.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u113.setCreationDate(now);
		u113.setEmail("testGroups113@gmail.com");
		u113.setFirstName("Group1 Team1");
		u113.setLanguage(Language.fr);
		u113.setLastName("User3");
		u113.setPassword("test");
		DBManager.save(u113);

		User u114 = new User();
		u114.setAdmin(false);
		u114.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u114.setCreationDate(now);
		u114.setEmail("testGroups114@gmail.com");
		u114.setFirstName("Group1 Team1");
		u114.setLanguage(Language.fr);
		u114.setLastName("User4");
		u114.setPassword("test");
		DBManager.save(u114);




		User u121 = new User();
		u121.setAdmin(false);
		u121.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u121.setCreationDate(now);
		u121.setEmail("testGroups121@gmail.com");
		u121.setFirstName("Group1 Team2");
		u121.setLanguage(Language.fr);
		u121.setLastName("User1");
		u121.setPassword("test");
		DBManager.save(u121);

		User u122 = new User();
		u122.setAdmin(false);
		u122.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u122.setCreationDate(now);
		u122.setEmail("testGroups122@gmail.com");
		u122.setFirstName("Group1 Team2");
		u122.setLanguage(Language.fr);
		u122.setLastName("User2");
		u122.setPassword("test");
		u122.setAdmin(true);
		DBManager.save(u122);

		User u123 = new User();
		u123.setAdmin(false);
		u123.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u123.setCreationDate(now);
		u123.setEmail("testGroups123@gmail.com");
		u123.setFirstName("Group1 Team2");
		u123.setLanguage(Language.fr);
		u123.setLastName("User3");
		u123.setPassword("test");
		DBManager.save(u123);

		User u124 = new User();
		u124.setAdmin(false);
		u124.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u124.setCreationDate(now);
		u124.setEmail("testGroups124@gmail.com");
		u124.setFirstName("Group1 Team2");
		u124.setLanguage(Language.fr);
		u124.setLastName("User4");
		u124.setPassword("test");
		DBManager.save(u124);




		User u131 = new User();
		u131.setAdmin(false);
		u131.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u131.setCreationDate(now);
		u131.setEmail("testGroups131@gmail.com");
		u131.setFirstName("Group1 Team3");
		u131.setLanguage(Language.fr);
		u131.setLastName("User1");
		u131.setPassword("test");
		DBManager.save(u131);

		User u132 = new User();
		u132.setAdmin(false);
		u132.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u132.setCreationDate(now);
		u132.setEmail("testGroups132@gmail.com");
		u132.setFirstName("Group1 Team3");
		u132.setLanguage(Language.fr);
		u132.setLastName("User2");
		u132.setPassword("test");
		DBManager.save(u132);

		User u133 = new User();
		u133.setAdmin(false);
		u133.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u133.setCreationDate(now);
		u133.setEmail("testGroups133@gmail.com");
		u133.setFirstName("Group1 Team3");
		u133.setLanguage(Language.fr);
		u133.setLastName("User3");
		u133.setPassword("test");
		DBManager.save(u133);

		User u134 = new User();
		u134.setAdmin(false);
		u134.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u134.setCreationDate(now);
		u134.setEmail("testGroups134@gmail.com");
		u134.setFirstName("Group1 Team3");
		u134.setLanguage(Language.fr);
		u134.setLastName("User4");
		u134.setPassword("test");
		DBManager.save(u134);

		User u211 = new User();
		u211.setAdmin(false);
		u211.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u211.setCreationDate(now);
		u211.setEmail("testGroups211@gmail.com");
		u211.setFirstName("Group2 Team1");
		u211.setLanguage(Language.fr);
		u211.setLastName("User1");
		u211.setPassword("test");
		DBManager.save(u211);

		User u212 = new User();
		u212.setAdmin(false);
		u212.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u212.setCreationDate(now);
		u212.setEmail("testGroups212@gmail.com");
		u212.setFirstName("Group2 Team1");
		u212.setLanguage(Language.fr);
		u212.setLastName("User2");
		u212.setPassword("test");
		DBManager.save(u212);

		User u213 = new User();
		u213.setAdmin(false);
		u213.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u213.setCreationDate(now);
		u213.setEmail("testGroups213@gmail.com");
		u213.setFirstName("Group2 Team1");
		u213.setLanguage(Language.fr);
		u213.setLastName("User3");
		u213.setPassword("test");
		DBManager.save(u213);

		User u214 = new User();
		u214.setAdmin(false);
		u214.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u214.setCreationDate(now);
		u214.setEmail("testGroups214@gmail.com");
		u214.setFirstName("Group2 Team1");
		u214.setLanguage(Language.fr);
		u214.setLastName("User4");
		u214.setPassword("test");
		DBManager.save(u214);




		User u221 = new User();
		u221.setAdmin(false);
		u221.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u221.setCreationDate(now);
		u221.setEmail("testGroups221@gmail.com");
		u221.setFirstName("Group2 Team2");
		u221.setLanguage(Language.fr);
		u221.setLastName("User1");
		u221.setPassword("test");
		DBManager.save(u221);

		User u222 = new User();
		u222.setAdmin(false);
		u222.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u222.setCreationDate(now);
		u222.setEmail("testGroups222@gmail.com");
		u222.setFirstName("Group2 Team2");
		u222.setLanguage(Language.fr);
		u222.setLastName("User2");
		u222.setPassword("test");
		DBManager.save(u222);

		User u223 = new User();
		u223.setAdmin(false);
		u223.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u223.setCreationDate(now);
		u223.setEmail("testGroups223@gmail.com");
		u223.setFirstName("Group2 Team2");
		u223.setLanguage(Language.fr);
		u223.setLastName("User3");
		u223.setPassword("test");
		DBManager.save(u223);

		User u224 = new User();
		u224.setAdmin(false);
		u224.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u224.setCreationDate(now);
		u224.setEmail("testGroups224@gmail.com");
		u224.setFirstName("Group2 Team2");
		u224.setLanguage(Language.fr);
		u224.setLastName("User4");
		u224.setPassword("test");
		DBManager.save(u224);




		User u231 = new User();
		u231.setAdmin(false);
		u231.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u231.setCreationDate(now);
		u231.setEmail("testGroups231@gmail.com");
		u231.setFirstName("Group2 Team3");
		u231.setLanguage(Language.fr);
		u231.setLastName("User1");
		u231.setPassword("test");
		DBManager.save(u231);

		User u232 = new User();
		u232.setAdmin(false);
		u232.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u232.setCreationDate(now);
		u232.setEmail("testGroups232@gmail.com");
		u232.setFirstName("Group2 Team3");
		u232.setLanguage(Language.fr);
		u232.setLastName("User2");
		u232.setPassword("test");
		DBManager.save(u232);

		User u233 = new User();
		u233.setAdmin(false);
		u233.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u233.setCreationDate(now);
		u233.setEmail("testGroups233@gmail.com");
		u233.setFirstName("Group2 Team3");
		u233.setLanguage(Language.fr);
		u233.setLastName("User3");
		u233.setPassword("test");
		DBManager.save(u233);

		User u234 = new User();
		u234.setAdmin(false);
		u234.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u234.setCreationDate(now);
		u234.setEmail("testGroups234@gmail.com");
		u234.setFirstName("Group2 Team3");
		u234.setLanguage(Language.fr);
		u234.setLastName("User4");
		u234.setPassword("test");
		DBManager.save(u234);



		User u311 = new User();
		u311.setAdmin(false);
		u311.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u311.setCreationDate(now);
		u311.setEmail("testGroups311@gmail.com");
		u311.setFirstName("Group3 Team1");
		u311.setLanguage(Language.fr);
		u311.setLastName("User1");
		u311.setPassword("test");
		DBManager.save(u311);

		User u312 = new User();
		u312.setAdmin(false);
		u312.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u312.setCreationDate(now);
		u312.setEmail("testGroups312@gmail.com");
		u312.setFirstName("Group3 Team1");
		u312.setLanguage(Language.fr);
		u312.setLastName("User2");
		u312.setPassword("test");
		DBManager.save(u312);

		User u313 = new User();
		u313.setAdmin(false);
		u313.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u313.setCreationDate(now);
		u313.setEmail("testGroups313@gmail.com");
		u313.setFirstName("Group3 Team1");
		u313.setLanguage(Language.fr);
		u313.setLastName("User3");
		u313.setPassword("test");
		DBManager.save(u313);

		User u314 = new User();
		u314.setAdmin(false);
		u314.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u314.setCreationDate(now);
		u314.setEmail("testGroups314@gmail.com");
		u314.setFirstName("Group3 Team1");
		u314.setLanguage(Language.fr);
		u314.setLastName("User4");
		u314.setPassword("test");
		DBManager.save(u314);




		User u321 = new User();
		u321.setAdmin(false);
		u321.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u321.setCreationDate(now);
		u321.setEmail("testGroups321@gmail.com");
		u321.setFirstName("Group3 Team2");
		u321.setLanguage(Language.fr);
		u321.setLastName("User1");
		u321.setPassword("test");
		DBManager.save(u321);

		User u322 = new User();
		u322.setAdmin(false);
		u322.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u322.setCreationDate(now);
		u322.setEmail("testGroups322@gmail.com");
		u322.setFirstName("Group3 Team2");
		u322.setLanguage(Language.fr);
		u322.setLastName("User2");
		u322.setPassword("test");
		DBManager.save(u322);

		User u323 = new User();
		u323.setAdmin(false);
		u323.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u323.setCreationDate(now);
		u323.setEmail("testGroups323@gmail.com");
		u323.setFirstName("Group3 Team2");
		u323.setLanguage(Language.fr);
		u323.setLastName("User3");
		u323.setPassword("test");
		DBManager.save(u323);

		User u324 = new User();
		u324.setAdmin(false);
		u324.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u324.setCreationDate(now);
		u324.setEmail("testGroups324@gmail.com");
		u324.setFirstName("Group3 Team2");
		u324.setLanguage(Language.fr);
		u324.setLastName("User4");
		u324.setPassword("test");
		DBManager.save(u324);




		User u331 = new User();
		u331.setAdmin(false);
		u331.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u331.setCreationDate(now);
		u331.setEmail("testGroups331@gmail.com");
		u331.setFirstName("Group3 Team3");
		u331.setLanguage(Language.fr);
		u331.setLastName("User1");
		u331.setPassword("test");
		DBManager.save(u331);

		User u332 = new User();
		u332.setAdmin(false);
		u332.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u332.setCreationDate(now);
		u332.setEmail("testGroups332@gmail.com");
		u332.setFirstName("Group3 Team3");
		u332.setLanguage(Language.fr);
		u332.setLastName("User2");
		u332.setPassword("test");
		DBManager.save(u332);

		User u333 = new User();
		u333.setAdmin(false);
		u333.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u333.setCreationDate(now);
		u333.setEmail("testGroups333@gmail.com");
		u333.setFirstName("Group3 Team3");
		u333.setLanguage(Language.fr);
		u333.setLastName("User3");
		u333.setPassword("test");
		DBManager.save(u333);

		User u334 = new User();
		u334.setAdmin(false);
		u334.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
		u334.setCreationDate(now);
		u334.setEmail("testGroups334@gmail.com");
		u334.setFirstName("Group3 Team3");
		u334.setLanguage(Language.fr);
		u334.setLastName("User4");
		u334.setPassword("test");
		DBManager.save(u334);


		Group g1 = new Group();
		g1.setName("École 1");
		DBManager.save(g1);
		g1.addGroupRoles(new GroupRole(u111, g1, Role.Manager));
		g1.addGroupRoles(new GroupRole(u112, g1, Role.Player));
		g1.addGroupRoles(new GroupRole(u113, g1, Role.Player));
		g1.addGroupRoles(new GroupRole(u114, g1, Role.Player));
		g1.addGroupRoles(new GroupRole(u121, g1, Role.Player));
		g1.addGroupRoles(new GroupRole(u122, g1, Role.Manager));
		g1.addGroupRoles(new GroupRole(u123, g1, Role.Player));
		g1.addGroupRoles(new GroupRole(u124, g1, Role.Player));
		g1.addGroupRoles(new GroupRole(u131, g1, Role.Player));
		g1.addGroupRoles(new GroupRole(u132, g1, Role.Player));
		g1.addGroupRoles(new GroupRole(u133, g1, Role.Player));
		g1.addGroupRoles(new GroupRole(u134, g1, Role.QuestionManager));
		DBManager.save(g1);

		Group g2 = new Group();
		g2.setName("École 2");
		DBManager.save(g2);
		g2.addGroupRoles(new GroupRole(u211, g2, Role.Manager));
		g2.addGroupRoles(new GroupRole(u212, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u213, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u214, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u221, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u222, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u223, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u224, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u231, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u232, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u233, g2, Role.Player));
		g2.addGroupRoles(new GroupRole(u234, g2, Role.QuestionManager));
		DBManager.save(g2);

		Group g3 = new Group();
		g3.setName("École 3");
		DBManager.save(g3);
		g3.addGroupRoles(new GroupRole(u311, g3, Role.Manager));
		g3.addGroupRoles(new GroupRole(u312, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u313, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u314, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u321, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u322, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u323, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u324, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u331, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u332, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u333, g3, Role.Player));
		g3.addGroupRoles(new GroupRole(u334, g3, Role.QuestionManager));
		DBManager.save(g3);

		//
		//	Team
		//
		Team t11 = new Team();
		t11.setName("École 1, équipe 1");
		g1.addTeam(t11);
		t11.addUser(u111);
		t11.addUser(u112);
		t11.addUser(u113);
		t11.addUser(u114);
		DBManager.save(t11);


		Team t12 = new Team();
		t12.setName("École 1, équipe 2");
		g1.addTeam(t12);
		t12.addUser(u121);
		t12.addUser(u122);
		t12.addUser(u123);
		t12.addUser(u124);
		DBManager.save(t12);


		Team t13 = new Team();
		t13.setName("École 1, équipe 3");
		g1.addTeam(t13);
		t13.addUser(u131);
		t13.addUser(u132);
		t13.addUser(u133);
		t13.addUser(u134);
		DBManager.save(t13);



		Team t21 = new Team();
		t21.setName("École 2, équipe 1");
		g2.addTeam(t21);
		t21.addUser(u211);
		t21.addUser(u212);
		t21.addUser(u213);
		t21.addUser(u214);
		DBManager.save(t21);


		Team t22 = new Team();
		t22.setName("École 2, équipe 2");
		g2.addTeam(t22);
		t22.addUser(u221);
		t22.addUser(u222);
		t22.addUser(u223);
		t22.addUser(u224);
		DBManager.save(t22);


		Team t23 = new Team();
		t23.setName("École 2, équipe 3");
		t23.setGroup(g2);
		t23.addUser(u231);
		t23.addUser(u232);
		t23.addUser(u233);
		t23.addUser(u234);
		DBManager.save(t23);



		Team t31 = new Team();
		t31.setName("École 3, équipe 1");
		g3.addTeam(t31);
		t31.addUser(u311);
		t31.addUser(u312);
		t31.addUser(u313);
		t31.addUser(u314);
		DBManager.save(t31);


		Team t32 = new Team();
		t32.setName("École 3, équipe 2");
		g3.addTeam(t32);
		t32.addUser(u321);
		t32.addUser(u322);
		t32.addUser(u323);
		t32.addUser(u324);
		DBManager.save(t32);


		Team t33 = new Team();
		t33.setName("École 3, équipe 3");
		g3.addTeam(t33);
		t33.addUser(u331);
		t33.addUser(u332);
		t33.addUser(u333);
		t33.addUser(u334);
		DBManager.save(t33);


		//
		//	Categories
		//
		Category c = new Category();
		c.setName("Test");
		c.setType(CategoryType.Unknown);
		DBManager.save(c);

		ExceptionLogger.getLogger().warning("cat Done");


		//
		//	Questions
		//
		QuestionAnagram q1 = new QuestionAnagram();
		q1.setAnagram("zioepunq");
		q1.setAnswer("openquiz");
		q1.setHint("Vous y jouer présentement");
		q1.setAuthor(u111.getKey());
		q1.setCategory(c.getKey());
		q1.setAvailableDate(now);
		q1.setLanguage(Language.fr);
		q1.setDegree(Degree.normal);
		DBManager.save(q1);

		QuestionAssociation q2 = new QuestionAssociation();
		q2.setQuestion("Associer le pays à sa capitale");
		List<Choice> choices = new ArrayList<Choice>();
		choices.add(new Choice("USA", "Washington"));
		choices.add(new Choice("Canada", "Ottawa"));
		choices.add(new Choice("UK", "Londre"));
		choices.add(new Choice("France", "Paris"));
		q2.setChoices(choices);
		q2.setAuthor(u211.getKey());
		q2.setCategory(c.getKey());
		q2.setAvailableDate(now);
		q2.setLanguage(Language.fr);
		q2.setDegree(Degree.easy);
		DBManager.save(q2);

		QuestionGeneral q3 = new QuestionGeneral();
		q3.setQuestion("Dans quel océan se trouve l'île de Madagascar?");
		q3.setAuthor(u211.getKey());
		q3.setAvailableDate(now);
		q3.setCategory(c.getKey());
		q3.setDegree(Degree.easy);
		q3.setLanguage(Language.fr);
		q3.setAnswer("Indien");
		DBManager.save(q3);

		QuestionGeneral q4 = new QuestionGeneral();
		q4.setQuestion("Quelle est la plus grosse planète du système solaire?");
		q4.setAuthor(u211.getKey());
		q4.setAvailableDate(now);
		q4.setCategory(c.getKey());
		q4.setDegree(Degree.easy);
		q4.setLanguage(Language.fr);
		q4.setAnswer("Jupiter");
		DBManager.save(q4);

		QuestionGeneral q5 = new QuestionGeneral();
		q5.setQuestion("Nommez les quatres groupes sanguins");
		q5.setAuthor(u211.getKey());
		q5.setAvailableDate(now);
		q5.setCategory(c.getKey());
		q5.setDegree(Degree.easy);
		q5.setLanguage(Language.fr);
		q5.setAnswer("A, B, AB et O");
		DBManager.save(q5);

		QuestionGeneral q6 = new QuestionGeneral();
		q6.setQuestion("Combien y a t-il de grammes dans un kilogramme?");
		q6.setAuthor(u211.getKey());
		q6.setAvailableDate(now);
		q6.setCategory(c.getKey());
		q6.setDegree(Degree.easy);
		q6.setLanguage(Language.fr);
		q6.setAnswer("1000");
		DBManager.save(q6);

		QuestionGeneral q7 = new QuestionGeneral();
		q7.setQuestion("De quelle espèce est l'otarie?");
		q7.setAuthor(u211.getKey());
		q7.setAvailableDate(now);
		q7.setCategory(c.getKey());
		q7.setDegree(Degree.easy);
		q7.setLanguage(Language.fr);
		q7.setAnswer("Mammifère");
		DBManager.save(q7);

		QuestionGeneral q8 = new QuestionGeneral();
		q8.setQuestion("Quel est la volume d'un cube ayant 2 cm de côté?");
		q8.setAuthor(u211.getKey());
		q8.setAvailableDate(now);
		q8.setCategory(c.getKey());
		q8.setDegree(Degree.easy);
		q8.setLanguage(Language.fr);
		q8.setAnswer("8 cm cube");
		DBManager.save(q8);

		QuestionGeneral q9 = new QuestionGeneral();
		q9.setQuestion("Qui a tenu le rôle de rambo au cinéma?");
		q9.setAuthor(u211.getKey());
		q9.setAvailableDate(now);
		q9.setCategory(c.getKey());
		q9.setDegree(Degree.easy);
		q9.setLanguage(Language.fr);
		q9.setAnswer("Sylvester Stallone");
		DBManager.save(q9);

		QuestionGeneral q10 = new QuestionGeneral();
		q10.setQuestion("Quel est le prénom de l'actrice Monroe?");
		q10.setAuthor(u211.getKey());
		q10.setAvailableDate(now);
		q10.setCategory(c.getKey());
		q10.setDegree(Degree.easy);
		q10.setLanguage(Language.fr);
		q10.setAnswer("Marylin");
		DBManager.save(q10);

		QuestionGeneral q11 = new QuestionGeneral();
		q11.setQuestion("Qui est le géant que David tua grâce à sa fronde?");
		q11.setAuthor(u211.getKey());
		q11.setAvailableDate(now);
		q11.setCategory(c.getKey());
		q11.setDegree(Degree.easy);
		q11.setLanguage(Language.fr);
		q11.setAnswer("Goliath");
		DBManager.save(q11);

		QuestionGeneral q12 = new QuestionGeneral();
		q12.setQuestion("Quel nom donne-t-on au fruit du châtaignier cultivé?");
		q12.setAuthor(u211.getKey());
		q12.setAvailableDate(now);
		q12.setCategory(c.getKey());
		q12.setDegree(Degree.easy);
		q12.setLanguage(Language.fr);
		q12.setAnswer("Marron");
		DBManager.save(q12);

		QuestionGeneral q13 = new QuestionGeneral();
		q13.setQuestion("Dans quel pays vit le Koala?");
		q13.setAuthor(u211.getKey());
		q13.setAvailableDate(now);
		q13.setCategory(c.getKey());
		q13.setDegree(Degree.easy);
		q13.setLanguage(Language.fr);
		q13.setAnswer("Australie");
		DBManager.save(q3);

		QuestionGeneral q14 = new QuestionGeneral();
		q14.setQuestion("Quel écrivain créat le personnage de Sherlock Holmes?");
		q14.setAuthor(u211.getKey());
		q14.setAvailableDate(now);
		q14.setCategory(c.getKey());
		q14.setDegree(Degree.easy);
		q14.setLanguage(Language.fr);
		q14.setAnswer("Sir Arthur Conan Doyle");
		DBManager.save(q14);

		QuestionGeneral q15 = new QuestionGeneral();
		q15.setQuestion("Quelle vitamine nous est donnée par le soleil?");
		q15.setAuthor(u211.getKey());
		q15.setAvailableDate(now);
		q15.setCategory(c.getKey());
		q15.setDegree(Degree.easy);
		q15.setLanguage(Language.fr);
		q15.setAnswer("D");
		DBManager.save(q15);

		QuestionGeneral q16 = new QuestionGeneral();
		q16.setQuestion("Quel est l'antonyme de sédentaire?");
		q16.setAuthor(u211.getKey());
		q16.setAvailableDate(now);
		q16.setCategory(c.getKey());
		q16.setDegree(Degree.easy);
		q16.setLanguage(Language.fr);
		q16.setAnswer("Nomade");
		DBManager.save(q16);

		QuestionGeneral q17 = new QuestionGeneral();
		q17.setQuestion("Quel est le nom scientifique du ver de terre?");
		q17.setAuthor(u211.getKey());
		q17.setAvailableDate(now);
		q17.setCategory(c.getKey());
		q17.setDegree(Degree.easy);
		q17.setLanguage(Language.fr);
		q17.setAnswer("Lombric");
		DBManager.save(q17);

		QuestionGeneral q18 = new QuestionGeneral();
		q18.setQuestion("Dans quel sport décerne-t-on la coupe Davis?");
		q18.setAuthor(u211.getKey());
		q18.setAvailableDate(now);
		q18.setCategory(c.getKey());
		q18.setDegree(Degree.easy);
		q18.setLanguage(Language.fr);
		q18.setAnswer("Tennis");
		DBManager.save(q18);

		QuestionGeneral q19 = new QuestionGeneral();
		q19.setQuestion("Que signifie le préfixe omni dans omnivore?");
		q19.setAuthor(u211.getKey());
		q19.setAvailableDate(now);
		q19.setCategory(c.getKey());
		q19.setDegree(Degree.easy);
		q19.setLanguage(Language.fr);
		q19.setAnswer("Tout");
		DBManager.save(q19);

		QuestionGeneral q20 = new QuestionGeneral();
		q20.setQuestion("Combien avons-nous de reins?");
		q20.setAuthor(u211.getKey());
		q20.setAvailableDate(now);
		q20.setCategory(c.getKey());
		q20.setDegree(Degree.easy);
		q20.setLanguage(Language.fr);
		q20.setAnswer("2");
		q20.addGroup(g1);
		DBManager.save(q20);

		
		QuestionAssociation q21 = new QuestionAssociation();
		List<Choice> choices1 = new ArrayList<Choice>();
		choices1.add(new Choice("Simon", "Charbonneau"));
		choices1.add(new Choice("Jean-Félix", "Duval"));
		choices1.add(new Choice("Jean-Sébastien", "Plante"));
		choices1.add(new Choice("Alexandre", "Cloutière"));
		q21.setQuestion("Associer le nom au bon nom de famille");
		q21.setChoices(choices1);
		q21.setLanguage(Language.fr);
		q21.setAuthor(u111.getKey());
		q21.setDegree(Degree.hard);
		q21.setAvailableDate(now);
		q21.setCategory(c.getKey());
		DBManager.save(q21);

		QuestionIdentification q22 = new QuestionIdentification();
		q22.getStatements().add("Célébre américain né le 26 juillet 1928 à New York");
		q22.getStatements().add("Il réalisa Spartacus et Lolita");
		q22.getStatements().add("Il réalisa Docteur Folamour");
		q22.getStatements().add("Il réalisa 2001, l'Odyssée de l'espace");
		q22.getStatements().add("Il réalisa Orange Mécanique");
		q22.setAnswer("Stanley Kubrick");
		q22.setLanguage(Language.fr);
		q22.setDegree(Degree.hard);
		q22.setAuthor(u111.getKey());
		q22.setAvailableDate(now);
		q22.setCategory(c.getKey());
		DBManager.save(q22);

		QuestionIntru q23 = new QuestionIntru();
		q23.getChoices().add("Terre");
		q23.getChoices().add("Mars");
		q23.getChoices().add("Neptune");
		q23.getChoices().add("Saturne");
		q23.getChoices().add("Pluton");
		q23.setAnswer("Pluton");
		q23.setQuestion("Laquelle n'est pas une planète");
		q23.setLanguage(Language.fr);
		q23.setAuthor(u111.getKey());
		q23.setAvailableDate(now);
		q23.setDegree(Degree.hard);
		q23.setCategory(c.getKey());
		DBManager.save(q23);
		
		
		ExceptionLogger.getLogger().warning("questions Done");

		//
		//	QuestionSets
		//
		QuestionSet qs = new QuestionSet();
		qs.setAuthor(u111.getKey());
		qs.setAvailableDate(now);
		qs.setName("Test");
		List<QuestionSetSection> sections = new ArrayList<QuestionSetSection>();

		QuestionSetSection section1 = new QuestionSetSection();
		section1.addQuestion(q1);
		section1.addQuestion(q2);
		section1.addQuestion(q3);
		section1.addQuestion(q4);
		section1.addQuestion(q5);
		section1.setDescription("Questions de test");
		section1.setPoints(10);
		section1.setQuestionTarget(QuestionTarget.Collectif);
		sections.add(section1);

		QuestionSetSection section2 = new QuestionSetSection();
		section2.addQuestion(q6);
		section2.addQuestion(q7);
		section2.addQuestion(q8);
		section2.addQuestion(q9);
		section2.addQuestion(q10);
		section2.setDescription("Questions de test #2 en équipe");
		section2.setPoints(10);
		section2.setQuestionTarget(QuestionTarget.Collectif);
		sections.add(section2);

		QuestionSetSection section3 = new QuestionSetSection();
		section3.addQuestion(q11);
		section3.addQuestion(q12);
		section3.addQuestion(q13);
		section3.addQuestion(q14);
		section3.addQuestion(q15);
		section3.setDescription("Questions de test #3");
		section3.setPoints(15);
		section3.setQuestionTarget(QuestionTarget.Individual);
		sections.add(section3);

		QuestionSetSection section4 = new QuestionSetSection();
		section4.addQuestion(q16);
		section4.addQuestion(q17);
		section4.addQuestion(q18);
		section4.addQuestion(q19);
		section4.setDescription("Questions de test #4");
		section4.setPoints(15);
		section4.setQuestionTarget(QuestionTarget.Individual);
		sections.add(section4);
		
		QuestionSetSection section5 = new QuestionSetSection();
		section5.addQuestion(q21);
		section5.addQuestion(q22);
		section5.addQuestion(q23);
		section5.setDescription("Questions de test #5");
		section5.setPoints(25);
		section5.setQuestionTarget(QuestionTarget.Individual);
		sections.add(section5);

		qs.setSections(sections);
		DBManager.save(qs);

		ExceptionLogger.getLogger().warning("sections Done");

		return Response.status(Status.OK).build();

	}

	@GET
	@Path("/addPublicData")
	public Response addPublicPresentationData(){
		Date now = new Date();
		
		//==== USERS =====
		User[] team1Users = new User[4];
		for(int i = 0; i < 4 ; i++){
			team1Users[i] = new User();
			User user = team1Users[i];
			user.setAdmin(false);
			user.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
			user.setCreationDate(now);
			user.setEmail("team1player" + i + "@gmail.com");
			user.setFirstName("Joueur");
			user.setLanguage(Language.fr);
			user.setLastName(Integer.toString(i));
			user.setPassword("test");
			DBManager.save(user);
		}
		User[] team2Users = new User[4];
		for(int i = 0; i < 4 ; i++){
			team2Users[i] = new User();
			User user = team2Users[i];
			user.setAdmin(false);
			user.setBirthDate(new Date(now.getTime() - 1000*60*60*24*365*20));
			user.setCreationDate(now);
			user.setEmail("team2player" + i + "@gmail.com");
			user.setFirstName("Joueur");
			user.setLanguage(Language.fr);
			user.setLastName(Integer.toString(i));
			user.setPassword("test");
			DBManager.save(user);
		}
		
		
		
		//==== GROUP =====
		Group g1 = new Group();
		g1.setName("Groupe Genius");
		DBManager.save(g1);
		
		for(int i = 0; i < 4 ; i++){
			g1.addGroupRoles(new GroupRole(team1Users[i], g1, Role.Manager));
			g1.addGroupRoles(new GroupRole(team2Users[i], g1, Role.Manager));
		}
		DBManager.save(g1);

		
		
		
		
		//==== TEAMS =====
		Team team1 = new Team();
		team1.setName("Génius équipe 1");
		g1.addTeam(team1);
		for(int i = 0; i < 4 ; i++){
			team1.addUser(team1Users[i]);
		}
		DBManager.save(team1);
		
		Team team2 = new Team();
		team2.setName("Génius équipe 2");
		g1.addTeam(team2);
		for(int i = 0; i < 4 ; i++){
			team2.addUser(team2Users[i]);
		}
		DBManager.save(team2);
		
		
		
		
		
		//====CATEGORY====
		for(CategoryType type : CategoryType.values()){
			Category c = new Category();
			c.setName(type.toString());
			c.setType(type);
			DBManager.save(c);
		}

		
		
		
		
		
		
		return Response.status(Status.OK).build();
	}
	*/
}