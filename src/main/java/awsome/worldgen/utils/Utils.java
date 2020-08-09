package awsome.worldgen.utils;

import java.util.ArrayList;
import java.util.Objects;

import awsome.techmod.worldgen.api.OreAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.Dimension;
import net.minecraftforge.common.ToolType;

/*
 * Using Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/utils/Utils.java
 * Original source and credit goes to ohitsjustjose
 */
public class Utils
{
    private static ArrayList<BlockState> defaultMatchersCached = null;

    public static String blockStateToName(BlockState state)
    {
        return I18n.format(state.getBlock().getTranslationKey());
    }

    public static ItemStack blockStateToStack(BlockState state)
    {
        return new ItemStack(state.getBlock().asItem(), 1);
    }

    public static boolean doStatesMatch(BlockState state1, BlockState state2)
    {
        return (state1.getBlock().getRegistryName() == state2.getBlock().getRegistryName());
    }

    public static String dimensionToString(Dimension dim)
    {
        return Objects.requireNonNull(dim.getType().getRegistryName().toString());
    }

    public static BlockPos getTopSolidBlock(IWorld world, BlockPos start)
    {
        BlockPos retPos = new BlockPos(start.getX(), world.getHeight() - 1, start.getZ());
        while (retPos.getY() > 0)
        {
            if (world.getBlockState(retPos).getMaterial().isSolid())
            {
                break;
            }
            retPos = retPos.down();
        }
        return retPos;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<BlockState> getDefaultMatchers()
    {
        return (ArrayList<BlockState>) defaultMatchersCached.clone();
    }

    @SuppressWarnings("deprecation")
    public static boolean addDefaultMatcher(Block block)
    {
        if (defaultMatchersCached == null)
        {
            defaultMatchersCached = new ArrayList<BlockState>();
            OreAPI.plutonRegistry.getStones().forEach(x -> defaultMatchersCached.add(x.getOre()));
        }
        BlockState defaultState = block.getDefaultState();
        if (!defaultState.isAir())
        {
            defaultMatchersCached.add(defaultState);
            return true;
        }
        return false;
    }

    public static boolean canMine(BlockState state, ItemStack stack)
    {
        int harvestLvl = stack.getHarvestLevel(ToolType.PICKAXE, null, null);
        return stack.getToolTypes().contains(ToolType.PICKAXE) && state.getHarvestLevel() <= harvestLvl;
    }
}
