package me.dr1zzy.ultimatepets.menuRefactor;

import me.dr1zzy.ultimatepets.UltimatePets;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;

public class mainMenu implements Listener {

    FileConfiguration config = UltimatePets.getProvidingPlugin(UltimatePets.class).getConfig();

    File menus = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder() + "/menus");

    File mainMenu;
    FileConfiguration mainMenuConfig;
    ItemStack shop;
    ItemStack inventory;
    ItemStack minion;

    @EventHandler
    public void onOpen(InventoryOpenEvent e){

        ConfigurationSection mainMenuC = config.getConfigurationSection("config").getConfigurationSection("menus").getConfigurationSection("main-menu");
        String title = mainMenuC.getString("title");
        String color = mainMenuC.getString("title-color");
        String permission = mainMenuC.getString("permission");

        HumanEntity p = e.getPlayer();
        Inventory inv = e.getInventory();
        String inventoryName = e.getInventory().getTitle();

        mainMenu = new File(menus, "main-menu.yml");
        mainMenuConfig = YamlConfiguration.loadConfiguration(mainMenu);

        if(inventoryName.equalsIgnoreCase("§" + color + title)){
            ConfigurationSection shop = mainMenuConfig.getConfigurationSection("pets-shop-item");
            ConfigurationSection inventory = mainMenuConfig.getConfigurationSection("pets-inventory-item");
            ConfigurationSection minion = mainMenuConfig.getConfigurationSection("minion-item");

            Boolean shopVisibleStatus = shop.getBoolean("visible");
            Boolean shopClickableStatus = shop.getBoolean("visible");
            ItemStack shopMaterial = shop.getItemStack("material");
            int shopSlot = shop.getInt("slot");
            String shopDisplayName = shop.getString("display-name");
            List<String> shopLore = shop.getStringList("lore");

            Boolean inventoryVisibleStatus = inventory.getBoolean("visible");
            Boolean inventoryClickableStatus = inventory.getBoolean("visible");
            ItemStack inventoryMaterial = inventory.getItemStack("material");
            String inventoryDisplayName = inventory.getString("display-name");
            int inventorySlot = inventory.getInt("slot");
            List<String> inventoryLore = inventory.getStringList("lore");

            ItemStack shopItem = new ItemStack(shopMaterial);
            ItemMeta shopItemMeta = shopItem.getItemMeta();
            shopItemMeta.setDisplayName(shopDisplayName);
            shopItemMeta.setLore(shopLore);
            shopItem.setItemMeta(shopItemMeta);

            ItemStack inventoryItem = new ItemStack(inventoryMaterial);
            ItemMeta inventoryItemMeta = inventoryItem.getItemMeta();
            inventoryItemMeta.setDisplayName(inventoryDisplayName);
            inventoryItemMeta.setLore(inventoryLore);
            inventoryItem.setItemMeta(inventoryItemMeta);

            ItemStack decoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            ItemMeta decorationMeta = decoration.getItemMeta();
            decorationMeta.setDisplayName(" ");
            decoration.setItemMeta(decorationMeta);

            if(shopVisibleStatus.equals(true)){
                inv.setItem(shopSlot, shopItem);
            }
            if(inventoryVisibleStatus.equals(true)){
                inv.setItem(inventorySlot, inventoryItem);
            }
            for (int i = 0; i < inv.getSize(); i++) {
                if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                    inv.setItem(i, decoration);
                }
            }

        }
    }
}
