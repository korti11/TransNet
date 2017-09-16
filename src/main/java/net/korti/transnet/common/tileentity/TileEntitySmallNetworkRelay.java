package net.korti.transnet.common.tileentity;

import net.korti.transnet.api.impl.TileEntityNetworkRelay;
import net.korti.transnet.common.constants.TileEntityInfo;

public class TileEntitySmallNetworkRelay extends TileEntityNetworkRelay{

    public TileEntitySmallNetworkRelay() {
        super(TileEntityInfo.SNR_MAX_CONNECTIONS, TileEntityInfo.SNR_RANGE);
    }

}
