package com.example.isaac_land_claim_mod.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public class ChunkUtil {

    /**
     * Converts the BlockPos of a block into ChunkPos (chunk coordinates).
     *
     * @param blockPos The BlockPos of the block in the world.
     * @return The ChunkPos containing the chunk coordinates.
     *
     * EXAMPLE: 
     * BlockPos blockPos = new BlockPos(100, 64, 200);
     * ChunkPos chunkPos = ChunkUtil.getChunkCoordinatesFromBlock(blockPos);
     * System.out.println("Chunk coordinates: " + chunkPos.x + ", " + chunkPos.z);
     * 
     */
    public static ChunkPos getChunkCoordinatesFromBlock(BlockPos blockPos) {
        // Divide the block's x and z coordinates by 16 to get the chunk coordinates
        int chunkX = blockPos.getX() >> 4;
        int chunkZ = blockPos.getZ() >> 4;
        return new ChunkPos(chunkX, chunkZ);
    }
}
