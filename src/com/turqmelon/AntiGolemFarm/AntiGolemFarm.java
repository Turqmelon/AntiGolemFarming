package com.turqmelon.AntiGolemFarm;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class AntiGolemFarm extends JavaPlugin implements Listener {

    private Random random;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        this.random = new Random();
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        if ((entity instanceof IronGolem)){
            event.getDrops().clear();

            if (entity.hasMetadata("agf.playerhit")){
                int num = random.nextInt(3);
                if (num > 0){
                    event.getDrops().add(new ItemStack(Material.IRON_INGOT, num));
                }
            }
            else{
                event.setDroppedExp(0);
            }

        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if ((entity instanceof IronGolem)){
            if ((event instanceof EntityDamageByEntityEvent)){
                Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
                if ((damager instanceof Player)){
                    if (!entity.hasMetadata("agf.playerhit")){
                        entity.setMetadata("agf.playerhit", new FixedMetadataValue(this, null));
                    }
                }
            }
        }
    }
}
