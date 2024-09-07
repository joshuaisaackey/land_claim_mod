package com.example.isaac_land_claim_mod.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class ChunkUtil {

    /**
     * Converts the BlockPos of a block into ChunkPos (chunk coordinates).
     *
     * @param blockPos The BlockPos of the block in the world.
     * @return The ChunkPos containing the chunk coordinates.
     *
     *         EXAMPLE:
     *         BlockPos blockPos = new BlockPos(100, 64, 200);
     *         ChunkPos chunkPos = ChunkUtil.getChunkCoordinatesFromBlock(blockPos);
     *         System.out.println("Chunk coordinates: " + chunkPos.x + ", " +
     *         chunkPos.z);
     * 
     */
    public static ChunkPos getChunkCoordinatesFromBlock(BlockPos blockPos) {
        // Divide the block's x and z coordinates by 16 to get the chunk coordinates
        int chunkX = blockPos.getX() >> 4;
        int chunkZ = blockPos.getZ() >> 4;
        return new ChunkPos(chunkX, chunkZ);
    }

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

        // Define the coordinates for the chunk
        int startX = chunkPos.x * 16;
        int startZ = chunkPos.z * 16;

        // Iterate over all blocks in the chunk
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < level.getMaxBuildHeight(); y++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos pos = new BlockPos(startX + x, y, startZ + z);
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
