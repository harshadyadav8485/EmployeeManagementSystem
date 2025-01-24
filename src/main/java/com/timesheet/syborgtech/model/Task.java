package com.timesheet.syborgtech.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id", nullable = false)
    private Sprint sprint;

    @NotBlank(message = "Task name is required")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "assignee_id", nullable = false)
    private Long assigneeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

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

    @OneToMany(mappedBy = "task")
    private List<Subtask> subtaskList;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;
    public enum TaskStatus {
        TO_DO,
        IN_PROGRESS,
        DEV_COMPLETED,
        UAT_COMPLETED,
        DEPLOYED,
        CLOSED
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }
}
