package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "logs")
public class Log extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long todoId;
    private Long managerId;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String reason;

    @Builder
    public Log(Long userId, Long todoId, Long managerId, String reason) {
        this.userId = userId;
        this.todoId = todoId;
        this.managerId = managerId;
        this.status = reason == null ? Status.SUCCESS : Status.FAILURE;
        this.reason = reason;
    }
}
