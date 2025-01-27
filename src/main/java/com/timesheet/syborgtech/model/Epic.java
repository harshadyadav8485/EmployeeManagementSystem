package com.timesheet.syborgtech.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "epics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Epic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Epic name is required")
    @Size(max = 100, message = "Epic name must not exceed 100 characters")
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

    @OneToMany(mappedBy = "epic")
    private List<Task> task;

}
