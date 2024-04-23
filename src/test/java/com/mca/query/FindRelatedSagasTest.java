package com.mca.query;

import org.junit.Test;

import com.mca.infrastructure.jpa.SagaRelationshipSpringJpaRepository;
import com.mca.query.SagaRelationship.SagaRelationshipId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class FindRelatedSagasTest {
    @Test
    public void bySaga_ShouldReturnRelatedSagas() {
        // Arrange
        final SagaRelationshipSpringJpaRepository repositoryMock = mock(SagaRelationshipSpringJpaRepository.class);
        final FindRelatedSagas findRelatedSagas = new FindRelatedSagas(repositoryMock);

        Integer sagaId = 1;
        Set<Integer> relatedSagaIds = new HashSet<>();
        relatedSagaIds.add(2);
        relatedSagaIds.add(3);

        when(repositoryMock.findAllByIdSaga1(sagaId))
            .thenReturn(Stream.of(
                SagaRelationship.builder().id(SagaRelationshipId.builder().saga2(2).build()).build(),
                SagaRelationship.builder().id(SagaRelationshipId.builder().saga2(3).build()).build()
            ));

        // Act
        Collection<String> result = findRelatedSagas.bySaga(sagaId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));
    }

    @Test
    public void bySaga_ShouldReturnEmptySet_WhenNoRelatedSagas() {
        // Arrange
        final SagaRelationshipSpringJpaRepository repositoryMock = mock(SagaRelationshipSpringJpaRepository.class);
        final FindRelatedSagas findRelatedSagas = new FindRelatedSagas(repositoryMock);

        Integer sagaId = 1;
        when(repositoryMock.findAllByIdSaga1(sagaId)).thenReturn(Stream.empty());

        // Act
        Collection<String> result = findRelatedSagas.bySaga(sagaId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
