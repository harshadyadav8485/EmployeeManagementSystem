package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.CommentRequestDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.TaskNotFoundException;
import com.timesheet.syborgtech.exceptions.UserNotFoundException;
import com.timesheet.syborgtech.model.Comment;
import com.timesheet.syborgtech.model.Task;
import com.timesheet.syborgtech.model.User;
import com.timesheet.syborgtech.repository.CommentRepository;
import com.timesheet.syborgtech.repository.TaskRepository;
import com.timesheet.syborgtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Response createComment(CommentRequestDto commentRequestDto) {
        Optional<User> user = userRepository.findById(commentRequestDto.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found Exception");
        }

        Task task = taskRepository.findById(commentRequestDto.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException("Task Not Found Exception"));
        Comment comment=new Comment();
        comment.setCommentText(commentRequestDto.getCommentText());
        comment.setTask(taskRepository.findById(commentRequestDto.getTaskId()).get());
        comment.setUser(user.get());
        commentRepository.save(comment);
        return Response.builder().message("Comment created successfully").build();

    }
}
