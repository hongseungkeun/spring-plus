package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(Long userId, Long todoId, Long managerId, String reason) {
        Log log = Log.builder()
                .userId(userId)         // 메소드를 실행한 유저 Id
                .todoId(todoId)         // 해당 일정 Id
                .managerId(managerId)   // 해당 담당자 Id
                .reason(reason)         // 결과 메시지
                .build();

        logRepository.save(log);
    }
}
