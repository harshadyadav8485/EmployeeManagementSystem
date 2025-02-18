package com.timesheet.syborgtech.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  //  @NotBlank(message = "Username is required")
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

   // @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

   // @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

 //   @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

  //  @NotBlank(message = "Email is required")
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
//    @NotBlank(message = "phoneNumber is required")
//    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

//    @NotNull(message = "Date of birth is required")
//    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "created_at", columnDefinition = "timestamp", updatable = false)
    private Date createAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "timestamp")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "date_of_joining")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfJoining;

    @Column(name = "date_of_resign")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfResign;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Task> taskList;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Subtask> subtaskList;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @Column(name = "isLoggedIn")
    private boolean isLoggedIn;

    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Projects> projects;

    @Lob
    private byte[] qrCode;
    public enum Status{
        CREATED,
        APPROVED,
        REJECTED
    }

}






