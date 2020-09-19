package awsome.realistech.client.gui.containter.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import awsome.realistech.Reference;
import awsome.realistech.client.gui.widget.button.HandworkButton;
import awsome.realistech.inventory.container.HandworkContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class HandworkScreen extends ContainerScreen<HandworkContainer> {
	
	private ResourceLocation GUI = new ResourceLocation(Reference.MODID, "textures/gui/molding.png");
	public ITextComponent name;
	
	@Override
	protected void init() {
		super.init();
		this.layoutCraftingButtons();
		this.initButtonTextures(this.container.buttonTextureX, this.container.buttonTextureY, this.container.buttonXDiffTex, this.container.buttonYDiffTex, new ResourceLocation(Reference.MODID, "textures/gui/widgets/handwork_icons.png"));
	}
	
	public HandworkScreen(HandworkContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.name = titleIn;
		this.xSize = 176;
		this.ySize = 194;
	}
	
	@Override
	protected boolean itemStackMoved(int keyCode, int scanCode) {
		if (this.hoveredSlot == this.container.getSlot(((HandworkContainer) container).getHandSlot())) {
			return false;
		}else {
			return super.itemStackMoved(keyCode, scanCode);
		}
	}
	
	public void initButtonTextures(int xStart, int yStart, int xDiffTexIn, int yDiffTexIn, ResourceLocation texture) {
		for (int i = 0; i < this.buttons.size(); i++) {
			if (this.buttons.get(i) instanceof HandworkButton) {
				((HandworkButton) this.buttons.get(i)).initTextureValues(xStart, yStart, xDiffTexIn, yDiffTexIn, texture);
			}
		}
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
	}
	
	private void layoutCraftingButtons() {
		int index = 0;
		for (int i = 0; i < 5; i ++) {
			for (int j = 0; j < 5; j++) {
				int l = (this.width - this.xSize) / 2;
				int m = (this.height - this.ySize) / 2;
				int x = j * 16 + l + 21;
				int y = i * 16 + m + 17;
				this.addButton(new HandworkButton(x, y, index));
				index++;
			}
		}
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int mouseButton,
			double mouseX2, double mouseY2) {
		for (int i = 0; i < this.buttons.size(); i ++) {
			if (this.buttons.get(i) instanceof HandworkButton) {
				HandworkButton button = (HandworkButton) this.buttons.get(i);
				if (button.mousePressed(mouseX, mouseY)) {
					if (!button.isActivated()) {
						button.activate();
						button.playDownSound(this.minecraft.getSoundHandler());
						return true;
					}else {
						return false;
					}
				}
			}
		}
		return super.mouseDragged(mouseX, mouseY, mouseButton, mouseX2, mouseY2);
	}
	
	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		for (int i = 0; i < this.buttons.size(); i ++) {
			if (this.buttons.get(i) instanceof HandworkButton) {
				HandworkButton button = (HandworkButton) this.buttons.get(i);
				if (button.mousePressed(p_mouseClicked_1_, p_mouseClicked_3_)) {
					if (!button.isActivated()) {
						button.activate();
						button.playDownSound(this.minecraft.getSoundHandler());
						return true;
					}else {
						return false;
					}
				}
			}
		}
		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relativeX = (this.width - this.xSize) / 2;
		int relativeY = (this.height - this.ySize) / 2;
		this.blit(relativeX, relativeY, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		font.drawString(this.name.getUnformattedComponentText(), 8, 6, 0x404040);
		font.drawString(this.playerInventory.getDisplayName().getUnformattedComponentText(), 8, ySize - 96, 0x404040);
		for (int i = 0; i < this.container.inventorySlots.size(); i++ ) {
			Slot currentSlot = this.container.inventorySlots.get(i);
			font.drawString("" + i, currentSlot.xPos, currentSlot.yPos, 0xffffd4);
		}
	}
	
}
