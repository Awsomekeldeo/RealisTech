package awsome.realistech.client.gui.containter.screen;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import awsome.realistech.Reference;
import awsome.realistech.inventory.container.KilnContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class KilnScreen extends ContainerScreen<KilnContainer> {
	private ResourceLocation GUI = new ResourceLocation(Reference.MODID, "textures/gui/kiln.png");
	public ITextComponent name;
	
	public KilnScreen(KilnContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.name = titleIn;
		this.xSize = 176;
		this.ySize = 185;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (mouseX >= i + 151 && mouseX <= i + 158 && mouseY >= j + 15 && mouseY <= j + 88)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format("realistech.info.temperature", ((KilnContainer)this.container).getTemperature()));
            this.renderTooltip(lines, mouseX, mouseY);
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
			this.blit(i + 38, j + 56 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		int l = this.container.getTemperatureScaled();
		this.blit(i + 152, j + 19 + 62 - l, 176, 32 + 62 - l, 6, l);
		
		int m = this.container.getFireProgressScaled();
		this.blit(i + 70, j + 51, 176, 14, m + 1, 16);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(this.name.getUnformattedComponentText(), 8, 6, 0x404040);
		font.drawString(this.playerInventory.getDisplayName().getUnformattedComponentText(), 8, ySize - 94, 0x404040);
	}
}
