package fi.swd.projektityo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import fi.swd.projektityo.domain.CloudStorageHelper;
import fi.swd.projektityo.domain.Game;
import fi.swd.projektityo.domain.Image;
import fi.swd.projektityo.domain.ImageRepository;

@Controller
public class WebsiteController {
	
	@Autowired
	private ImageRepository repository;
	private CloudStorageHelper firebase = new CloudStorageHelper();

//requests
    @RequestMapping(value = "/login")
    public String login() {
        return "/login";
    }
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
    	firebase.uploadFile(file, name);
    	return "";
    }
    
//unnecessary Rest methods
	
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public @ResponseBody List<Image> ImagelistJson() {
		return (List<Image>) repository.findAll();
	}
	
	@RequestMapping (value = "/image/{id}", method = RequestMethod.GET)
	public @ResponseBody Image getImageJson(@PathVariable("id") Long ImageId) {
		return repository.findOne(ImageId);
	}    	
	
//React catch all
	@RequestMapping(value = {"*"}, method = RequestMethod.GET)
  	public String index() {
  		return "/index";
  	}
	
}