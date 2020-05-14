package dev.wommu.itemlock;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ItemLockPlugin extends JavaPlugin {

    private final Map<UUID, ItemLockProfile> profileMap = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        // set up the /itemlock command
        PluginCommand   pluginCommand = getCommand("itemlock");
        ItemLockCommand command       = new ItemLockCommand(this);
        command.setEnableMessage(config.getString("lock-enabled-message", "&aYour item lock has been enabled."));
        command.setDisableMessage(config.getString("lock-disabled-message", "&aYour item lock has been disabled."));
        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);

        // set up the listener
        ItemLockListener listener = new ItemLockListener(this);
        listener.setLockIsEnabledMessage(config.getString("no-drop-lock-enabled", "&cYou may not drop items, disable your item lock with /itemlock"));
        listener.setLockTimerMessage(config.getString("no-drop-disable-time", "&cYou may not drop items, wait %time% more seconds."));
        listener.setDisableTime(config.getLong("drop-disable-time", 5000L));
        getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        profileMap.clear();
    }

    /**
     * Get the profile of a player by their uuid, or create one if it doesn't exist.
     * @param uuid The unique id of the player.
     * @return The {@link ItemLockProfile} of the player.
     */
    public ItemLockProfile getProfile(UUID uuid) {
        return profileMap.computeIfAbsent(uuid, ItemLockProfile::new);
    }

    /**
     * Remove the profile of a player by their uuid.
     * @param uuid The unique id of the player.
     */
    public void removeProfile(UUID uuid) {
        profileMap.remove(uuid);
    }

    /**
     * Get whether or not the profile map contains a player by their uuid.
     * @param uuid The unique id of the player.
     * @return True if a profile exists for that player in the map.
     */
    public boolean containsProfile(UUID uuid) {
        return profileMap.containsKey(uuid);
    }
}
