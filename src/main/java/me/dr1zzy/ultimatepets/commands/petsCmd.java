package me.dr1zzy.ultimatepets.commands;

import me.dr1zzy.ultimatepets.UltimatePets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class petsCmd implements CommandExecutor {

    FileConfiguration config = UltimatePets.getProvidingPlugin(UltimatePets.class).getConfig();

    //shopMenu
    File shopMenuFile = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder() + "/menus");
    File shopMenu;
    FileConfiguration shopMenuYml;

    private File pets;
    private FileConfiguration petsConfig;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length == 0){
            p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa el comando: /pets help");
        } else if (args[0].equalsIgnoreCase("menu")) {
            ConfigurationSection mainMenu = config.getConfigurationSection("config").getConfigurationSection("menus").getConfigurationSection("main-menu");
            String title = mainMenu.getString("title");
            String color = mainMenu.getString("title-color");
            int size = mainMenu.getInt("size");
                Inventory inv = Bukkit.createInventory(p, size, "§" + color + title);
                p.openInventory(inv);


        } else if (args[0].equalsIgnoreCase("create")) {
            if(args[1].length() == 0){
                p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa el comando: /pets help");
            }else {
                pets = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder(), "pets.yml");
                petsConfig = YamlConfiguration.loadConfiguration(pets);

                shopMenu = new File(shopMenuFile, "shop-menu.yml");
                shopMenuYml = YamlConfiguration.loadConfiguration(shopMenu);

                List petsList = petsConfig.getList("created-pets");
                if(petsList.contains(args[1])){
                    p.sendMessage(ChatColor.RED + "Esta mascota ya existe!");
                } else if (!petsList.contains(args[1])) {
                    if(!args[1].contains("§")){
                        try {
                            petsList.add(args[1]);
                            petsConfig.createSection(args[1]);
                            ConfigurationSection createdPetSection = petsConfig.getConfigurationSection(args[1]);
                            createdPetSection.set("status", true);
                            createdPetSection.set("nameColor", 1);
                            createdPetSection.set("display-name", args[1]);
                            createdPetSection.set("needs-permission", false);
                            createdPetSection.set("use-permission", "pets.use." + args[1]);
                            createdPetSection.set("prefix", "none");
                            createdPetSection.set("prefix-color", "c");
                            createdPetSection.set("suffix", "none");
                            createdPetSection.set("suffix-color", "c");

                            List lore = new ArrayList<>();
                            lore.add("default configuration");
                            lore.add("edit this in your shop-menu.yml");

                            ConfigurationSection newPetSection = shopMenuYml.createSection(args[1]);
                            ConfigurationSection Pet = shopMenuYml.getConfigurationSection(args[1]);
                            Pet.set("visible", true);
                            Pet.set("buyable", true);
                            Pet.set("price", 0);
                            Pet.set("display-name", args[1]);
                            Pet.set("material", "MATERIAL.GLASS");
                            Pet.set("slot", 0);
                            Pet.set("lore", lore);


                            petsConfig.save(pets);
                            shopMenuYml.save(shopMenu);
                            p.sendMessage(ChatColor.GREEN + "Has creado con exito la mascota " + ChatColor.GOLD + "'" + args[1] + "'" + ChatColor.GREEN + " editala en la configuracion.");
                        }catch (IOException exception){
                            exception.printStackTrace();
                        }
                    } else if (args[1].contains("§")) {
                        p.sendMessage(ChatColor.RED + "El nombre de la mascota no puede contener codigos de colores!");
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            UltimatePets.getProvidingPlugin(UltimatePets.class).reloadConfig();
            p.sendMessage(ChatColor.GREEN + "Plugin reloaded!");
        }

        return true;
    }
}
