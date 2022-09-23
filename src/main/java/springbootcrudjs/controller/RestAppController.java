package springbootcrudjs.controller;

import springbootcrudjs.model.User;
import springbootcrudjs.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.SortedSet;
import java.util.TreeSet;

@RestController
public class RestAppController {

    private final AppService appService;

    public RestAppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping(value = "/admin/users")
    public User saveUser(@ModelAttribute User user, HttpServletRequest request) {
        SortedSet<String> roles = new TreeSet<String>();
        if ("ADMIN".equals(request.getParameter("add_input_userRoles"))) {
            roles.add("ROLE_ADMIN");
        }
        roles.add("ROLE_USER");
        appService.saveUser(user, roles);
        return user;
    }

    @GetMapping(value = "/admin/users/{id}")
    public User getUser(@PathVariable("id") int id) {
        return appService.findUserById(id);
    }

    @DeleteMapping("/admin/users/{id}")
    public void deleteAdminUsers(@PathVariable int id) {
        appService.deleteUserById(id);
    }

    @PatchMapping(value = "/admin/users")
    public User editUser(@ModelAttribute User user, HttpServletRequest request) {

        SortedSet<String> roles = new TreeSet<String>();
        if ("ADMIN".equals(request.getParameter("edit_input_userRoles"))) {
            roles.add("ROLE_ADMIN");
        }
        roles.add("ROLE_USER");

        appService.saveUser(user, roles);
        return user;
    }

}
