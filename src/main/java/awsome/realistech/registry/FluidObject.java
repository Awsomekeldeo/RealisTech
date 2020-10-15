package awsome.realistech.registry;

import java.util.function.Supplier;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.item.BucketItem;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fluids.ForgeFlowingFluid;

/* TCon FluidObject Class
 * This class and all subclasses are licensed seperately under the MIT liscense instead of the GNU-GPLv3 license.
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

public class FluidObject<F extends ForgeFlowingFluid> implements Supplier<F>, IItemProvider {

	private final Supplier<F> source;
	private final Supplier<F> flowing;
	private final Supplier<FlowingFluidBlock> block;
	private final Supplier<BucketItem> bucket;

	public FluidObject(Supplier<F> source, Supplier<F> flowing, Supplier<FlowingFluidBlock> block, Supplier<BucketItem> bucket) {
		this.source = source;
		this.flowing = flowing;
		this.block = block;
		this.bucket = bucket;
	}

	/**
	 * Gets the still form of this fluid
	 * @return  Still form
	 */
	public F getStill() {
		return source.get();
	}

	/**
	 * Gets the flowing form of this fluid
	 * @return  flowing form
	 */
	public F getFlowing() {
		return flowing.get();
	}

	@Override
	public F get() {
		return getStill();
	}

	/**
	 * Gets the block form of this fluid
	 * @return  Block form
	 */
	public FlowingFluidBlock getBlock() {
		return block.get();
	}

	/**
	 * Gets the bucket form of this fluid
	 * @return  Bucket form
	 */
	@Override
	public BucketItem asItem() {
		return bucket.get();
	}
}
