package me.dr1zzy.ultimatepets.menuManager;

import me.dr1zzy.ultimatepets.UltimatePets;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class inventoryMenuManager implements Listener {
    Server server = UltimatePets.getProvidingPlugin(UltimatePets.class).getServer();
    private double blocksdown = 2;
    private double blocksToUpAndDown = 0;
    private double blocksdowned = blocksToUpAndDown;
    boolean up = false;
    List<Player> playersWithPets = new ArrayList<Player>();

    FileConfiguration config = UltimatePets.getProvidingPlugin(UltimatePets.class).getConfig();

    File pets;
    FileConfiguration petsYml;

    File playersData = new File(UltimatePets.getPlugin(UltimatePets.class).getDataFolder() + "/playersData");
    //playerFirstJoinData
    File playerDataYml;
    FileConfiguration playerDataYmlConfig;

    ArmorStand pet;

    BukkitTask task;

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        String inventoryTitle = e.getView().getTitle();
        String clickedItemName= e.getCurrentItem().getItemMeta().getDisplayName();

        ConfigurationSection inventoryMenuC = config.getConfigurationSection("config").getConfigurationSection("menus").getConfigurationSection("inventory-menu");
        String shopTitle = inventoryMenuC.getString("title");
        String shopColor = inventoryMenuC.getString("title-color");
        String shopPermission = inventoryMenuC.getString("permission");

        if(inventoryTitle.equalsIgnoreCase("§" + shopColor + shopTitle)){
            pets = new File(UltimatePets.getProvidingPlugin(UltimatePets.class).getDataFolder(), "pets.yml");
            petsYml = YamlConfiguration.loadConfiguration(pets);

            playerDataYml = new File(playersData, p.getUniqueId() + ".yml");
            playerDataYmlConfig = YamlConfiguration.loadConfiguration(playerDataYml);
            ConfigurationSection dInfo = playerDataYmlConfig.getConfigurationSection("default-info");
            Boolean havePetOn = dInfo.getBoolean("havePet");

            ConfigurationSection dirtSection = petsYml.getConfigurationSection("dirt-pet");
            String dirtColor = dirtSection.getString("nameColor");
            String dirtDisplayName = dirtSection.getString("display-name");

            ConfigurationSection stoneSection = petsYml.getConfigurationSection("stone-pet");
            String stoneColor = stoneSection.getString("nameColor");
            String stoneDisplayName = stoneSection.getString("display-name");

            ConfigurationSection ironSection = petsYml.getConfigurationSection("iron-pet");
            String ironColor = ironSection.getString("nameColor");
            String ironDisplayName = ironSection.getString("display-name");

            ConfigurationSection goldSection = petsYml.getConfigurationSection("gold-pet");
            String goldColor = goldSection.getString("nameColor");
            String goldDisplayName = goldSection.getString("display-name");

            ConfigurationSection diamondSection = petsYml.getConfigurationSection("diamond-pet");
            String diamondColor = diamondSection.getString("nameColor");
            String diamondDisplayName = diamondSection.getString("display-name");

            //BLOCKED
            String blocked = ChatColor.RED + "" + ChatColor.UNDERLINE + "BLOQUEADO";
            //DECORATION
            ItemStack decoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            ItemMeta decorationMeta = decoration.getItemMeta();
            decorationMeta.setDisplayName(" ");
            decoration.setItemMeta(decorationMeta);
            //DIRT
            String dirtName = "§" + dirtColor + dirtDisplayName;
            //STONE
            String stoneName = "§" + stoneColor + stoneDisplayName;
            //IRON
            String ironName = "§" + ironColor + ironDisplayName;
            //GOLD
            String goldName = "§" + goldColor + goldDisplayName;
            //DIAMOND
            String diamondName = "§" + diamondColor + diamondDisplayName;
            //DISABLE
            String disable = ChatColor.RED + "DESACTIVAR MASCOTA";

            if(clickedItemName.equalsIgnoreCase(blocked)){
                e.setCancelled(true);
            }
            if(e.getCurrentItem().equals(decoration)){
                e.setCancelled(true);
            }
            if (clickedItemName.equals(disable)){
                if(!playersWithPets.contains(p)){
                    p.sendMessage(ChatColor.RED + "No tienes ninguna mascota activa!");
                    e.setCancelled(true);
                }else if (playersWithPets.contains(p)){
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "Has desactivado tu mascota correctamente.");
                    playersWithPets.remove(p);
                }
            }
            if(clickedItemName.equalsIgnoreCase(dirtName)){
                e.setCancelled(true);
                if(playersWithPets.contains(p)){
                    p.sendMessage(ChatColor.RED + "No puedes usar dos mascotas al mismo tiempo!");
                }else {

                    playersWithPets.add(p);
                    pet = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(+0.5, +1, +0.5), EntityType.ARMOR_STAND);
                    pet.setCustomNameVisible(true);
                    pet.setCustomName(dirtName + " de " + p.getName());
                    pet.setVisible(false);
                    pet.setGravity(false);
                    pet.setHelmet(new ItemStack(Material.DIRT));

                    p.closeInventory();
                    p.sendMessage(ChatColor.GREEN + "Has equipado a tu mascota");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);

                    task = new BukkitRunnable(){

                        @Override
                        public void run(){
                            if(playersWithPets.contains(p) && p.isOnline()){
                                pet.teleport(p.getLocation().add(+0.3, +1, +0.3));
                            }else {
                                pet.remove();
                                task.cancel();
                            }
                        }

                    }.runTaskTimer(UltimatePets.getProvidingPlugin(UltimatePets.class), 0 ,1);
                }
            }
            if(clickedItemName.equalsIgnoreCase(stoneName)){
                e.setCancelled(true);
                if(playersWithPets.contains(p)){
                    p.sendMessage(ChatColor.RED + "No puedes usar dos mascotas al mismo tiempo!");
                }else {

                    playersWithPets.add(p);
                    pet = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(+0.5, +1, +0.5), EntityType.ARMOR_STAND);
                    pet.setCustomNameVisible(true);
                    pet.setCustomName(stoneName + " de " + p.getName());
                    pet.setVisible(false);
                    pet.setGravity(false);
                    pet.setHelmet(new ItemStack(Material.COBBLESTONE));

                    p.closeInventory();
                    p.sendMessage(ChatColor.GREEN + "Has equipado a tu mascota");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);

                    task = new BukkitRunnable(){

                        @Override
                        public void run(){
                            if(playersWithPets.contains(p) && p.isOnline()){
                                pet.teleport(p.getLocation().add(+0.3, +1, +0.3));
                            }else {
                                pet.remove();
                                task.cancel();
                            }
                        }

                    }.runTaskTimer(UltimatePets.getProvidingPlugin(UltimatePets.class), 0 ,1);
                }
            }
            if(clickedItemName.equalsIgnoreCase(ironName)){
                e.setCancelled(true);
                if(playersWithPets.contains(p)){
                    p.sendMessage(ChatColor.RED + "No puedes usar dos mascotas al mismo tiempo!");
                }else {

                    playersWithPets.add(p);
                    pet = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(+0.5, +1, +0.5), EntityType.ARMOR_STAND);
                    pet.setCustomNameVisible(true);
                    pet.setCustomName(ironName + " de " + p.getName());
                    pet.setVisible(false);
                    pet.setGravity(false);
                    pet.setHelmet(new ItemStack(Material.IRON_BLOCK));

                    p.closeInventory();
                    p.sendMessage(ChatColor.GREEN + "Has equipado a tu mascota");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);

                    task = new BukkitRunnable(){

                        @Override
                        public void run(){
                            if(playersWithPets.contains(p) && p.isOnline()){
                                pet.teleport(p.getLocation().add(+0.3, +1, +0.3));
                            }else {
                                pet.remove();
                                task.cancel();
                            }
                        }

                    }.runTaskTimer(UltimatePets.getProvidingPlugin(UltimatePets.class), 0 ,1);
                }
            }
            if(clickedItemName.equalsIgnoreCase(goldName)){
                e.setCancelled(true);
                if(playersWithPets.contains(p)){
                    p.sendMessage(ChatColor.RED + "No puedes usar dos mascotas al mismo tiempo!");
                }else {

                    playersWithPets.add(p);
                    pet = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(+0.5, +1, +0.5), EntityType.ARMOR_STAND);
                    pet.setCustomNameVisible(true);
                    pet.setCustomName(goldName + " de " + p.getName());
                    pet.setVisible(false);
                    pet.setGravity(false);
                    pet.setHelmet(new ItemStack(Material.GOLD_BLOCK));

                    p.closeInventory();
                    p.sendMessage(ChatColor.GREEN + "Has equipado a tu mascota");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);

                    task = new BukkitRunnable(){

                        @Override
                        public void run(){
                            if(playersWithPets.contains(p) && p.isOnline()){
                                pet.teleport(p.getLocation().add(+0.3, +1, +0.3));
                            }else {
                                pet.remove();
                                task.cancel();
                            }
                        }

                    }.runTaskTimer(UltimatePets.getProvidingPlugin(UltimatePets.class), 0 ,1);
                }
            }
            if(clickedItemName.equalsIgnoreCase(diamondName)){
                e.setCancelled(true);
                if(playersWithPets.contains(p)){
                    p.sendMessage(ChatColor.RED + "No puedes usar dos mascotas al mismo tiempo!");
                }else {

                    playersWithPets.add(p);
                    pet = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(+0.5, +1, +0.5), EntityType.ARMOR_STAND);
                    pet.setCustomNameVisible(true);
                    pet.setCustomName(diamondName + " de " + p.getName());
                    pet.setVisible(false);
                    pet.setGravity(false);
                    pet.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));

                    p.closeInventory();
                    p.sendMessage(ChatColor.GREEN + "Has equipado a tu mascota");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);

                    task = new BukkitRunnable(){

                        @Override
                        public void run(){
                            if(playersWithPets.contains(p) && p.isOnline()){
                                pet.teleport(p.getLocation().add(+0.3, +1, +0.3));
                            }else {
                                pet.remove();
                                task.cancel();
                            }
                        }

                    }.runTaskTimer(UltimatePets.getProvidingPlugin(UltimatePets.class), 0 ,1);
                }
            }


        }
    }

}
