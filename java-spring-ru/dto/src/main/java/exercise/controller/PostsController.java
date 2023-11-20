package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public List<PostDTO> getAll() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDtos = new ArrayList<>();
        posts.forEach(post -> {
            List<Comment> comments = commentRepository.findByPostId(post.getId());
            postDtos.add(convertToDto(post, comments));
        });
        return postDtos;
    }

    @GetMapping("/{id}")
    public PostDTO get(@PathVariable long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        return convertToDto(post, comments);
    }

    private PostDTO convertToDto(Post post, List<Comment> comments) {
        PostDTO postDTO = new PostDTO();

        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());

        postDTO.setComments(comments.stream().map(this::convertToDto).toList());
        return postDTO;
    }

    private CommentDTO convertToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }
}
// END
