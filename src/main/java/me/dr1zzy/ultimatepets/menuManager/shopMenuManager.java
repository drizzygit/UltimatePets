package me.dr1zzy.ultimatepets.menuManager;

import me.dr1zzy.ultimatepets.UltimatePets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;

public class shopMenuManager implements Listener {

    FileConfiguration config = UltimatePets.getProvidingPlugin(UltimatePets.class).getConfig();

    File menus = new File(UltimatePets.getProvidingPlugin(UltimatePets.class).getDataFolder() + "/menus");

    File shopMenu;
    FileConfiguration shopMenuConfig;


    File playersData = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder() + "/playersData");
    //playerFirstJoinData
    File playerDataYml;
    FileConfiguration playerDataYmlConfig;

    Boolean havePet;
    String lastPet;

    @EventHandler
    public void onClick(InventoryClickEvent e){

        ConfigurationSection shopMenuC = config.getConfigurationSection("config").getConfigurationSection("menus").getConfigurationSection("shop-menu");
        String shopTitle = shopMenuC.getString("title");
        String shopColor = shopMenuC.getString("title-color");
        String shopPermission = shopMenuC.getString("permission");
        int shopSize = shopMenuC.getInt("size");

        String inventoryName = e.getView().getTitle();
        if(inventoryName.equalsIgnoreCase("§" + shopColor + shopTitle)){
            Player p = (Player) e.getWhoClicked();

            playerDataYml = new File(playersData, p.getUniqueId() + ".yml");
            playerDataYmlConfig = YamlConfiguration.loadConfiguration(playerDataYml);

            shopMenu = new File(menus, "shop-menu.yml");
            shopMenuConfig = YamlConfiguration.loadConfiguration(shopMenu);

            //dirt
            ConfigurationSection dirtPet = shopMenuConfig.getConfigurationSection("dirt-pet");
            Boolean dirtBuyableStatus = dirtPet.getBoolean("buyable");
            int dirtPrice = dirtPet.getInt("price");
            String dirtName = dirtPet.getString("display-name");

            //stone
            ConfigurationSection stonePet = shopMenuConfig.getConfigurationSection("stone-pet");
            Boolean stoneBuyableStatus = stonePet.getBoolean("buyable");
            int stonePrice = stonePet.getInt("price");
            String stoneName = stonePet.getString("display-name");

            //iron
            ConfigurationSection ironPet = shopMenuConfig.getConfigurationSection("iron-pet");
            Boolean ironBuyableStatus = ironPet.getBoolean("buyable");
            int ironPrice = ironPet.getInt("price");
            String ironName = ironPet.getString("display-name");

            //gold
            ConfigurationSection goldPet = shopMenuConfig.getConfigurationSection("gold-pet");
            Boolean goldBuyableStatus = goldPet.getBoolean("buyable");
            int goldPrice = goldPet.getInt("price");
            String goldName = goldPet.getString("display-name");

            //diamond
            ConfigurationSection diamondPet = shopMenuConfig.getConfigurationSection("diamond-pet");
            Boolean diamondBuyableStatus = diamondPet.getBoolean("buyable");
            int diamondPrice = diamondPet.getInt("price");
            String diamondName = diamondPet.getString("display-name");

            ItemStack decoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            ItemMeta decorationMeta = decoration.getItemMeta();
            decorationMeta.setDisplayName(" ");
            decoration.setItemMeta(decorationMeta);

            String currentItemName = e.getCurrentItem().getItemMeta().getDisplayName();
            ConfigurationSection buyedPets = playerDataYmlConfig.getConfigurationSection("buyed-pets");
            int dirtPetStatus = buyedPets.getInt("dirt-pet");
            int stonePetStatus = buyedPets.getInt("stone-pet");
            int ironPetStatus = buyedPets.getInt("iron-pet");
            int goldPetStatus = buyedPets.getInt("gold-pet");
            int diamondPetStatus = buyedPets.getInt("diamond-pet");

            ConfigurationSection dInfo = playerDataYmlConfig.getConfigurationSection("default-info");

            if(currentItemName.equalsIgnoreCase(dirtName)){
                if(dirtBuyableStatus.equals(true)){
                    if(dirtPetStatus == 0){
                        Double currentStars = (Double) playerDataYmlConfig.getConfigurationSection("default-info").getDouble("stars");
                        if(currentStars >= dirtPrice){
                            try {
                                Double finalStars = currentStars-dirtPrice;
                                buyedPets.set("dirt-pet", 1);
                                dInfo.set("stars", finalStars);
                                playerDataYmlConfig.save(playerDataYml);
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.GREEN + "Has comprado a DIRT por la cantidad de: " + ChatColor.GOLD + "$" + dirtPrice);
                            }catch (IOException exception){
                                exception.printStackTrace();
                            }
                        } else if (currentStars < dirtPrice) {
                            e.setCancelled(true);
                            p.sendMessage(ChatColor.RED + "No tienes las estrellas suficientes!");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Esta mascota ya esta en tu inventario!");
                        e.setCancelled(true);
                    }
                }else {
                    p.sendMessage(ChatColor.RED + "Por el momento esta mascota no se puede comprar.");
                    e.setCancelled(true);
                }
            }
            if(currentItemName.equalsIgnoreCase(stoneName)){
                if(stoneBuyableStatus.equals(true)){
                    if (stonePetStatus == 0){
                        Double currentStars = (Double) playerDataYmlConfig.getConfigurationSection("default-info").getDouble("stars");
                        if(currentStars >= stonePrice){

                            try {
                                Double finalStars = currentStars-stonePrice;
                                buyedPets.set("stone-pet", 1);
                                dInfo.set("stars", finalStars);
                                playerDataYmlConfig.save(playerDataYml);
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.GREEN + "Has comprado a STONE por la cantidad de: " + ChatColor.GOLD + "$" + stonePrice);
                            }catch (IOException exception){
                                exception.printStackTrace();
                            }

                        }else if(currentStars < stonePrice){
                            e.setCancelled(true);
                            p.sendMessage(ChatColor.RED + "No tienes las estrellas suficientes!");
                        }
                    }else {
                        p.sendMessage(ChatColor.RED + "Esta mascota ya esta en tu inventario!");
                        e.setCancelled(true);
                    }
                }else {
                    p.sendMessage(ChatColor.RED + "Por el momento esta mascota no se puede comprar.");
                    e.setCancelled(true);
                }
            }
            if(currentItemName.equalsIgnoreCase(ironName)){
                if(ironBuyableStatus.equals(true)){
                    if (ironPetStatus == 0){
                        Double currentStars = (Double) playerDataYmlConfig.getConfigurationSection("default-info").getDouble("stars");
                        if(currentStars >= ironPrice){

                            try {
                                Double finalStars = currentStars-ironPrice;
                                buyedPets.set("iron-pet", 1);
                                dInfo.set("stars", finalStars);
                                playerDataYmlConfig.save(playerDataYml);
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.GREEN + "Has comprado a IRON por la cantidad de: " + ChatColor.GOLD + "$" + ironPrice);
                            }catch (IOException exception){
                                exception.printStackTrace();
                            }

                        }else if(currentStars < ironPrice){
                            e.setCancelled(true);
                            p.sendMessage(ChatColor.RED + "No tienes las estrellas suficientes!");
                        }
                    }else {
                        p.sendMessage(ChatColor.RED + "Esta mascota ya esta en tu inventario!");
                        e.setCancelled(true);
                    }
                }else {
                    p.sendMessage(ChatColor.RED + "Por el momento esta mascota no se puede comprar.");
                    e.setCancelled(true);
                }
            }
            if(currentItemName.equalsIgnoreCase(goldName)){
                if(goldBuyableStatus.equals(true)){
                    if (goldPetStatus == 0){
                        Double currentStars = (Double) playerDataYmlConfig.getConfigurationSection("default-info").getDouble("stars");
                        if(currentStars >= goldPrice){

                            try {
                                Double finalStars = currentStars-goldPrice;
                                buyedPets.set("gold-pet", 1);
                                dInfo.set("stars", finalStars);
                                playerDataYmlConfig.save(playerDataYml);
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.GREEN + "Has comprado a GOLD por la cantidad de: " + ChatColor.GOLD + "$" + goldPrice);
                            }catch (IOException exception){
                                exception.printStackTrace();
                            }

                        }else if(currentStars < goldPrice){
                            e.setCancelled(true);
                            p.sendMessage(ChatColor.RED + "No tienes las estrellas suficientes!");
                        }
                    }else {
                        p.sendMessage(ChatColor.RED + "Esta mascota ya esta en tu inventario!");
                        e.setCancelled(true);
                    }
                }else {
                    p.sendMessage(ChatColor.RED + "Por el momento esta mascota no se puede comprar.");
                    e.setCancelled(true);
                }
            }
            if(currentItemName.equalsIgnoreCase(diamondName)){
                if(diamondBuyableStatus.equals(true)){
                    if (diamondPetStatus == 0){
                        Double currentStars = (Double) playerDataYmlConfig.getConfigurationSection("default-info").getDouble("stars");
                        if(currentStars >= diamondPrice){

                            try {
                                Double finalStars = currentStars-diamondPrice;
                                buyedPets.set("diamond-pet", 1);
                                dInfo.set("stars", finalStars);
                                playerDataYmlConfig.save(playerDataYml);
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.GREEN + "Has comprado a DIAMOND por la cantidad de: " + ChatColor.GOLD + "$" + diamondPrice);
                            }catch (IOException exception){
                                exception.printStackTrace();
                            }

                        }else if(currentStars < diamondPrice){
                            e.setCancelled(true);
                            p.sendMessage(ChatColor.RED + "No tienes las estrellas suficientes!");
                        }
                    }else {
                        p.sendMessage(ChatColor.RED + "Esta mascota ya esta en tu inventario!");
                        e.setCancelled(true);
                    }
                }else {
                    p.sendMessage(ChatColor.RED + "Por el momento esta mascota no se puede comprar.");
                    e.setCancelled(true);
                }
            }
            if(e.getCurrentItem().equals(decoration)){
                e.setCancelled(true);
            }
        }
    }

}
