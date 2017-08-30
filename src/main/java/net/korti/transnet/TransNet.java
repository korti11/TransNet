package net.korti.transnet;

import net.korti.transnet.common.CommonProxy;
import net.korti.transnet.common.constants.ModInfo;
import net.korti.transnet.common.helper.LogHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
public class TransNet {

    @Mod.Instance(ModInfo.MOD_ID)
    public static TransNet instance;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy proxy;

    public static CreativeTabs transNetTab = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.REDSTONE);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.setLogger(event.getModLog());
        LogHelper.i("Lovely, lovely new world. It's time to transport some fluids and items :D");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
