package awsome.realistech.client.gui.containter.screen;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import awsome.realistech.Reference;
import awsome.realistech.inventory.container.BoilerContainer;
import awsome.realistech.util.RenderUtil;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BoilerScreen extends ContainerScreen<BoilerContainer> {
	
	private ResourceLocation GUI = new ResourceLocation(Reference.MODID, "textures/gui/boiler.png");
	public ITextComponent name;

	public BoilerScreen(BoilerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.name = titleIn;
		this.xSize = 176;
		this.ySize = 177;
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
		int l = ((BoilerContainer)this.container).getTemperatureScaled();
		this.blit(i + 152, j + 16 + 62 - l, 176, 1 + 62 - l, 6, l);
		
		RenderUtil.renderFluidTank(this, this.container.boilerTanks.get(0), 16000, 76, 19, 16, 63, 100);
        RenderUtil.renderFluidTank(this, this.container.boilerTanks.get(1), 16000, 98, 19, 16, 63, 100);
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (mouseX >= i + 151 && mouseX <= i + 158 && mouseY >= j + 11 && mouseY <= j + 85)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format("realistech.info.temperature", ((BoilerContainer)this.container).getTemperature()));
            this.renderTooltip(lines, mouseX, mouseY);
        }
        
        if (mouseX >= i + 76 && mouseX <= i + 91 && mouseY >= j + 19 && mouseY <= j + 81)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format(this.container.boilerTanks.get(0).getTranslationKey()) + ": " + I18n.format("realistech.info.bucketUnit", this.container.boilerTanks.get(0).getAmount()));
            this.renderTooltip(lines, mouseX, mouseY);
        }
        
        if (mouseX >= i + 98 && mouseX <= i + 113 && mouseY >= j + 19 && mouseY <= j + 81)
        {
            List<String> lines = Lists.newArrayList();
            lines.add(I18n.format(this.container.boilerTanks.get(1).getTranslationKey()) + ": " + I18n.format("realistech.info.bucketUnit", this.container.boilerTanks.get(1).getAmount()));
            this.renderTooltip(lines, mouseX, mouseY);
        }
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int i = this.guiLeft;
		int j = this.guiTop;
		
		this.blit(i + 76, j + 19, 182, 0, 16, 63);
        this.blit(i + 98, j + 19, 182, 0, 16, 63);
        
		font.drawString(this.name.getUnformattedComponentText(), 8, 6, 0x404040);
		font.drawString(this.playerInventory.getDisplayName().getUnformattedComponentText(), 8, ySize - 94, 0x404040);
	}
}
