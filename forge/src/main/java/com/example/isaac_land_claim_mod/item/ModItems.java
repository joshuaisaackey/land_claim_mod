/*
package com.example.isaac_land_claim_mod.item;

import com.example.isaac_land_claim_mod.ExampleMod;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example",
            () -> new Item(new Item.Properties()));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
*/

package com.example.isaac_land_claim_mod.item;

import com.example.isaac_land_claim_mod.ExampleMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    // Method to safely get an ItemStack for the land claim block
    public static ItemStack getLandClaimBlockItem(int blockCount) {
        Item landClaimBlockItem = ITEMS.getEntries().stream()
                .filter(item -> item.getId().getPath().equals("land_claim_block"))
                .map(RegistryObject::get)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Land Claim Block Item not registered!"));

        return new ItemStack(landClaimBlockItem, blockCount);
    }
}
