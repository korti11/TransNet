package net.korti.transnet.api.network;

import net.korti.transnet.api.impl.ErrorNetworkPackage;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Queue;

/**
 * @since API Version: 1.0.0
 */
public interface INetworkNode {

    /**
     * Connect to the given network node.
     * @param node Network node that want to get connected.
     * @param isOtherValid True if the validation on the other node is already done.
     * @param simulate True if the connection operation should just be simulated.
     *                 Could be used to test if the connection would be successful.
     * @return A status message about what happened. If the connection operation was successful or not.
     * @since API Version: 1.0.0
     */
    IStatusMessage connectToNode(INetworkNode node, boolean isOtherValid, boolean simulate);

    /**
     * Disconnect from the given network node.
     * @param node Network node that want to get disconnected.
     * @param isOtherDisconnected True if the other node is already disconnected from this node.
     * @param simulate True if the disconnection operation should just be simulated.
     *                 Could be used to test if the disconnection would be successful.
     * @return A status message about what happened. If the disconnection operation was successful or not.
     * @since API Version: 1.0.0
     */
    IStatusMessage disconnectFromNode(INetworkNode node, boolean isOtherDisconnected, boolean simulate);

    /**
     * Disconnect from the connected network node.
     * @return A status message about what happened. If the disconnection operation was successful or not.
     * @since API Version: 1.0.0
     */
    default IStatusMessage disconnectFromNode(){
        return disconnectFromNode(getConnection(), false, false);
    }

    /**
     * @return Current connected network node.
     * @since API Version: 1.0.0
     */
    INetworkNode getConnection();

    /**
     * Saves the network node position to the nbt tag compound.
     * @param tagCompound Where the position should saved on.
     * @implNote Will be used to save the position on the connection device.
     *           Could be done in the class of the connection device but that would need a cast to a tile entity,
     *           so it will be easier.
     */
    void savePositionToNBT(NBTTagCompound tagCompound);

    /**
     * @return A queue with the cached network packages.
     * @since API Version: 1.0.0
     */
    Queue<INetworkPackage> getPackageQueue();

    /**
     * Handles cached network packages through the FIFO principle.
     * @since API Version: 1.0.0
     */
    default void handleNetworkPackages() {
        if (getPackageQueue() != null && !getPackageQueue().isEmpty()) {
            INetworkPackage networkPackage = getPackageQueue().poll();
            if(networkPackage != null) {
                if (networkPackage.canHandlePackage(this)) {
                    networkPackage.handlePackage(this);
                } else {
                    if (networkPackage.getSender() != null) {
                        networkPackage.getSender().receiveNetworkPackage(new ErrorNetworkPackage(this));
                    }
                }
            }
        }
    }

    /**
     * Send a network package to the connected node.
     * @param networkPackage The package to send.
     * @since API Version: 1.0.0
     */
    default void sendNetworkPackage(INetworkPackage networkPackage) {
        if(getConnection() != null) {
            getConnection().receiveNetworkPackage(networkPackage);
        }
    }

    /**
     * Receives a network package from the connected node
     * and add it to the package queue.
     * @param networkPackage The received package.
     * @since API Version: 1.0.0
     */
    default void receiveNetworkPackage(INetworkPackage networkPackage) {
        getPackageQueue().add(networkPackage);
    }
}
