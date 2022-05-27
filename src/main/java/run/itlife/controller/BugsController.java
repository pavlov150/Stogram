package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import run.itlife.dto.BugsDto;
import run.itlife.service.BugsService;
import run.itlife.service.UserService;

@Controller
public class BugsController {

    private final BugsService bugsService;
    private final UserService userService;

    @Autowired
    public BugsController(BugsService bugsService, UserService userService) {
        this.bugsService = bugsService;
        this.userService = userService;
    }

    @GetMapping("/bug/new")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String bugNew(ModelMap modelMap) {
        return "bugs-add";
    }

    @PostMapping("/bug/new")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String postNew(BugsDto bugsDto) {
        bugsService.create(bugsDto);
        return "message-send";
    }

    @GetMapping("/bugs_view")
    @PreAuthorize("hasRole('ADMIN')")
    public String index(ModelMap modelMap) {
        modelMap.put("bugs", bugsService.listAllBugs());
        modelMap.put("userslist", userService.findAll());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        return "bugs";
    }

}
