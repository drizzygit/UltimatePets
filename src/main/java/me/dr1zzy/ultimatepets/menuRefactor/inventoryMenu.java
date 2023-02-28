package me.dr1zzy.ultimatepets.menuRefactor;

import me.dr1zzy.ultimatepets.UltimatePets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class inventoryMenu implements Listener {

    File menus = new File(UltimatePets.getProvidingPlugin(UltimatePets.class).getDataFolder() + "/menus");
    File inventoryMenu;
    FileConfiguration inventoryMenuConfig;

    File pets;
    FileConfiguration petsYml;
    FileConfiguration config = UltimatePets.getProvidingPlugin(UltimatePets.class).getConfig();
    File playersData = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder() + "/playersData");
    //playerFirstJoinData
    File playerDataYml;
    FileConfiguration playerDataYmlConfig;

    @EventHandler
    public void onOpen(InventoryOpenEvent e){
        HumanEntity p = e.getPlayer();

        ConfigurationSection inventoryMenuC = config.getConfigurationSection("config").getConfigurationSection("menus").getConfigurationSection("inventory-menu");
        String shopTitle = inventoryMenuC.getString("title");
        String shopColor = inventoryMenuC.getString("title-color");
        String shopPermission = inventoryMenuC.getString("permission");

        Inventory inv = e.getInventory();

        if(e.getView().getTitle().equalsIgnoreCase("§" + shopColor + shopTitle)){
            playerDataYml = new File(playersData, p.getUniqueId() + ".yml");
            playerDataYmlConfig = YamlConfiguration.loadConfiguration(playerDataYml);

            inventoryMenu = new File(menus, "inventory-menu.yml");
            inventoryMenuConfig = YamlConfiguration.loadConfiguration(inventoryMenu);

            pets = new File(UltimatePets.getProvidingPlugin(UltimatePets.class).getDataFolder(), "pets.yml");
            petsYml = YamlConfiguration.loadConfiguration(pets);

            if(playerDataYml.exists()){
                ConfigurationSection buyedPets = playerDataYmlConfig.getConfigurationSection("buyed-pets");
                int dirtPetStatus = buyedPets.getInt("dirt-pet");
                int stonePetStatus = buyedPets.getInt("stone-pet");
                int ironPetStatus = buyedPets.getInt("iron-pet");
                int goldPetStatus = buyedPets.getInt("gold-pet");
                int diamondPetStatus = buyedPets.getInt("diamond-pet");

                ItemStack blocked = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
                ItemMeta blockedMeta = blocked.getItemMeta();
                blockedMeta.setDisplayName(ChatColor.RED + "" + ChatColor.UNDERLINE + "BLOQUEADO");
                List<String> blockedLore = new ArrayList<>();
                blockedLore.add("");
                blockedLore.add(ChatColor.RED + "Compra esta mascota en la tienda!");
                blockedMeta.setLore(blockedLore);
                blocked.setItemMeta(blockedMeta);


                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.GRAY + "(CLICK PARA EQUIPAR)");

                //DIRT PET
                ConfigurationSection dirtSection = petsYml.getConfigurationSection("dirt-pet");
                String dirtColor = dirtSection.getString("nameColor");
                String dirtDisplayName = dirtSection.getString("display-name");
                int dirtSlot = inventoryMenuConfig.getInt("dirt-slot");

                ItemStack dirt = new ItemStack(Material.DIRT);
                ItemMeta dirtMeta = dirt.getItemMeta();
                dirtMeta.setDisplayName("§" + dirtColor + dirtDisplayName);
                dirtMeta.setLore(lore);
                dirt.setItemMeta(dirtMeta);


                //STONE PET
                ConfigurationSection stoneSection = petsYml.getConfigurationSection("stone-pet");
                String stoneColor = stoneSection.getString("nameColor");
                String stoneDisplayName = stoneSection.getString("display-name");
                int stoneSlot = inventoryMenuConfig.getInt("stone-slot");

                ItemStack stone = new ItemStack(Material.COBBLESTONE);
                ItemMeta stoneMeta = stone.getItemMeta();
                stoneMeta.setDisplayName("§" + stoneColor + stoneDisplayName);
                stoneMeta.setLore(lore);
                stone.setItemMeta(stoneMeta);

                //IRON PET
                ConfigurationSection ironSection = petsYml.getConfigurationSection("iron-pet");
                String ironColor = ironSection.getString("nameColor");
                String ironDisplayName = ironSection.getString("display-name");
                int ironSlot = inventoryMenuConfig.getInt("iron-slot");

                ItemStack iron = new ItemStack(Material.IRON_BLOCK);
                ItemMeta ironMeta = iron.getItemMeta();
                ironMeta.setDisplayName("§" + ironColor + ironDisplayName);
                ironMeta.setLore(lore);
                iron.setItemMeta(ironMeta);

                //GOLD PET
                ConfigurationSection goldSection = petsYml.getConfigurationSection("gold-pet");
                String goldColor = goldSection.getString("nameColor");
                String goldDisplayName = goldSection.getString("display-name");
                int goldSlot = inventoryMenuConfig.getInt("gold-slot");

                ItemStack gold = new ItemStack(Material.GOLD_BLOCK);
                ItemMeta goldMeta = gold.getItemMeta();
                goldMeta.setDisplayName("§" + goldColor + goldDisplayName);
                goldMeta.setLore(lore);
                gold.setItemMeta(goldMeta);

                //DIAMOND PET
                ConfigurationSection diamondSection = petsYml.getConfigurationSection("diamond-pet");
                String diamondColor = diamondSection.getString("nameColor");
                String diamondDisplayName = diamondSection.getString("display-name");
                int diamondSlot = inventoryMenuConfig.getInt("diamond-slot");

                ItemStack diamond = new ItemStack(Material.DIAMOND_BLOCK);
                ItemMeta diamondMeta = diamond.getItemMeta();
                diamondMeta.setDisplayName("§" + diamondColor + diamondDisplayName);
                diamondMeta.setLore(lore);
                diamond.setItemMeta(diamondMeta);

                //DECORATION
                ItemStack decoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
                ItemMeta decorationMeta = decoration.getItemMeta();
                decorationMeta.setDisplayName(" ");
                decoration.setItemMeta(decorationMeta);

                ItemStack disable = new ItemStack(Material.BARRIER);
                ItemMeta disableMeta = disable.getItemMeta();
                disableMeta.setDisplayName(ChatColor.RED + "DESACTIVAR MASCOTA");
                disable.setItemMeta(disableMeta);

                inv.setItem(22, disable);
                if(dirtPetStatus == 1){
                    inv.setItem(dirtSlot, dirt);
                }else if (dirtPetStatus < 1){
                    inv.setItem(dirtSlot, blocked);
                }
                if(stonePetStatus == 1){
                    inv.setItem(stoneSlot, stone);
                } else if (stonePetStatus < 1) {
                    inv.setItem(stoneSlot, blocked);
                }
                if(ironPetStatus == 1){
                    inv.setItem(ironSlot, iron);
                } else if (ironPetStatus < 1) {
                    inv.setItem(ironSlot, blocked);
                }
                if(goldPetStatus == 1){
                    inv.setItem(goldSlot, gold);
                } else if (goldPetStatus < 1) {
                    inv.setItem(goldSlot, blocked);
                }
                if(diamondPetStatus == 1){
                    inv.setItem(diamondSlot, diamond);
                } else if (diamondPetStatus < 1) {
                    inv.setItem(diamondSlot, blocked);
                }
                for (int i = 0; i < inv.getSize(); i++) {
                    if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                        inv.setItem(i, decoration);
                    }
                }
            }else {

            }
        }
    }

}
