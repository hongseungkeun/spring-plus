package org.example.expert.domain.todo.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TodoSearchResponse(
        String title,
        Integer managerCount,
        Integer commentCount
) {
}
