package net.insprill.spigotutils;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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
    public static final ServerEnvironment BUKKIT = new ServerEnvironment("org.bukkit.Bukkit", 0);
    /**
     * <a href="https://www.spigotmc.org/">https://www.spigotmc.org/</a>
     */
    public static final ServerEnvironment SPIGOT = new ServerEnvironment("org.spigotmc.SpigotConfig", 1);
    /**
     * <a href="https://papermc.io/">https://papermc.io/</a>
     */
    public static final ServerEnvironment PAPER = new ServerEnvironment("com.destroystokyo.paper.PaperConfig", 2);
    /**
     * <a href="https://github.com/MockBukkit/MockBukkit">https://github.com/MockBukkit/MockBukkit</a>
     */
    public static final ServerEnvironment MOCK_BUKKIT = new ServerEnvironment("be.seeseemelk.mockbukkit.ServerMock", 2); // Implements Paper
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
        Preconditions.checkNotNull(checkClass, "Check class cannot be null");
        this.ordinal = ordinal;
        try {
            Class.forName(checkClass);
            this.isCurrentEnvironment = true;
            // Will be null on the first call until initialized.
            if (getCurrentEnvironment() == null || ordinal > getCurrentEnvironment().getOrdinal()) {
                setCurrentEnvironment(this);
            }
        } catch (ClassNotFoundException e) {
            this.isCurrentEnvironment = false;
        }
    }

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @NotNull
    private static ServerEnvironment currentEnvironment;

    /**
     * @return Whether the server is running Purpur, or a fork of it.
     */
    public static boolean isPurpur() {
        return isAtLeast(ServerEnvironment.PURPUR);
    }

    /**
     * @return Whether the server is MockBukkit's ServerMock
     */
    public static boolean isMockBukkit() {
        return ServerEnvironment.MOCK_BUKKIT.isCurrentEnvironment;
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
    public static boolean isAtLeast(@NotNull ServerEnvironment environment) {
        Preconditions.checkNotNull(environment, "Environment cannot be null");
        return getCurrentEnvironment().getOrdinal() >= environment.getOrdinal();
    }

    /**
     * @param environment The environment to check.
     * @return Whether the server is running an environment lower than the once specified.
     */
    public static boolean isLowerThan(@NotNull ServerEnvironment environment) {
        Preconditions.checkNotNull(environment, "Environment cannot be null");
        return getCurrentEnvironment().getOrdinal() < environment.getOrdinal();
    }

}
