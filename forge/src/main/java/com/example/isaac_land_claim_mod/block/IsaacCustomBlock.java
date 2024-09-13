package com.example.isaac_land_claim_mod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.example.isaac_land_claim_mod.entity.IsaacClaimBlockEntity;

public class IsaacCustomBlock extends Block implements EntityBlock{
    private String name = "unknown";
    

    public IsaacCustomBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F).sound(SoundType.STONE));
        this.name="unknown";
    }

    public IsaacCustomBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.name="unknown";
    }

    public IsaacCustomBlock(String name, BlockBehaviour.Properties properties) {
        super(properties);
        this.name=name;
    }

    public String getIsaacCustomBlockName() {
        return this.name;
    }

    // You can override onPlace or other methods to interact with placement events
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        // Add any custom behavior when the block is placed
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IsaacClaimBlockEntity(blockPos, blockState);
    }
}
