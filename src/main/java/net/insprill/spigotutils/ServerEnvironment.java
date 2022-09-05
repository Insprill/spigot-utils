package net.insprill.spigotutils;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Used to check the environment the server is running.
 * Useful when different environments require different implementations.
 */
public class ServerEnvironment {

    // region Environments
    /**
     * <a href="https://dev.bukkit.org/">https://dev.bukkit.org/</a>
     */
    public static final ServerEnvironment BUKKIT = new ServerEnvironment("", 0);
    /**
     * <a href="https://www.spigotmc.org/">https://www.spigotmc.org/</a>
     */
    public static final ServerEnvironment SPIGOT = new ServerEnvironment("org.spigotmc.SpigotConfig", 1);
    /**
     * <a href="https://papermc.io/">https://papermc.io/</a>
     */
    public static final ServerEnvironment PAPER = new ServerEnvironment("com.destroystokyo.paper.PaperConfig", 2);
    /**
     * <a href="https://purpurmc.org/">https://purpurmc.org/</a>
     */
    public static final ServerEnvironment PURPUR = new ServerEnvironment("org.purpurmc.purpur.PurpurConfig", 3);
    // endregion

    @Getter
    private final int ordinal;
    @Getter
    private boolean isCurrentEnvironment;

    /**
     * @param checkClass The class to check for to see if the server is running the given environment.
     * @param ordinal    The ordinal of the environment, or fork level. (Bukkit is 0, Spigot is 1, Paper is 2, etc).
     */
    public ServerEnvironment(@NotNull String checkClass, int ordinal) {
        this.ordinal = ordinal;
        try {
            Class.forName(checkClass);
            this.isCurrentEnvironment = true;
            if (ordinal > ServerEnvironment.currentEnvironment.getOrdinal()) {
                ServerEnvironment.currentEnvironment = this;
            }
        } catch (ClassNotFoundException e) {
            this.isCurrentEnvironment = false;
        }
    }

    @Getter
    private static ServerEnvironment currentEnvironment = BUKKIT;

    /**
     * @return Whether the server is running Purpur, or a fork of it.
     */
    public static boolean isPurpur() {
        return isAtLeast(ServerEnvironment.PURPUR);
    }

    /**
     * @return Whether the server is running Paper, or a fork of it.
     */
    public static boolean isPaper() {
        return isAtLeast(ServerEnvironment.PAPER);
    }

    /**
     * @return Whether the server is running Spigot, or a fork of it.
     */
    public static boolean isSpigot() {
        return isAtLeast(ServerEnvironment.SPIGOT);
    }

    /**
     * @return Whether the server is running Bukkit, or a fork of it.
     */
    public static boolean isBukkit() {
        return isAtLeast(ServerEnvironment.BUKKIT);
    }

    /**
     * @param environment The environment to check.
     * @return Whether the server is running the given environment, or a fork of it.
     */
    public static boolean isAtLeast(ServerEnvironment environment) {
        return environment.getOrdinal() >= ServerEnvironment.currentEnvironment.getOrdinal();
    }

}
