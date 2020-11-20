package awsome.realistech.client.gui.containter.screen;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import awsome.realistech.Reference;
import awsome.realistech.inventory.container.BloomeryContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BloomeryScreen extends ContainerScreen<BloomeryContainer> {
	
	private ResourceLocation GUI = new ResourceLocation(Reference.MODID, "textures/gui/bloomery.png");
	private ResourceLocation OVERLAY = new ResourceLocation(Reference.MODID, "textures/gui/invalid_multiblock_overlay.png");
	public ITextComponent name;

	public BloomeryScreen(BloomeryContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.name = titleIn;
		this.xSize = 176;
		this.ySize = 185;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		if (container.multiblockFormed) {
			this.renderHoveredToolTip(mouseX, mouseY);
			int i = (this.width - this.xSize) / 2;
	        int j = (this.height - this.ySize) / 2;
	        if (mouseX >= i + 151 && mouseX <= i + 158 && mouseY >= j + 15 && mouseY <= j + 88 && container.multiblockFormed)
	        {
	            List<String> lines = Lists.newArrayList();
	            lines.add(I18n.format("realistech.info.temperature", this.container.getTemperature()));
	            this.renderTooltip(lines, mouseX, mouseY);
	        }
		}
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
			this.blit(i + 118, j + 56 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		
		int l = this.container.getTemperatureScaled();
		this.blit(i + 152, j + 19 + 62 - l, 176, 39 + 62 - l, 6, l);
		
		if (this.container.getAir() > 0) {
			int m = this.container.getAirScaled();
			this.blit(i + 12, j + 13 + 76 - m, 176, 101 + 76 - m, 7, m);
		}
		
		if (this.container.getBloomProgress() > 0) {
			int n = this.container.getBloomProgressScaled();
			this.blit(i + 55, j + 39, 176, 14, 20, n);
		}
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.name.getUnformattedComponentText();
		font.drawString(s, (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6, 0x404040);
		font.drawString(this.playerInventory.getDisplayName().getUnformattedComponentText(), 8, ySize - 96 + 3, 0x404040);
		if (!container.multiblockFormed) {
			this.minecraft.getTextureManager().bindTexture(OVERLAY);
			this.blit(5, 40, 0, 0, 164, 197);
			String s1 = I18n.format("realistech.invalidMultiblock");
			font.drawString(s1, (this.xSize / 2 - this.font.getStringWidth(s1) / 2), 62, 0xFF0000);
			String s2 = I18n.format("realistech.invalidMultiblockDesc");
			font.drawSplitString(s2, (this.xSize / 2 - this.font.getStringWidth(s2) / 2) + (154 - this.font.getStringWidth(s2) / 2), 83, 154, 0x404040);
		}
	}

}
