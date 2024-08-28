package com.example.isaac_land_claim_mod.event;

import com.example.isaac_land_claim_mod.ExampleMod;

import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Bus.FORGE)
public class PigDeathEventHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        BlockPos pos = entity.blockPosition();

        if (entity.getType().toString().contains("pig")) {
            // Log the event
            LOGGER.info("{} died at ({}, {}, {})",
                    entity.getType(), pos.getX(), pos.getY(), pos.getZ());

            ItemEntity diamondDrop = new ItemEntity(
                    entity.level(),
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    Items.DIAMOND.getDefaultInstance());

            entity.level().addFreshEntity(diamondDrop);
        }

    }
}
