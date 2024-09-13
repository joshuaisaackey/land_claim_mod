package com.example.isaac_land_claim_mod.entity;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class IsaacClaimBlockEntity extends BlockEntity {
    private UUID ownerUUID;
    
    public IsaacClaimBlockEntity(BlockPos worldPos, BlockState blockState) {
        super(
            BlockEntities.ISAAC_CLAIM_BLOCK_ENTITY.get(), 
            worldPos, 
            blockState);

    }

    // Store the player's UUID when the block is placed
    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    // Get the player's UUID
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (ownerUUID != null) {
            tag.putUUID("OwnerUUID", ownerUUID);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("OwnerUUID")) {
            this.ownerUUID = tag.getUUID("OwnerUUID");
        }
    }
    
}
