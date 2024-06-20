package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Entity
@Table(name = "res_user")
public class User implements UserDetails {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Getter
    @Column(unique = true, nullable = false)
    private String username;
    @Getter
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


//    @Getter
//    @ManyToOne
//    @JoinColumn(name = "role_id", unique = false, nullable = false)
//    Role role;

//    @Getter
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "person_id", unique = true, nullable = false)
//    private Person person;

    @OneToOne(mappedBy = "user")
    private Person person;


//    @Getter
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
//            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleId")}
//    )
//    private List<Role> roles;

    public User(Long userId, String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Person person) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.person = person;
    }

    public User(String username, String password) {
        this(null, username, password, true, true, true, true, null);
    }

    public User() {
        this(null, null);
    }

    public User(String userName, String password, UserRole userRole) {
        this.username = userName;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        String[] userRoles = getRole().stream()
//                .map((role) -> role.getRoleName())
//                .toArray(String[]::new);

        return AuthorityUtils.createAuthorityList(new String[]{"ADMIN","STUDENT","SYS_ADMIN"});
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}