package awsome.realistech.blocks;

import net.minecraft.block.GrassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ClayGrassBlock extends GrassBlock {

	public ClayGrassBlock() {
		super(Properties.create(Material.ORGANIC)
				.hardnessAndResistance(0.6f)
				.sound(SoundType.PLANT)
				.harvestTool(ToolType.SHOVEL)
				.harvestLevel(-1));
	}
	
}
