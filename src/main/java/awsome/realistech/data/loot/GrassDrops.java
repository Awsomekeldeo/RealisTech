package awsome.realistech.data.loot;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class GrassDrops {
	
	public static class GrassDropSerializer extends GlobalLootModifierSerializer<GrassDropModifier> {

		@Override
		public GrassDropModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
			
			JsonObject range = JSONUtils.getJsonObject(object, "count");
			float minCount = JSONUtils.getFloat(range, "min");
			float maxCount = JSONUtils.getFloat(range, "max");
			IRandomRange randomRange = RandomValueRange.of(minCount, maxCount);
			
			Item drop = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(object, "item")));
			
			return new GrassDropModifier(ailootcondition, randomRange, drop);
		}
		
	}
	
	public static class GrassDropModifier extends LootModifier {
		
		private final IRandomRange randomRange;
		private final Item item;

		protected GrassDropModifier(ILootCondition[] conditionsIn, IRandomRange rangeIn, Item itemIn) {
			super(conditionsIn);
			this.randomRange = rangeIn;
			this.item = itemIn;
		}
		
		@Nonnull
		@Override
		protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
			generatedLoot.add(new ItemStack(item, randomRange.generateInt(context.getRandom())));
			return generatedLoot;
		}
		
	}
	
}
