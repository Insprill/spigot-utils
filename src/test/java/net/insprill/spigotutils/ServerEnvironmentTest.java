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
    void isSpigot_True() {
        assertTrue(ServerEnvironment.isSpigot());
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
    void isPurpur_False() {
        assertFalse(ServerEnvironment.isPurpur());
    }

    @Test
    void isAtLeast_Bukkit_True() {
        assertTrue(ServerEnvironment.isAtLeast(ServerEnvironment.BUKKIT));
    }

    @Test
    void isAtLeast_Spigot_True() {
        assertTrue(ServerEnvironment.isAtLeast(ServerEnvironment.SPIGOT));
    }

    @Test
    void isAtLeast_Paper_True() {
        assertTrue(ServerEnvironment.isAtLeast(ServerEnvironment.PAPER));
    }

    @Test
    void isAtLeast_Purpur_False() {
        assertFalse(ServerEnvironment.isAtLeast(ServerEnvironment.PURPUR));
    }

}
