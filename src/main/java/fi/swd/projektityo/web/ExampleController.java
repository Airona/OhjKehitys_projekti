package fi.swd.projektityo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.swd.projektityo.domain.Example;
import fi.swd.projektityo.domain.ExampleRepository;

@CrossOrigin
@Controller
public class ExampleController {
	
	@Autowired
	private ExampleRepository repository;
	
    @RequestMapping(value="/login")
    public String login() {	
        return "login";
    }
	
//Rest methods
	
	@RequestMapping(value = "/Examples", method = RequestMethod.GET)
	public @ResponseBody List<Example> ExamplelistJson() {
		return (List<Example>) repository.findAll();
	}
	
	@RequestMapping (value = "/Example/{id}", method = RequestMethod.GET)
	public @ResponseBody Example getExampleJson(@PathVariable("id") Long ExampleId) {
		return repository.findOne(ExampleId);
	}

}