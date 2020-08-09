package awsome.techmod.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import awsome.techmod.Techmod;
import awsome.techmod.worldgen.api.OreAPI;
import awsome.techmod.worldgen.api.PlutonType;
import awsome.techmod.worldgen.api.deposit.Deposit;
import awsome.techmod.worldgen.api.deposit.DepositBiomeRestricted;
import awsome.techmod.worldgen.api.deposit.DepositMultiOre;
import awsome.techmod.worldgen.api.deposit.DepositMultiOreBiomeRestricted;
import awsome.techmod.worldgen.api.deposit.DepositStone;
import awsome.techmod.worldgen.api.deposit.IDeposit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

/*
 * Modified version of Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/config/OreConfig.java
 * Original source and credit goes to ohitsjustjose
 */
public class OreDepositRegistry {
	
	/**
     * Registers a stone with the OreAPI using the passed params
     *
     * @param stone        The String form of the IBlockState
     * @param yMin         The minimum Y level this stone can generate
     * @param yMax         The maximum Y level this stone can generate
     * @param chance       The chance this stone can generate
     * @param size         The size this stone will be generated
     * @param dimBlacklist The list of dims the stone cannot generate
     * @return false if the state does not exist; true otherwise
     */
    public boolean register(String stone, int yMin, int yMax, int chance, int size, String[] dimBlacklist)
    {
        BlockState state = fromString(stone);
        if (state == null)
        {
            return false;
        }
        OreAPI.plutonRegistry.addStonePluton(new DepositStone(state, yMin, yMax, chance, size, dimBlacklist));

        Techmod.LOGGER.info("Registered a stone pluton of {}.", stone);

        return true;
    }
    
    /**
     * Registers an ore with the GeolosysAPI using the passed params
     *
     * @param oreBlocks          A pair of String forms of an IBlockState of ores, paried with their chance
     * @param sampleBlocks       A pair of String forms of an IBlockState of samples, paired with their chance
     * @param yMin               The minimum Y level this ore can generate
     * @param yMax               The maximum Y level this ore can generate
     * @param size               The size this ore can generate to be
     * @param chance             The chance this ore can generate
     * @param dimBlacklist       The list of dims the ore cannot generate
     * @param blockStateMatchers A list of String forms of IBlockStates that this ore can replace when genning
     * @param biomes             A list of Biomes that this ore can/cannot generate in
     * @param isWhitelist        Whether or not the list of biomes is whitelist or not
     * @param hasIsWhitelist     Whether or not the isWhitelist boolean had been populated
     * @param density            The density (amount of ore vs. air/stone blocks) this deposit has
     * @return true if successfully registered, false if any blockstates are null
     */
    public boolean register(HashMap<String, Integer> oreBlocks, HashMap<String, Integer> sampleBlocks, int yMin,
            int yMax, int size, int chance, String[] dimBlacklist, ArrayList<String> blockStateMatchers,
            ArrayList<Biome> biomes, List<BiomeDictionary.Type> biomeTypes, boolean isWhitelist, boolean hasIsWhitelist,
            PlutonType type, float density)
    {
        HashMap<BlockState, Integer> oreBlocksParsed = new HashMap<>();
        HashMap<BlockState, Integer> sampleBlocksParsed = new HashMap<>();
        ArrayList<BlockState> blockStateMatchersParsed = new ArrayList<>();

        IDeposit toRegister = null;

        // Validate every oreBlock item
        for (Entry<String, Integer> e : oreBlocks.entrySet())
        {
            BlockState state = fromString(e.getKey());
            if (state == null)
            {
                return false;
            }
            oreBlocksParsed.put(state, e.getValue());
        }

        // Validate every sampleBlock item
        for (Entry<String, Integer> e : sampleBlocks.entrySet())
        {
            BlockState state = fromString(e.getKey());
            if (state == null)
            {
                return false;
            }
            sampleBlocksParsed.put(state, e.getValue());
        }

        // Validate every blockStateMatcher
        for (String s : blockStateMatchers)
        {
            BlockState state = fromString(s);
            if (state == null)
            {
                return false;
            }
            blockStateMatchersParsed.add(state);
        }

        // Ensure that if biomes are specified, whether or not it's a whitelist is also specified
        if ((biomes.size() > 0 || biomeTypes.size() > 0) && !hasIsWhitelist)
        {
            return false;
        }

        // Ensure that the PlutonType is declared
        if (type == null)
        {
            return false;
        }

        // Nullify an empty blockStateMatcher so that the default is used.
        if (blockStateMatchersParsed.size() <= 0)
        {
            blockStateMatchersParsed = null;
        }

        // Register as some variant of DepositMultiOre
        if (oreBlocks.size() > 0 || sampleBlocks.size() > 0)
        {
            if (biomes.size() > 0 || biomeTypes.size() > 0)
            {
                toRegister = new DepositMultiOreBiomeRestricted(oreBlocksParsed, sampleBlocksParsed, yMin, yMax, size,
                        chance, dimBlacklist, blockStateMatchersParsed, biomes, biomeTypes, isWhitelist, type, density);
            }
            else
            {
                toRegister = new DepositMultiOre(oreBlocksParsed, sampleBlocksParsed, yMin, yMax, size, chance,
                        dimBlacklist, blockStateMatchersParsed, type, density);
            }
        }
        else
        {
            if (biomes.size() > 0 || biomeTypes.size() > 0)
            {
                for (BlockState b : oreBlocksParsed.keySet())
                {
                    for (BlockState s : sampleBlocksParsed.keySet())
                    {
                        toRegister = new DepositBiomeRestricted(b, s, yMin, yMax, size, chance, dimBlacklist,
                                blockStateMatchersParsed, biomes, biomeTypes, isWhitelist, type, density);
                        break;
                    }
                    break;
                }
            }
            else
            {
                for (BlockState b : oreBlocksParsed.keySet())
                {
                    for (BlockState s : sampleBlocksParsed.keySet())
                    {
                        toRegister = new Deposit(b, s, yMin, yMax, size, chance, dimBlacklist, blockStateMatchersParsed,
                                type, density);
                        break;
                    }
                    break;
                }
            }
        }

        Techmod.LOGGER.info(
                "Registered a {} ore pluton of blocks={}, samples={}, and density={}. This ore {} custom biome registries.",
                type.toString().toLowerCase(), oreBlocks, sampleBlocks, density,
                (biomeTypes.size() > 0 || biomes.size() > 0) ? "has" : "does not have");

        return toRegister != null && OreAPI.plutonRegistry.addOrePluton(toRegister);
    }
    
    private BlockState fromString(String iBlockState)
    {
        String[] parts = iBlockState.split(":");
        if (parts.length == 2)
        {
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(parts[0], parts[1]));
            return b.getDefaultState();
        }
        return null;
    }
}
