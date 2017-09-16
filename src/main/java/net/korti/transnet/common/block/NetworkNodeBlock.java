package net.korti.transnet.common.block;

import net.korti.transnet.api.impl.TileEntityNetworkNode;
import net.minecraft.block.material.Material;

public class NetworkNodeBlock extends RotatableBlock {

    public NetworkNodeBlock(Material materialIn, String name,
                            Class<? extends TileEntityNetworkNode> tileEntityClass) {
        super(materialIn, name, tileEntityClass);
    }

}
