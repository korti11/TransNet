package net.korti.transnet.common.item;

import net.korti.transnet.TransNet;
import net.korti.transnet.common.constants.ModInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public abstract class ModItem extends Item {

    private TextFormatting nameColor;

    public ModItem(String name, TextFormatting nameColor) {
        this.nameColor = nameColor;

        this.setCreativeTab(TransNet.transNetTab);
        this.setUnlocalizedName(ModInfo.MOD_ID + "." + name);
        this.setRegistryName(ModInfo.MOD_ID, name);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return nameColor.toString() + super.getItemStackDisplayName(stack) + TextFormatting.RESET;
    }
}
