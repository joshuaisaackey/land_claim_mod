package com.example.isaac_land_claim_mod.event;

import com.example.isaac_land_claim_mod.item.ModItems;
import com.example.isaac_land_claim_mod.ExampleMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID)
public class PlayerJoinEventHandler {

    private static final String FIRST_JOIN_KEY = "firstJoin";

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CompoundTag persistentData = player.getPersistentData();
            CompoundTag modData = persistentData.getCompound(ExampleMod.MODID);

            // Check if the player is joining for the first time
            if (!modData.getBoolean(FIRST_JOIN_KEY)) {
                // Use the new method to get the ItemStack
                ItemStack landClaimBlock = ModItems.getLandClaimBlockItem(20);

                // Only add the block if it's not empty
                if (!landClaimBlock.isEmpty()) {
                    player.getInventory().add(landClaimBlock); 
                    // Send a welcome message
                    player.displayClientMessage(
                            Component.literal("Welcome! You have received a Land Claim Block."),
                            true
                    );
                } else {
                    player.displayClientMessage(Component.literal("Failed to obtain Land Claim Block."), true);
                }

                // Mark the player as no longer new
                modData.putBoolean(FIRST_JOIN_KEY, true);
                persistentData.put(ExampleMod.MODID, modData);
            }
        }
    }
}
