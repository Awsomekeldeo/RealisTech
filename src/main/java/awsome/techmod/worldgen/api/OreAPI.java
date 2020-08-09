package awsome.techmod.worldgen.api;

import java.util.ArrayList;

import awsome.worldgen.PlutonRegistry;
import awsome.worldgen.capability.IWorldgenCapability;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/*
 * Using a modified version of Geolosys' worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/api/OreAPI.java
 * Original source and credit goes to ohitsjustjose
 */

public class OreAPI
{
    @CapabilityInject(IWorldgenCapability.class)
    public static final Capability<IWorldgenCapability> TECHMOD_WORLDGEN_CAPABILITY = null;
    // A collection of BlockStates that can trigger the prospector's pick
    public static ArrayList<BlockState> proPickExtras = new ArrayList<>();
    // A collection of blocks to ignore in the OreConverter feature
    public static ArrayList<BlockState> oreConverterBlacklist = new ArrayList<>();
    // An instance of the registry for all generatable plutons
    public static PlutonRegistry plutonRegistry = new PlutonRegistry();
}
