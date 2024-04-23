package com.mca.query;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mca.infrastructure.jpa.SagaRelationshipSpringJpaRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FindRelatedSagas {
    private final SagaRelationshipSpringJpaRepository sagaRelationshipRepository;

    @Transactional
    public Collection<String> bySaga(Integer sagaId) {
        return sagaRelationshipRepository
            .findAllByIdSaga1(sagaId)
            .map(arg0 -> arg0.getId().getSaga2().toString())
            .collect(Collectors.toList());
    }
}
