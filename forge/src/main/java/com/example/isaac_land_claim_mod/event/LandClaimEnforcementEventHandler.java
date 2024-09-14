package com.example.isaac_land_claim_mod.event;

import java.util.List;
import java.util.UUID;

import com.example.isaac_land_claim_mod.ExampleMod;
import com.example.isaac_land_claim_mod.block.IsaacCustomBlock;
import com.example.isaac_land_claim_mod.entity.IsaacClaimBlockEntity;
import com.example.isaac_land_claim_mod.utils.ChunkUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Bus.FORGE)
public class LandClaimEnforcementEventHandler {

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        // Check if the entity is a player
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            BlockPos pos = event.getPos();

            // Get the chunk position from the block position
            ChunkPos chunkPos = event.getLevel().getChunk(pos).getPos();

            // land_claim_block is handled by LandClaimBlockPlacmentEventHandler
            if (event.getPlacedBlock().getBlock().getDescriptionId().contains("land_claim_block")) {
                return;
            }

            // Check if the placed block is a land_claim_block
            List<BlockPos> blockPositions = ChunkUtil.findBlocksByDescriptionId(player.level(), chunkPos,
                    "land_claim_block");
            for (BlockPos blockPos : blockPositions) {
                BlockEntity blockEntity = event.getLevel().getBlockEntity(blockPos);
                if (blockEntity instanceof IsaacClaimBlockEntity) {
                    UUID ownerUUID = ((IsaacClaimBlockEntity) blockEntity).getOwnerUUID();
                    if (player.getUUID().equals(ownerUUID)) {
                        return;
                    }
                }
            }

            // unclaimed land is free for anyone to build on
            if (blockPositions.isEmpty()) {
                return;
            }

            // you only get here if you have not claimed the chunk.
            event.setCanceled(true);
            player.displayClientMessage(
                    Component.literal("this is not your land that you claimed."),
                    true);
        }

    }
    @SubscribeEvent
    public static void onBlockDestroy(BlockEvent.BreakEvent event) {
        Player player = (Player) event.getPlayer();
        BlockPos pos = event.getPos();

        // Get the chunk position from the block position
        ChunkPos chunkPos = event.getLevel().getChunk(pos).getPos();

        // land_claim_block is handled by LandClaimBlockPlacmentEventHandler
        if (event.getState().getBlock().getDescriptionId().contains("land_claim_block")) {
            return;
        }

        // Check if the placed block is a land_claim_block
        List<BlockPos> blockPositions = ChunkUtil.findBlocksByDescriptionId(player.level(), chunkPos,
                "land_claim_block");
        for (BlockPos blockPos : blockPositions) {
            BlockEntity blockEntity = event.getLevel().getBlockEntity(blockPos);
            if (blockEntity instanceof IsaacClaimBlockEntity) {
                UUID ownerUUID = ((IsaacClaimBlockEntity) blockEntity).getOwnerUUID();
                if (player.getUUID().equals(ownerUUID)) {
                    return;
                }
            }
        }

        // unclaimed land is free for anyone to build on
        if (blockPositions.isEmpty()) {
            return;
        }

        // you only get here if you have not claimed the chunk.
        event.setCanceled(true);
        player.displayClientMessage(
                Component.literal("this is not your land that you claimed."),
                true);
    }
}