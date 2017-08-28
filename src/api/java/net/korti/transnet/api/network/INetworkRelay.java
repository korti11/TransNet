package net.korti.transnet.api.network;

import net.korti.transnet.api.constants.NetworkMessages;
import net.korti.transnet.api.impl.StatusMessage;

import java.util.List;

/**
 * @since API Version: 1.0.0
 */
public interface INetworkRelay extends INetworkNode {

    @Override
    default IStatusMessage disconnectFromNode() {
        throw new UnsupportedOperationException("This method not supported for INetworkRelay. Use the method 'disconnectFromAllNodes' instead.");
    }

    @Override
    default INetworkNode getConnection() {
        throw new UnsupportedOperationException("This method is not supported for INetworkRelay. Use the method 'getConnections' instead.");
    }

    /**
     * @return A list of all connected nodes.
     * @since API Version: 1.0.0
     */
    List<INetworkNode> getConnections();

    /**
     * Disconnect from all connected nodes.
     * @return A status message about what happened. If the disconnection operation was successful or not.
     * @implNote No validation if all disconnection operation were successful.
     * Should only be used for breaking a block that is associated with the {@link INetworkRelay} interface.
     * @since API Version: 1.0.0
     */
    default IStatusMessage disconnectFromAllNodes() {
        List<INetworkNode> tempNodes = getConnections();
        for (INetworkNode node : tempNodes) {
            node.disconnectFromNode(this, true, false);
        }

        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_DISCONNECTED);
    }

    /**
     * Check if the given node is in range.
     * @param node The node to check.
     * @return True if it is in range.
     */
    boolean isInRange(INetworkNode node);

    @Override
    default void handleNetworkPackages() {
        if (getPackageQueue() != null && !getPackageQueue().isEmpty()) {
            INetworkPackage networkPackage = getPackageQueue().poll();
            if (networkPackage.canHandlePackage(this)) {
                networkPackage.handlePackage(this);
            } else {
                broadcastNetworkPackage(networkPackage);
            }
        }
    }

    /**
     * Send the network package to all connected nodes.
     * @param networkPackage The package to broadcast.
     * @since API Version: 1.0.0
     */
    default void broadcastNetworkPackage(INetworkPackage networkPackage) {
        if (getConnections() != null) {
            getConnections().forEach(node -> node.receiveNetworkPackage(networkPackage));
        }
    }

    @Override
    default void sendNetworkPackage(INetworkPackage networkPackage) {
        broadcastNetworkPackage(networkPackage);
    }
}
