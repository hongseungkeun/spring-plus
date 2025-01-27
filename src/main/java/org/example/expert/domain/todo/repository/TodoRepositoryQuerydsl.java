package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TodoRepositoryQuerydsl {

    Optional<Todo> findByIdWithUser(Long todoId);

    Page<TodoSearchResponse> findAllByCondition(Pageable pageable, String title, String sort, String nickname);
}
