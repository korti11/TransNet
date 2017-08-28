package net.korti.transnet.api.impl;

import net.korti.transnet.api.NetworkHandler;
import net.korti.transnet.api.constants.NBT;
import net.korti.transnet.api.constants.NetworkMessages;
import net.korti.transnet.api.info.INetworkRelayInfo;
import net.korti.transnet.api.network.INetworkNode;
import net.korti.transnet.api.network.INetworkPackage;
import net.korti.transnet.api.network.INetworkRelay;
import net.korti.transnet.api.network.IStatusMessage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @since API Version: 1.0.0
 */
public abstract class TileEntityNetworkRelay extends TileEntity implements ITickable, INetworkRelay, INetworkRelayInfo {

    private final List<BlockPos> networkNodes;
    private final int maxConnections;
    private final int range;
    private final Queue<INetworkPackage> packageQueue;

    public TileEntityNetworkRelay(int maxConnections, int range) {
        this.networkNodes = new LinkedList<>();
        this.maxConnections = maxConnections;
        this.range = range;
        this.packageQueue = new LinkedList<>();
    }

    //region TileEntity
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.writeNodesToNBT(compound);
        return super.writeToNBT(compound);
    }

    private void writeNodesToNBT(NBTTagCompound tagCompound) {
        if (!networkNodes.isEmpty()) {
            NBTTagList networkNodeTags = new NBTTagList();
            for (BlockPos node : networkNodes) {
                NBTTagCompound nodeTag = new NBTTagCompound();
                nodeTag.setInteger(NBT.NETWORK_X, node.getX());
                nodeTag.setInteger(NBT.NETWORK_Y, node.getY());
                nodeTag.setInteger(NBT.NETWORK_Z, node.getZ());
                networkNodeTags.appendTag(nodeTag);
            }
            tagCompound.setTag(NBT.NETWORK_NODES, networkNodeTags);
        }
    }

    protected void writeSyncData(NBTTagCompound tagCompound) {
        writeNodesToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readNodesFromNBT(compound);
    }

    private void readNodesFromNBT(NBTTagCompound tagCompound) {
        networkNodes.clear();
        NBTTagList networkNodeTags = tagCompound.getTagList(NBT.NETWORK_NODES, 10);
        for (NBTBase tag : networkNodeTags) {
            if (tag instanceof NBTTagCompound) {
                NBTTagCompound nodeTag = (NBTTagCompound) tag;
                int x = nodeTag.getInteger(NBT.NETWORK_X);
                int y = nodeTag.getInteger(NBT.NETWORK_Y);
                int z = nodeTag.getInteger(NBT.NETWORK_Z);
                networkNodes.add(new BlockPos(x, y, z));
            }
        }
    }

    protected void readSyncData(NBTTagCompound tagCompound) {
        readNodesFromNBT(tagCompound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeSyncData(compound);
        return new SPacketUpdateTileEntity(getPos(), -1, compound);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readSyncData(pkt.getNbtCompound());
    }

    protected void syncClient() {
        if (!getWorld().isRemote) {
            markDirty();
            IBlockState state = getWorld().getBlockState(getPos());
            getWorld().notifyBlockUpdate(getPos(), state, state, 3);
        }
    }
    //endregion

    //region INetworkRelayInfo
    @Override
    public int getNetworkConnections() {
        return this.networkNodes.size();
    }

    @Override
    public int getMaxNetworkConnections() {
        return this.maxConnections;
    }
    //endregion

    //region INetworkRelay
    @Override
    public List<INetworkNode> getConnections() {
        return NetworkHandler.getNetworkNodes(getWorld(), this.networkNodes);
    }

    @Override
    public IStatusMessage connectToNode(INetworkNode node, boolean isOtherValid, boolean simulate) {
        if (!(node instanceof TileEntity)) {
            return new StatusMessage(false, NetworkMessages.CAN_NOT_CONNECT);
        } else if (networkNodes.size() == maxConnections) {
            return new StatusMessage(false, NetworkMessages.MAX_CONNECTIONS, maxConnections);
        } else if (this == node) {
            return new StatusMessage(false, NetworkMessages.SAME_NODE);
        } else if (networkNodes.contains(((TileEntity) node).getPos())) {
            return new StatusMessage(false, NetworkMessages.ALREADY_CONNECTED);
        }

        if (!isOtherValid) {
            if (!this.isInRange(node) &&
                    (!(node instanceof INetworkRelay) || !(((INetworkRelay) node).isInRange(this)))) {
                return new StatusMessage(false, NetworkMessages.OUT_OF_RANGE);
            }

            IStatusMessage message = node.connectToNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }

        if (!simulate) {
            networkNodes.add(((TileEntity) node).getPos());
            syncClient();
        }

        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_CONNECTED);
    }

    @Override
    public IStatusMessage disconnectFromNode(INetworkNode node, boolean isOtherDisconnected, boolean simulate) {
        if (!(node instanceof TileEntity) || !networkNodes.contains(((TileEntity) node).getPos())) {
            return new StatusMessage(false, NetworkMessages.NOT_CONNECTED);
        }

        if (!isOtherDisconnected) {
            IStatusMessage message = node.disconnectFromNode(this, true, simulate);
            if (!message.isSuccessful()) {
                return message;
            }
        }

        if (!simulate) {
            networkNodes.remove(((TileEntity) node).getPos());
            syncClient();
        }

        return new StatusMessage(true, NetworkMessages.SUCCESSFUL_DISCONNECTED);
    }

    @Override
    public boolean isInRange(INetworkNode node) {
        if(node instanceof TileEntity) {
            double range = Math.sqrt(((TileEntity) node)
                    .getDistanceSq(getPos().getX(), getPos().getY(), getPos().getZ()));
            return range < (this.range / 2);
        }
        return false;
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
