package com.mca.query;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoGameView {
    @Id
    @JsonIgnore
    private Integer id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String genre;

    @JsonIgnore
    private Long sagaId;
    @JsonIgnore
    private int relevance;

    @JsonProperty("id")
    public String getIdString() {
        return this.id.toString();
    }
}
