package net.korti.transnet.common.item;

import net.korti.transnet.common.constants.ItemInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemWrench extends ModItem {

    public ItemWrench() {
        super(ItemInfo.WRENCH, TextFormatting.YELLOW);

        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side,
                                           float hitX, float hitY, float hitZ, EnumHand hand) {
        final IBlockState state = world.getBlockState(pos);
        final Block affectedBlock = state.getBlock();
        affectedBlock.rotateBlock(world, pos, side);
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }
}
