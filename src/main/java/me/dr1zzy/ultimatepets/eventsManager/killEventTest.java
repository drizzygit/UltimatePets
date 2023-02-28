package me.dr1zzy.ultimatepets.eventsManager;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class killEventTest implements Listener {

    @EventHandler
    public void onKill(EntityDamageByEntityEvent e){
        Player p = (Player) e.getDamager();
        Location deathLocation = e.getEntity().getLocation();
        if(e.getDamager() instanceof Player){
            if(e.getEntity().isDead()){
                e.getEntity().getWorld().playEffect(deathLocation, Effect.SPLASH, 1, 2);
            }
        }
    }

}
