package net.korti.transnet.common;

import net.korti.transnet.common.registry.TileEntities;

public class CommonProxy {

    public void preInit() {
        TileEntities.registerTileEntities();
    }

}
