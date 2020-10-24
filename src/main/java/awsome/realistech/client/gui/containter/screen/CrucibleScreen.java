package awsome.realistech.client.gui.containter.screen;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import awsome.realistech.Reference;
import awsome.realistech.inventory.container.CrucibleContainer;
import awsome.realistech.util.RenderUtil;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CrucibleScreen extends ContainerScreen<CrucibleContainer> {
	private ResourceLocation GUI = new ResourceLocation(Reference.MODID, "textures/gui/crucible.png");
	public ITextComponent name;
	

	public CrucibleScreen(CrucibleContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.name = titleIn;
		this.xSize = 175;
		this.ySize = 214;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (mouseX >= i + 144 && mouseX <= i + 151 && mouseY >= j + 29 && mouseY <= j + 102)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format("realistech.info.temperature", ((CrucibleContainer)this.container).getTemperature()));
            this.renderTooltip(lines, mouseX, mouseY);
        }
        
        if (mouseX >= i + 8 && mouseX <= i + 49 && mouseY >= j + 40 && mouseY <= j + 61)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format("realistech.info.crucibleContents"));
            for (int k = 0; k < this.container.crucibleTanks.size(); k++) {
            	if (!(this.container.crucibleTanks.get(k).isEmpty())) { 
            		lines.add(I18n.format(this.container.crucibleTanks.get(k).getTranslationKey()) + ": " + I18n.format("realistech.info.bucketUnit", this.container.crucibleTanks.get(k).getAmount()));
            	}
            	
            	if (!(this.container.crucibleTanks.size() > 1)) {
            		lines.add(I18n.format("realistech.info.empty"));
            	}
            }
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
		int l = ((CrucibleContainer)this.container).getTemperatureScaled();
		this.blit(i + 145, j + 33 + 62 - l, 175, 1 + 62 - l, 6, l);
		
		RenderUtil.renderFluidTanks(this, this.container.crucibleTanks, 4608, 63, 31, 48, 66, 100);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(this.name.getUnformattedComponentText(), 8, 6, 0x404040);
		font.drawString(this.playerInventory.getDisplayName().getUnformattedComponentText(), 10, ySize - 96, 0x404040);
	}
}
