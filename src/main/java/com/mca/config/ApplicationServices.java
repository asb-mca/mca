package com.mca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mca.application.ProcessStockChange;
import com.mca.infrastructure.jpa.VideoGameJpaRepository;

@Configuration
public class ApplicationServices {

    @Bean
    public ProcessStockChange getProcessStockChange(VideoGameJpaRepository videoGameRepository) {
        return new ProcessStockChange(videoGameRepository);
    }
    
}
