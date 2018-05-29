package com.example.controller;

import com.example.model.User;
import com.example.model.order;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.repository.orderRepo;
import com.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import java.util.*;
@Controller
public class AdminController {
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
    private com.example.repository.wishRepo wishRepo;

    @Autowired
    private orderRepo orderRepo;

    @PostMapping(value = "/deleteComment/{id}")
    public String deleteComment(@PathVariable int id){
        wishRepo.deleteById(id);
        return("redirect:/admin/home");
    }
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(ModelMap model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        String passwordDec = bCryptPasswordEncoder.encode(user.getPassword());
        String username = user.getName() + " "  + user.getLastName();

        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getLastName());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("city", user.getCity());
        model.addAttribute("username", username);

        return "profile";
    }
    @RequestMapping(value = "/basicTable", method = RequestMethod.GET)
    public String getBasicTable(ModelMap model) {
        List<order> orders = orderRepo.findAll();
        model.addAttribute("allOrders", orders);
        System.out.println(orders);
        return "basic-table";
    }

    @RequestMapping(value = "/blankPage", method = RequestMethod.GET)
    public String getBlankPage() {
        return"blank";
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String getMap() {
        return "map-google";
    }
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String getError(){
        return "error";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "surname", required = false) String surname,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam(value = "password", required = false) String password,
                              @RequestParam(value="password2", required = false) String password2,
                              @RequestParam(value = "city", required = false) String city, ModelMap model)
                              {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        String passwordDec = bCryptPasswordEncoder.encode(user.getPassword());
        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getLastName());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("city", user.getCity());

        user.setName(name);
        user.setLastName(surname);
        user.setEmail(email);
        user.setCity(city);
        System.out.println("passwords are = "+password+" : " +password2);
        System.out.println("passowrd "+user.getPassword());
        if(password.equals(password2) && (!(password.equals("")) && !(password2.equals(""))   )){
                                  user.setPassword(bCryptPasswordEncoder.encode(password));
                                  userRepository.save(user);
                                  model.addAttribute ("passwordConfirm", "Personal information updated! ");
                                  return "/profile";
                              }
        else if(password.equals("") && password2.equals("")){
            model.addAttribute("passwordConfirm", "set password ");
        }
        else{

            model.addAttribute ("passwordConfirm", "Passwords didn't match! ");
        }
        return "/profile";
    }

    @PostMapping(value = "/deleteOrder/{id}")
    public String deleteOrder(@PathVariable int id){
        orderRepo.deleteById(id);
        return("basic-table");
    }

    @RequestMapping("/sortByQ")
    public String sortByQuantity(ModelMap model){
        List<order> orders = orderRepo.findAllByOrderByQuantityAsc();
        model.addAttribute("allOrders", orders);
        return "basic-table";
    }
    @RequestMapping("/sortByDate")
    public String sortByArea(ModelMap model){
        List<order> orders = orderRepo.findAllByOrderByDateAsc();
        model.addAttribute("allOrders", orders);
        return "basic-table";
    }
}