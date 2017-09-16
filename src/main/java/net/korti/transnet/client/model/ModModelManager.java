package net.korti.transnet.client.model;

import net.korti.transnet.common.constants.ModInfo;
import net.korti.transnet.common.registry.ModBlocks;
import net.korti.transnet.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class ModModelManager {

    public static final ModModelManager INSTANCE = new ModModelManager();

    private final StateMapperBase propertyStringMapper = new StateMapperBase() {
        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return new ModelResourceLocation("minecraft:air");
        }
    };
    private final Set<Item> itemsRegistered = new HashSet<>();

    private ModModelManager() {

    }

    @SubscribeEvent
    public static void registerAllModels(final ModelRegistryEvent event) {
        INSTANCE.registerBlockModels();
        INSTANCE.registerItemModels();
    }

    private void registerBlockModels() {
        ModBlocks.RegistrationHandler.ITEM_BLOCKS.stream().filter(item -> !itemsRegistered.contains(item)).
                forEach(this::registerItemModel);
    }

    private void registerItemModels() {
        ModItems.RegistrationHandler.ITEMS.stream().filter(item -> !itemsRegistered.contains(item)).
                forEach(this::registerItemModel);
    }

    private void registerBlockItemModel(final IBlockState state) {
        final Block block = state.getBlock();
        final Item item = Item.getItemFromBlock(block);

        if (item != Items.AIR) {
            registerItemModel(item , new ModelResourceLocation(
                    block.getRegistryName(),
                    propertyStringMapper.getPropertyString(state.getProperties())
                )
            );
        }
    }

    private void registerItemModel(final Item item) {
        registerItemModel(item, item.getRegistryName().toString());
    }

    private void registerItemModel(final Item item, final String modelLocation) {
        final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(modelLocation, "inventory");
        registerItemModel(item, modelResourceLocation);
    }

    private void registerItemModel(final Item item, final ModelResourceLocation modelResourceLocation) {
        ModelBakery.registerItemVariants(item, modelResourceLocation);
        registerItemModel(item, MeshDefinitionFix.create(stack -> modelResourceLocation));
    }

    private void registerItemModel(final Item item, final ItemMeshDefinition itemMeshDefinition) {
        itemsRegistered.add(item);
        ModelLoader.setCustomMeshDefinition(item, itemMeshDefinition);
    }
}
