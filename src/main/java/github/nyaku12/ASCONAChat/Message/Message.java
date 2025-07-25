package github.nyaku12.ASCONAChat.Message;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contain;
    @Column(name = "receiver")
    private Long receiverId;
    @Column(name = "sender")
    private Long sender_id;
    private Boolean status;

    public Message(String contain, Long receiverId, Long sender_id) {
        this.contain = contain;
        this.receiverId = receiverId;
        this.sender_id = sender_id;
        this.status = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getContain() {
        return contain;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public Long getSender_id() {
        return sender_id;
    }

    public Boolean getStatus() {
        return status;
    }
}
