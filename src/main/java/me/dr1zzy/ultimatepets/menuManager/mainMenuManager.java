package me.dr1zzy.ultimatepets.menuManager;

import me.dr1zzy.ultimatepets.UltimatePets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.security.Permission;

public class mainMenuManager implements Listener {
    Inventory inv;

    FileConfiguration config = UltimatePets.getProvidingPlugin(UltimatePets.class).getConfig();

    File menus = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder() + "/menus");
    File mainMenu;
    FileConfiguration mainMenuConfig;

    @EventHandler
    public void onClick(InventoryClickEvent e){
        ConfigurationSection mainMenuC = config.getConfigurationSection("config").getConfigurationSection("menus").getConfigurationSection("main-menu");
        String title = mainMenuC.getString("title");
        String color = mainMenuC.getString("title-color");
        String permission = mainMenuC.getString("permission");

        Player p = (Player) e.getWhoClicked();
        String inventoryName = e.getView().getTitle();
        Material clickedItem = e.getCurrentItem().getType();

        if(inventoryName.equalsIgnoreCase("§" + color + title)){

            mainMenu = new File(menus, "main-menu.yml");
            mainMenuConfig = YamlConfiguration.loadConfiguration(mainMenu);

            ConfigurationSection shop = mainMenuConfig.getConfigurationSection("pets-shop-item");
            ConfigurationSection inventory = mainMenuConfig.getConfigurationSection("pets-inventory-item");
            ConfigurationSection minion = mainMenuConfig.getConfigurationSection("minion-item");

            String shopName = shop.getString("display-name");
            String inventoryItemName = inventory.getString("display-name");
            String minionName = minion.getString("display-name");

            ItemStack decoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            ItemMeta decorationMeta = decoration.getItemMeta();
            decorationMeta.setDisplayName(" ");
            decoration.setItemMeta(decorationMeta);

            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(shopName)){
                ConfigurationSection shopMenuC = config.getConfigurationSection("config").getConfigurationSection("menus").getConfigurationSection("shop-menu");
                String shopTitle = shopMenuC.getString("title");
                String shopColor = shopMenuC.getString("title-color");
                int shopSize = shopMenuC.getInt("size");

                e.setCancelled(true);
                Inventory shopInv = Bukkit.createInventory(p, shopSize, "§" + shopColor + shopTitle);
                    p.openInventory(shopInv);

            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(inventoryItemName)){
                ConfigurationSection inventoryMenuC = config.getConfigurationSection("config").getConfigurationSection("menus").getConfigurationSection("inventory-menu");
                String inventoryTitle = inventoryMenuC.getString("title");
                String inventoryColor = inventoryMenuC.getString("title-color");
                int inventorySize = inventoryMenuC.getInt("size");

                e.setCancelled(true);
                Inventory inventoryInv = Bukkit.createInventory(p, inventorySize, "§" + inventoryColor + inventoryTitle);
                    p.openInventory(inventoryInv);


            }

            if (e.getCurrentItem().equals(decoration)){
                e.setCancelled(true);
            }
        }
    }

}
