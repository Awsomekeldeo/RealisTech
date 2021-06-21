package awsome.realistech.util;

import org.lwjgl.opengl.GL11;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

/* Modified version of TCon RenderUtil Class
 * This class is licensed seperately under the MIT liscense instead of the GNU-GPLv3 license.
 * MIT License
 *
 * Copyright (c) 2020 SlimeKnights
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

public class RenderUtil {

	public Vector3f arrayToVector(JsonObject json, String name) {
		JsonArray jsonArray = JSONUtils.getJsonArray(json, name);	
		if (jsonArray.size() > 3) {
			throw new JsonParseException("Expected 3 " + name + " values, found: " + jsonArray.size());
		}

		float[] vector = new float[3];

		for (int i = 0; i < vector.length; i++) {
			vector[i] = JSONUtils.getFloat(jsonArray.get(i), name + "[" + i + "]");
		}

		return new Vector3f(vector[0], vector[1], vector[2]);
	}

	/**
	 * Renders a fluid tank with a partial fluid level
	 * @param screen    Parent screen
	 * @param stack     Fluid stack
	 * @param capacity  Tank capacity, determines height
	 * @param x         Tank X position
	 * @param y         Tank Y position
	 * @param width     Tank width
	 * @param height    Tank height
	 * @param depth     Tank depth
	 */
	public static void renderFluidTanks(ContainerScreen<?> screen, NonNullList<FluidStack> stacks, int capacity, int x, int y, int width, int height, int depth) {
		int yOffset = 0;
		for (int i = 0; i < stacks.size(); i++) {
			FluidStack stack = stacks.get(i);
			if(!stack.isEmpty()) {
				int maxY = y + height;
				int fluidHeight = Math.min(height * stack.getAmount() / capacity, height);
				renderTiledFluid(screen, stack, x, (maxY - fluidHeight) - yOffset, width, fluidHeight, depth);
				yOffset += fluidHeight;
			}
		}
	}
	
	/**
	 * Renders a fluid tank with a partial fluid level
	 * @param screen    Parent screen
	 * @param stack     Fluid stack
	 * @param capacity  Tank capacity, determines height
	 * @param x         Tank X position
	 * @param y         Tank Y position
	 * @param width     Tank width
	 * @param height    Tank height
	 * @param depth     Tank depth
	 */
	public static void renderFluidTank(ContainerScreen<?> screen, FluidStack stack, int capacity, int x, int y, int width, int height, int depth) {
		if(!stack.isEmpty()) {
			int maxY = y + height;
			int fluidHeight = Math.min(height * stack.getAmount() / capacity, height);
			if (stack.getFluid().getAttributes().isGaseous(stack)) {
				fluidHeight = height;
			}
			renderTiledFluid(screen, stack, x, (maxY - fluidHeight), width, fluidHeight, depth);
		}
	}

	public static int alpha(int c) {
		return (c >> 24) & 0xFF;
	}

	public static int red(int c) {
		return (c >> 16) & 0xFF;
	}

	public static int green(int c) {
		return (c >> 8) & 0xFF;
	}

	public static int blue(int c) {
		return (c) & 0xFF;
	}

	public static void setColorRGBA(int color) {
		float a = alpha(color) / 255.0F;
		float r = red(color) / 255.0F;
		float g = green(color) / 255.0F;
		float b = blue(color) / 255.0F;

		RenderSystem.color4f(r, g, b, a);
	}

	/**
	 * Colors and renders a fluid sprite
	 * @param screen  Parent screen
	 * @param stack   Fluid stack
	 * @param x       Fluid X
	 * @param y       Fluid Y
	 * @param width   Fluid width
	 * @param height  Fluid height
	 * @param depth   Fluid depth
	 */
	public static void renderTiledFluid(ContainerScreen<?> screen, FluidStack stack, int x, int y, int width, int height, int depth) {
		if (!stack.isEmpty()) {
			TextureAtlasSprite fluidSprite = screen.getMinecraft().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(stack.getFluid().getAttributes().getStillTexture(stack));
			int r = red(stack.getFluid().getAttributes().getColor(stack));
			int g = green(stack.getFluid().getAttributes().getColor(stack));
			int b = blue(stack.getFluid().getAttributes().getColor(stack));
			int a = alpha(stack.getFluid().getAttributes().getColor(stack));
			
			RenderUtil.setColorRGBA(stack.getFluid().getAttributes().getColor(stack));
			renderTiledTextureAtlas(screen, fluidSprite, x, y, width, height, depth, stack.getFluid().getAttributes().isGaseous(stack));
		}
	}

	/**
	 * Renders a texture atlas sprite tiled over the given area
	 * @param screen  Parent screen
	 * @param sprite      Sprite to render
	 * @param x           X position to render
	 * @param y           Y position to render
	 * @param width       Render width
	 * @param height      Render height
	 * @param depth       Render depth
	 * @param upsideDown  If true, flips the sprite
	 */
	public static void renderTiledTextureAtlas(ContainerScreen<?> screen, TextureAtlasSprite sprite, int x, int y, int width, int height, int depth, boolean upsideDown) {
		// start drawing sprites
		screen.getMinecraft().getTextureManager().bindTexture(sprite.getAtlasTexture().getTextureLocation());
		BufferBuilder builder = Tessellator.getInstance().getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		// tile vertically
		float u1 = sprite.getMinU();
		float v1 = sprite.getMinV();
		int spriteHeight = sprite.getHeight();
		int spriteWidth = sprite.getWidth();
		int startX = x + screen.getGuiLeft();
		int startY = y + screen.getGuiTop();
		do {
			int renderHeight = Math.min(spriteHeight, height);
			height -= renderHeight;
			float v2 = sprite.getInterpolatedV((16f * renderHeight) / spriteHeight);

			// we need to draw the quads per width too
			int x2 = startX;
			int widthLeft = width;
			// tile horizontally
			do {
				int renderWidth = Math.min(spriteWidth, widthLeft);
				widthLeft -= renderWidth;

				float u2 = sprite.getInterpolatedU((16f * renderWidth) / spriteWidth);
				if(upsideDown) {
					// FIXME: I think this causes tiling errors, look into it
					buildSquare(builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v1, v2);
				} else {
					buildSquare(builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v1, v2);
				}
				x2 += renderWidth;
			} while(widthLeft > 0);

			startY += renderHeight;
		} while(height > 0);

		// finish drawing sprites
		builder.finishDrawing();
		RenderSystem.enableAlphaTest();
		WorldVertexBufferUploader.draw(builder);
	}

	/**
	 * Adds a square of texture to a buffer builder
	 * @param builder  Builder instance
	 * @param x1       X start
	 * @param x2       X end
	 * @param y1       Y start
	 * @param y2       Y end
	 * @param z        Depth
	 * @param u1       Texture U start
	 * @param u2       Texture U end
	 * @param v1       Texture V start
	 * @param v2       Texture V end
	 */
	private static void buildSquare(BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
		builder.pos((double)x1, (double)y2, (double)z).tex(u1, v2).endVertex();
		builder.pos((double)x2, (double)y2, (double)z).tex(u2, v2).endVertex();
		builder.pos((double)x2, (double)y1, (double)z).tex(u2, v1).endVertex();
		builder.pos((double)x1, (double)y1, (double)z).tex(u1, v1).endVertex();
	}

}
