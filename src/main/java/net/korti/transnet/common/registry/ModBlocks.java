package net.korti.transnet.common.registry;

import com.google.common.base.Preconditions;
import net.korti.transnet.common.block.NetworkNodeBlock;
import net.korti.transnet.common.block.NetworkRelayBlock;
import net.korti.transnet.common.constants.BlockInfo;
import net.korti.transnet.common.constants.ModInfo;
import net.korti.transnet.common.tileentity.TileEntityInterfaceNode;
import net.korti.transnet.common.tileentity.TileEntityLargeNetworkRelay;
import net.korti.transnet.common.tileentity.TileEntityMediumNetworkRelay;
import net.korti.transnet.common.tileentity.TileEntitySmallNetworkRelay;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

@GameRegistry.ObjectHolder(ModInfo.MOD_ID)
public class ModBlocks {

    private static NetworkRelayBlock smallNetworkRelay =
            new NetworkRelayBlock(BlockInfo.SMALL_NETWORK_RELAY, TileEntitySmallNetworkRelay.class);
    private static NetworkRelayBlock mediumNetworkRelay =
            new NetworkRelayBlock(BlockInfo.MEDIUM_NETWORK_RELAY, TileEntityMediumNetworkRelay.class);
    private static NetworkRelayBlock largeNetworkRelay =
            new NetworkRelayBlock(BlockInfo.LARGE_NETWORK_RELAY, TileEntityLargeNetworkRelay.class);

    private static NetworkNodeBlock interfaceNetworkNode =
            new NetworkNodeBlock(BlockInfo.INTERFACE_NODE, TileEntityInterfaceNode.class);

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();

            final Block[] blocks = {
                    smallNetworkRelay,
                    mediumNetworkRelay,
                    largeNetworkRelay,
                    interfaceNetworkNode
            };

            registry.registerAll(blocks);
        }

        @SubscribeEvent
        public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> registry = event.getRegistry();

            final ItemBlock[] items = {
                    new ItemBlock(smallNetworkRelay),
                    new ItemBlock(mediumNetworkRelay),
                    new ItemBlock(largeNetworkRelay),
                    new ItemBlock(interfaceNetworkNode)
            };

            for (final ItemBlock item : items) {
                final Block block = item.getBlock();
                final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(),
                        "Block %s has null registry name!", block);
                registry.register(item.setRegistryName(registryName));
                ITEM_BLOCKS.add(item);
            }
        }

    }

}
