package awsome.realistech.client.renderer;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import awsome.realistech.tileentity.BellowsTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class BellowsRenderer extends TileEntityRenderer<BellowsTileEntity> {

	public BellowsRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(BellowsTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		
		if (tileEntityIn.shouldAnimate()) {
			
			IBakedModel modelBellows = Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(tileEntityIn.getBlockState());
			List<BakedQuad> quads = modelBellows.getQuads(tileEntityIn.getBlockState(), null, null, tileEntityIn.getModelData());
			RenderType renderType = RenderTypeLookup.getRenderType(tileEntityIn.getBlockState());
			IVertexBuilder vertexBuilder = bufferIn.getBuffer(renderType);
			Direction direction = tileEntityIn.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
			
			float xScale = 1;
			float zScale = 1;
			
			float innerTranslateX = 0;
			float innerTranslateZ = 0;
			
			float boardTranslateX = 0;
			float boardTranslateZ = 0;
			
			float f = tileEntityIn.animationTimer * 0.1f;
			
			if (f < 0) {
				f = 0;
			}
			
			int i = 0;
			
			if (f != 0) {
				if (direction.equals(Direction.EAST)) {
					xScale = 1 - f;
					innerTranslateX = -((2/16f) * f) + f;
					boardTranslateX = (12/16f * f);
				}else if(direction.equals(Direction.WEST)) {
					xScale = 1 - f;
					innerTranslateX = -((14/16f) * f) + f;
					boardTranslateX = -(12/16f * f);
				}else if(direction.equals(Direction.NORTH)) {
					zScale = 1 - f;
					innerTranslateZ = -((14/16f) * f) + f;
					boardTranslateZ = -(12/16f * f);
				}else if(direction.equals(Direction.SOUTH)) {
					zScale = 1 - f;
					innerTranslateZ = -((2/16f) * f) + f;
					boardTranslateZ = (12/16f * f);
				}
			}
			
			for(BakedQuad bakedquad : quads) {
				
				
				matrixStackIn.push();
				MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
				
				
				if (i / 6 == 1) {
					
					matrixStackIn.translate(innerTranslateX, 0, innerTranslateZ);
					matrixStackIn.scale(xScale, 1, zScale);
										
				}else if (i / 6 == 2) {
					
					matrixStackIn.translate(boardTranslateX, 0, boardTranslateZ);
					
				}
				
				vertexBuilder.addVertexData(matrixstack$entry, bakedquad, 1, 1, 1, combinedLightIn, combinedOverlayIn, true);
				matrixStackIn.pop();
				
				i++;
			}
		}
	}
}
