package net.korti.transnet.common.registry;

import net.korti.transnet.common.constants.ModInfo;
import net.korti.transnet.common.item.ItemWrench;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

@GameRegistry.ObjectHolder(ModInfo.MOD_ID)
public class ModItems {

    private static ItemWrench wrench = new ItemWrench();

    public static class RegistrationHandler {
        public static final Set<Item> ITEMS = new HashSet<>();

        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> registry = event.getRegistry();

            final Item[] items = {
                    wrench
            };

            for(final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
            }
        }
    }

}
