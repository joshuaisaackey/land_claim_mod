package com.example.isaac_land_claim_mod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.UUID;

public class IsaacCustomBlock extends Block {
    private UUID ownerUUID;

    public IsaacCustomBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    // Store the player's UUID when the block is placed
    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    // Get the player's UUID
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    // You can override onPlace or other methods to interact with placement events
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        // Add any custom behavior when the block is placed
    }
}
