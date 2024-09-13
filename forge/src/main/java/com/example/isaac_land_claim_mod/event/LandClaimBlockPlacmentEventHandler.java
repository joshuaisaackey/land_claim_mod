package com.example.isaac_land_claim_mod.event;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
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

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Bus.FORGE)
public class LandClaimBlockPlacmentEventHandler {

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        // Check if the entity is a player
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            BlockPos pos = event.getPos();

            // Get the chunk position from the block position
            ChunkPos chunkPos = event.getLevel().getChunk(pos).getPos();
            // Check if the placed block is a land_claim_block
            if (event.getPlacedBlock().getBlock().getDescriptionId().contains("land_claim_block")) {
                // If a land_claim_block has already been placed in this chunk, cancel the event
                if (ChunkUtil.findBlocksByDescriptionId(player.level(), chunkPos, "land_claim_block").size() > 1) {
                    player.displayClientMessage(
                            Component.literal("You cannot place another land_claim_block in this chunk!"), true);
                    event.setCanceled(true);

                    // ###HACK START###
                    // ###
                    // This is a hack to ensure that the item is visible after a failed attempt to
                    // ###
                    // place the item
                    // Find the current selected item slot in the player's hotbar
                    int currentSlot = player.getInventory().selected;

                    // Get the ItemStack in the current slot
                    ItemStack currentStack = player.getInventory().getItem(currentSlot);

                    // Check if the current item is a land_claim_block

                    // Find the target slot (one to the left, wrapping around if necessary)
                    int targetSlot = (currentSlot == 0) ? 8 : currentSlot - 1;

                    // Get the stack in the target slot
                    ItemStack targetStack = player.getInventory().getItem(targetSlot);

                    // Move the land_claim_block to the target slot if it's empty, or swap if needed
                    player.getInventory().setItem(targetSlot, currentStack);
                    player.getInventory().setItem(currentSlot, targetStack);

                    // ###HACK END###
                } else {
                    String ownerName = player.getName().getString();
                    player.displayClientMessage(Component.literal("This chunk is claimed by " + ownerName + "."), true);
                    if (event.getPlacedBlock().getBlock() instanceof IsaacCustomBlock) {
                        if (event.getLevel().getBlockEntity(pos) != null) {
                            BlockEntity blockEntity = event.getLevel().getBlockEntity(pos);
                            if(blockEntity instanceof IsaacClaimBlockEntity){
                                ((IsaacClaimBlockEntity) blockEntity).setOwnerUUID(player.getUUID());
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockDestroy(BlockEvent.BreakEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getState().getBlock().getDescriptionId().contains("land_claim_block")) {
            if (event.getState().getBlock() instanceof IsaacCustomBlock) {
                if (event.getPlayer() instanceof Player) {
                    BlockPos pos = event.getPos();
                    if (event.getLevel().getBlockEntity(pos) != null) {
                        BlockEntity blockEntity = event.getLevel().getBlockEntity(pos);
                        if(blockEntity instanceof IsaacClaimBlockEntity){
                            if(player.getUUID().equals(((IsaacClaimBlockEntity) blockEntity).getOwnerUUID())) {
                                event.getPlayer().displayClientMessage(
                                    Component.literal("You released your claim on this chunk."),
                                    true);
                                    return;
                            }
                        }
                    }
                }    
            }
            player.displayClientMessage(Component.literal("You can't destroy someone elses claim block."), true);
            event.setCanceled(true);
        }
    }
}
