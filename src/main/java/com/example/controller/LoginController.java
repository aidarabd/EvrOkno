package com.example.controller;

import com.example.model.Role;
import com.example.model.order;
import com.example.model.wishes;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.repository.orderRepo;
import com.example.repository.wishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Qualifier("userRepository")
	@Autowired
	private UserRepository userRepository;


	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Qualifier("roleRepository")
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private wishRepo wishRepo;
	@Autowired
	private orderRepo orderRepo;

	@RequestMapping(value = {"/",}, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("a");
		return modelAndView;
	}


	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String testcase() {
		return roleRepository.findByRole("ADMIN").getRole();
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration(RedirectAttributes redir) {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("a");

		redir.addAttribute("auth", "reg");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@RequestParam(value = "email") String email,
									  @RequestParam(value = "name") String name,
									  @RequestParam(value = "lastName") String lastName,
									  @RequestParam(value = "password") String password
	) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userRepository.findByEmail(email);

		if (userExists != null) {
			modelAndView.addObject("message", "User Exists! Pleas rry another email!");
		} else {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setName(name);
			newUser.setLastName(lastName);
			newUser.setPassword(password);
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setActive(true);
			Role userRole = roleRepository.findByRole("USER");

			newUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			userRepository.save(newUser);
			modelAndView.addObject("message", "User was registered successfully!");
		}

		modelAndView.addObject("isReg", true);
		modelAndView.setViewName("a");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String home(ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		long countOrder = orderRepo.count();
		long userCount = userRepository.count();

		List<wishes> wishes = wishRepo.findFirst3ByOrderByDateAsc();

		List<order> orders = orderRepo.findFirst7ByOrderByDateDesc();
		model.addAttribute("wishes", wishes);
		model.addAttribute("orders", orders);
		System.out.println("orders = "+orders);
		model.addAttribute("userCount", userCount);
		model.addAttribute("userName", user.getName());
		model.addAttribute("countOrder", countOrder);



		//modelAndView.addObject("userName", user.getName());
		//modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");

		modelAndView.setViewName("admin/home");
		return "index";
	}

	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public String homeUser() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		System.out.println(userId);
		return "userHome";
	}

	@RequestMapping(value = "/guest", method = RequestMethod.GET)
	public String homeGuest() {
		return "a";
	}

	@RequestMapping(value = "/makeOrderPage", method = RequestMethod.POST)
	public String order() {
		return "order";
	}
	@RequestMapping(value = "/makeOrderPageUser", method = RequestMethod.POST)
	public String orderUser() {
		return "orderUser";
	}

	@RequestMapping(value = "/makeOrderPage/makeOrder", method = RequestMethod.POST)
	public String makeOrder(
			@RequestParam(value = "user_name", required = false) String name,
			@RequestParam(value = "phone_num", required = false) Integer phoneNum,
			@RequestParam(value = "vertical", required = false) Integer vertical,
			@RequestParam(value = "horizontal", required = false) Integer horizontal,
			@RequestParam(value = "podokonnik", required = false) String podokonnik,
			@RequestParam(value = "additional", required = false) String additional,
			@RequestParam(value = "prof_type", required = false) String profType,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "quantity", required = false) Integer quantity) {

		order ord = new order();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		String userName = user.getName();
		String userLastName = user.getLastName();
		String fullName = userName+" "+ userLastName;

		ord.setUserFullName(fullName);
		ord.setPhoneNum(phoneNum);
		ord.setVertical(vertical);
		ord.setHorizontal(horizontal);
		ord.setPodokonnik(podokonnik);
		ord.setAdditional(additional);
		ord.setProfType(profType);
		ord.setColor(color);
		ord.setQuantity(quantity);
		ord.setDate(new Date());
		orderRepo.save(ord);
		return "a";
	}
	@RequestMapping(value = "/makeOrderPage/makeOrderUser", method = RequestMethod.POST)
	public String makeOrderUser(
			@RequestParam(value = "user_name", required = false) String name,
			@RequestParam(value = "phone_num", required = false) Integer phoneNum,
			@RequestParam(value = "vertical", required = false) Integer vertical,
			@RequestParam(value = "horizontal", required = false) Integer horizontal,
			@RequestParam(value = "podokonnik", required = false) String podokonnik,
			@RequestParam(value = "additional", required = false) String additional,
			@RequestParam(value = "prof_type", required = false) String profType,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "quantity", required = false) Integer quantity) {

		order ord = new order();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		String userName = user.getName();
		String userLastName = user.getLastName();
		String fullName = userName+" "+ userLastName;

		ord.setUserFullName(fullName);
		ord.setPhoneNum(phoneNum);
		ord.setVertical(vertical);
		ord.setHorizontal(horizontal);
		ord.setPodokonnik(podokonnik);
		ord.setAdditional(additional);
		ord.setProfType(profType);
		ord.setColor(color);
		ord.setQuantity(quantity);
		ord.setDate(new Date());
		orderRepo.save(ord);
		return "userHome";
	}
	@RequestMapping(value = "/getContact", method = RequestMethod.POST)
	public String getContact(@RequestParam(value = "name", required = false) String name,
							 @RequestParam(value = "user_id", required = false) Integer user_Id,
							 @RequestParam(value="user_full_name", required = false) String full,
							 @RequestParam(value = "phone_num", required = false) String phoneNum,
							 @RequestParam(value = "comment", required = false) String comment) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		int userId = user.getId();
		String name1 = user.getName();
		String lastName = user.getLastName();
		String fullName = name1+" " + lastName;
		String email = user.getEmail();

		wishes wish = new wishes();
		wish.setName(name);
		wish.setUserId(userId);
		wish.setUserFullName(fullName);
		wish.setPhoneNum(email);
		wish.setComment(comment);
		wish.setDate(new Date());
		wishRepo.save(wish);

		return "userHome";
	}

	@RequestMapping(value = "/getContactGuest", method = RequestMethod.POST)
	public String getContactGuest(@RequestParam(value = "name", required = false) String name,
								  @RequestParam(value = "phone_num", required = false) String phoneNum,
								  @RequestParam(value = "comment", required = false) String comment) {

		wishes wish = new wishes();
		wish.setName(name);
		wish.setPhoneNum(phoneNum);
		wish.setComment(comment);
		wish.setDate(new Date());
		wishRepo.save(wish);

		return "a";
	}


}
