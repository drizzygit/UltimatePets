package me.dr1zzy.ultimatepets.eventsManager;
import me.dr1zzy.ultimatepets.UltimatePets;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class firstJoinEvent implements Listener {

    File playersData = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder() + "/playersData");
    //playerFirstJoinData
    File playerDataYml;
    FileConfiguration playerDataYmlConfig;


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        playerDataYml = new File(playersData, p.getUniqueId() + ".yml");
        playerDataYmlConfig = YamlConfiguration.loadConfiguration(playerDataYml);
        if(!playerDataYml.exists()){
            try{
                playerDataYmlConfig.createSection("default-info");
                ConfigurationSection dInfo = playerDataYmlConfig.getConfigurationSection("default-info");
                dInfo.set("name", p.getDisplayName());
                dInfo.set("havePet", false);
                dInfo.set("lastPet", "NONE");
                dInfo.set("stars", 0.0);
                playerDataYmlConfig.createSection("buyed-pets");
                ConfigurationSection buyedPets = playerDataYmlConfig.getConfigurationSection("buyed-pets");
                buyedPets.set("dirt-pet", 0);
                buyedPets.set("stone-pet", 0);
                buyedPets.set("iron-pet", 0);
                buyedPets.set("gold-pet", 0);
                buyedPets.set("diamond-pet", 0);
                ConfigurationSection minionConfig = playerDataYmlConfig.getConfigurationSection("minion-config");
                minionConfig.set("utility", "DEFAULT");
                minionConfig.set("head", "PLAYER_HEAD");
                minionConfig.set("chestPlate-color", "RED");
                minionConfig.set("leggins-color", "RED");
                minionConfig.set("boots-color", "RED");

                playerDataYmlConfig.save(playerDataYml);
                p.sendMessage(ChatColor.GREEN + "Carpeta creada!");
            }catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }
}
