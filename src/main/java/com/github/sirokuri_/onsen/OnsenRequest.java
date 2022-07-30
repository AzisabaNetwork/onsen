package com.github.sirokuri_.onsen;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OnsenRequest implements Listener {

    private final Onsen plugin;

    public OnsenRequest(Onsen onsen) {
        this.plugin = onsen;
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e){
        Player player = e.getPlayer();
        ItemStack itempack = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itempack.getItemMeta();
        if(itemMeta == null) return;
        String itemdisplayname = itemMeta.getDisplayName();
        if(itemdisplayname.equals((ChatColor.translateAlternateColorCodes('&',"&a温泉リクエストチケット")))) {
            Block clickedBlock = e.getBlock();
            if (clickedBlock.getType() == Material.OAK_WALL_SIGN) {
                Sign sign = (Sign) clickedBlock.getState();
                String line = sign.getLine(0);
                if(line.equals("温泉リクエスト")){
                    Block block = e.getBlock();
                    for (BlockFace face : new BlockFace[] {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST,}) {
                        Block relative = block.getRelative(face);
                        if (relative.getType() == Material.CHEST) {
                            Chest chest = (Chest) relative.getState();
                            Inventory inv = chest.getInventory();
                            int emptySlot = inv.firstEmpty();
                            if (emptySlot == -1) {
                                player.sendMessage("チェストが満タンです。運営に報告してください");
                                return;
                            }else{
                                inv.addItem(itempack);
                                itempack.setAmount(0);
                                player.sendMessage("リクエスト用紙を申請しました");
                            }
                        }
                    }
                }
            }
        }
    }
}
