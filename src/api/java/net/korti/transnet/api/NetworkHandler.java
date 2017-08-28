package net.korti.transnet.api;

import net.korti.transnet.api.network.INetworkNode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korti on 28.08.2017.
 */
public final class NetworkHandler {

    public static INetworkNode getNetworkNode(World world, BlockPos pos) {
        if (world != null && pos != null) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof INetworkNode) {
                return (INetworkNode) tileEntity;
            }
        }
        return null;
    }

    public static List<INetworkNode> getNetworkNodes(World world, List<BlockPos> posList) {
        List<INetworkNode> nodes = new LinkedList<>();
        for (BlockPos pos : posList) {
            nodes.add(getNetworkNode(world, pos));
        }
        return nodes;
    }

}
