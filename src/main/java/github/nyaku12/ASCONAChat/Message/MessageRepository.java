package github.nyaku12.ASCONAChat.Message;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
//    @Query("SELECT id, receiver, sender, contain, status, senttime FROM messages WHERE receiver = :receiverid")
    List<Message> getByreceiverIdOrderBySenttime(@Param("receiver") int receiver_id);
}
