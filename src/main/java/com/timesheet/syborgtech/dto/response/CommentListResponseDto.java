package com.timesheet.syborgtech.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timesheet.syborgtech.model.Task;
import com.timesheet.syborgtech.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponseDto {

    private Long id;
    private Task task;
    private String commentText;
    private Date createAt;
    private Date updatedAt;
}
