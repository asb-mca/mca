package com.mca.infrastructure;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mca.application.ProcessStockChange;
import com.mca.infrastructure.model.VideoGameEvent;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@AllArgsConstructor
public class VideoGameStockProcessor {
    private final ProcessStockChange processStockChange;

    @Transactional
    @KafkaListener(topics = "#{@environment.resolvePlaceholders('${topic}')}", groupId = "vg-stock-processor")
    public void listen(ConsumerRecord<String, String> record) {
        final Gson gson = new Gson();
        final VideoGameEvent videoGameEvent;

        if (!"VideoGameEvent".equals(record.key())) {
            return;
        }

        try {
            videoGameEvent = gson.fromJson(record.value(), VideoGameEvent.class);

            processStockChange.execute(
                ProcessStockChange.Input
                .builder()
                .videoGameId(videoGameEvent.getVideoGameId())
                .date(videoGameEvent.getUpdateTime().toLocalDateTime())
                .build()
            );

            log.info("Stock updated successfully for VideoGame ID {}", videoGameEvent.getVideoGameId());
        } catch (Exception e) {
            log.error(String.format("Error processing message: %s", e.getMessage()));
        }
    }
}
