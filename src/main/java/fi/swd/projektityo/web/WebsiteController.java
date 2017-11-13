package fi.swd.projektityo.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import fi.swd.projektityo.domain.CloudStorageHelper;
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

    @RequestMapping(value = "/wblob", method = RequestMethod.GET)
    public String createBlob() {
    	firebase.createSampleBlob();
        return "/index";
    }
    
    @RequestMapping(value = "/ublob", method = RequestMethod.GET)
    public String updateBlob() {
    	firebase.updateSampleBlob();
        return "/index";
    }
    
    @RequestMapping(value = "/rblob", method = RequestMethod.GET)
    public String readBlob() {
    	firebase.getSampleBlob();
        return "/index";
    }
    
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public String getall() {
    	firebase.listBucket();
        return "/index";
    }
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<String>  handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws Exception{
        if (name.contains("/")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Folder separators not allowed.");
        } else if (name.contains("/")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Relative pathnames not allowed.");
        }

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                return ResponseEntity.ok("File " + name + " uploaded.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("You failed to upload " + name + " because the file was empty.");
        }
    }
    
//Rest methods
	
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public @ResponseBody List<Image> ImagelistJson() {
		return (List<Image>) repository.findAll();
	}
	
	@RequestMapping (value = "/image/{id}", method = RequestMethod.GET)
	public @ResponseBody Image getImageJson(@PathVariable("id") Long ImageId) {
		return repository.findOne(ImageId);
	}

//React base catchall
	@RequestMapping(value = {"*"}, method = RequestMethod.GET)
  	public String index() {
  		return "/index";
  	}
	
}