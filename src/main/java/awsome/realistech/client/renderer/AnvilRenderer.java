package awsome.realistech.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import awsome.realistech.tileentity.AnvilTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;

@OnlyIn(Dist.CLIENT)
public class AnvilRenderer extends TileEntityRenderer<AnvilTileEntity> {

	public AnvilRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}
	
	@Override
	public void render(AnvilTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Direction direction = tileEntityIn.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
		NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
		
		TileEntity te = tileEntityIn.getWorld().getTileEntity(tileEntityIn.getPos());
		
		te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
			for (int i = 0; i < h.getSlots(); i++) {
				inventory.set(i, h.getStackInSlot(i));
			}
		});
		
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.get(i);
			if (stack != ItemStack.EMPTY) {
				matrixStackIn.push();
				if (i == 0) {
					matrixStackIn.translate(0.5, (226d/256d), 0.5);
					matrixStackIn.scale(1.0f, 1.0f, 1.0f);
					Direction direction1 = Direction.byHorizontalIndex((i + direction.getHorizontalIndex()) % 4);
					float f = -direction1.getHorizontalAngle();
					matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f - 180.0f));
					matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));
					matrixStackIn.translate(0.25D, 0.0D, 0.0D);
					matrixStackIn.scale(0.375f, 0.375f, 0.375f);
				}else{
					matrixStackIn.translate(0.5, (226d/256d), 0.5);
					matrixStackIn.scale(1.0f, 1.0f, 1.0f);
					Direction direction1 = Direction.byHorizontalIndex((i + direction.getHorizontalIndex()) % 4);
					float f = -direction1.getHorizontalAngle();
					matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f - 90.0f));
					matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0f));
					matrixStackIn.translate(-0.1875D, 0.0D, 0.0D);
					matrixStackIn.scale(0.375f, 0.375f, 0.375f);
				}
				Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
				matrixStackIn.pop();
			}
		}
		
	}
}
