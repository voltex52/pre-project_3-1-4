package springbootcrudjs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import springbootcrudjs.model.User;
import springbootcrudjs.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.SortedSet;
import java.util.TreeSet;

@RestController
@RequestMapping("/admin")
public class RestAppController {

    private final AppService appService;

    public RestAppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> saveUser(@ModelAttribute User user, HttpServletRequest request) {
        SortedSet<String> roles = new TreeSet<String>();
        if ("ADMIN".equals(request.getParameter("add_input_userRoles"))) {
            roles.add("ROLE_ADMIN");
        }
        roles.add("ROLE_USER");
        appService.saveUser(user, roles);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        return new ResponseEntity<>(appService.findUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Exception> deleteAdminUsers(@PathVariable int id) {
        appService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/users")
    public ResponseEntity<User> editUser(@ModelAttribute User user, HttpServletRequest request) {

        SortedSet<String> roles = new TreeSet<String>();
        if ("ADMIN".equals(request.getParameter("edit_input_userRoles"))) {
            roles.add("ROLE_ADMIN");
        }
        roles.add("ROLE_USER");
        appService.updateUser(user, roles);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
