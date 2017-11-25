package fi.swd.projektityo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fi.swd.projektityo.domain.CloudStorageHelper;
import fi.swd.projektityo.domain.Image;
import fi.swd.projektityo.domain.ImageRepository;
import fi.swd.projektityo.domain.User;
import fi.swd.projektityo.domain.UserRepository;
import fi.swd.projektityo.web.error.EmailExistsException;

@Controller
public class WebsiteController {
	
	@Autowired
	private ImageRepository irepository;
	@Autowired
	private UserRepository urepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private CloudStorageHelper firebase = new CloudStorageHelper();

//requests SPRING SECURITY
    @RequestMapping(value = "/login")
    public String login() {
        return "/login";
    }    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signup";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView registerUserAccount
          (@ModelAttribute("user") @Valid UserDto accountDto, 
          BindingResult result, WebRequest request, Errors errors)
    {    	
    	if (true) { //!result.hasErrors()
        	registerNewUserAccount(accountDto);            
        }
        
        if (result.hasErrors()) {
            return new ModelAndView("signup", "user", accountDto);
        } 
        else { //TODO, give username to loginpage
            return new ModelAndView("login", "user", accountDto);
        }
    }
    
    private User registerNewUserAccount(UserDto accountDto) { //TODO , implement into services()
    	String email = accountDto.getEmail();
    	boolean emailBoolean = emailExist(email);
		if (emailBoolean) {
			throw new EmailExistsException("There is an account with that email address: " + accountDto.getEmail());
		}
		final User user = new User();
		user.setUsername(accountDto.getUsername());
		String password = passwordEncoder.encode(accountDto.getPassword());
		user.setPasswordHash(password);
		user.setEmail(accountDto.getEmail());
		user.setRole("ROLE_USER");
		urepository.save(user);
		return user;
    }
    private boolean emailExist(String email) {
		User user = null;
        try {
        	user = urepository.findByEmail(email);			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if (user != null) {
            return true;
        }
        return false;
    }
    
//Image upload request
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody Image handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
    	return firebase.uploadFile(file, name); 
    }
    
//unnecessary Rest methods
/*
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public @ResponseBody List<Image> ImagelistJson() {
		return (List<Image>) repository.findAll();
	}
	
	@RequestMapping (value = "/image/{id}", method = RequestMethod.GET)
	public @ResponseBody Image getImageJson(@PathVariable("id") Long ImageId) {
		return repository.findOne(ImageId);
	}    	
*/
//React catch all (must be last to catch, TODO edit to match only possible pages)
	@RequestMapping(value = {"*"}, method = RequestMethod.GET)
  	public String index() {
  		return "/index";
	}

}