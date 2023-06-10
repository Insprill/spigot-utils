package net.insprill.spigotutils;

import be.seeseemelk.mockbukkit.MockBukkit;
import lombok.SneakyThrows;
import org.bukkit.craftbukkit.v1_18_R2.CraftServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinecraftVersionTest {

    private MinecraftVersion originalVersion;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        MockBukkit.mock();
        // These tests were written assuming 1.18.2. We have to set it back to that so we can update MockBukkit.
        this.originalVersion = MinecraftVersion.getCurrentVersion();
        setCurrentVersion(new MinecraftVersion(18, 2));
    }

    @SneakyThrows
    @AfterEach
    void teardown() {
        MockBukkit.unmock();
        setCurrentVersion(this.originalVersion);
    }

    @Test
    void getCurrentVersion_CorrectVersion() {
        assertEquals(new MinecraftVersion(18, 2), MinecraftVersion.getCurrentVersion());
    }

    @Test
    void getDisplayName_NoPatch() {
        MinecraftVersion version = new MinecraftVersion(19, 0);

        assertEquals("1.19", version.getDisplayName());
    }

    @Test
    void getDisplayName_WithPatch() {
        MinecraftVersion version = new MinecraftVersion(19, 1);

        assertEquals("1.19.1", version.getDisplayName());
    }

    @Test
    void isNew_NewerThanOneDotThirteen_True() {
        assertTrue(MinecraftVersion.isNew());
    }

    // region is
    @Test
    void is_VersionsEqual_True() {
        assertTrue(MinecraftVersion.is(new MinecraftVersion(18, 2)));
    }

    @Test
    void is_DifferentPatch_False() {
        assertFalse(MinecraftVersion.is(new MinecraftVersion(18, 0)));
    }

    @Test
    void is_DifferentMajor_False() {
        assertFalse(MinecraftVersion.is(new MinecraftVersion(19, 2)));
    }
    // endregion

    // region isMajor
    @Test
    void isMajor_VersionsEqual_True() {
        assertTrue(MinecraftVersion.isMajor(new MinecraftVersion(18, 2)));
    }

    @Test
    void isMajor_DifferentPatch_True() {
        assertTrue(MinecraftVersion.isMajor(new MinecraftVersion(18, 0)));
    }

    @Test
    void isMajor_DifferentMajor_False() {
        assertFalse(MinecraftVersion.isMajor(new MinecraftVersion(19, 2)));
    }
    // endregion

    // region isNewerThan
    @Test
    void isNewerThan_CurrentPatchIsNewer_True() {
        assertTrue(MinecraftVersion.isNewerThan(new MinecraftVersion(18, 1)));
    }

    @Test
    void isNewerThan_CurrentMajorIsNewer_True() {
        assertTrue(MinecraftVersion.isNewerThan(new MinecraftVersion(17, 2)));
    }

    @Test
    void isNewerThan_VersionsEqual_False() {
        assertFalse(MinecraftVersion.isNewerThan(new MinecraftVersion(18, 2)));
    }

    @Test
    void isNewerThan_CurrentMajorIsOlder_False() {
        assertFalse(MinecraftVersion.isNewerThan(new MinecraftVersion(19, 0)));
    }

    @Test
    void isNewerThan_CurrentPatchIsOlder_False() {
        assertFalse(MinecraftVersion.isNewerThan(new MinecraftVersion(18, 3)));
    }
    // endregion

    // region isAtLeast
    @Test
    void isAtLeast_CurrentMajorIsNewer_True() {
        assertTrue(MinecraftVersion.isAtLeast(new MinecraftVersion(17, 2)));
    }

    @Test
    void isAtLeast_CurrentPatchIsNewer_True() {
        assertTrue(MinecraftVersion.isAtLeast(new MinecraftVersion(18, 1)));
    }

    @Test
    void isAtLeast_VersionsEqual_True() {
        assertTrue(MinecraftVersion.isAtLeast(new MinecraftVersion(18, 2)));
    }

    @Test
    void isAtLeast_CurrentMajorIsOlder_False() {
        assertFalse(MinecraftVersion.isAtLeast(new MinecraftVersion(19, 2)));
    }

    @Test
    void isAtLeast_CurrentPatchIsOlder_False() {
        assertFalse(MinecraftVersion.isAtLeast(new MinecraftVersion(18, 3)));
    }
    // endregion

    // region isOlderThan
    @Test
    void isOlderThan_CurrentMajorIsNewer_False() {
        assertFalse(MinecraftVersion.isOlderThan(new MinecraftVersion(17, 2)));
    }

    @Test
    void isOlderThan_CurrentPatchIsNewer_False() {
        assertFalse(MinecraftVersion.isOlderThan(new MinecraftVersion(18, 1)));
    }

    @Test
    void isOlderThan_VersionsEqual_False() {
        assertFalse(MinecraftVersion.isOlderThan(new MinecraftVersion(18, 2)));
    }

    @Test
    void isOlderThan_CurrentMajorIsOlder_True() {
        assertTrue(MinecraftVersion.isOlderThan(new MinecraftVersion(19, 2)));
    }

    @Test
    void isOlderThan_CurrentPatchIsOlder_True() {
        assertTrue(MinecraftVersion.isOlderThan(new MinecraftVersion(18, 3)));
    }
    // endregion

    @Test
    void getCraftBukkitVersion_CannotFind_Null() {
        assertNull(MinecraftVersion.getCraftBukkitVersion());
    }

    @Test
    void getCraftBukkitVersion_CorrectVersion() {
        MockBukkit.unmock();
        MockBukkit.mock(new CraftServerMock());
        assertEquals("v1_18_R2", MinecraftVersion.getCraftBukkitVersion());
    }

    private static void setCurrentVersion(MinecraftVersion version) throws ReflectiveOperationException {
        final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        final Unsafe unsafe = (Unsafe) unsafeField.get(null);
        Field currentVersion = MinecraftVersion.class.getDeclaredField("currentVersion");
        final Object staticFieldBase = unsafe.staticFieldBase(currentVersion);
        final long staticFieldOffset = unsafe.staticFieldOffset(currentVersion);
        unsafe.putObject(staticFieldBase, staticFieldOffset, version);
    }

}
