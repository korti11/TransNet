package net.korti.transnet.common.block;

import net.korti.transnet.api.impl.TileEntityNetworkRelay;
import net.minecraft.block.material.Material;

public class NetworkRelayBlock extends RotatableBlock {

    public NetworkRelayBlock(String name,
                             Class<? extends TileEntityNetworkRelay> tileEntityClass) {
        super(Material.IRON, name, tileEntityClass);
    }

}
