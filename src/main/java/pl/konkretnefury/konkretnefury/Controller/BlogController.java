//package pl.konkretnefury.konkretnefury.Controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import pl.konkretnefury.konkretnefury.modele.BlogPost;
//import pl.konkretnefury.konkretnefury.service.BlogService;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/blog")
//public class BlogController {
//
//    private final BlogService blogService;
//
//    public BlogController(BlogService blogService) {
//        this.blogService = blogService;
//    }
//
//    @GetMapping
//    public String blogPage(Model model) {
//        model.addAttribute("posts", blogService.findTop5Posts());
//        model.addAttribute("activePage", "blog");
//        return "blog/blog";
//    }
//
//    @GetMapping("/{id}")
//    public String postDetails(@PathVariable Long id, Model model) {
//        Optional<BlogPost> postOptional = blogService.getPostById(id);
//        if (postOptional.isEmpty()) {
//            return "redirect:/blog";
//        }
//        model.addAttribute("post", postOptional.get());
//        model.addAttribute("activePage", "blog");
//        return "blog/blog-post";
//    }
//}
