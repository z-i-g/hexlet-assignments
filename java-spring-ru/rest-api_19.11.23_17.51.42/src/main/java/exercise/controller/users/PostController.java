package exercise.controller.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RequestMapping("/api")
@RestController
public class PostController {
    private List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    public List<Post> getPost(@PathVariable int id) {
        return posts
                .stream()
                .filter(post -> post.getUserId() == id)
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return post;
    }
}
// END
