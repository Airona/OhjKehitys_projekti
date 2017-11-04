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
	
	private final ImageRepository repository;
	private final UserRepository urepository;
	
	@Autowired
	public DatabaseLoader(ImageRepository repository, UserRepository urepository) {
		this.repository = repository;
		this.urepository = urepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		
		//create users
		User user1 = new User("user", "$2a$07$fWtYtMWmjXVHByLa6SqgNeXAVgrVd6oOxDptC95J/t7nSTkxj8d7G", "user@harjoitus", "USER");
		User user2 = new User("admin", "$2a$07$C.d3uG.8KVbGIzxOOiApI.C4QI7pd1BN40ay88Yi4euw7.TCuSOYm", "admin@harjoitus", "ADMIN");
		urepository.save(user1);
		urepository.save(user2);
		
		// save examples
		repository.save(new Image("cs","kz_kzlt_dementia01","11/01/2017","url.jpg","kz, cs1.6"));
		
		// fetch all examples
		log.info("Examples found with findAll():");
		log.info("-------------------------------");
		for (Image image : repository.findAll()) {
			log.info(image.toString());
		}
		log.info("-------------------------------");
		
		
	}
}