package com.mca.domain;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VIDEOGAME")
public class VideoGame {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "RELEVANCE")
    private Integer relevance;

    @Column(name = "RELEASE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime releaseDate;

    @ManyToOne
    @JoinColumn(name = "GENRE_ID")
    private Genre genre;

    @OneToOne(mappedBy = "videoGame", cascade = CascadeType.ALL)
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "SAGA_ID")
    private VideoGameSaga saga;

    public void changeStock(boolean available, LocalDateTime lastUpdated) {
        if (this.stock == null) {
            Stock stock = Stock.builder()
                .availability(false)
                .lastUpdated(LocalDateTime.MIN)
                .build();
            this.setStock(stock);
            stock.setVideoGame(this);
        }

        if (lastUpdated.isBefore(this.stock.getLastUpdated())) {
            log.warning(String.format(
                "[%d]. An update date (%s) prior to the existing one (%s) has been provided.",
                this.id,
                lastUpdated,
                this.stock.getLastUpdated()
            ));

            return;
        }

        this.stock.setAvailability(available);
        this.stock.setLastUpdated(lastUpdated);
    }
}
