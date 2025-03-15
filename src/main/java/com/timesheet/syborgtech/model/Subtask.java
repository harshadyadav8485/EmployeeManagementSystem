package com.timesheet.syborgtech.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "subtasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @NotBlank(message = "Subtask name is required")
    @Size(max = 255, message = "Subtask name must not exceed 255 characters")
    @Column(nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubtaskStatus status;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "budgeted_hours")
    private Long budgetedHours;

    @Column(name = "actual_hours")
    private Long actualHours;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "start_date", columnDefinition = "timestamp", updatable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "end_date", columnDefinition = "timestamp", updatable = false)
    private Date endDate;

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
    public enum SubtaskStatus {
        TO_DO,
        IN_PROGRESS,
        COMPLETED,
        DONE
    }
}
