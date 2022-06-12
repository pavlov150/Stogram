package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;
import run.itlife.enums.Sex;
import run.itlife.service.PostService;
import run.itlife.service.SubscriptionsService;
import run.itlife.service.UserService;
import javax.imageio.ImageIO;
import javax.persistence.EntityExistsException;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import static run.itlife.utils.EditImage.resizeImage;
import static run.itlife.utils.OtherUtils.generateFileName;

//UserController, отвечающий за логин юзеров и т.д.
//Создаем в папке view страницу register.html. Далее необходимо сделать, чтобы мы пересылали данные в контроллер.
//У UserController будет страница по которой будет идти регистрация. Для этого нужно сделать форму и она уже будет
//идти на контроллер для регистрации
@Controller
public class UserController {

    private final UserService userService;
    private final SubscriptionsService subscriptionsService;
    private final PostService postService;
    private final ServletContext context;

    @Autowired
    public UserController(UserService userService, ServletContext context, SubscriptionsService subscriptionsService, PostService postService) {
        this.userService = userService;
        this.context = context;
        this.subscriptionsService = subscriptionsService;
        this.postService = postService;
    }

    @GetMapping("/login")
    public String login(ModelMap modelMap){
        return "login";
    }

    @GetMapping("/register")
    public String register(ModelMap modelMap){
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        try {
            userService.create(user);
            return "redirect:/login";
        } catch (EntityExistsException e) {
            return "exist";
        }
    }

    @GetMapping("/profile_edit/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profile_edit(ModelMap modelMap, @PathVariable String user){
        setCommonParams(modelMap, user);
        modelMap.put("sex_male", Sex.MALE);
        modelMap.put("sex_female", Sex.FEMALE);
        return "profile-edit";
    }

    @PostMapping("/profile_edit")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profile_edit(UserDto userDto, @RequestParam("file") String file, ModelMap modelMap) {
        setCommonParams(modelMap);

       if (!file.isEmpty()) {
            try {
                // изменение и генерация ноового имени файла
                String filename = generateFileName() + ".jpg";
                // получение имени фото и сохранение имени фото и данных поста в БД
                userService.checkAuthority(userDto.getUserId());
                userDto.setPhoto(filename);
                userService.update(userDto);

                // сохранение самого файла в папку юзера
                final String username = SecurityContextHolder.getContext().getAuthentication().getName();
                File dir = new File(context.getRealPath("/resources/img/users/" + username + "/profile/"));
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File uploadedFile = new File(dir + "/" + filename);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));

                String base64Image = file.split(",")[1];
                byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
                stream.write(imageBytes);

                //изменение размера до 500х500
                BufferedImage originalImage = ImageIO.read(uploadedFile);
                BufferedImage resizeImage = null;
                File newFileJPG = null;
                resizeImage = resizeImage(originalImage, 500, 500);
                newFileJPG = new File(dir.getAbsolutePath() + File.separator + filename);

                //записываем файл
                ImageIO.write(resizeImage, "png", newFileJPG);
                stream.flush();
                stream.close();
                return "redirect:/posts/";
            } catch (Exception e) {
                return "error";
            }
        } else {
           userService.checkAuthority(userDto.getUserId());
           userService.update(userDto);
           return "redirect:/";
        }
    }

    @GetMapping("/subscriptions")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribes(ModelMap modelMap) {
        setCommonParams(modelMap);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("sub", subscriptionsService.findSubscribes(username));
        return "subscriptions";
    }

    @GetMapping("/subscribers")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribers(ModelMap modelMap) {
        setCommonParams(modelMap);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("sub", subscriptionsService.findSubscribers(username));
        return "subscribers";
    }

    @GetMapping("/subscriptions_subuser/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribes_subuser(ModelMap modelMap, @PathVariable String user) {
        setCommonParams(modelMap);
        modelMap.put("sub", subscriptionsService.findSubscribes(user));
        return "subscriptions-subuser";
    }

    @GetMapping("/subscribers_subuser/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribers_subuser(ModelMap modelMap, @PathVariable String user) {
        setCommonParams(modelMap);
        modelMap.put("sub", subscriptionsService.findSubscribers(user));
        return "subscribers-subuser";
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String search(ModelMap modelMap, @RequestParam(required = false) String search) {
        setCommonParams(modelMap);
        modelMap.put("countSearchUsers", userService.countSearchUsers(search));
        modelMap.put("countSearchTags", postService.countSearchTags(search));
        modelMap.put("tagUserName", search);
        if (search != null) {
            modelMap.put("findUsers", userService.searchUsers(search));
            modelMap.put("findTags", postService.searchTags(search));
            return "search-results";
        } else {
            modelMap.put("findUsers", userService.findAll());
            return "search-results";
        }
    }

    private void setCommonParams(ModelMap modelMap, String user) {
        modelMap.put("user", user);
        modelMap.put("userinfo", userService.findByUsername(user));
    }

    private void setCommonParams(ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userslist", userService.findAll());
        modelMap.put("userOnlyList", userService.getUsersOnly());
    }

}