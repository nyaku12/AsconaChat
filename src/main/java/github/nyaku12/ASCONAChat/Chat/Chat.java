package github.nyaku12.ASCONAChat.Chat;


import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "public")
    private Boolean open;
    private Long password;

    public Chat() {
    }

    public Chat(String name, Boolean open, Long password) {
        this.name = name;
        this.open = open;
        this.password = password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getOpen() {
        return open;
    }

    public Long getPassword() {
        return password;
    }
}
