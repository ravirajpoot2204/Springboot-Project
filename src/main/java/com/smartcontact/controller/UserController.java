package com.smartcontact.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.smartcontact.dao.ContactRepo;
import com.smartcontact.dao.UserRepo;
import com.smartcontact.entities.Contact;
import com.smartcontact.entities.User;
import com.smartcontact.helper.Message;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepo repo;

    @Autowired
    private ContactRepo repo2;

//Adding common data
    @ModelAttribute
    public void addCommonData(@RequestParam(value = "error", required = false) String error, Model model,
	    Principal principal) {
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	String Username = principal.getName();

	// getting user data

	User user = this.repo.getUserByUserName(Username);

	// sending data to view
	model.addAttribute(user);
    }

    // UserDashboard
    @GetMapping("/")
    public String userDashboard(@RequestParam(value = "error", required = false) String error, Model model) {
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	model.addAttribute("title", "UserDashboard : SmartContactManger");

	return "normal/index";
    }

    @GetMapping("/add-contact")
    public String addContact(@RequestParam(value = "error", required = false) String error, Model model) {
	
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	model.addAttribute("title", "Add Contact : SmartContactManger");
	model.addAttribute("contact", new Contact());
	return "normal/addContact";
    }

    // add contact process

    @PostMapping("/add-Contact-Process")
    public String addContactProcess(@RequestParam(value = "error", required = false) String error,
	    @RequestParam("profilePic") MultipartFile f, Principal principal, Model model, HttpSession session)
	    throws InterruptedException {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	Contact contact = (Contact) session.getAttribute("contact");

	try {

	    if (f.isEmpty()) {

		contact.setImageurl("default.jpg"); // Save a default image name
	    } else {

		String originalFilename = f.getOriginalFilename();
		String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;

		// Set the unique filename in the contact object
		contact.setImageurl(uniqueFilename);

		File savef = new ClassPathResource("static/img").getFile();
		Path path = Paths.get(savef.getAbsolutePath() + File.separator + uniqueFilename);

		Files.copy(f.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	    }

	    String name = principal.getName();

	    User user = this.repo.getUserByUserName(name);
	    contact.setUser(user);

	    user.getContact().add(contact);

	    this.repo.save(user);

	    model.addAttribute("checkStatus", "Contact added Successfully");
	    model.addAttribute("cmt", "alert-success text-center");
	} catch (Exception e) {
	    model.addAttribute("checkStatus", "Something got wrong!!");
	    model.addAttribute("cmt", "alert-danger text-center");

	    e.printStackTrace();
	}
	return "normal/addContact";
    }

    // showing and loading n*contact per page
    // page have 5 contact per page

    // we can have n page depend on contacts

    @GetMapping("/showContact/{page}")
    public String showContact(@RequestParam(value = "error", required = false) String error,
	    @PathVariable("page") Integer page, Model model, Principal principal) {
	// share contact list

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	//getting user by user-name = (email)
	String name = principal.getName();
	User user = this.repo.getUserByUserName(name);

	// pageable will have two variable for
	// current page and =page
	// contact per page=5
	Pageable pageable = PageRequest.of(page, 5);
	Page<Contact> contacts = this.repo2.findByUserId(user.getUid(), pageable);

	model.addAttribute("title", "View Contact");

	model.addAttribute("contacts", contacts);

	model.addAttribute("currentPage", page);

	model.addAttribute("totalPage", contacts.getTotalPages());

	return "normal/showAllContact";
    }

    // Showing contact profile
    @GetMapping("/contact/{cid}")
    public String showOneContact(@RequestParam(value = "error", required = false) String error,
	    @PathVariable("cid") Integer id, Model model, Principal principal) {
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	Optional<Contact> optional = this.repo2.findById(id);
	Contact contact = optional.get();
	String name = principal.getName();
	User user = this.repo.getUserByUserName(name);
	if (contact.getUser().getUid() == user.getUid())
	    model.addAttribute("oneContact", contact);
	return "/normal/showContactProfile";
    }

    // Showing User profile
    @GetMapping("/profile/{uid}")
    public String showUserProfile(@RequestParam(value = "error", required = false) String error,
	    @PathVariable("uid") Integer id, Model model, Principal principal, HttpSession session) {
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	String name = principal.getName();
	User user = this.repo.getUserByUserName(name);
	model.addAttribute("user", user);
	session.setAttribute("msg", "");
	session.setAttribute("type", "");

	return "/normal/showUserProfile";
    }

    @GetMapping("contact/delete/{cid}")
    public String deleteContact(@RequestParam(value = "error", required = false) String error,
	    @PathVariable("cid") Integer cid, Principal principal, HttpSession session) {
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	String name = principal.getName();
	User user = this.repo.getUserByUserName(name);

	Optional<Contact> optional = this.repo2.findById(cid);
	if (optional.isPresent()) {
	    Contact contact = optional.get();

	    // Check ownership
	    if (contact.getUser().getUid() == (user.getUid())) {

		String uniqueFilename = contact.getImageurl();
		if (!"default.jpg".equals(uniqueFilename)) {
		    try {
			File savef = new ClassPathResource("static/img").getFile();
			Path path = Paths.get(savef.getAbsolutePath() + File.separator + uniqueFilename);
			Files.deleteIfExists(path);
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}

		this.repo2.delete(contact);
		session.setAttribute("message", new Message("Contact deleted successfully!", "alert-success"));
	    } else {
		session.setAttribute("message",
			new Message("Contact does not exist in your database!", "alert-danger"));
	    }
	} else {
	    session.setAttribute("message", new Message("Contact not found!", "alert-warning"));
	}

	return "redirect:/user/showContact/0";
    }

    @PostMapping("/contact/update/{cid}")

    // Updatecontact Handler
    public String updateContactHandler(@RequestParam(value = "error", required = false) String error,
	    @PathVariable("cid") Integer cid, Model model) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	Contact contact = this.repo2.findById(cid).get();
	model.addAttribute("c", contact);

	return "normal/updateForm";
    }

    // update contact process

    @PostMapping("/update-Contact-Process")
    public String updateContactProcess(@RequestParam(value = "error", required = false) String error,
	    @ModelAttribute Contact contact, Principal principal, HttpSession session,
	    @RequestParam("profilePic") MultipartFile mfile, Model model) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	try {

	    User user = this.repo.getUserByUserName(principal.getName());

	    contact.setUser(user);
	    Contact oldContact = this.repo2.findById(contact.getCid()).get();

	    String oldImg = oldContact.getImageurl();

	    if (user.getUid() == oldContact.getUser().getUid()) {

		if (!mfile.isEmpty()) {

		    // deleting existing picture
		    File savef = new ClassPathResource("static/img").getFile();
		    Path dpath = Paths.get(savef.getAbsolutePath() + File.separator + oldImg);
		    Files.deleteIfExists(dpath);

		    // saving new picture
		    String uniqueFileName = System.currentTimeMillis() + "_" + mfile.getOriginalFilename();
		    Path spath = Paths.get(savef.getAbsolutePath() + File.separator + uniqueFileName);
		    Files.copy(mfile.getInputStream(), spath, StandardCopyOption.REPLACE_EXISTING);

		    // Set the unique filename in the contact object
		    contact.setImageurl(uniqueFileName);

		} else {

		    contact.setImageurl(oldImg);

		}

		this.repo2.save(contact);

	    } else {
		session.setAttribute("msg", "unauthorized access");

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    // TODO: handle exception
	}
	return "redirect:/user/contact/" + contact.getCid();
    }

    // User Update Handler
    @PostMapping("/account/update/{uid}")
    public String updateUserHandler(@RequestParam(value = "error", required = false) String error,
	    @PathVariable("uid") Integer uid, Model model) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	Optional<User> optional = this.repo.findById(uid);
	User user = optional.get();
	model.addAttribute("user", user);

	return "normal/updateUserForm";
    }

    // Update User Process
    @PostMapping("/update-User-Process")
    public String updateUserProcess(@RequestParam(value = "error", required = false) String error,
	    @ModelAttribute User user, Principal principal, @RequestParam("profilePic") MultipartFile mfile,
	    HttpSession session) {
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	try {
	    int uid = user.getUid();
	    User userByUserName = this.repo.getUserByUserName(principal.getName());
	    String oldImg = userByUserName.getImageurl();
	    if (user.getUid() == uid) {
		if (!mfile.isEmpty()) {
		    // deleting existing picture
		    File savef = new ClassPathResource("static/img").getFile();
		    Path dpath = Paths.get(savef.getAbsolutePath() + File.separator + oldImg);
		    Files.deleteIfExists(dpath);
		    // saving new picture
		    String uniqueFileName = System.currentTimeMillis() + "_" + mfile.getOriginalFilename();
		    Path spath = Paths.get(savef.getAbsolutePath() + File.separator + uniqueFileName);
		    Files.copy(mfile.getInputStream(), spath, StandardCopyOption.REPLACE_EXISTING);
		    // Set the unique filename in the contact object
		    user.setImageurl(uniqueFileName);
		} else {
		    user.setImageurl(oldImg);
		}
		this.repo.save(user);
	    } else {
		session.setAttribute("msg", "unauthorized access");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return "redirect:/user/profile/" + user.getUid();
    }

    // settings page
    @GetMapping("/settings")
    public String openSettings(@RequestParam(value = "error", required = false) String error, HttpSession session) {
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	session.setAttribute("msg", "");
	session.setAttribute("type", "");
	return "normal/settings";
    }

    // change Password process Handler
    @PostMapping("/change-password")
    public String changePasswordProcess(@RequestParam(value = "error", required = false) String error,
	    @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,
	    Principal principal, HttpSession session) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	 

	User currentUser = this.repo.getUserByUserName(principal.getName());

	if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
	    // change password
	    currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
	    this.repo.save(currentUser);
	    session.setAttribute("msg", "You have changed your password successfully!!");
	    session.setAttribute("type", "alert alert-success");
	    return "normal/settings";

	} else {
	    session.setAttribute("msg", "Wrong old password!!");
	    session.setAttribute("type", "alert alert-danger");
	    return "normal/settings";
	}

    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error_page";
    }
}
