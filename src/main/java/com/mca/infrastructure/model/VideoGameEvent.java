package com.mca.infrastructure.model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VideoGameEvent {
	private Integer videoGameId;

	private boolean availability;

	private Timestamp updateTime;


}
