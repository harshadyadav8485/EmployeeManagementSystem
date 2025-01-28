package com.timesheet.syborgtech.dto.response;

import com.timesheet.syborgtech.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponseDto {

    private Long userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private Date createAt;
    private Date updatedAt;
    private User.Status status;
    private Date dateOfJoining;
    private Date dateOfResign;
    private Set<Role> roles;
    private List<Task> taskList;
    private List<Subtask> subtaskList;
    private List<Comment> comments;
    private List<Projects> projects;
}
