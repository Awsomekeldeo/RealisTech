package awsome.realistech.client.gui.containter.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import awsome.realistech.inventory.container.MediumHeatFurnaceContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MediumHeatFurnaceScreen extends ContainerScreen<MediumHeatFurnaceContainer> {
	
	public static final ResourceLocation GUI = new ResourceLocation("minecraft:textures/gui/container/furnace.png");
	public ITextComponent name;

	public MediumHeatFurnaceScreen(MediumHeatFurnaceContainer screenContainer, PlayerInventory inv,
			ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.name = titleIn;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relativeX = (this.width - this.xSize) / 2;
		int relativeY = (this.height - this.ySize) / 2;
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(relativeX, relativeY, 0, 0, this.xSize, this.ySize);
		if (this.container.isBurning()) {
			int k = this.container.getBurnLeftScaled();
			this.blit(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		
		int l = this.container.getSmeltProgressScaled();
		this.blit(i + 79, j + 34, 176, 14, l + 1, 16);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.name.getUnformattedComponentText();
		font.drawString(s, (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6, 0x404040);
		font.drawString(this.playerInventory.getDisplayName().getUnformattedComponentText(), 8, ySize - 96 + 2, 0x404040);
	}

}
