package springbootcrudjs.controller;

import springbootcrudjs.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {
    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping(value = "/admin/users")
    public String adminPanelPage(ModelMap model) {
        model.addAttribute("users", appService.findAllUsers());
        return "/admin";
    }

    @GetMapping(value = "/user")
    public String userProfilePage(ModelMap model) {
        return "/user";
    }

}
