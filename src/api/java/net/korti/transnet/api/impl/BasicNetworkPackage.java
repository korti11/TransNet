package net.korti.transnet.api.impl;

import net.korti.transnet.api.network.INetworkNode;
import net.korti.transnet.api.network.INetworkPackage;

/**
 * @since API Version: 1.0.0
 */
public abstract class BasicNetworkPackage implements INetworkPackage {

    private final INetworkNode sender;

    public BasicNetworkPackage(INetworkNode sender) {
        this.sender = sender;
    }

    @Override
    public INetworkNode getSender() {
        return this.sender;
    }
}
