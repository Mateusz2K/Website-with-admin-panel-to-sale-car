package pl.konkretnefury.konkretnefury.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.konkretnefury.konkretnefury.modele.BlogPost;

import java.util.List;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Long> {

    // ZMIANA: Poprawiona składnia dla MySQL
    @Query("SELECT bp FROM BlogPost bp ORDER BY CASE WHEN bp.dataPublikacji IS NULL THEN 1 ELSE 0 END, bp.dataPublikacji DESC")
    List<BlogPost> findRecentPosts(Pageable pageable);

    List<BlogPost> findTop5ByOrderByDataPublikacjiDesc();

}
