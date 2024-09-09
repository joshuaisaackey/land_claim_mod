package com.example.isaac_land_claim_mod.event;

import com.example.isaac_land_claim_mod.ExampleMod;
import com.example.isaac_land_claim_mod.utils.ChunkUtil;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Bus.FORGE)
public class BlockPlaceEventHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            BlockState blockState = event.getPlacedBlock();
            Block block = blockState.getBlock();
            Level world = player.level();
            BlockPos pos = event.getPos();
            ChunkPos chunk = event.getLevel().getChunk(pos).getPos();

            // Log the event
            LOGGER.info("{} placed {} at ({}, {}, {}) in chunk at ({},{}) in dimension {}", 
                        player.getName().getString(), 
                        block.getName().getString(), 
                        pos.getX(), pos.getY(), pos.getZ(), 
                        chunk.x, chunk.z,
                        world.dimension().location());
        }
    }
}
