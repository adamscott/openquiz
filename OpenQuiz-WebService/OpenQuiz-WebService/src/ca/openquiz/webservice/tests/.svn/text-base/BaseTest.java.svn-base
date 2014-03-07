package ca.openquiz.webservice.tests;

import java.util.Date;
import org.junit.After;
import org.junit.Before;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Game;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.model.GroupRole;
import ca.openquiz.webservice.model.QuestionAnagram;
import ca.openquiz.webservice.model.QuestionAssociation;
import ca.openquiz.webservice.model.QuestionGeneral;
import ca.openquiz.webservice.model.QuestionIdentification;
import ca.openquiz.webservice.model.QuestionIntru;
import ca.openquiz.webservice.model.QuestionSet;
import ca.openquiz.webservice.model.Team;
import ca.openquiz.webservice.model.Tournament;
import ca.openquiz.webservice.model.User;

public class BaseTest {
	
	private final LocalServiceTestHelper helper =  
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());  


	@Before  
	public void setUp() {  
		helper.setUp();          
	}  

	@After  
	public void tearDown() {  
		helper.tearDown();  
	}  

	public User user;
	public User user2;
	public Team team;
	public Team team2;
	public Group group;
	public Group group2;
	public GroupRole groupRole;
	public GroupRole groupRole2;

	public Tournament tournament;
	public Tournament tournament2;
	public Game game;
	public Game game2;

	public QuestionSet questionSet;
	public QuestionGeneral questionGeneral;
	public QuestionIdentification questionIdentification;
	public QuestionAnagram questionAnagram;
	public QuestionAssociation questionAssociation;
	public QuestionIntru questionIntru;

	public BaseTest()
	{}
	
	public void initUser()
	{
		user = new User();
		
		Date birthday = new Date(100);
		Date creation = new Date(1000);
		Date lastLogin = new Date(9000);
		
		String email = new String("test@email.com");
		String firstName = new String("FirstNameTest");
		String lastName = new String("LastNameTest");
		String password = new String("passwordTest");
		String salt = new String("saltTest");
		
		user.setBirthDate(birthday);
		user.setCreationDate(creation);
		user.setLastLogin(lastLogin);
		
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setLanguage(Language.en);

		DBManager.save(user);
	}
	
	public void initUsers()
	{
		initUser();
		
		user2 = new User();
		
		Date birthday = new Date(999);
		Date creation = new Date(9999);
		Date lastLogin = new Date(9999);
		
		String email = new String("user2@email.com");
		String firstName = new String("FirstNameUser2");
		String lastName = new String("LastNameUser2");
		String password = new String("passwordUser2");
		String salt = new String("saltUser2");
		
		user2.setBirthDate(birthday);
		user2.setCreationDate(creation);
		user2.setLastLogin(lastLogin);
		
		user2.setEmail(email);
		user2.setFirstName(firstName);
		user2.setLastName(lastName);
		user2.setPassword(password);
		user2.setLanguage(Language.fr);

		DBManager.save(user2);
	}
	
	public void initTeam()
	{
		team = new Team();
		
		String name = new String("nameTest");
		team.setName(name);
		team.setActive(true);
		DBManager.save(team);
	}
	
	public void initTeams()
	{
		initTeam();
		team2 = new Team();
		
		String name = new String("nameTeam2");
		team2.setName(name);

		DBManager.save(team2);
	}
	
	public void initGroup()
	{
		group = new Group();
		group.setName("groupTest");
		DBManager.save(group);
	}
	
	public void initGroups()
	{
		initGroup();
		group2 = new Group();
		group2.setName("groupTest2");
		DBManager.save(group2);
	}
	
	public void initGroupRole()
	{
		groupRole = new GroupRole();
		groupRole.setRole(Role.Manager);
		DBManager.save(groupRole);
	}
	
	public void initGroupRoles()
	{
		initGroupRole();
		groupRole2 = new GroupRole();
		groupRole2.setRole(Role.Player);
		DBManager.save(groupRole2);
	}
	
	public void initTournament()
	{
		tournament = new Tournament();
				
		tournament.setName("nameTournament");
		tournament.setStartDate(new Date(6666));
		tournament.setEndDate(new Date(6667));
		tournament.setAddress("123 rue test");
		tournament.setCity("Sherbrooke");
		tournament.setCountry("Canada");

		DBManager.save(tournament);
	}
	
	public void initTournaments()
	{
		initTournament();
		tournament2 = new Tournament();
		
		tournament2.setName("nameTournament2");
		tournament2.setStartDate(new Date(7777));
		tournament2.setEndDate(new Date(7778));
		tournament2.setAddress("456 rue test");
		tournament2.setCity("Farnham");
		tournament2.setCountry("Russie");

		DBManager.save(tournament2);
	}
	
	public void initGame()
	{
		game = new Game();
		
		game.setGameDate(new Date(1000));
		game.setName("GameTest");

		DBManager.save(game);
	}
	
	public void initGames()
	{
		initGame();
		game2 = new Game();
		
		game2.setGameDate(new Date(2000));

		DBManager.save(game2);
	}

	public void initQuestions()
	{
		questionGeneral = new QuestionGeneral();
		questionIdentification = new QuestionIdentification();
		questionAnagram = new QuestionAnagram();
		questionAssociation = new QuestionAssociation();
		questionIntru = new QuestionIntru();
		
		questionGeneral.setQuestion("QuestionGeneral");
		questionGeneral.setAnswer("ReponseGeneral");
		
//		TODO : QuestionIdentification
		
		questionAnagram.setAnagram("QuestionAnagram");
		questionAnagram.setAnswer("ReponseAnagram");
		
//		TODO : QuestionAssociation
		
		questionIntru.setQuestion("QuestionIntru");
		questionIntru.setAnswer("ReponseIntru");
		
		DBManager.save(questionGeneral);
		DBManager.save(questionAnagram);
		DBManager.save(questionIntru);		
	}
}
