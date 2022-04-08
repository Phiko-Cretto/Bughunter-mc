package me.phiko.bughunter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Material.*;

public class Main extends JavaPlugin {

    FileConfiguration config = getConfig();
    private static String ti = "Text.Item.";

    @Override
    public void onEnable() {
        loadConfig();
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            //Send console a message sender.sendMessage();
            return true;
        }
        Player player = (Player) sender;
        String cn = "commandName.";
        String t = "Text.";

        if (player.hasPermission("CubeAwesome.bughunter")) {
            if (args.length > 0) {
                Player rewardPlayer = Bukkit.getPlayer(args[0]);
                if (org.bukkit.Bukkit.getPlayerExact(args[0]) != null) {
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase(config.getString(cn + "Small"))) {
                            giveItem(rewardPlayer, player, config.getString(t + "smallItemName"), STONE_SWORD);
                        }else if (args[1].equalsIgnoreCase(config.getString(cn + "Medium"))) {
                            giveItem(rewardPlayer, player, config.getString(t + "mediumItemName"), IRON_SWORD);
                        }else if  (args[1].equalsIgnoreCase(config.getString(cn + "Big"))) {
                            giveItem(rewardPlayer, player, config.getString(t + "bigItemName"), DIAMOND_SWORD);
                        }else {
                            player.sendMessage(Utils.chat(config.getString(t + "nochoose")));
                        }
                    } else {
                        player.sendMessage(Utils.chat(config.getString(t + "choose")));
                    }
                } else {
                    player.sendMessage(Utils.chat(config.getString(t + "notExistPlayer")));
                }
            } else {
                player.sendMessage(Utils.chat(config.getString(t + "noPlayer")));
            }
        }
        return false;
    }
    public void giveItem(Player p, Player sender, String dName, Material material) {
        ItemStack Item = new ItemStack(material);
        Item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemMeta itemMeta = Item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat(dName));
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(Utils.chat(config.getString(ti + "Lore1")));
        lore.add(Utils.chat(config.getString(ti + "Lore2") + sender.getName()));
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        Item.setItemMeta(itemMeta);
        p.getInventory().addItem(Item);
    }
}
