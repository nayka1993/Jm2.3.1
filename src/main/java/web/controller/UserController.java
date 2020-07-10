package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.User;
import web.service.UserService;


@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listUsers(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUser", this.userService.listUsers());
        return "users";
    }

    @RequestMapping(value= "/user/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user){
        if(user.getId() == 0){
            this.userService.addUser(user);
        }else{
            this.userService.updateUser(user);
        }
        return "redirect:/";
    }

    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id) {
        this.userService.removeUser(id);
        return "redirect:/";
    }


    @RequestMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @RequestMapping("/update/{id}")
    public String updateUser(@PathVariable("id") int id, User user, Model model) {
        userService.updateUser(user);
        model.addAttribute("users", userService.listUsers());
        return "redirect:/";
    }
}
