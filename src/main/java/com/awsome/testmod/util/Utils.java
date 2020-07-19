package com.awsome.testmod.util;

import net.minecraft.block.state.IBlockState;

public class Utils {
	/*	Using a modified version of:
	 * 	https://github.com/oitsjustjose/Geolosys/src/main/java/com/oitsjustjose/geolosys/common/util/Utils.java
	 * 	Original source & credit goes to oitsjustjose
	 */	
	public static boolean doStatesMatch(IBlockState state1, IBlockState state2) {
        return (state1.getBlock() == state2.getBlock()
                && state1.getBlock().getMetaFromState(state1) == state2.getBlock().getMetaFromState(state2));
    }

}
