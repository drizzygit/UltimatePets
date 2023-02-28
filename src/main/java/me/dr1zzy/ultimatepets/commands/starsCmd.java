package me.dr1zzy.ultimatepets.commands;

import me.dr1zzy.ultimatepets.UltimatePets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class starsCmd implements CommandExecutor {

    File playersData = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder() + "/playersData");
    //playerFirstJoinData
    File playerDataYml;
    FileConfiguration playerDataYmlConfig;
    String lastPet;
    Boolean havePet;

    Double amountToSet;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        List<String> functions = new ArrayList<>();
        functions.add("add");
        functions.add("take");
        functions.add("set");
        functions.add("clear");

        if(args.length == 0){
            p.sendMessage(" ");
            p.sendMessage(ChatColor.RED + "------------------------------------");
            p.sendMessage(ChatColor.YELLOW + "/stars add <player> <amount>");
            p.sendMessage(ChatColor.YELLOW + "/stars take <player> <amount>");
            p.sendMessage(ChatColor.YELLOW + "/stars set <player> <amount>");
            p.sendMessage(ChatColor.YELLOW + "/stars clear <player>");
            p.sendMessage(ChatColor.RED + "------------------------------------");
            p.sendMessage(" ");
        } else if (args[0].equalsIgnoreCase("add")) {
            if(args[1].length() == 0){
                p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa: /stars help");
            }else {
                if(args[2].length() == 0){
                    p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa: /stars help");
                }else {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    playerDataYml = new File(playersData, target.getUniqueId() + ".yml");
                    playerDataYmlConfig = YamlConfiguration.loadConfiguration(playerDataYml);
                    ConfigurationSection dInfo = playerDataYmlConfig.getConfigurationSection("default-info");
                    Boolean havePet = dInfo.getBoolean("havePet");
                    String  lasPet = dInfo.getString("lasPet");
                    Double currentAmount = dInfo.getDouble("stars");
                    Integer amount = Integer.parseInt(args[2]);
                    if(playerDataYml.exists()){
                        amountToSet = amount + currentAmount;
                        try {
                            dInfo.set("stars", amountToSet);
                            playerDataYmlConfig.save(playerDataYml);
                            p.sendMessage(ChatColor.GREEN + "Has agregado " + ChatColor.GOLD + "$" + amount + ChatColor.GRAY + " a " + ChatColor.GOLD + args[1]);
                        }catch (IOException exception){
                            exception.printStackTrace();
                        }
                    }else {
                        p.sendMessage(ChatColor.RED + "Este jugador no existe!");
                    }

                }
            }
        } else if (args[0].equalsIgnoreCase("take")) {
            if(args[1].length() == 0){
                p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa: /stars help");
            }else {
                if(args[2].length() == 0){
                    p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa: /stars help");
                }else {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    playerDataYml = new File(playersData, target.getUniqueId() + ".yml");
                    playerDataYmlConfig = YamlConfiguration.loadConfiguration(playerDataYml);
                    ConfigurationSection dInfo = playerDataYmlConfig.getConfigurationSection("default-info");
                    Boolean havePet = dInfo.getBoolean("havePet");
                    String  lasPet = dInfo.getString("lasPet");
                    Double currentAmount = dInfo.getDouble("stars");
                    Integer amount = Integer.parseInt(args[2]);
                    if(target.hasPlayedBefore()){
                        if(playerDataYml.exists()){
                            if (currentAmount < amount) {
                                amountToSet = 0.0;
                            } else {
                                amountToSet = currentAmount-amount;
                            }
                            try {
                                dInfo.set("stars", amountToSet);
                                playerDataYmlConfig.save(playerDataYml);
                                p.sendMessage(ChatColor.GREEN + "Le has quitado " + ChatColor.GOLD + "$" + amount + ChatColor.GRAY + " a " + ChatColor.GOLD + args[1]);
                            }catch (IOException exception){
                                exception.printStackTrace();
                            }
                        }
                    }else {
                        p.sendMessage(ChatColor.RED + "Jugador invalido!");
                    }

                }
            }
        } else if (args[0].equalsIgnoreCase("set")) {
            if(args[1].length() == 0){
                p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa: /stars help");
            }else {
                if(args[2].length() == 0){
                    p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa: /stars help");
                }else {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    playerDataYml = new File(playersData, target.getUniqueId() + ".yml");
                    playerDataYmlConfig = YamlConfiguration.loadConfiguration(playerDataYml);
                    ConfigurationSection dInfo = playerDataYmlConfig.getConfigurationSection("default-info");
                    Integer amount = Integer.parseInt(args[2]);
                    if(target.hasPlayedBefore()){
                        if(playerDataYml.exists()){
                            try {
                                dInfo.set("stars", amount);
                                playerDataYmlConfig.save(playerDataYml);
                                p.sendMessage(ChatColor.GREEN + "Le has colocado " + ChatColor.GOLD + "$" + amount + ChatColor.GRAY + " a " + ChatColor.GOLD + args[1]);
                            }catch (IOException exception){
                                exception.printStackTrace();
                            }
                        }
                    }else {
                        p.sendMessage(ChatColor.RED + "Jugador invalido!");
                    }

                }
            }
        } else if (args[0].equalsIgnoreCase("clear")) {
            if(args[1].length() == 0){
                p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa: /stars help");
            }else {
                if(args[2].length() == 0){
                    p.sendMessage(ChatColor.RED + "Argumentos insuficientes! Usa: /stars help");
                }else {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    playerDataYml = new File(playersData, target.getUniqueId() + ".yml");
                    playerDataYmlConfig = YamlConfiguration.loadConfiguration(playerDataYml);
                    ConfigurationSection dInfo = playerDataYmlConfig.getConfigurationSection("default-info");
                    if(target.hasPlayedBefore()){
                        if(playerDataYml.exists()){
                            try {
                                dInfo.set("stars", 0.0);
                                playerDataYmlConfig.save(playerDataYml);
                                p.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + "Ahora tiene " + ChatColor.GOLD + "$0");
                            }catch (IOException exception){
                                exception.printStackTrace();
                            }
                        }
                    }else {
                        p.sendMessage(ChatColor.RED + "Jugador invalido!");
                    }

                }
            }
        }


        return true;
    }
}
