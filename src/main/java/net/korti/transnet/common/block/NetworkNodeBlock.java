package net.korti.transnet.common.block;

import net.korti.transnet.api.impl.TileEntityNetworkNode;
import net.minecraft.block.material.Material;

public class NetworkNodeBlock extends RotatableBlock {

    public NetworkNodeBlock(String name,
                            Class<? extends TileEntityNetworkNode> tileEntityClass) {
        super(Material.IRON, name, tileEntityClass);
    }

}
