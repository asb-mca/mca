package com.mca.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "VIDEOGAME_SAGA")
public class VideoGameSaga {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @OneToMany(mappedBy = "saga")
    private Set<VideoGame> videoGames;

    @ManyToMany
    @JoinTable(
        name = "SAGA_RELATIONSHIP",
        joinColumns = @JoinColumn(name = "SAGA_ID_1"),
        inverseJoinColumns = @JoinColumn(name = "SAGA_ID_2")
    )
    private Set<VideoGameSaga> relatedSagas;
}
