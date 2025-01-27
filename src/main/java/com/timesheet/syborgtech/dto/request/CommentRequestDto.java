package com.timesheet.syborgtech.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesheet.syborgtech.model.Task;
import com.timesheet.syborgtech.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private Long id;
    private Long taskId;
    private String commentText;
    private Long userId;

}
