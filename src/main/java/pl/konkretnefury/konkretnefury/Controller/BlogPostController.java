package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.konkretnefury.konkretnefury.dto.blog.BlogReciveDTO;
import pl.konkretnefury.konkretnefury.dto.blog.BlogReciveDTO;
import pl.konkretnefury.konkretnefury.modele.BlogPost;
import pl.konkretnefury.konkretnefury.service.BlogService;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/blog")
public class BlogPostController {

    private final BlogService blogPostService;

    public BlogPostController(BlogService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", blogPostService.getAllPosts());
        return "admin/blog/blog-list"; // ZMIANA: Zwracamy nowy widok
    }

    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("blogPost", new BlogReciveDTO());
        return "admin/blog/blog-post-form";
    }

    @PostMapping
    public String savePost(@ModelAttribute("blogPost") BlogReciveDTO blogReciveDTO) {
        BlogPost blogPost = new BlogPost();
        blogPost.setId(blogReciveDTO.getId());
        blogPost.setTytul(blogReciveDTO.getTytul());
        blogPost.setTresc(blogReciveDTO.getTresc());
        blogPost.setAutor(blogReciveDTO.getAutor());
        blogPost.setDataPublikacji(LocalDate.now()); // automatyczna data
        blogPostService.savePost(blogPost);
        return "redirect:/admin/blog";
    }

    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Optional<BlogPost> blogPostOptional = blogPostService.getPostById(id);
        if (blogPostOptional.isEmpty()) {
            return "redirect:/admin/blog";
        }

        BlogPost blogPost = blogPostOptional.get();
        BlogReciveDTO blogReciveDTO = new BlogReciveDTO();
        blogReciveDTO.setId(blogPost.getId());
        blogReciveDTO.setTytul(blogPost.getTytul());
        blogReciveDTO.setTresc(blogPost.getTresc());
        blogReciveDTO.setAutor(blogPost.getAutor());

        model.addAttribute("blogPost", blogReciveDTO);
        return "admin/blog/blog-post-form";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        if (blogPostService.getPostById(id).isEmpty()) {
            return "redirect:/admin/blog";
        }
        blogPostService.deletePost(id);
        return "redirect:/admin/blog";
    }
}
