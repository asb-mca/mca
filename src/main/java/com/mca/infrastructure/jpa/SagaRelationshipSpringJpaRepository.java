package com.mca.infrastructure.jpa;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mca.query.SagaRelationship;


@Repository
public interface SagaRelationshipSpringJpaRepository extends JpaRepository<SagaRelationship, SagaRelationship.SagaRelationshipId>{
    public Stream<SagaRelationship> findAllByIdSaga1(Integer saga1);
}
