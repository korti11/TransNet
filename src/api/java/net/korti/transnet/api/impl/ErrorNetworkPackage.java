package net.korti.transnet.api.impl;

import net.korti.transnet.api.constants.NetworkMessages;
import net.korti.transnet.api.network.INetworkNode;
import net.korti.transnet.api.network.IStatusMessage;

/**
 * @since API Version: 1.0.0
 */
public class ErrorNetworkPackage extends BasicNetworkPackage {

    public ErrorNetworkPackage(INetworkNode sender) {
        super(sender);
    }

    @Override
    public boolean canHandlePackage(INetworkNode node) {
        return true;
    }

    @Override
    public void handlePackage(INetworkNode node) {

    }

    @Override
    public IStatusMessage getMessage() {
        return new StatusMessage(false, NetworkMessages.CAN_NOT_HANDLE_PACKAGE.toString());
    }
}
