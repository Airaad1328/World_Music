package com.gmail.clarkin200.WorldMusicDemo.model.user;

import com.gmail.clarkin200.WorldMusicDemo.model.Image;
import com.gmail.clarkin200.WorldMusicDemo.model.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    private Long hubSessionId;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image icon;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id",
                            referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id",
                            referencedColumnName = "id")
            }
    )
    private List<Role> roles = new ArrayList<>();

    public User(Long id, String username, String email, String password, Long hubSessionId, Image icon) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.hubSessionId = hubSessionId;
        this.icon = icon;
    }

    public User () {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getHubSessionId() {
        return hubSessionId;
    }

    public void setHubSessionId(Long hubSessionId) {
        this.hubSessionId = hubSessionId;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(hubSessionId, user.hubSessionId) && Objects.equals(icon, user.icon) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, hubSessionId, icon, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", hubSessionId=" + hubSessionId +
                '}';
    }
}
