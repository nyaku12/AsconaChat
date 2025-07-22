package github.nyaku12.ASCONAChat.User;


import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    @Column(name = "password")
    private Long passHash;
    private String name;
    private Boolean status; //true - online, false - offline
    @Column(name = "enskey")
    private String enscryptionKey;

    public User() {
    }

    public User(String login, Long passHash, String name, String enscryptionKey) {
        this.login = login;
        this.passHash = passHash;
        this.name = name;
        this.enscryptionKey = enscryptionKey;
        this.status = true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassHash(Long passHash) {
        this.passHash = passHash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setEnscryptionKey(String enscryptionKey) {
        this.enscryptionKey = enscryptionKey;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Long getPassHash() {
        return passHash;
    }

    public String getName() {
        return name;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getEnscryptionKey() {
        return enscryptionKey;
    }
}
