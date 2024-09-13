package com.example.isaac_land_claim_mod.entity;

import com.example.isaac_land_claim_mod.ExampleMod;
import com.example.isaac_land_claim_mod.block.ModBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExampleMod.MODID);

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
        ISAAC_CLAIM_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "land_claim_block",
            () -> BlockEntityType.Builder.of(
                IsaacClaimBlockEntity::new, 
                ModBlocks.ISAAC_CLAIM_BLOCK.get())
                .build(null)
        );
    }

    public static RegistryObject<BlockEntityType<IsaacClaimBlockEntity>> ISAAC_CLAIM_BLOCK_ENTITY;
}
