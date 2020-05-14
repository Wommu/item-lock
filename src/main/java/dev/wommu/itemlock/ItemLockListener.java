package dev.wommu.itemlock;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class ItemLockListener implements Listener {

    private final ItemLockPlugin plugin;

    private long   disableTime;
    private String lockIsEnabledMessage;
    private String lockTimerMessage;

    ItemLockListener(ItemLockPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Set the amount of time before a player may drop an item after disabling the item lock.
     * @param disableTime The time in milliseconds.
     */
    public void setDisableTime(long disableTime) {
        this.disableTime = disableTime;
    }

    /**
     * Set the message which is sent to the player when they attempt to drop an item when their item lock is enabled.
     * @param message The message.
     */
    public void setLockIsEnabledMessage(String message) {
        this.lockIsEnabledMessage = ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Set the message which is sent to the player when they attempt to drop an item after disabling within the disable
     * time.
     * @param message The message.
     */
    public void setLockTimerMessage(String message) {
        this.lockTimerMessage = ChatColor.translateAlternateColorCodes('&', message);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.removeProfile(event.getPlayer().getUniqueId());
    }

    @EventHandler(ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent event) {
        Player          player  = event.getPlayer();
        ItemLockProfile profile = plugin.getProfile(player.getUniqueId());

        if(profile.isEnabled())
            player.sendMessage(lockIsEnabledMessage);
        else if(System.currentTimeMillis() - profile.getToggleTime() < disableTime)
            player.sendMessage(lockTimerMessage.replace("%time%", Double.toString(Math.round((System.currentTimeMillis() - profile.getToggleTime()) / 100D) / 10D)));
        else return;

        event.setCancelled(true);
    }
}
