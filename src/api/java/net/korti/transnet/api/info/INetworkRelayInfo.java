package net.korti.transnet.api.info;

/**
 * Used for Waila/TheOneProbe integration.
 * @since API Version: 1.0.0
 */
public interface INetworkRelayInfo {

    /**
     * @return Number of current connected connections.
     * @since API Version: 1.0.0
     */
    int getNetworkConnections();

    /**
     * @return Number of maximum connections.
     * @since API Version: 1.0.0
     */
    int getMaxNetworkConnections();

}
