package fi.swd.projektityo.domain;

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
	private final GameRepository grepository;
	private final ImageRepository irepository;
	
	@Autowired
	public DatabaseLoader(UserRepository urepository, ImageRepository irepository, GameRepository grepository) {
		this.urepository = urepository;
		this.grepository = grepository;
		this.irepository = irepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		
		//create users
		User user1 = new User(1L,"user", "$2a$07$fWtYtMWmjXVHByLa6SqgNeXAVgrVd6oOxDptC95J/t7nSTkxj8d7G", "user@harjoitus", "USER");
		User user2 = new User(0L,"admin", "$2a$07$C.d3uG.8KVbGIzxOOiApI.C4QI7pd1BN40ay88Yi4euw7.TCuSOYm", "admin@harjoitus", "ADMIN");
		urepository.save(user1);
		urepository.save(user2);
		
		// save examples
		Game game1 = new Game(1L,"Counter-Strike 1.6");
		grepository.save(game1);
		
		Image image1 = new Image(1L,1L,"Kuva 1","16/11/2017","https://www.googleapis.com/download/storage/v1/b/reactimages-games.appspot.com/o/3%5B1%5D.jpg-2017-11-16-074739139?generation=1510818459645216&alt=media");
		Image image2 = new Image(1L,1L,"Kuva 2","16/11/2017","https://www.googleapis.com/download/storage/v1/b/reactimages-games.appspot.com/o/0%5B1%5D.jpg-2017-11-16-074605419?generation=1510818368483454&alt=media");
		irepository.save(image1);
		irepository.save(image2);
		
		// fetch all examples
		log.info("Examples found with findAll():");
		log.info("-------------------------------");
		for (Image image : irepository.findAll()) {
			log.info(image.toString());
		}
		log.info("-------------------------------");
		
		
	}
}