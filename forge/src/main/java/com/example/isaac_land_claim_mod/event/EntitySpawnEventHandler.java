package com.example.isaac_land_claim_mod.event;

import com.example.isaac_land_claim_mod.ExampleMod;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Bus.FORGE)
public class EntitySpawnEventHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        // Log the entity spawn event
        LOGGER.info("{} spawned in the world at ({}, {}, {})",
                entity.getType(), 
                entity.getX(), 
                entity.getY(), 
                entity.getZ());

        // Perform additional actions for specific entities, if needed
        if (entity.getType().toString().contains("pig")) {
            LOGGER.info("A pig has spawned!");
            // Additional logic here...
        }
    }
}
