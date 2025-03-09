package net.insprill.spigotutils;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Used to check the environment the server is running.
 * Useful when different environments require different implementations.
 */
@EqualsAndHashCode
public class ServerEnvironment {

    // region Environments
    /**
     * <a href="https://purpurmc.org/">https://purpurmc.org/</a>
     */
    public static final ServerEnvironment PURPUR = new ServerEnvironment("org.purpurmc.purpur.PurpurConfig");
    /**
     * <a href="https://papermc.io/software/folia">https://papermc.io/software/folia</a>
     */
    public static final ServerEnvironment FOLIA = new ServerEnvironment("io.papermc.paper.threadedregions.scheduler.FoliaRegionScheduler");
    /**
     * <a href="https://github.com/MockBukkit/MockBukkit">https://github.com/MockBukkit/MockBukkit</a>
     */
    public static final ServerEnvironment MOCK_BUKKIT = new ServerEnvironment("org.mockbukkit.mockbukkit.ServerMock"); // Implements Paper
    /**
     * <a href="https://papermc.io/">https://papermc.io/</a>
     */
    public static final ServerEnvironment PAPER = new ServerEnvironment("com.destroystokyo.paper.PaperConfig", MOCK_BUKKIT, FOLIA, PURPUR);
    /**
     * <a href="https://www.spigotmc.org/">https://www.spigotmc.org/</a>
     */
    public static final ServerEnvironment SPIGOT = new ServerEnvironment("org.spigotmc.SpigotConfig", PAPER);
    /**
     * <a href="https://dev.bukkit.org/">https://dev.bukkit.org/</a>
     */
    public static final ServerEnvironment BUKKIT = new ServerEnvironment("org.bukkit.Bukkit", SPIGOT);
    // endregion

    private final String checkClass;
    @EqualsAndHashCode.Exclude
    private final List<ServerEnvironment> downstreams;
    @Getter
    @EqualsAndHashCode.Exclude
    private boolean isCurrentEnvironment;

    /**
     * @param checkClass  The class to check for to see if the server is running the given environment.
     * @param downstreams All ServerEnvironments that are directly downstream (forks) of this one.
     */
    public ServerEnvironment(@NotNull String checkClass, ServerEnvironment... downstreams) {
        Preconditions.checkNotNull(checkClass, "Check class cannot be null");
        this.checkClass = checkClass;
        this.downstreams = Arrays.asList(downstreams);
        try {
            Class.forName(checkClass);
            this.isCurrentEnvironment = true;
            // Will be null on the first call until initialized.
            //noinspection ConstantValue
            if (getCurrentEnvironment() == null || isAtLeast(this)) {
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
        return ServerEnvironment.MOCK_BUKKIT.isCurrentEnvironment();
    }

    /**
     * @return Whether the server is running Paper, or a fork of it.
     */
    public static boolean isFolia() {
        return isAtLeast(ServerEnvironment.FOLIA);
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
        ServerEnvironment env = getCurrentEnvironment();
        return env.equals(environment) || environment.downstreams.stream().anyMatch((e) -> isAtLeast(env));
    }

    /**
     * @param environment The environment to check.
     * @return Whether the server is running an environment lower than the once specified.
     */
    public static boolean isLowerThan(@NotNull ServerEnvironment environment) {
        return !isAtLeast(environment);
    }

}
