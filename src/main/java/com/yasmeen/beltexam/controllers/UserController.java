package com.yasmeen.beltexam.controllers;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yasmeen.beltexam.services.RoleService;
import com.yasmeen.beltexam.services.SubscriptionService;
import com.yasmeen.beltexam.services.UserService;
import com.yasmeen.beltexam.validators.UserValidator;
import com.yasmeen.beltexam.models.Role;
import com.yasmeen.beltexam.models.Subscription;
import com.yasmeen.beltexam.models.User;

@Controller
@RequestMapping("/")
public class UserController {

	private UserValidator userValidator;
	private UserService userService;
	private RoleService roleService;
	private SubscriptionService subService;
	public UserController(UserService userService, UserValidator userValidator, RoleService roleService, SubscriptionService subService) {
	    this.userService = userService;
	    this.userValidator = userValidator;
	    this.roleService = roleService;
	    this.subService = subService;
    }
	
	@RequestMapping(value={"/login","/register"})
	public String login(Model model,@RequestParam(value="error",required=false) String error,@RequestParam(value="logout",required=false) String logout){
		if(error != null){model.addAttribute("errorMessage","Invalid Credentials.");}
		if(logout != null){model.addAttribute("logoutMessage","Logout Successful");}
		
		model.addAttribute("user",new User());
		return "loginRegister.jsp";
	}
	
	@PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "loginRegister.jsp";
        }
        else if(roleService.findByName("ROLE_ADMIN").getUsers().size() < 1) {
        		userService.saveUserWithAdminRole(user);
        		return "redirect:/login";
        } else {
        		userService.saveWithUserRole(user);
        		return "redirect:/login";
        }
    }
	
	@RequestMapping(value = {"/dashboard"})
	public String showHome(Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("currentUser", userService.findByEmail(email));
        userService.updateUser(user);
        if(userService.checkIfAdmin(user)) {
        		return "redirect:/admin";
        } else if(user.getSubscription() == null){
        		return "redirect:/signup";
        } else {
        		return "homePage.jsp";
        }
        
	}
	
	@RequestMapping("/admin")
	public String displayAdmin(Principal principal, Model model, @ModelAttribute("subscription") Subscription subscription) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        model.addAttribute("all", userService.getAll());
        model.addAttribute("allsubs", subService.getAll());
        return "adminPage.jsp";
	}
	
	@RequestMapping("/signup")
	public String getSub(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        model.addAttribute("allsub", subService.getAll());
        return "signUp.jsp";
	}
	@PostMapping("/createsub")
	public String createSub(Principal principal, @Valid @ModelAttribute("subscription") Subscription subscription, BindingResult result) {
		subService.createSub(subscription);
//		String email = principal.getName();
//        User user = userService.findByEmail(email);
        return "redirect:/admin";
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping("/signup")
	public String setSub(Principal principal, @RequestParam("due") Integer due, @RequestParam("subid") Long id) {
		String email = principal.getName();
		User user = userService.findByEmail(email);
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endofmonth = c.getTime();
		if(due < date.getDate()) {
			date.setMonth(date.getMonth()+1);
			date.setDate(due);
		} else if (due > endofmonth.getDate()) {
			date.setDate(endofmonth.getDate());
		}
//		Date today = new Date();
//    		Calendar cal = Calendar.getInstance();
//    		cal.setTime (date);
//    		cal.add (Calendar.DATE, due);
//    		date = cal.getTime();
//    		if(date.getMonth() != today.getMonth()) {
//    			Calendar cal2 = Calendar.getInstance();
//    			cal2.set(Calendar.DATE, cal2.getActualMaximum(Calendar.DATE));
//    			Date lastDayOfMonth = cal2.getTime();
//    			user.setDueDate(lastDayOfMonth);
//    			Subscription sub = subService.getOneById(id);
//    			user.setSubscription(sub);
//    			userService.updateUser(user);
//    			return "redirect:/dashboard";
//    		}
		user.setDueDate(date);
		Subscription sub = subService.getOneById(id);
		user.setSubscription(sub);
		userService.updateUser(user);
		return "redirect:/dashboard";
	}
	
	@RequestMapping("/")
	public String gotoLogin() {
		return "redirect:/login";
	}
	
	
	@RequestMapping("/sub/activate/{id}")
	public String activate(@PathVariable("id") Long id) {
		Subscription sub = subService.getOneById(id);
		Boolean availability = true;
		sub.setAvailability(availability);
		subService.updateSub(sub);
		return "redirect:/admin";
	}
	
	@RequestMapping("/sub/deactivate/{id}")
	public String deactivate(@PathVariable("id") Long id) {
		Subscription sub = subService.getOneById(id);
		Boolean availability = false;
		sub.setAvailability(availability);
		subService.updateSub(sub);
		return "redirect:/admin";
	}
	
	@RequestMapping("/sub/delete/{id}")
	public String deleteSub(@PathVariable("id") Long id) {
		subService.deleteSub(id);
		return "redirect:/admin";
	}
//	
}

