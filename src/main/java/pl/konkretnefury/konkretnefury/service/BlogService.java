package pl.konkretnefury.konkretnefury.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.konkretnefury.konkretnefury.modele.BlogPost;
import pl.konkretnefury.konkretnefury.repository.BlogPostRepo;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogPostRepo blogPostRepository;

    public BlogService(BlogPostRepo blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    public Optional<BlogPost> getPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    public void savePost(BlogPost blogPost) {
        blogPostRepository.save(blogPost);
    }

    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }

    public List<BlogPost> findLast5Posts() {
        Pageable pageable = PageRequest.of(0, 5);
        return blogPostRepository.findRecentPosts(pageable);
    }
}
