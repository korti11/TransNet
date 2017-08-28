package net.korti.transnet.api.network;

/**
 * @since API Version: 1.0.0
 */
public interface INetworkPackage {

    /**
     * @return The network node who created this package and sent it to the network.
     * @since API Version: 1.0.0
     */
    INetworkNode getSender();

    /**
     * Checks if the given network node can handle this package.
     * @param node The network node to check.
     * @return True if the given network node can handle this package.
     * @since API Version: 1.0.0
     */
    boolean canHandlePackage(INetworkNode node);

    /**
     * What should this package do?
     * @param node The network node who can handle this package.
     * @since API Version: 1.0.0
     */
    void handlePackage(INetworkNode node);

    /**
     * @return A status message about this package. Could be anything.
     * @since API Version: 1.0.0
     */
    IStatusMessage getMessage();

}
