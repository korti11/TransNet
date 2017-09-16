package net.korti.transnet.common.block;

import net.korti.transnet.api.impl.TileEntityNetworkRelay;
import net.minecraft.block.material.Material;

public class NetworkRelayBlock extends RotatableBlock {

    public NetworkRelayBlock(Material materialIn, String name,
                             Class<? extends TileEntityNetworkRelay> tileEntityClass) {
        super(materialIn, name, tileEntityClass);
    }

}
