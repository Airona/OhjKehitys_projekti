package fi.swd.projektityo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.swd.projektityo.domain.Example;
import fi.swd.projektityo.domain.ExampleRepository;
import fi.swd.projektityo.domain.User;
import fi.swd.projektityo.domain.UserRepository;

@SpringBootApplication
public class ProjektityoApplication {

	private static final Logger log = LoggerFactory.getLogger(ProjektityoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ProjektityoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(ExampleRepository repository, UserRepository urepository) {
		return (args) -> {
			//create users
			User user1 = new User("user", "$2a$07$fWtYtMWmjXVHByLa6SqgNeXAVgrVd6oOxDptC95J/t7nSTkxj8d7G", "user@harjoitus", "USER");
			User user2 = new User("admin", "$2a$07$C.d3uG.8KVbGIzxOOiApI.C4QI7pd1BN40ay88Yi4euw7.TCuSOYm", "admin@harjoitus", "ADMIN");
			urepository.save(user1);
			urepository.save(user2);
			
			// save a couple of books
			repository.save(new Example("item"));
			
			// fetch all examples
						log.info("Examples found with findAll():");
						log.info("-------------------------------");
						for (Example example : repository.findAll()) {
							log.info(example.toString());
						}
		};
	}
}
