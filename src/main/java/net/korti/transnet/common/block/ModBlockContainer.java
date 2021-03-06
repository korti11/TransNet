package net.korti.transnet.common.block;

import net.korti.transnet.TransNet;
import net.korti.transnet.common.constants.ModInfo;
import net.korti.transnet.common.helper.LogHelper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class ModBlockContainer extends BlockContainer {

    private final Class<? extends TileEntity> tileEntityClass;

    public ModBlockContainer(Material materialIn, String name, Class<? extends TileEntity> tileEntityClass) {
        super(materialIn);
        this.tileEntityClass = tileEntityClass;
        this.setCreativeTab(TransNet.transNetTab);
        this.setUnlocalizedName(ModInfo.MOD_ID + "." + name);
        this.setRegistryName(ModInfo.MOD_ID, name);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        try {
            return tileEntityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LogHelper.e(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
