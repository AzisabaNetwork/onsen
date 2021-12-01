package com.github.sirokuri_.onsen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OnsenCommands implements CommandExecutor {
    private final Onsen plugin;

    public OnsenCommands(Onsen onsen) {
        this.plugin = onsen;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("onsen")) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (args.length <= 0) {
                return true;
            }
            if(args[0].equalsIgnoreCase("set")){
                if (sender.hasPermission("Onsen.permission.admin")) {
                    Location loc = player.getLocation();
                    String world = loc.getWorld().getName();
                    int x = loc.getBlockX();
                    int y = loc.getBlockY();
                    int z = loc.getBlockZ();
                    int yaw = (int) loc.getYaw();
                    int pitch = (int) loc.getPitch();
                    plugin.getConfig().set("spawn",world + "," + x + "," + y + "," + z + "," + yaw + "," + pitch);
                    plugin.saveConfig();
                    player.sendMessage("spawnを登録しました");
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("reload")){
                if (sender.hasPermission("Onsen.permission.admin")) {
                    plugin.reloadConfig();
                    plugin.getLogger().info("configリロードしました");
                    sender.sendMessage("[server] OnsenPL configリロードしました");
                }
                return true;
            }

            if(args[0].equalsIgnoreCase("spawn")){
                String data = plugin.getConfig().getString(args[0]);
                if (data == null) return true;
                String[] loc = data.split(",");
                World world = Bukkit.getServer().getWorld(loc[0]);
                double x = Double.parseDouble(loc[1]);
                double y = Double.parseDouble(loc[2]);
                double z = Double.parseDouble(loc[3]);
                int yaw = (int) Double.parseDouble(loc[4]);
                int pitch = (int) Double.parseDouble(loc[5]);
                Location location = new Location(world, x, y, z);
                location.setPitch(pitch);
                location.setYaw(yaw);
                player.teleport(location);
                player.sendMessage(ChatColor.GOLD + "温泉街へ移動しました" + ChatColor.AQUA + "^w^");
                return true;
            }

            if(args[0].equalsIgnoreCase("request")){
                if (args.length <= 1) {
                    return true;
                }
                if(args[1].equalsIgnoreCase(args[1])){
                    ItemStack itempack = player.getInventory().getItemInMainHand();
                    ItemMeta itemMeta = itempack.getItemMeta();
                    String itemdisplayname = itemMeta.getDisplayName();
                    if(itemdisplayname.equals((ChatColor.translateAlternateColorCodes('&',"&5温泉リクエストチケット")))) {
                        World world = player.getWorld();
                        String worldname = world.getName();
                        String displayname = player.getName();
                        Location loc = player.getLocation();
                        int x = loc.getBlockX();
                        int y = loc.getBlockY();
                        int z = loc.getBlockZ();
                        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a温泉リクエストチケット"));
                        List<String> lore = new ArrayList<String>();
                        lore.add(ChatColor.translateAlternateColorCodes('&', "&dプレイヤー名&f:" + displayname));
                        lore.add(ChatColor.translateAlternateColorCodes('&', "&dワールド名&f:" + worldname));
                        lore.add(ChatColor.translateAlternateColorCodes('&', "&dX座標&f:" + x));
                        lore.add(ChatColor.translateAlternateColorCodes('&', "&dY座標&f:" + y));
                        lore.add(ChatColor.translateAlternateColorCodes('&', "&dZ座標&f:" + z));
                        lore.add(ChatColor.translateAlternateColorCodes('&',"&d温泉名&f:"+ args[1]));
                        itemMeta.setLore(lore);
                        itempack.setItemMeta(itemMeta);
                        player.sendMessage("リクエスト用として情報を記載しました");
                    }
                }
                return true;
            }
        }
        return true;
    }
}
