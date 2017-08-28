package net.korti.transnet.api.info;

/**
 * Used for Waila/TheOneProbe integration.
 * @since API Version: 1.0.0
 */
public interface INetworkNodeInfo {

    /**
     * @return True if the network node is connected.
     * @since API Version: 1.0.0
     */
    boolean isConnected();

}
