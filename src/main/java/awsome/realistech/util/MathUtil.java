package awsome.realistech.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class MathUtil {
	/**
	* Round to certain number of decimals
	* 
	* @param f The float to round
	* @param decimalPlace Number of decimal places to round to
	* @return The rounded value as a float
	*/
	public static float roundFloat(float f, int decimalPlace) {
	    BigDecimal bd = new BigDecimal(Float.toString(f));
	    bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
	    return bd.floatValue();
	}
	
	
	/**
	 * Rotates a {@link VoxelShape}
	 */
	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[]{ shape, VoxelShapes.empty() };

		int times = (to.getHorizontalIndex() - from.getHorizontalIndex() + 4) % 4;
		for (int i = 0; i < times; i++) {
			buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1], VoxelShapes.create(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = VoxelShapes.empty();
		}

		return buffer[0];
	}
	
	public static Rotation getRotationBetweenFacings(Direction dir1, Direction dir2) {
		
		if (dir2 == dir1) {
			return Rotation.NONE;
		}
		
		if (dir1.getAxis()==Axis.Y||dir2.getAxis()==Axis.Y) {
			return null;
		}
		
		dir1 = dir1.rotateY();
		
		if (dir2 == dir1) {
			return Rotation.CLOCKWISE_90;
		}
		
		dir1 = dir1.rotateY();
		
		if (dir2 == dir1) {
			return Rotation.CLOCKWISE_180;
		}
		
		dir1 = dir1.rotateY();
		
		if (dir2 == dir1) {
			return Rotation.COUNTERCLOCKWISE_90;
		}
		
		return null;
	}
}
