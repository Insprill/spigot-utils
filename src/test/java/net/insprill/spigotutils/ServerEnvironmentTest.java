package net.insprill.spigotutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerEnvironmentTest {

    @Test
    void isBukkit_True() {
        assertTrue(ServerEnvironment.isBukkit());
    }

    @Test
    void isSpigot_False() {
        assertFalse(ServerEnvironment.isSpigot());
    }

    @Test
    void isPaper_True() {
        assertTrue(ServerEnvironment.isPaper());
    }

    @Test
    void isMockBukkit_True() {
        assertTrue(ServerEnvironment.isMockBukkit());
    }

    @Test
    void isFolia_Fale() {
        assertFalse(ServerEnvironment.isFolia());
    }

    @Test
    void isPurpur_False() {
        assertFalse(ServerEnvironment.isPurpur());
    }

}
