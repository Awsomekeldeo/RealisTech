package awsome.realistech.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ModOreBlock extends Block {
	
	private static final List<ModOreBlock> ORES = new ArrayList<>();
	
	public ModOreBlock(int harvestLevel) {
		super(Properties.create(Material.ROCK)
			.sound(SoundType.STONE)
			.harvestLevel(harvestLevel)
			.harvestTool(ToolType.PICKAXE)
			.hardnessAndResistance(3.0f)
		);
		ORES.add(this);
	}
	
	public static List<ModOreBlock> getOreList() {
		return Collections.unmodifiableList(ORES);
	}
}
