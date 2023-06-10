package net.insprill.spigotutils;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used to check the Minecraft version the server is running.
 * Useful when different Minecraft versions require different implementations.
 */
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@SuppressWarnings("unused")
public class MinecraftVersion {

    private static final Pattern versionPattern = Pattern.compile("(?i)(?<major>\\d)\\.(?<minor>\\d+)\\.?(?<patch>\\d+)?");

    // region Versions
    public static final MinecraftVersion v1_8_0 = new MinecraftVersion(8, 0);
    public static final MinecraftVersion v1_8_1 = new MinecraftVersion(8, 1);
    public static final MinecraftVersion v1_8_2 = new MinecraftVersion(8, 2);
    public static final MinecraftVersion v1_8_3 = new MinecraftVersion(8, 3);
    public static final MinecraftVersion v1_8_4 = new MinecraftVersion(8, 4);
    public static final MinecraftVersion v1_8_5 = new MinecraftVersion(8, 5);
    public static final MinecraftVersion v1_8_6 = new MinecraftVersion(8, 6);
    public static final MinecraftVersion v1_8_7 = new MinecraftVersion(8, 7);
    public static final MinecraftVersion v1_8_8 = new MinecraftVersion(8, 8);
    public static final MinecraftVersion v1_9_0 = new MinecraftVersion(9, 0);
    public static final MinecraftVersion v1_9_1 = new MinecraftVersion(9, 1);
    public static final MinecraftVersion v1_9_2 = new MinecraftVersion(9, 2);
    public static final MinecraftVersion v1_9_3 = new MinecraftVersion(9, 3);
    public static final MinecraftVersion v1_9_4 = new MinecraftVersion(9, 4);
    public static final MinecraftVersion v1_10_0 = new MinecraftVersion(10, 0);
    public static final MinecraftVersion v1_10_1 = new MinecraftVersion(10, 1);
    public static final MinecraftVersion v1_10_2 = new MinecraftVersion(10, 2);
    public static final MinecraftVersion v1_11_0 = new MinecraftVersion(11, 0);
    public static final MinecraftVersion v1_11_1 = new MinecraftVersion(11, 1);
    public static final MinecraftVersion v1_11_2 = new MinecraftVersion(11, 2);
    public static final MinecraftVersion v1_12_0 = new MinecraftVersion(12, 0);
    public static final MinecraftVersion v1_12_1 = new MinecraftVersion(12, 1);
    public static final MinecraftVersion v1_12_2 = new MinecraftVersion(12, 2);
    public static final MinecraftVersion v1_13_0 = new MinecraftVersion(13, 0);
    public static final MinecraftVersion v1_13_1 = new MinecraftVersion(13, 1);
    public static final MinecraftVersion v1_13_2 = new MinecraftVersion(13, 2);
    public static final MinecraftVersion v1_14_0 = new MinecraftVersion(14, 0);
    public static final MinecraftVersion v1_14_1 = new MinecraftVersion(14, 1);
    public static final MinecraftVersion v1_14_2 = new MinecraftVersion(14, 2);
    public static final MinecraftVersion v1_14_3 = new MinecraftVersion(14, 3);
    public static final MinecraftVersion v1_14_4 = new MinecraftVersion(14, 4);
    public static final MinecraftVersion v1_15_0 = new MinecraftVersion(15, 0);
    public static final MinecraftVersion v1_15_1 = new MinecraftVersion(15, 1);
    public static final MinecraftVersion v1_15_2 = new MinecraftVersion(15, 2);
    public static final MinecraftVersion v1_16_0 = new MinecraftVersion(16, 0);
    public static final MinecraftVersion v1_16_1 = new MinecraftVersion(16, 1);
    public static final MinecraftVersion v1_16_2 = new MinecraftVersion(16, 2);
    public static final MinecraftVersion v1_16_3 = new MinecraftVersion(16, 3);
    public static final MinecraftVersion v1_16_4 = new MinecraftVersion(16, 4);
    public static final MinecraftVersion v1_16_5 = new MinecraftVersion(16, 5);
    public static final MinecraftVersion v1_17_0 = new MinecraftVersion(17, 0);
    public static final MinecraftVersion v1_17_1 = new MinecraftVersion(17, 1);
    public static final MinecraftVersion v1_18_0 = new MinecraftVersion(18, 0);
    public static final MinecraftVersion v1_18_1 = new MinecraftVersion(18, 1);
    public static final MinecraftVersion v1_18_2 = new MinecraftVersion(18, 2);
    public static final MinecraftVersion v1_19_0 = new MinecraftVersion(19, 0);
    public static final MinecraftVersion v1_19_1 = new MinecraftVersion(19, 1);
    public static final MinecraftVersion v1_19_2 = new MinecraftVersion(19, 2);
    public static final MinecraftVersion v1_19_3 = new MinecraftVersion(19, 3);
    public static final MinecraftVersion v1_19_4 = new MinecraftVersion(19, 4);
    public static final MinecraftVersion v1_20_0 = new MinecraftVersion(20, 0);
    // endregion

