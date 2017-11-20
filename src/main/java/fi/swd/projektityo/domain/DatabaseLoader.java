package fi.swd.projektityo.domain;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fi.swd.projektityo.ProjektityoApplication;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ProjektityoApplication.class);
	
	
	private final UserRepository urepository;
	//private final GameRepository grepository;
	private final ImageRepository irepository;
	
	@Autowired
	public DatabaseLoader(UserRepository urepository, ImageRepository irepository, GameRepository grepository) {
		this.urepository = urepository;
		//this.grepository = grepository;
		this.irepository = irepository;
	}

	@Override
	@Transactional
	public void run(String... strings) throws Exception {
		
		/*example
		 * 
		 *	// save a couple of categories
	        BookCategory categoryA = new BookCategory("Category A");
	        Set bookAs = new HashSet<Book>(){{
	            add(new Book("Book A1", categoryA));
	            add(new Book("Book A2", categoryA));
	            add(new Book("Book A3", categoryA));
	        }};
	        categoryA.setBooks(bookAs);
		 * 
		 * 
		 * 
		 * */
		
		
		
		
		//create users
		User user1 = new User(1L,"user", "$2a$07$fWtYtMWmjXVHByLa6SqgNeXAVgrVd6oOxDptC95J/t7nSTkxj8d7G", "user@harjoitus", "USER");
		User user2 = new User(0L,"admin", "$2a$07$C.d3uG.8KVbGIzxOOiApI.C4QI7pd1BN40ay88Yi4euw7.TCuSOYm", "admin@harjoitus", "ADMIN");
		urepository.save(user1);
		urepository.save(user2);
		
		// save examples
		//Game game1 = new Game(1L,"Counter-Strike 1.6");
		//grepository.save(game1);
		
//		irepository.save(new Image("Counter-Strike 1.6","user","Kuva 1","2017-11-16-004350118","https://www.googleapis.com/download/storage/v1/b/reactimages-games.appspot.com/o/2017-11-19-004350118-6.jpg?generation=1511052233004029&alt=media"));
//		irepository.save(new Image("Comic","user","Potato","2017-11-19-000809946","https://www.googleapis.com/download/storage/v1/b/reactimages-games.appspot.com/o/2017-11-19-000809946-Ee0DlD1.png?generation=1511050096184593&alt=media"));
//		irepository.save(new Image("Counter-Strike 1.6","user","Kuva 2","2017-11-18-004654076","https://www.googleapis.com/download/storage/v1/b/reactimages-games.appspot.com/o/2017-11-19-004654076-9.jpg?generation=1511052417456676&alt=media"));
//		irepository.save(new Image("Counter-Strike 1.6","user","Kuva 3","2017-11-17-004341373","https://www.googleapis.com/download/storage/v1/b/reactimages-games.appspot.com/o/2017-11-19-004341373-0.jpg?generation=1511052224509095&alt=media"));
//		irepository.save(new Image("Counter-Strike 1.6","user","Kuva 4","2017-11-19-005237851","https://www.googleapis.com/download/storage/v1/b/reactimages-games.appspot.com/o/2017-11-19-005237851-11.jpg?generation=1511052761207630&alt=media"));
		
		
		// fetch all examples
		log.info("Examples found with findAll():");
		log.info("-------------------------------");
		for (Image image : irepository.findAll()) {
			log.info(image.toString());
		}
		log.info("-------------------------------");
		
		// fetch all examples
		log.info("Examples found with findByGame():");
	}
}