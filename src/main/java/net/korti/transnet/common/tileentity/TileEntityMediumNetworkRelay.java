package net.korti.transnet.common.tileentity;

import net.korti.transnet.api.impl.TileEntityNetworkRelay;
import net.korti.transnet.common.constants.TileEntityInfo;

public class TileEntityMediumNetworkRelay extends TileEntityNetworkRelay {

    public TileEntityMediumNetworkRelay() {
        super(TileEntityInfo.MNR_MAX_CONNECTIONS, TileEntityInfo.MNR_RANGE);
    }

}
