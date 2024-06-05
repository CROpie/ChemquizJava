package chemquizAPI.repositories;

import chemquizAPI.models.Score;
import chemquizAPI.models.ScoreDTO;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    @Query("SELECT COUNT(*) FROM Score s WHERE s.user.userId = :userId")
    int findAttemptCount(int userId);

    // @Query("SELECT s FROM Score s WHERE s.user.userId = :userId ORDER BY s.attemptDate DESC")
    // List<Score> findUserTopScores(int userId, Pageable pageable);

    @Query("SELECT NEW chemquizAPI.models.ScoreDTO(s.scoreValue, s.attemptDate) FROM Score s WHERE s.user.userId = :userId ORDER BY s.attemptDate DESC")
    List<ScoreDTO> findUserTopScores(int userId, Pageable pageable);

}