package com.mca.query;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SAGA_RELATIONSHIP")
public class SagaRelationship {
    @EmbeddedId
    private SagaRelationshipId id;
    

    @Data
    @Builder
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SagaRelationshipId {
        @Column(name = "SAGA_ID_1")
        private Integer saga1;

        @Column(name = "SAGA_ID_2")
        private Integer saga2;
    }
}
