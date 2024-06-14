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
    @GeneratedValue
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
    protected Person(){}

    public Person(LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password) {
        this.birthDate = birthDate;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }
}
