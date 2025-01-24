package com.timesheet.syborgtech.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity(name = "sprints")
@Table
@Data
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Sprint name is required")
    @Size(max = 100, message = "Sprint name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

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


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", columnDefinition = "timestamp")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", columnDefinition = "timestamp")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "sprintStatus")
    private SprintStatus sprintStatus;

    @ManyToOne
    @JoinColumn(name = "epic_id", nullable = false)
    private Epic epic;

    @OneToMany(mappedBy = "sprint",fetch = FetchType.LAZY)
    private List<Task> taskList;
     public enum SprintStatus{
         ACTIVE,COMPLETED
     }
}
