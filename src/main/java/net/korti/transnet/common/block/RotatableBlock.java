package net.korti.transnet.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class RotatableBlock extends ModBlockContainer {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public RotatableBlock(Material materialIn, String name, Class<? extends TileEntity> tileEntityClass) {
        super(materialIn, name, tileEntityClass);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        final EnumFacing enumFacing = EnumFacing.getFront(meta);
        return super.getDefaultState().withProperty(FACING, enumFacing);
    }
}
