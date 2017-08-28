package net.korti.transnet.api.impl;

import net.korti.transnet.api.NetworkHandler;
import net.korti.transnet.api.constants.NBT;
import net.korti.transnet.api.constants.NetworkMessages;
import net.korti.transnet.api.info.INetworkNodeInfo;
import net.korti.transnet.api.network.INetworkNode;
import net.korti.transnet.api.network.INetworkPackage;
import net.korti.transnet.api.network.IStatusMessage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @since API Version: 1.0.0
 */
public abstract class TileEntityNetworkNode extends TileEntity implements ITickable, INetworkNode, INetworkNodeInfo {

    private BlockPos networkNode;
    private final Queue<INetworkPackage> packageQueue;

    public TileEntityNetworkNode() {
        this.networkNode = null;
        this.packageQueue = new LinkedList<>();
    }

    //region TileEntity
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.writeNetworkNodeToNBT(compound);
        return super.writeToNBT(compound);
    }

    private void writeNetworkNodeToNBT(NBTTagCompound compound) {
        if(networkNode != null) {
            compound.setInteger(NBT.NETWORK_X, networkNode.getX());
            compound.setInteger(NBT.NETWORK_Y, networkNode.getY());
            compound.setInteger(NBT.NETWORK_Z, networkNode.getZ());
        }
    }

    protected void writeSyncData(NBTTagCompound compound) {
        writeNetworkNodeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readNetworkNodeFromNBT(compound);
    }

    private void readNetworkNodeFromNBT(NBTTagCompound compound) {
        if (compound.hasKey(NBT.NETWORK_X) && compound.hasKey(NBT.NETWORK_Y) && compound.hasKey(NBT.NETWORK_Z)) {
            int x = compound.getInteger(NBT.NETWORK_X);
            int y = compound.getInteger(NBT.NETWORK_Y);
            int z = compound.getInteger(NBT.NETWORK_Z);
            this.networkNode = new BlockPos(x, y, z);
        }
    }

    protected void readSyncData(NBTTagCompound compound) {
        readNetworkNodeFromNBT(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeSyncData(compound);
        return new SPacketUpdateTileEntity(getPos(), -1, compound);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.readSyncData(pkt.getNbtCompound());
    }

    protected void syncClient() {
        if (!getWorld().isRemote) {
            markDirty();
            IBlockState state = getWorld().getBlockState(getPos());
            getWorld().notifyBlockUpdate(getPos(), state, state, 3);
        }
    }
    //endregion

    //region INetworkNodeInfo
    @Override
    public boolean isConnected() {
        return networkNode != null;
    }
    //endregion

    //region INetworkNode
    @Override
    public IStatusMessage connectToNode(INetworkNode node, boolean isOtherValid, boolean simulate) {
        if (node == null || !(node instanceof TileEntity)) {
            return new StatusMessage(false, NetworkMessages.CAN_NOT_CONNECT);
        } else if (this == node) {
            return new StatusMessage(false, NetworkMessages.SAME_NODE);
        }

        if (!isOtherValid) {
            IStatusMessage message = node.connectToNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }

        if (!simulate) {
            if (networkNode != null) {
                disconnectFromNode();
            }
            networkNode = ((TileEntity) node).getPos();
            syncClient();
        }

        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_CONNECTED);
    }

    @Override
    public IStatusMessage disconnectFromNode(INetworkNode node, boolean isOtherDisconnected, boolean simulate) {
        if (node == null || getConnection() == null || getConnection() != node) {
            return new StatusMessage(false, NetworkMessages.NOT_CONNECTED);
        }

        if (!isOtherDisconnected) {
            IStatusMessage message = node.disconnectFromNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }

        if (!simulate) {
            networkNode = null;
            syncClient();
        }

        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_DISCONNECTED);
    }

    @Override
    public INetworkNode getConnection() {
        return NetworkHandler.getNetworkNode(world, networkNode);
    }

    @Override
    public void savePositionToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger(NBT.NETWORK_X, getPos().getX());
        tagCompound.setInteger(NBT.NETWORK_Y, getPos().getY());
        tagCompound.setInteger(NBT.NETWORK_Z, getPos().getZ());
    }

    @Override
    public Queue<INetworkPackage> getPackageQueue() {
        return this.packageQueue;
    }
    //endregion

    //region ITickable
    @Override
    public void update() {
        if (!getWorld().isRemote) {
            handleNetworkPackages();
        }
    }
    //endregion
}
