package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;
import run.itlife.repository.UserRepository;
import run.itlife.service.UserService;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

//UserController, отвечающий за логин юзеров и т.д.
//Создаем в папке view страницу register.html. Далее необходимо сделать, чтобы мы пересылали данные в контроллер.
//У UserController будет страница по которой будет идти регистрация. Для этого нужно сделать форму и она уже будет
//идти на контроллер для регистрации
@Controller
public class UserController {

    private final UserService userService;
    private final ServletContext context;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, ServletContext context, UserRepository userRepository) {
        this.userService = userService;
        this.context = context;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(ModelMap modelMap){
        return "login";
    }

    @PostMapping("/register")
    public String register(User user){
        userService.create(user);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(ModelMap modelMap){
        return "register";
    }

    @GetMapping("/profile_edit/{user}")
    @PreAuthorize("hasRole('USER')")
    public String profile_edit(ModelMap modelMap, @PathVariable String user){
      //  String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("userinfo", userService.findByUsername(user));
       // modelMap.put("test", userRepository.findById(user).orElseThrow().getUsername());
        //modelMap.put("user", username);
        return "profile-edit";
    }

    @PostMapping("/profile_edit")
    @PreAuthorize("hasRole('USER')")
    public String profile_edit(UserDto userDto, @RequestParam("file") MultipartFile file) {

       if (!file.isEmpty()) {
            try {
                // изменение и генерация ноового имени файла
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String filename = encoder.encode(file.getOriginalFilename()).substring(8, 15) + ".jpg";

                // получаем имя юзера для формирования пути сохранения фото
                final String username = SecurityContextHolder.getContext().getAuthentication().getName();

                // получение имени фото и сохранение имени фото и данных поста в БД
                userService.checkAuthority(userDto.getUserId());
                userDto.setPhoto(filename);
                userService.update(userDto);

                // сохранение самого файла в папку юзера
                byte[] bytes = file.getBytes();
                final String rootPath = context.getRealPath("/resources/img/" + username + "/profile/");
                File dir = new File(rootPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + filename);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
                return "redirect:/";
            } catch (Exception e) {
                return "error";
            }
        } else {
            return "error";
        }
    }

}