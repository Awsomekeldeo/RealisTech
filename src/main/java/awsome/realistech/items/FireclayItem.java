package awsome.realistech.items;

import java.util.List;

import awsome.realistech.setup.ModSetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FireclayItem extends Item {

	public FireclayItem() {
		super(new Item.Properties().group(ModSetup.REALISTECH_MATERIALS));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip.realistech.fireclay").applyTextStyle(TextFormatting.DARK_GRAY));
	}

}
