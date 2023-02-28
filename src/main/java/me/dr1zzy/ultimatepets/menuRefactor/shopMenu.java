package me.dr1zzy.ultimatepets.menuRefactor;

import me.dr1zzy.ultimatepets.UltimatePets;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;

public class shopMenu implements Listener {

    FileConfiguration config = UltimatePets.getProvidingPlugin(UltimatePets.class).getConfig();

    File shopMenuFile = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder() + "/menus");
    File shopMenu;
    FileConfiguration shopMenuYml;


    private File pets;
    private FileConfiguration petsConfig;

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        ConfigurationSection shopMenuC = config.getConfigurationSection("config").getConfigurationSection("menus").getConfigurationSection("shop-menu");
        String shopTitle = shopMenuC.getString("title");
        String shopColor = shopMenuC.getString("title-color");
        String shopPermission = shopMenuC.getString("permission");
        int shopSize = shopMenuC.getInt("size");

        Inventory inv = e.getInventory();
        String invTitle = inv.getTitle();

        shopMenu = new File(shopMenuFile, "shop-menu.yml");
        shopMenuYml = YamlConfiguration.loadConfiguration(shopMenu);

        if(invTitle.equalsIgnoreCase("§" + shopColor + shopTitle)){
            //dirt pet
            ConfigurationSection dirtPetSection = shopMenuYml.getConfigurationSection("dirt-pet");
            Boolean dirtVisibleStatus = dirtPetSection.getBoolean("visible");
            Boolean dirtBuyableStatus = dirtPetSection.getBoolean("buyable");
            Integer dirtPrice = dirtPetSection.getInt("price");
            String dirtDisplayName = dirtPetSection.getString("display-name");
            int dirtSlot = dirtPetSection.getInt("slot");
            List<String> dirtLore = dirtPetSection.getStringList("lore");
            //stone pet
            ConfigurationSection stonePetSection = shopMenuYml.getConfigurationSection("stone-pet");
            Boolean stoneVisibleStatus = stonePetSection.getBoolean("visible");
            Boolean stoneBuyableStatus = stonePetSection.getBoolean("buyable");
            Integer stonePrice = stonePetSection.getInt("price");
            String stoneDisplayName = stonePetSection.getString("display-name");
            int stoneSlot = stonePetSection.getInt("slot");
            List<String> stoneLore = stonePetSection.getStringList("lore");
            //iron pet
            ConfigurationSection ironPetSection = shopMenuYml.getConfigurationSection("iron-pet");
            Boolean ironVisibleStatus = ironPetSection.getBoolean("visible");
            Boolean ironBuyableStatus = ironPetSection.getBoolean("buyable");
            Integer ironPrice = ironPetSection.getInt("price");
            String ironDisplayName = ironPetSection.getString("display-name");
            int ironSlot = ironPetSection.getInt("slot");
            List<String> ironLore = ironPetSection.getStringList("lore");
            //gold pet
            ConfigurationSection goldPetSection = shopMenuYml.getConfigurationSection("gold-pet");
            Boolean goldVisibleStatus = goldPetSection.getBoolean("visible");
            Boolean goldBuyableStatus = goldPetSection.getBoolean("buyable");
            Integer goldPrice = goldPetSection.getInt("price");
            String goldDisplayName = goldPetSection.getString("display-name");
            int goldSlot = goldPetSection.getInt("slot");
            List<String> goldLore = goldPetSection.getStringList("lore");
            //diamond pet
            ConfigurationSection diamondPetSection = shopMenuYml.getConfigurationSection("diamond-pet");
            Boolean diamondVisibleStatus = diamondPetSection.getBoolean("visible");
            Boolean diamondBuyableStatus = diamondPetSection.getBoolean("buyable");
            Integer diamondPrice = diamondPetSection.getInt("price");
            String diamondDisplayName = diamondPetSection.getString("display-name");
            int diamondSlot = diamondPetSection.getInt("slot");
            List<String> diamondLore = diamondPetSection.getStringList("lore");

            //dirtItemStack
            ItemStack dirtItem = new ItemStack(Material.DIRT);
            ItemMeta dirtItemMeta = dirtItem.getItemMeta();
            dirtItemMeta.setDisplayName(dirtDisplayName);
            dirtItemMeta.setLore(dirtLore);
            dirtItem.setItemMeta(dirtItemMeta);
            //stoneItemStack
            ItemStack stoneItem = new ItemStack(Material.COBBLESTONE);
            ItemMeta stoneItemMeta = stoneItem.getItemMeta();
            stoneItemMeta.setDisplayName(stoneDisplayName);
            stoneItemMeta.setLore(stoneLore);
            stoneItem.setItemMeta(stoneItemMeta);
            //ironItemStack
            ItemStack ironItem = new ItemStack(Material.IRON_BLOCK);
            ItemMeta ironItemMeta = ironItem.getItemMeta();
            ironItemMeta.setDisplayName(ironDisplayName);
            ironItemMeta.setLore(ironLore);
            ironItem.setItemMeta(ironItemMeta);
            //goldItemStack
            ItemStack goldItem = new ItemStack(Material.GOLD_BLOCK);
            ItemMeta goldItemMeta = goldItem.getItemMeta();
            goldItemMeta.setDisplayName(goldDisplayName);
            goldItemMeta.setLore(goldLore);
            goldItem.setItemMeta(goldItemMeta);
            //diamondItemStack
            ItemStack diamondItem = new ItemStack(Material.DIAMOND_BLOCK);
            ItemMeta diamondItemMeta = diamondItem.getItemMeta();
            diamondItemMeta.setDisplayName(diamondDisplayName);
            diamondItemMeta.setLore(diamondLore);
            diamondItem.setItemMeta(diamondItemMeta);
            //decorationItemStack
            ItemStack decoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            ItemMeta decorationMeta = decoration.getItemMeta();
            decorationMeta.setDisplayName(" ");
            decoration.setItemMeta(decorationMeta);

            if(dirtVisibleStatus.equals(true)){
                //dirt
                inv.setItem(dirtSlot, dirtItem);
            }
            if(stoneVisibleStatus.equals(true)){
                //stone
                inv.setItem(stoneSlot, stoneItem);
            }
            if(ironVisibleStatus.equals(true)){
                //iron
                inv.setItem(ironSlot, ironItem);

            }
            if (goldVisibleStatus.equals(true)){
                //gold
                inv.setItem(goldSlot, goldItem);
            }
            if(diamondVisibleStatus.equals(true)){
                //diamond
                inv.setItem(diamondSlot, diamondItem);
            }
            for (int i = 0; i < inv.getSize(); i++) {
                if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                    inv.setItem(i, decoration);
                }
            }


        }

    }
}
