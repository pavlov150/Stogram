package run.itlife.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import run.itlife.dto.PostDto;
import run.itlife.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostApiController {

    private final PostService postService;

    @Autowired
    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> findAll(@RequestParam(required = false) String search) {
        return  new ResponseEntity<>(search != null ?
                postService.searchDtos(search) :
                postService.listAllPostsAsDto(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable long id) {
        return new ResponseEntity<>(postService.getAsDto(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @RequestBody PostDto postDto){
        postDto.setPostId(id);
        postService.update(postDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        postService.delete(id);
    }
    
}
