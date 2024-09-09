package com.example.isaac_land_claim_mod.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class ChunkUtil {

    /**
     * Finds all blocks in the specified chunk that match the given descriptionId.
     *
     * @param level The world level.
     * @param chunkPos The ChunkPos to search.
     * @param descriptionId The descriptionId to match against block states.
     * @return A list of BlockPos that match the descriptionId.
     */
    public static java.util.List<BlockPos> findBlocksByDescriptionId(Level level, ChunkPos chunkPos, String descriptionId) {
        java.util.List<BlockPos> matchingBlocks = new ArrayList<>();
    
        // Get the world coordinates for the bottom corner of the chunk
        int chunkStartX = chunkPos.getMinBlockX(); // This gets the minimum X block coordinate in the chunk
        int chunkStartZ = chunkPos.getMinBlockZ(); // This gets the minimum Z block coordinate in the chunk
    
        // Iterate over all blocks in the chunk
        for (int x = 0; x < 16; x++) {
            for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos pos = new BlockPos(chunkStartX + x, y, chunkStartZ + z);
                    BlockState state = level.getBlockState(pos);
    
                    if (state.getBlock().getDescriptionId().contains(descriptionId)) {
                        matchingBlocks.add(pos);
                    }
                }
            }
        }
    
        return matchingBlocks;
    }
    
}
