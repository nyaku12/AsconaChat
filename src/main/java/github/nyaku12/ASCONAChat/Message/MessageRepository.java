package github.nyaku12.ASCONAChat.Message;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getByreceiverIdAndStatusFalseOrderBySenttime(@Param("receiver") int receiver_id);
    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.status=true WHERE m.id = :id")
    void readMessage(@Param("id") long id);
}
