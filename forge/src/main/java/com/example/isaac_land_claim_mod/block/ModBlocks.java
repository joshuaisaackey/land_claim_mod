/*
package com.example.isaac_land_claim_mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;
import com.example.isaac_land_claim_mod.ExampleMod;
import com.example.isaac_land_claim_mod.item.ModItems;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK = registerBlock("example_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
 */
package com.example.isaac_land_claim_mod.block;

import com.example.isaac_land_claim_mod.ExampleMod;
import com.example.isaac_land_claim_mod.item.ModItems;
import com.example.isaac_land_claim_mod.utils.JarUtils;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            ExampleMod.MODID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        registerAllBlocksFromJson();
    }

    private static void registerAllBlocksFromJson() {

        String jarPath = JarUtils.getJarPath(ModBlocks.class);
        if (jarPath == null) {
            System.err.println("Failed to get JAR path.");
            return;
        }

        if (jarPath.contains(".jar")) {

            try (JarFile jarFile = new JarFile(jarPath)) {
                jarFile.stream()
                        .filter(entry -> entry.getName().startsWith("assets/" + ExampleMod.MODID + "/models/block/")
                                && entry.getName().endsWith(".json"))
                        .forEach(entry -> {
                            String fileName = entry.getName();
                            String blockName = fileName.substring(fileName.lastIndexOf('/') + 1,
                                    fileName.length() - ".json".length());
                            registerBlock(blockName,
                                    () -> new IsaacCustomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                                            .sound(SoundType.STONE)));
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            jarPath = jarPath.replace("file:", "");
            String base = jarPath.split("/build/")[0];
            Path blockModelsPath = Paths.get(base + "/src/main/resources/assets/" + ExampleMod.MODID + "/models/block/");
            try (Stream<Path> blockPaths = Files.walk(blockModelsPath, 1)) {
                blockPaths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".json"))
                        .forEach(path -> {
                            String fileName = path.getFileName().toString();
                            String blockName = fileName.substring(0, fileName.length() - ".json".length());
                            registerBlock(blockName,
                                    () -> new IsaacCustomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                                            .sound(SoundType.STONE)));
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
