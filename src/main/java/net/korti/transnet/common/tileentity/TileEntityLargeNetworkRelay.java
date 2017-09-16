package net.korti.transnet.common.tileentity;

import net.korti.transnet.api.impl.TileEntityNetworkRelay;
import net.korti.transnet.common.constants.TileEntityInfo;

public class TileEntityLargeNetworkRelay extends TileEntityNetworkRelay {

    public TileEntityLargeNetworkRelay() {
        super(TileEntityInfo.LNR_MAX_CONNECTIONS, TileEntityInfo.LNR_RANGE);
    }

}
