package chemquizAPI.repositories;

import chemquizAPI.models.ReactionQ;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReactionRepository extends JpaRepository<ReactionQ, Integer> {


    // JPQL doesn't support RAND(), so need to use Native Query
    @Query(value = "SELECT * FROM reaction_qs WHERE is_difficult = ?1 ORDER BY RAND() LIMIT ?2", nativeQuery = true)
    List<ReactionQ> findReactionQ(boolean isDifficult, int noReactionQs);
}