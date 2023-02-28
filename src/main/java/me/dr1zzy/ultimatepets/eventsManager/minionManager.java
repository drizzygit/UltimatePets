package me.dr1zzy.ultimatepets.eventsManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class minionManager implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        Player p = e.getPlayer();
        Entity entity = e.getRightClicked();
        EntityType type = e.getRightClicked().getType();
        if(type.equals(EntityType.ARMOR_STAND)){
            if(entity.getName().equalsIgnoreCase(ChatColor.GOLD + p.getName())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        Entity entity = e.getEntity();
        EntityType entityType = e.getEntityType();
        if(e.getDamager() instanceof Player){
            if(entityType.equals(EntityType.ARMOR_STAND)){
                if(entity.getName().equalsIgnoreCase(ChatColor.GOLD + e.getDamager().getName())){
                    e.setCancelled(true);
                }
            }
        }
    }

}
