package net.insprill.spigotutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerEnvironmentTest {

    @Test
    void isBukkit_True() {
        assertTrue(ServerEnvironment.isBukkit());
    }

    @Test
    void isSpigot_False() {
        assertTrue(ServerEnvironment.isBukkit());
    }

    @Test
    void isAtLeast_Bukkit_True() {
        assertTrue(ServerEnvironment.isAtLeast(ServerEnvironment.BUKKIT));
    }

    @Test
    void isAtLeast_Spigot_False() {
        assertTrue(ServerEnvironment.isAtLeast(ServerEnvironment.SPIGOT));
    }

}
