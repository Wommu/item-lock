package dev.wommu.itemlock;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class ItemLockCommand implements CommandExecutor, TabCompleter {

    private final ItemLockPlugin plugin;

    private String enableMessage;
    private String disableMessage;

    ItemLockCommand(ItemLockPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Set the message sent after a player enables their item lock.
     * @param message The message.
     */
    public void setEnableMessage(String message) {
        this.enableMessage = ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Set the message sent after a player disable their item lock.
     * @param message The message.
     */
    public void setDisableMessage(String message) {
        this.disableMessage = ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may use this command.");
            return true;
        }

        Player          player  = (Player) sender;
        ItemLockProfile profile = plugin.getProfile(player.getUniqueId());
        // send the appropriate message for the new toggle status of the lock
        player.sendMessage(profile.toggleLock() ? enableMessage : disableMessage);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
