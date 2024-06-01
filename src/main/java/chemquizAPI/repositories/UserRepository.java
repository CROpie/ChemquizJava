package chemquizAPI.repositories;

import chemquizAPI.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called groceryRepository

// CrudRepository is basic, JpaRepository extends it with additional features

// Integer represents the primary key of the User entity
public interface UserRepository extends CrudRepository<User, Integer> {


    // custom query to get username based on username
    // @Query("SELECT u FROM User u WHERE u.username = :username")
    @Query(value="SELECT * FROM User WHERE username = :username", nativeQuery = true)
    List<User> findByUsername(String username);
    
}
