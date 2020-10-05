package awsome.realistech.client.gui.containter.screen;

import awsome.realistech.inventory.container.KnappingContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class KnappingScreen extends AbstractHandworkScreen<KnappingContainer> {

	public KnappingScreen(KnappingContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}
	
}
