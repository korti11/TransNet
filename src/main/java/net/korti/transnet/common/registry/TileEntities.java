package net.korti.transnet.common.registry;

import net.korti.transnet.common.constants.TileEntityInfo;
import net.korti.transnet.common.tileentity.TileEntityInterfaceNode;
import net.korti.transnet.common.tileentity.TileEntityLargeNetworkRelay;
import net.korti.transnet.common.tileentity.TileEntityMediumNetworkRelay;
import net.korti.transnet.common.tileentity.TileEntitySmallNetworkRelay;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntitySmallNetworkRelay.class, TileEntityInfo.SNR_ID);
        GameRegistry.registerTileEntity(TileEntityMediumNetworkRelay.class, TileEntityInfo.MNR_ID);
        GameRegistry.registerTileEntity(TileEntityLargeNetworkRelay.class, TileEntityInfo.LNR_ID);
        GameRegistry.registerTileEntity(TileEntityInterfaceNode.class, TileEntityInfo.IN_ID);
    }

}
