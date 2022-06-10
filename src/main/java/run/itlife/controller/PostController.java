package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import run.itlife.config.WebConfig;
import run.itlife.dto.PostDto;
import run.itlife.repository.PostRepository;
import run.itlife.repository.UserRepository;
import run.itlife.service.*;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static run.itlife.utils.EditImage.cropImage;
import static run.itlife.utils.EditImage.resizeImage;
import static run.itlife.utils.OtherUtils.generateFileName;

//Контроллер для постов (создание, редактирование, удаление)
@Controller
public class PostController {

    private final PostService postService;
    private final LikesService likesService;
    private final UserService userService;
    private final CommentService commentService;
    private final ServletContext context;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SubscriptionsService subscriptionsService;

    @Autowired
    ServletContext servletContext;

    @Autowired
    public PostController(PostService postsService, LikesService likesService, UserService userService, CommentService commentService, ServletContext context, UserRepository userRepository, PostRepository postRepository, SubscriptionsService subscriptionsService) {
        this.postService = postsService;
        this.likesService = likesService;
        this.userService = userService;
        this.commentService = commentService;
        this.context = context;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.subscriptionsService = subscriptionsService;
    }

    @GetMapping("/")
    public String index(ModelMap modelMap, @RequestParam(required = false) String search) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts_sub", postService.findSubscribesPosts(username));
        modelMap.put("userslist", userService.findAll());
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("countPosts", postService.countSubscribesPosts(username));
     //   modelMap.put("countLikes", likesService.countLikesByPostId(id));
     //   modelMap.put("isLike", likesService.isLikePostForCurrentUser(id, username));

        return "posts-detail-sub";
    }

    //@RequestMapping(value = "/posts_detail", method = RequestMethod.GET)
    @GetMapping("/posts_detail") // такая же запись как и выше, но в другом виде, более современная
    public String posts_detail(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        modelMap.put("posts", postService.sortedPostsByDate(username));
        modelMap.put("userslist", userService.findAll());
        //  modelMap.put("comments", commentService.sortCommentsByDate(id));
        //  modelMap.put("lastComments", commentService.getLastComments());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("countLikes", likesService.countLikesByUsername(username));
      //  modelMap.put("isLike", likesService.isLikePostForCurrentUser(postId, username));

        //   modelMap.put("countComments", postService.countComments(id));

        return "posts-detail";
    }


    @GetMapping("/posts")
    public String posts(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts", postService.sortedPostsByDate(username));
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("countPosts", postService.countPosts(username));
        modelMap.put("countSubscribe", subscriptionsService.countSubscribe(username));
        modelMap.put("countSubscribers", subscriptionsService.countSubscribers(username));
     //   modelMap.put("test", userRepository.findById(4L).orElseThrow().getUsername());
        return "posts";

    }




    @GetMapping("/posts_detail_subuser/{user}")
    @PreAuthorize("hasRole('USER')")
    public String posts_detail_subuser(ModelMap modelMap, @PathVariable String user) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts", postService.sortedPostsByDate(user));
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user_sub", user);
        modelMap.put("userinfo_sub", userService.findByUsername(user));
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());

        return "posts-detail-subuser";
    }






    @GetMapping("/post/newVideo")
    @PreAuthorize("hasRole('USER')")
    public String postNewVideo(ModelMap modelMap) {
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        return "post-new-video";
    }

    @GetMapping("/post/newImage")
    @PreAuthorize("hasRole('USER')")
    public String postNewImage(ModelMap modelMap) {
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        return "post-new-img";
    }

   /* @GetMapping("/fragments")
    public String view_header(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "fragments";
    }*/

    @PostMapping("/post/newVideo")
    @PreAuthorize("hasRole('USER')")
    public String postNewVideo(PostDto postDto, @RequestParam("file") MultipartFile file, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));

        if (!file.isEmpty()) {
            try {
                if(file.getContentType().equals("video/mp4") || file.getContentType().equals("video/quicktime") ) {
                 //   final String username = SecurityContextHolder.getContext().getAuthentication().getName();

                    String extension;
                    switch (file.getContentType()) {
                        case "video/mp4":
                            extension = "mp4";
                            break;
                        case "video/quicktime":
                            extension = "mov";
                            break;
                        default:
                            extension = "mp4";
                            break;
                    }

                    String filename = generateFileName() + "." + extension;
                    postDto.setExtFile(extension);
                    postDto.setPhoto(filename);
                    long postId = postService.createPost(postDto);
                    File dir = new File(context.getRealPath("/resources/video/users/" + username));
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    // TODO написать логику обрезки видео
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File(dir + "/" + filename)));
                    stream.write(bytes);
                    stream.close();
                    return "redirect:/post/" + postId;
                }
                return "error";

            } catch (Exception e) {
                return "error";
            }
        } else {
            return "error";
        }

    }


    @PostMapping("/post/newImage")
    @PreAuthorize("hasRole('USER')")
    public String postNewImage(PostDto postDto, @RequestParam("file") String file, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));

        String name = null;
        if (!file.isEmpty()) {
            try {
                String base64Image = file.split(",")[1];
                byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

                String filename = generateFileName() + ".png";
                postDto.setExtFile("png");
                postDto.setPhoto(filename);
                long postId = postService.createPost(postDto);


                name = filename;
                File dir = new File(context.getRealPath("/resources/img/users/" + username));
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File uploadedFile = new File(dir + "/" + filename);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(imageBytes);
                stream.flush();
                stream.close();
                return "redirect:/post/" + postId;
            } catch (Exception e) {
                return "error";
            }
        } else {
            return "error";
        }

    }

    @GetMapping("/post/{postId}/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(ModelMap modelMap, @PathVariable long postId) {
        postService.checkAuthority(postId);
        modelMap.put("post", postService.getAsDto(postId));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        setCommonParams(modelMap);
        return "post-edit";
    }

    @PostMapping("/post/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(PostDto postDto, ModelMap modelMap) {
        // получаем имя юзера для формирования пути сохранения фото
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        postService.checkAuthority(postDto.getPostId());
        postService.update(postDto);
        return "redirect:/post/" + postDto.getPostId();
    }

    @GetMapping("/post-view-sub/{id}")
    public String post_view_sub(@PathVariable long id, ModelMap modelMap){
        modelMap.put("post", postService.findById(id));
        modelMap.put("comments", commentService.sortCommentsByDate(id));
        modelMap.put("countComments", postService.countComments(id));
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userslist", userService.findAll());
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("countLikes", likesService.countLikesByPostId(id));
        modelMap.put("isLike", likesService.isLikePostForCurrentUser(id, username));

        return "post-view-sub";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable long id, ModelMap modelMap){
        modelMap.put("post", postService.findById(id));
        modelMap.put("comments", commentService.sortCommentsByDate(id));
        modelMap.put("countComments", postService.countComments(id));
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userslist", userService.findAll());
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("countLikes", likesService.countLikesByPostId(id));
        modelMap.put("isLike", likesService.isLikePostForCurrentUser(id, username));

        return "post-view";
    }

    @PostMapping("/post/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        postService.delete(id);
    }

    @GetMapping("/post/{id}/delete_one_post")
    @PreAuthorize("hasRole('USER')")
    public String delete_one_post(@PathVariable long id){
        postService.delete(id);
        return "redirect:/posts_detail";
    }

    private void setCommonParams(ModelMap modelMap) {
        modelMap.put("users", userService.findAll());
        modelMap.put("contextPath", context.getContextPath());
    }



}