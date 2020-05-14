package dev.wommu.itemlock;

import java.util.UUID;

public final class ItemLockProfile {

    private final UUID uuid;

    private boolean enabled    = true;
    private long    toggleTime;

    ItemLockProfile(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Get the unique id of the player who owns the profile.
     * @return The uuid.
     */
    public UUID getUniqueId() {
        return uuid;
    }

    /**
     * Get whether or not the item lock is enabled.
     * @return True if enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Get the last time the item lock was switched.
     * @return
     */
    public long getToggleTime() {
        return toggleTime;
    }

    /**
     * Set whether or not the item lock is enabled.
     * @param enabled True if the lock should be enabled, false otherwise.
     */
    public void setEnabled(boolean enabled) {
        if(this.enabled == enabled)
            return;
        this.enabled = enabled;
        toggleTime = System.currentTimeMillis();
    }

    /**
     * Alternate the lock's enabled state.
     * @return The state of the lock after it is toggled.
     */
    public boolean toggleLock() {
        setEnabled(!enabled);
        return enabled;
    }
}
