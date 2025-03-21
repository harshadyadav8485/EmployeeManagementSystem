package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.CommentRequestDto;
import com.timesheet.syborgtech.dto.response.CommentListResponseDto;
import com.timesheet.syborgtech.dto.response.CommentResponseDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.CommentNotFoundException;
import com.timesheet.syborgtech.exceptions.TaskNotFoundException;
import com.timesheet.syborgtech.exceptions.UserNotFoundException;
import com.timesheet.syborgtech.model.Comment;
import com.timesheet.syborgtech.model.Task;
import com.timesheet.syborgtech.model.User;
import com.timesheet.syborgtech.repository.CommentRepository;
import com.timesheet.syborgtech.repository.TaskRepository;
import com.timesheet.syborgtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public CommentResponseDto getComments(String searchTerm, Integer pageNo, Integer recordsPerPage, Long commentId) {
        Pageable page = PageRequest.of(pageNo - 1, recordsPerPage, Sort.Direction.DESC, "id");

        Page<Comment> commentPage = commentRepository.findAll(containsComment(searchTerm,commentId),page);

        List<CommentListResponseDto> commentListResponseDto = new ArrayList<>();

        commentPage.forEach(comment -> {
            CommentListResponseDto commentResponseDto = new CommentListResponseDto();
            commentResponseDto.setId(comment.getId());
            commentResponseDto.setCommentText(comment.getCommentText());
            commentResponseDto.setTaskId(comment.getTask().getTaskId());
            commentResponseDto.setTaskName(comment.getTask().getName());
            commentResponseDto.setUserId(comment.getUser().getId());
            commentResponseDto.setCreateAt(comment.getCreateAt());
            commentResponseDto.setUpdatedAt(comment.getUpdatedAt());
            commentListResponseDto.add(commentResponseDto);
        });

        CommentResponseDto response = new CommentResponseDto();
        response.setCommentListResponseDto(commentListResponseDto);
        response.setTotalPage(commentPage.getTotalPages());
        response.setCurrentPage(commentPage.getNumber()+1);
        response.setPageSize(commentPage.getSize());
        response.setTotalElements(commentPage.getTotalElements());

        return response;
    }

    public static Specification<Comment> containsComment(String searchTerm, Long commentId) {

        boolean hasSearchTerm = StringUtils.hasLength(searchTerm);
        String finalSearchTerm = hasSearchTerm && !searchTerm.contains("%")
                ? "%" + searchTerm.toLowerCase() + "%"
                : searchTerm;

        if (Objects.nonNull(commentId)) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), commentId);
        }
        if (hasSearchTerm) {

            Specification<Comment> searchSpec=null;

            return  ((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("commentText")), finalSearchTerm)
            )));

        }
        return null;
    }

    public DataResponse updateComment(CommentRequestDto commentRequestDto) {
       Comment comment = commentRepository.findById(commentRequestDto.getId())
               .orElseThrow(() -> new CommentNotFoundException("Comment not found with ID: " + commentRequestDto.getId()));

        comment.setCommentText(commentRequestDto.getCommentText());
        Comment updatedComment = commentRepository.save(comment);

        return Response.builder().message("Role Updated Successfully").build();
    }


    public DataResponse deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with ID: " + commentId));

        commentRepository.deleteById(comment.getId());

        return Response.builder().message("Comment Deleted Successfully").build();
    }
}
