package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoRepositoryQuerydslImpl implements TodoRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(jpaQueryFactory.select(todo)
                .from(todo)
                .leftJoin(todo.user, user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }

    @Override
    public Page<TodoSearchResponse> findAllByCondition(Pageable pageable, String title, String sort, String nickname) {
        BooleanBuilder builder = new BooleanBuilder();

        if (title != null && !title.isBlank()) {
            builder.and(todo.title.containsIgnoreCase(title));
        }

        if (nickname != null && !nickname.isBlank()) {
            builder.and(todo.managers.any().user.nickname.containsIgnoreCase(nickname));
        }

        OrderSpecifier<?> orderSpecifier = "latest".equalsIgnoreCase(sort) ? todo.modifiedAt.desc() : todo.createdAt.desc();

        List<TodoSearchResponse> todos = jpaQueryFactory.select(
                        Projections.constructor(
                                TodoSearchResponse.class,
                                todo.title,
                                todo.managers.size(),
                                todo.comments.size()
                        ))
                .from(todo)
                .where(builder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(
                        todo.count())
                .from(todo)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(todos, pageable, count != null ? count : 0L);
    }
}
