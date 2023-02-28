package me.dr1zzy.ultimatepets;

import me.dr1zzy.ultimatepets.commands.*;
import me.dr1zzy.ultimatepets.eventsManager.firstJoinEvent;
import me.dr1zzy.ultimatepets.eventsManager.killEventTest;
import me.dr1zzy.ultimatepets.eventsManager.minionManager;
import me.dr1zzy.ultimatepets.menuManager.inventoryMenuManager;
import me.dr1zzy.ultimatepets.menuManager.mainMenuManager;
import me.dr1zzy.ultimatepets.menuManager.shopMenuManager;
import me.dr1zzy.ultimatepets.menuRefactor.inventoryMenu;
import me.dr1zzy.ultimatepets.menuRefactor.mainMenu;
import me.dr1zzy.ultimatepets.menuRefactor.shopMenu;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class UltimatePets extends JavaPlugin {

    //RESOURCES
    File defaultPlayerConfig;
    FileConfiguration  defaultPlayerConfigGet;

    ArrayList<Player> playersWithMin = new ArrayList<Player>();
    //CONFIG
    FileConfiguration config = getConfig();
    //playersData
    File playersData = new File(getDataFolder() + "/playersData");
    //playerFirstJoinData
    File playerDataYml;
    FileConfiguration playerDataYmlConfig;

    File menus = new File(getDataFolder() + "/menus");
    //playerFirstJoinData
    File menusYml;
    FileConfiguration menusYmlConfig;
    File shopMenu;
    FileConfiguration shopMenuConfig;

    File mainMenu;
    FileConfiguration mainMenuConfig;
    File inventoryMenu;
    FileConfiguration inventoryMenuConfig;

    InputStream defaultShopMenuConfig;

    //playerFirstJoinData
    private File pets;
    private FileConfiguration petsConfig;


    //stars
    HashMap<String, Double> stars = new HashMap<String, Double>();

    @Override
    public void onEnable() {

        saveDefaultConfig();
        saveResource("pets.yml", true);

        //PlayersDataFolder
        if(!playersData.exists()){
            playersData.mkdir();
        }
        if(!menus.exists()){
            menus.mkdir();

            shopMenu = new File(menus, "shop-menu.yml");
            shopMenuConfig = YamlConfiguration.loadConfiguration(shopMenu);

            mainMenu = new File(menus, "main-menu.yml");
            mainMenuConfig = YamlConfiguration.loadConfiguration(mainMenu);

            inventoryMenu = new File(menus, "inventory-menu.yml");
            inventoryMenuConfig = YamlConfiguration.loadConfiguration(inventoryMenu);

            if(!inventoryMenu.exists()){
                try {
                    inventoryMenuConfig.set("dirt-slot", 11);
                    inventoryMenuConfig.set("stone-slot", 12);
                    inventoryMenuConfig.set("iron-slot", 13);
                    inventoryMenuConfig.set("gold-slot", 14);
                    inventoryMenuConfig.set("diamond-slot", 15);
                    inventoryMenuConfig.save(inventoryMenu);
                }catch (IOException exception){
                    exception.printStackTrace();;
                }
            }

            if(!shopMenu.exists()){
                try{
                    List<String> dirtPetLore = new ArrayList<String>();
                    dirtPetLore.add("");
                    dirtPetLore.add("§8This is the dirt pet");
                    dirtPetLore.add("§8he can give you some potion");
                    dirtPetLore.add("§8effects.");
                    dirtPetLore.add("");
                    dirtPetLore.add("§6Price: 100");

                    List<String> stonePetLore = new ArrayList<>();
                    stonePetLore.add("");
                    stonePetLore.add("§8This is the stone pet");
                    stonePetLore.add("§8he can help you to mine.");
                    stonePetLore.add("");
                    stonePetLore.add("§6Price: 200");

                    List<String> ironPetLore = new ArrayList<>();
                    ironPetLore.add("");
                    ironPetLore.add("§8This is the iron pet");
                    ironPetLore.add("§8he can give you strength");
                    ironPetLore.add("§8when you are fighting.");
                    ironPetLore.add("");
                    ironPetLore.add("§6Price: 300");

                    List<String> goldPetLore = new ArrayList<>();
                    goldPetLore.add("");
                    goldPetLore.add("§8This is the gold pet");
                    goldPetLore.add("§8he give your more hearts.");
                    goldPetLore.add("");
                    goldPetLore.add("§6Price: 600");

                    List<String> diamondPetLore = new ArrayList<>();
                    diamondPetLore.add("");
                    diamondPetLore.add("§8This is the gold pet");
                    diamondPetLore.add("§8he give your some powerful");
                    diamondPetLore.add("§8potion effects.");
                    diamondPetLore.add("");
                    diamondPetLore.add("§6Price: 1000");

                    shopMenuConfig.createSection("dirt-pet");
                    ConfigurationSection dirtPet = shopMenuConfig.getConfigurationSection("dirt-pet");
                    dirtPet.set("visible", true);
                    dirtPet.set("buyable", true);
                    dirtPet.set("price", 100);
                    String dirtName = "§cDirt pet";
                    dirtPet.set("display-name", dirtName);
                    dirtPet.set("slot", 0);
                    dirtPet.set("lore", dirtPetLore);

                    shopMenuConfig.createSection("stone-pet");
                    ConfigurationSection stonePet = shopMenuConfig.getConfigurationSection("stone-pet");
                    stonePet.set("visible", true);
                    stonePet.set("buyable", true);
                    stonePet.set("price", 200);
                    String stoneName = "§8Stone Pet";
                    stonePet.set("display-name", stoneName);
                    stonePet.set("slot", 1);
                    stonePet.set("lore", stonePetLore);

                    shopMenuConfig.createSection("iron-pet");
                    ConfigurationSection ironPet = shopMenuConfig.getConfigurationSection("iron-pet");
                    ironPet.set("visible", true);
                    ironPet.set("buyable", true);
                    ironPet.set("price", 300);
                    String ironName = "§fIron pet";
                    ironPet.set("display-name", ironName);
                    ironPet.set("slot", 2);
                    ironPet.set("lore", ironPetLore);

                    shopMenuConfig.createSection("gold-pet");
                    ConfigurationSection goldPet = shopMenuConfig.getConfigurationSection("gold-pet");
                    goldPet.set("visible", true);
                    goldPet.set("buyable", true);
                    goldPet.set("price", 600);
                    String goldName = "§6Gold pet";
                    goldPet.set("display-name", goldName);
                    goldPet.set("slot", 3);
                    goldPet.set("lore", goldPetLore);

                    shopMenuConfig.createSection("diamond-pet");
                    ConfigurationSection diamondPet = shopMenuConfig.getConfigurationSection("diamond-pet");
                    diamondPet.set("visible", true);
                    diamondPet.set("buyable", true);
                    diamondPet.set("price", 1000);
                    String diamondName = "§bDiamond Pet";
                    diamondPet.set("display-name", diamondName);
                    diamondPet.set("slot", 4);
                    diamondPet.set("lore", diamondPetLore);

                    shopMenuConfig.save(shopMenu);

                }catch (IOException exception){
                    exception.printStackTrace();
                }
            }

            if(!mainMenu.exists()){
                try{
                    List petsLore = new ArrayList<String>();
                    petsLore.add("");
                    petsLore.add("§8Buy some special pets");
                    petsLore.add("§8for stars.");

                    List petsInvLore = new ArrayList<String>();
                    petsInvLore.add("");
                    petsInvLore.add("§8Check your owned pets");
                    petsInvLore.add("§8and equip them.");

                    mainMenuConfig.createSection("pets-shop-item");
                    ConfigurationSection pets = mainMenuConfig.getConfigurationSection("pets-shop-item");
                    pets.set("visible", true);
                    pets.set("clickable", true);
                    ItemStack shopMaterial = new ItemStack(Material.NETHER_STAR);
                    pets.set("material", shopMaterial);
                    pets.set("slot", 13);
                    String petsName = "§cPets Shop";
                    pets.set("display-name", petsName);
                    pets.set("lore", petsLore);

                    mainMenuConfig.createSection("pets-inventory-item");
                    ConfigurationSection petsInv = mainMenuConfig.getConfigurationSection("pets-inventory-item");
                    petsInv.set("visible", true);
                    petsInv.set("clickable", true);
                    ItemStack inventoryMaterial = new ItemStack(Material.CHEST);
                    petsInv.set("material", inventoryMaterial);
                    petsInv.set("slot", 15);
                    String petsInvName = "§6Pets Inventory";
                    petsInv.set("display-name", petsInvName);
                    petsInv.set("lore", petsInvLore);

                    mainMenuConfig.save(mainMenu);

                }catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        }


        //commands
        getCommand("pets").setExecutor(new petsCmd());
        getCommand("stars").setExecutor(new starsCmd());

        //Events
        getServer().getPluginManager().registerEvents(new minionManager(), this);
        getServer().getPluginManager().registerEvents(new killEventTest(), this);
        getServer().getPluginManager().registerEvents(new firstJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new mainMenu(), this);
        getServer().getPluginManager().registerEvents(new mainMenuManager(), this);
        getServer().getPluginManager().registerEvents(new shopMenuManager(), this);
        getServer().getPluginManager().registerEvents(new shopMenu(), this);
        getServer().getPluginManager().registerEvents(new inventoryMenu(), this);
        getServer().getPluginManager().registerEvents(new inventoryMenuManager(),this);

    }

}
