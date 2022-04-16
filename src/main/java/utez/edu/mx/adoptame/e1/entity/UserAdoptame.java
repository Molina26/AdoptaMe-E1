package utez.edu.mx.adoptame.e1.entity;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserAdoptame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "fist_lastname", nullable = false, length = 150)
    private String firstLastname;

    @Column(name = "second_lastname", length = 150)
    private String secondLastname;

    @Column(name = "username", nullable = false, length = 250, unique = true)
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @ManyToMany
    @JoinTable(
            name = "favorites_pets_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")

    )
    private Set<Pet> favoritesPets = new HashSet<Pet>();

    @ManyToMany
    @JoinTable(
            name = "authorities",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority")
    )
    private Set<Role> roles = new HashSet<Role>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastname() {
        return firstLastname;
    }

    public void setFirstLastname(String firstLastname) {
        this.firstLastname = firstLastname;
    }

    public String getSecondLastname() {
        return secondLastname;
    }

    public void setSecondLastname(String secondLastname) {
        this.secondLastname = secondLastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Pet> getFavoritesPets() {
        return favoritesPets;
    }

    public void setFavoritesPets(Set<Pet> favoritesPets) {
        this.favoritesPets = favoritesPets;
    }

 

    
}

