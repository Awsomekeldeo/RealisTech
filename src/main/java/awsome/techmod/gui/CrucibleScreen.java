package awsome.techmod.gui;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import awsome.techmod.Reference;
import awsome.techmod.inventory.container.ContainerCrucible;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CrucibleScreen extends ContainerScreen<ContainerCrucible> {
	private ResourceLocation GUI = new ResourceLocation(Reference.MODID, "textures/gui/crucible.png");
	public ITextComponent name;
	

	public CrucibleScreen(ContainerCrucible screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.name = titleIn;
		this.xSize = 180;
		this.ySize = 216;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (mouseX >= i + 147 && mouseX <= i + 154 && mouseY >= j + 29 && mouseY <= j + 102)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format("techmod.info.temperature", ((ContainerCrucible)this.container).getTemperature()));
            this.renderTooltip(lines, mouseX, mouseY);
        }
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relativeX = (this.width - this.xSize) / 2;
		int relativeY = (this.height - this.ySize) / 2;
		this.blit(relativeX, relativeY, 0, 0, this.xSize, this.ySize);
		int i = this.guiLeft;
		int j = this.guiTop;
		int l = ((ContainerCrucible)this.container).getTemperatureScaled();
		this.blit(i + 148, j + 33 + 62 - l, 180, 1 + 62 - l, 6, l);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(this.name.getUnformattedComponentText(), 8, 6, 0x404040);
		font.drawString(this.playerInventory.getDisplayName().getUnformattedComponentText(), 10, ySize - 96, 0x404040);
	}
}
