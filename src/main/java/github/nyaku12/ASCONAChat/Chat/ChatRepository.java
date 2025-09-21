package github.nyaku12.ASCONAChat.Chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query(value = "SELECT c.* FROM chats c JOIN chat_members cm WHERE cm.userid = :userid", nativeQuery = true)
    List<Chat> getChatsByUserId(@Param("userid") int userid);
}
