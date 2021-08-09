package onsen.onsen;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class OnsenTipMessage implements Listener {
    
    private final Onsen plugin;
    
    public OnsenTipMessage(Onsen onsen) {
        this.plugin = onsen;
        startTimer();
    }

    private void startTimer() {
        String TipMessage = plugin.getConfig().getString("Onsen.TipMessage");
        String TipText = plugin.getConfig().getString("Onsen.Text");
        plugin.task = new BukkitRunnable() {
            @Override
            public void run() {
                BaseComponent[] tipMessage = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "" + TipMessage)).create();
                HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, tipMessage);
                BaseComponent[] message = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "" + TipText)).event(hover).create();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(message);
                }
            }
        };
        plugin.task.runTaskTimer(plugin, 0, 6000);
    }
}
