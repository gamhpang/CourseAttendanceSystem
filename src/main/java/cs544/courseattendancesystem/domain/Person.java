package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@SecondaryTable(name = "PersonAccount",
    pkJoinColumns = @PrimaryKeyJoinColumn(name="id"))
public abstract class Person implements IRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate birthDate;
    private String emailAddress;
    private String firstName;
    private String lastName;
    @Column(table = "PersonAccount")
    private String userName;
    @Column(table="PersonAccount")
    private String password;
    @Embedded
    private AuditData auditData;
    @Enumerated(EnumType.STRING)
    private GenderType genderType;
    public abstract String getRole();
    public Person(){}

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    public Person(LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password, UserRole userRole) {
        this.birthDate = birthDate;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setUserRole(userRole);
        this.user = user;
    }
}