    @Getter
    @NotNull
    private static final MinecraftVersion currentVersion;

    static {
        int major = 0;
        int patch = 0;

        String versionStr = Bukkit.getVersion();

        // Attempt to get version from
        if (ServerEnvironment.isPaper()) {
            try {
                versionStr = (String) Bukkit.getServer().getClass().getMethod("getMinecraftVersion").invoke(Bukkit.getServer());
            } catch (Exception ignored) {
            }
        }

        Matcher matcher = versionPattern.matcher(versionStr);
        if (matcher.find()) {
            if (matcher.groupCount() >= 2) {
                major = parseIntSafe(matcher.group("minor"));
            }
            if (matcher.groupCount() >= 3) {
                patch = parseIntSafe(matcher.group("patch"));
            }
        }

        // Fallback to attempt to get major version.
        if (major == 0 && getCraftBukkitVersion() != null) {
            String[] version = getCraftBukkitVersion().split("_");
            if (version.length >= 2) {
                major = parseIntSafe(version[1]);
            }
        }

        currentVersion = new MinecraftVersion(major, patch);
    }

    @Getter
    private final int major;
    @Getter
    private final int patch;

    /**
     * Gets the display name of a version. E.g. "1.8.8" or "1.18". Ignores pre-release versions.
     *
     * @return The display name of the version.
     */
    @NotNull
    public String getDisplayName() {
        return "1." + major + ((patch == 0) ? "" : "." + patch);
    }

    /**
     * @return Whether the version is at least 1.13.
     */
    public static boolean isNew() {
        return currentVersion.getMajor() >= 13;
    }

    /**
     * @param version The version to check.
     * @return Whether the current server version matches the provided version.
     */
    public static boolean is(@NotNull MinecraftVersion version) {
        checkNotNull(version);
        return currentVersion.getMajor() == version.getMajor() && currentVersion.getPatch() == version.getPatch();
    }

    /**
     * @param version The version to check.
     * @return Whether the current server major version matches the provided major version.
     */
    public static boolean isMajor(@NotNull MinecraftVersion version) {
        checkNotNull(version);
        return currentVersion.getMajor() == version.getMajor();
    }

    /**
     * @param version The version to check.
     * @return Whether the current server version is newer than the provided version.
     */
    public static boolean isNewerThan(@NotNull MinecraftVersion version) {
        checkNotNull(version);
        return (currentVersion.getMajor() > version.getMajor()) || (currentVersion.getMajor() == version.getMajor() && currentVersion.getPatch() > version.getPatch());
    }

    /**
     * @param version The version to check.
     * @return Whether the current server version is at least the provided version.
     */
    public static boolean isAtLeast(@NotNull MinecraftVersion version) {
        checkNotNull(version);
        return (currentVersion.getMajor() > version.getMajor()) || (currentVersion.getMajor() == version.getMajor() && currentVersion.getPatch() >= version.getPatch());
    }

    /**
     * @param version The version to check.
     * @return Whether the current server version is older than the provided version.
     */
    public static boolean isOlderThan(@NotNull MinecraftVersion version) {
        checkNotNull(version);
        return (currentVersion.getMajor() < version.getMajor()) || (currentVersion.getMajor() == version.getMajor() && currentVersion.getPatch() < version.getPatch());
    }

    /**
     * Attempts to get the CraftBukkit version from the server implementation's package name.
     *
     * @return The CraftBukkit version, or null if it could not be determined.
     */
    public static @Nullable String getCraftBukkitVersion() {
        String[] pckg = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        return (pckg.length >= 4) ? pckg[3] : null;
    }

    /**
     * Parses an integer from a String, returning 0 if it's not valid.
     *
     * @param str The String to parse.
     * @return The parsed String, or 0 if invalid.
     */
    private static int parseIntSafe(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ignored) {
            return 0;
        }
    }

    /**
     * Checks that the provided MinecraftVersion is not null, and throws a
     * NullPointerException with a custom message if it is.
     *
     * @param version The version to check
     */
    private static void checkNotNull(@Nullable MinecraftVersion version) {
        Preconditions.checkNotNull(version, "Version cannot be null");
    }

}
