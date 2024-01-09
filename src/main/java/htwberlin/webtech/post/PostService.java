package htwberlin.webtech.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;

    public List<PostDto> getAll(){
        return postRepo.findAll()
                .stream()
                .map(PostDto::toPostDto)
                .collect(Collectors.toList());
    }

    public PostDto findPostById (Long id) {
        return postRepo.findById(id)
                .map(PostDto::toPostDto)
                .orElse(null);
    }

    public PostDto findPostByTitle(String title) {
        return PostDto.toPostDto(postRepo.findByTitle(title));
    }

    public PostDto createPost(PostRequest request) {
        Post post = new Post(
                request.getTitle(),
                request.getAuthor(),
                request.getContent()
        );
        return PostDto.toPostDto(postRepo.save(post));
    }

    public PostDto updatePost(Long id, PostRequest req) {
        Optional<Post> post = postRepo.findById(id);
        if(post.isEmpty()) {
            return null;
        }
        Post updatedPost = post.get();
        updatedPost.setTitle(req.getTitle());
        updatedPost.setContent(req.getContent());
        updatedPost.setAuthor(req.getAuthor());
        updatedPost.setUpdatedOn(req.getUpdatedOn());
        updatedPost =  postRepo.save(updatedPost);
        return PostDto.toPostDto(updatedPost);
    }

    public boolean deletePost(Long id) {
        if(!postRepo.existsById(id)) return false;
        postRepo.deleteById(id);
        return true;
    }

}
