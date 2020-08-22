package awsome.realistech.worldgen.capability;

import net.minecraft.util.math.BlockPos;

/*
 * Using Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/world/capability/CapUtils.java
 * Original source and credit goes to ohitsjustjose
 */
public class CapUtils {
	public static String toString(BlockPos pos)
    {
        return "[" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + "]";
    }

    public static BlockPos fromString(String s)
    {
        String[] parts = s.replace("[", "").replace("]", "").split(",");
        return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
