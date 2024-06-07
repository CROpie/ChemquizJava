package chemquizAPI.repositories;

import chemquizAPI.models.Score;
import chemquizAPI.models.StructureQ;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StructureRepository extends JpaRepository<StructureQ, Integer> {

    // @Query("SELECT s FROM StructureQ s WHERE s.isDifficult = :isDifficult")
    // List<StructureQ> findStructureQs(boolean isDifficult);

    // JPQL doesn't support RAND(), so need to use Native Query
    @Query(value = "SELECT * FROM structure_qs WHERE is_difficult = ?1 ORDER BY RAND() LIMIT ?2", nativeQuery = true)
    List<StructureQ> findStructureQ(boolean isDifficult, int noStructureQs);
}