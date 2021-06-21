package awsome.realistech.registry;

import java.util.function.Function;
import java.util.function.Supplier;

import awsome.realistech.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/* Modified Version of TCon FluidDefferedRegisterer Class
 * This class and all nested classes are licensed seperately under the MIT liscense instead of the GNU-GPLv3 license.
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

public class FluidRegisterer {

	private static final Item.Properties BUCKET_PROPERTIES = new Item.Properties().group(ItemGroup.MISC).maxStackSize(1).containerItem(Items.BUCKET);
	private final DeferredRegister<Block> blockRegistry = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
	private final DeferredRegister<Item> itemRegistry = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);
	private final DeferredRegister<Fluid> fluidRegistry = DeferredRegister.create(ForgeRegistries.FLUIDS, Reference.MODID);
	
	public void register(IEventBus bus) {
		fluidRegistry.register(bus);
		blockRegistry.register(bus);
		itemRegistry.register(bus);
	}
	
	public <I extends Fluid> RegistryObject<I> registerFluid(final String name, final Supplier<? extends I> sup) {
		return fluidRegistry.register(name, sup);
	}
	
	/**
	 * Registers a fluid with still, flowing, block, and bucket
	 * @param name     Fluid name
	 * @param builder  Properties builder
	 * @param still    Function to create still from the properties
	 * @param flowing  Function to create flowing from the properties
	 * @param block    Function to create block from the fluid supplier
	 * @param <F>      Fluid type
	 * @return  Fluid object
	 */
	public <F extends ForgeFlowingFluid> FluidObject<F> register(final String name, Builder builder, Function<Properties,? extends F> still, Function<Properties,? extends F> flowing, Function<Supplier<? extends FlowingFluid>,? extends FlowingFluidBlock> block) {
		// have to create still and flowing later, they need props
		DelayedSupplier<F> stillSup = new DelayedSupplier<>();
		DelayedSupplier<F> flowingSup = new DelayedSupplier<>();
		// create flowing and bucket, they just need a still supplier
		RegistryObject<FlowingFluidBlock> blockObj = blockRegistry.register(name + "_fluid", () -> block.apply(stillSup));
		RegistryObject<BucketItem> bucketObj = itemRegistry.register(name + "_bucket", () -> new BucketItem(stillSup, BUCKET_PROPERTIES));
		// create props with the suppliers
		Properties props = builder.build(stillSup, flowingSup, blockObj, bucketObj);
		// create fluids now that we have props
		stillSup.supplier = registerFluid(name, () -> still.apply(props));
		flowingSup.supplier = registerFluid("flowing_" + name, () -> flowing.apply(props));
		// return the final nice object
		return new FluidObject<>(stillSup.supplier, flowingSup.supplier, blockObj, bucketObj);
	}
	
	/**
	 * Registers a fluid with still, flowing, block, and bucket using the default fluid block
	 * @param name       Fluid name
	 * @param builder    Properties builder
	 * @param still      Function to create still from the properties
	 * @param flowing    Function to create flowing from the properties
	 * @param material   Block material
	 * @param lightLevel Block light level
	 * @param <F>      Fluid type
	 * @return  Fluid object
	 */
	public <F extends ForgeFlowingFluid> FluidObject<F> register(final String name, FluidAttributes.Builder builder, final Function<Properties,? extends F> still, final Function<Properties,? extends F> flowing, Material material, int lightLevel) {
		return register(name, new Builder(builder).explosionResistance(100f), still, flowing, (fluid) -> new FlowingFluidBlock(fluid, Block.Properties.create(material).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops().lightValue(lightLevel)));
	}
	
	/**
	 * Registers a fluid with still, flowing, block, and bucket using the default Forge fluid
	 * @param name       Fluid name
	 * @param builder    Properties builder
	 * @param material   Block material
	 * @param lightLevel Block light level
	 * @return  Fluid object
	 */
	public FluidObject<ForgeFlowingFluid> register(final String name, FluidAttributes.Builder builder, Material material, int lightLevel) {
		return register(name, builder, ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new, material, lightLevel);
	}

	private static class DelayedSupplier<T> implements Supplier<T> {
		public Supplier<T> supplier;

		@Override
		public T get() {
			return supplier.get();
		}
	}

	public static class Builder {
		private FluidAttributes.Builder attributes;
		private boolean canMultiply = false;
		private int slopeFindDistance = 4;
		private int levelDecreasePerBlock = 1;
		private float explosionResistance = 1;
		private int tickRate = 5;

		public Builder(FluidAttributes.Builder attributes) {
			this.attributes = attributes;
		}

		public Builder canMultiply() {
			canMultiply = true;
			return this;
		}

		public Builder slopeFindDistance(int slopeFindDistance) {
			this.slopeFindDistance = slopeFindDistance;
			return this;
		}

		public Builder levelDecreasePerBlock(int levelDecreasePerBlock) {
			this.levelDecreasePerBlock = levelDecreasePerBlock;
			return this;
		}

		public Builder explosionResistance(float explosionResistance) {
			this.explosionResistance = explosionResistance;
			return this;
		}

		public Builder tickRate(int tickRate) {
			this.tickRate = tickRate;
			return this;
		}

		/**
		 * Builds Forge fluid properties from this builder
		 * @param still    Still fluid supplier
		 * @param flowing  Flowing supplier
		 * @param block    Block supplier
		 * @param bucket   Bucket supplier
		 * @return  Forge fluid properties
		 */
		public ForgeFlowingFluid.Properties build(Supplier<? extends Fluid> still, Supplier<? extends Fluid> flowing, Supplier<? extends FlowingFluidBlock> block, Supplier<? extends Item> bucket) {
			ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(still, flowing, this.attributes)
					.slopeFindDistance(this.slopeFindDistance)
					.levelDecreasePerBlock(this.levelDecreasePerBlock)
					.explosionResistance(this.explosionResistance)
					.tickRate(this.tickRate)
					.block(block)
					.bucket(bucket);
			if (this.canMultiply) {
				properties.canMultiply();
			}
			return properties;
		}
	}
}
