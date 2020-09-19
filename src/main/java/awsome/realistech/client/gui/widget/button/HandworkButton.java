package awsome.realistech.client.gui.widget.button;

import com.mojang.blaze3d.systems.RenderSystem;

import awsome.realistech.network.RealistechPacketHandler;
import awsome.realistech.network.packet.SConsumeHandworkItemPacket;
import awsome.realistech.network.packet.SSetSlotWithOffsetPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class HandworkButton extends Widget {
	
	public ResourceLocation texture;
	public int xTexStart;
	public int yTexStart;
	public int xDiffTex;
	public int yDiffTex;
	public int index;
	public boolean stateTriggered;

	public HandworkButton(int x, int y, int index) {
		super(x, y, 16, 16, "");
		this.stateTriggered = false;
		this.index = index;
	}
	
	public void initTextureValues(int xStart, int yStart, int xDiffTexIn, int yDiffTexIn, ResourceLocation texture) {
		this.xTexStart = xStart;
		this.yTexStart = yStart;
		this.xDiffTex = xDiffTexIn;
		this.yDiffTex = yDiffTexIn;
		this.texture = texture;
	}
	
	public void activate() {
		this.stateTriggered = true;
		RealistechPacketHandler.sendToServer(new SSetSlotWithOffsetPacket(this.index, ItemStack.EMPTY, 37));
		RealistechPacketHandler.sendToServer(new SConsumeHandworkItemPacket(this.stateTriggered));
	}
	
	public boolean isActivated() {
		return this.stateTriggered;
	}
	
	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(this.texture);
		RenderSystem.disableDepthTest();
		int i = this.xTexStart;
		int j = this.yTexStart;
		if (this.stateTriggered) {
			i += this.xDiffTex;
		}

		if (this.isHovered()) {
			j += this.yDiffTex;
		}

		this.blit(this.x, this.y, i, j, this.width, this.height);
		RenderSystem.enableDepthTest();
	}
	
	@Override
	protected boolean isValidClickButton(int p_isValidClickButton_1_) {
		if (this.stateTriggered == true) {
			return false;
		}else {
			return super.isValidClickButton(p_isValidClickButton_1_);
		}
	}
	
	public boolean mousePressed (double mouseX, double mouseY) {
		return this.clicked(mouseX, mouseY);
	}
}
