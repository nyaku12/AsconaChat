package github.nyaku12.ASCONAChat.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByLogin(String login);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    void updateStatusById(@Param("id") int id, @Param("status") Boolean status);
    Boolean findStatusById(@Param("id") int id);
    @Query ("Update User u SET u.status = false")
    @Transactional
    @Modifying
    void resetOnline();
}
