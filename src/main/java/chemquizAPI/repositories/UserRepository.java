package chemquizAPI.repositories;

import chemquizAPI.models.LeaderboardDTO;
import chemquizAPI.models.Score;
import chemquizAPI.models.User;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called groceryRepository

// CrudRepository is basic, JpaRepository extends it with additional features
// seems to support @Query better...

// Integer represents the primary key of the User entity
public interface UserRepository extends JpaRepository<User, Integer> {

    // @Query(value="SELECT * FROM User WHERE username = :username", nativeQuery = true)
    @Query("SELECT u FROM User u WHERE u.username = :username")
    List<User> findByUsername(String username);

    // @Query("SELECT s FROM Score s WHERE s.user.userId = :userId")
    @Query("SELECT u.scores FROM User u WHERE u.userId = :userId")
    List<Score> findUserScores(int userId);


    // ** need to use chemquizAPI.models.LeaderboardDTO, instead of just LeaderboardDTO **
    @Query("SELECT new chemquizAPI.models.LeaderboardDTO(u.userId, u.username, s.attemptDate, MAX(s.scoreValue)) " +
            "FROM User u " +
            "JOIN u.scores s " +
            "GROUP BY u.userId, u.username, s.attemptDate " +
            "ORDER BY Max(s.scoreValue) DESC")
    List<LeaderboardDTO> findLeaderboardScores(Pageable pageable);



}

/*
 *  JPQL Query
 * @Query("SELECT u FROM User u WHERE u.username = :username")
 * u (User entities)
 * User (entity)
 * :username (input String username)
 * 
 * @Query("SELECT u.password FROM User u WHERE u.username = :username")
 * 
 * NATIVE SQL
 * Can refer to either User entity or users table, which can be ambiguous
 * 
 * @Query(value="SELECT * FROM User WHERE username = :username", nativeQuery = true)
 * User (entity)
 * 
 * @Query(value="SELECT * FROM users WHERE username = :username", nativeQuery = true)
 * users (table)
 * 
 * Return values of JPQL
 * 
 *  * *** not sure if this is the correct way to do parameters for native query, maybe below is better:
 * 
 * @Query(value="SELECT * FROM users WHERE username = ?1", nativeQuery = true)
 * List<User> findByUsername(String username)
 * 
 *  * *** or:
 * 
 *  * @Query(value="SELECT * FROM users WHERE username = :username", nativeQuery = true)
 * List<User> findByUsername(@Param("username") String username)
 * 
 * 1. Entity
 * @Query("SELECT u FROM User u WHERE u.username = :username)
 * List<User> findByUsername(String username);
 * 
 * 2. DTO
 * @Query("SELECT new UserDTO(u.id, u.name) FROM User u WHERE u.username  = :username)
 * List<UserDTO> findUserDTOByUsername(String username);
 * 
 * 3. Scalar Values
 * @Query("SELECT COUNT(u) FROM User u")
 * int countUsers();
 * 
 * 4. Aggregated Values
 * @Query("SELECT MAX(u.age) FROM User u")
 * int findMaxAge();
 * 
 * 5. Custom Object Arrays
 * @Query("SELECT u.id, u.name FROM User u WHERE u.username = :username")
 * List<Object[]> findIdAndNameByUsername(String username);
 * 
 * Return values of native queries
 * 
 * 1. Entity
 * @Query(value="SELECT * FROM users WHERE username = :username", nativeQuery = true)
 * List<User> findByUsername(String username)
 * 
 * 2. DTO
 * @Query(value="Select userId, password FROM users WHERE username = :username", nativeQuery = true)
 * List<UserDTO> findUserDTOByUsername(String username)
 * 
 * 3. Scalar Values
 * @Query(value="SELECT COUNT(*) FROM users", nativeQuery = true)
 * int countUsers();
 * 
 * 4. Aggregated Values
 * @Query(value="SELECT MAX(age) FROM users", nativeQuery = true)
 * int findMaxAge();
 * 
 * 5. Custom Object Arrays
 * @Query(value="SELECT id, name FROM users WHERE username = :username, nativeQuery = true)
 * List<Object[]> findIdAndNameByUsername(String username);
 * 
 * Example Hibernate query for @Query("SELECT u.scores FROM User u WHERE u.userId = :userId")
 * 2024-06-05T14:36:22.160+10:00 DEBUG 42652 --- [chemquizJavaAPI] [nio-8080-exec-5] org.hibernate.SQL
 * select s1_0.game_id,s1_0.score_value,u1_0.user_id,u1_0.date_joined,u1_0.is_admin,u1_0.password,u1_0.username 
 * from users u1_0 
 * join scores s1_0 
 * on u1_0.user_id=s1_0.user_id 
 * where u1_0.user_id=?
 * 
[]
 */
