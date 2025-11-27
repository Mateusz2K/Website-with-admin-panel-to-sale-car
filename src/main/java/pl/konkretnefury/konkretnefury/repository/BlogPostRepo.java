package pl.konkretnefury.konkretnefury.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.konkretnefury.konkretnefury.modele.BlogPost;

import java.util.List;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Long> {

    @Query("SELECT bp FROM BlogPost bp ORDER BY bp.dataPublikacji DESC NULLS LAST")
    List<BlogPost> findRecentPosts(Pageable pageable);

}
