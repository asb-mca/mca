package com.mca.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

public class VideoGameTest {

    @Test
    public void changeStock_creates_new_stock() {
        VideoGame vg = VideoGame.builder().title("test game").build();
        LocalDateTime now = LocalDateTime.now();

        assertNull(vg.getStock());

        vg.changeStock(true, now);

        assertNotNull(vg.getStock());
        assertTrue(vg.getStock().getAvailability());
        assertEquals(now, vg.getStock().getLastUpdated());
    }

    @Test
    public void changeStock_does_not_update() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minusDays(1);

        VideoGame vg = VideoGame.builder()
            .title("test game")
            .stock(Stock.builder().availability(true).lastUpdated(now).build())
            .build();

        vg.changeStock(false, before);
        assertTrue(vg.getStock().getAvailability());
        assertEquals(now, vg.getStock().getLastUpdated());
    }

    @Test
    public void changeStock_does_update() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minusDays(1);

        VideoGame vg = VideoGame.builder()
            .title("test game")
            .stock(Stock.builder().availability(true).lastUpdated(before).build())
            .build();

        vg.changeStock(false, now);
        assertFalse(vg.getStock().getAvailability());
        assertEquals(now, vg.getStock().getLastUpdated());
    }
    
}
