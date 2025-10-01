package net.insprill.spigotutils;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Used to check the environment the server is running.
 * Useful when different environments require different implementations.
 */
public enum ServerEnvironment {

    // region Environments
    /**
     * <a href="https://purpurmc.org/">https://purpurmc.org/</a>
     */
    PURPUR("org.purpurmc.purpur.PurpurConfig"),
    /**
     * <a href="https://papermc.io/software/folia">https://papermc.io/software/folia</a>
     */
    FOLIA("io.papermc.paper.threadedregions.scheduler.FoliaRegionScheduler"),
    /**
     * <a href="https://github.com/MockBukkit/MockBukkit">https://github.com/MockBukkit/MockBukkit</a>
     */
    MOCK_BUKKIT("be.seeseemelk.mockbukkit.ServerMock", "org.mockbukkit.mockbukkit.ServerMock"),
    /**
     * <a href="https://papermc.io/">https://papermc.io/</a>
     */
    PAPER("com.destroystokyo.paper.PaperConfig", "io.papermc.paper.ServerBuildInfo"),
    /**
     * <a href="https://www.spigotmc.org/">https://www.spigotmc.org/</a>
     */
    SPIGOT("org.spigotmc.SpigotConfig", "org.bukkit.Server.Spigot"),
    /**
     * <a href="https://dev.bukkit.org/">https://dev.bukkit.org/</a>
     */
    BUKKIT("org.bukkit.Bukkit");
    // endregion

    private boolean isCurrentEnvironment;

    /**
     * @param checkClasses The classes to check for to see if the server is running the given environment.
     */
    ServerEnvironment(@NotNull String... checkClasses) {
        Preconditions.checkNotNull(checkClasses, "Check classes cannot be null");

        for (String checkClass : checkClasses) {
            try {
                Class.forName(checkClass);
                this.isCurrentEnvironment = true;
                break;
            } catch (ClassNotFoundException e) {
                this.isCurrentEnvironment = false;
            }
        }
    }

    /**
     * @return Whether the server is running Purpur, or a fork of it.
     */
    public static boolean isPurpur() {
        return ServerEnvironment.PURPUR.isCurrentEnvironment;
    }

    /**
     * @return Whether the server is MockBukkit's ServerMock
     */
    public static boolean isMockBukkit() {
        return ServerEnvironment.MOCK_BUKKIT.isCurrentEnvironment;
    }

    /**
     * @return Whether the server is running Folia, or a fork of it.
     */
    public static boolean isFolia() {
        return ServerEnvironment.FOLIA.isCurrentEnvironment;
    }

    /**
     * @return Whether the server is running Paper, or a fork of it.
     */
    public static boolean isPaper() {
        return ServerEnvironment.PAPER.isCurrentEnvironment;
    }

    /**
     * @return Whether the server is running Spigot, or a fork of it.
     */
    public static boolean isSpigot() {
        return ServerEnvironment.SPIGOT.isCurrentEnvironment;
    }

    /**
     * @return Whether the server is running Bukkit, or a fork of it.
     */
    public static boolean isBukkit() {
        return ServerEnvironment.BUKKIT.isCurrentEnvironment;
    }

}
