package fi.swd.projektityo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.swd.projektityo.domain.Image;
import fi.swd.projektityo.domain.ImageRepository;

@Controller
public class WebsiteController {
	
	@Autowired
	private ImageRepository repository;
	
    @RequestMapping(value = "/login")
    public String login() {	
        return "login";
    }
	
//Rest methods
	
	@RequestMapping(value = "/Images", method = RequestMethod.GET)
	public @ResponseBody List<Image> ImagelistJson() {
		return (List<Image>) repository.findAll();
	}
	
	@RequestMapping (value = "/Image/{id}", method = RequestMethod.GET)
	public @ResponseBody Image getImageJson(@PathVariable("id") Long ImageId) {
		return repository.findOne(ImageId);
	}

//React base catchall
	@RequestMapping(value = {"*"}, method = RequestMethod.GET)
  	public String index() {
  		return "/index";
  	}
	
}