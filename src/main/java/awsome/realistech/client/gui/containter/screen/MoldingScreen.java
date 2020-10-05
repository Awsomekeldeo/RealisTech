package awsome.realistech.client.gui.containter.screen;

import awsome.realistech.inventory.container.MoldingContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class MoldingScreen extends AbstractHandworkScreen<MoldingContainer> {
	
	public MoldingScreen(MoldingContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}
}
