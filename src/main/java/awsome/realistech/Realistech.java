package awsome.realistech;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import awsome.realistech.inventory.container.HandworkContainer;
import awsome.realistech.registry.Registration;
import awsome.realistech.setup.ClientSetup;
import awsome.realistech.setup.ModSetup;
import awsome.realistech.worldgen.capability.WorldgenCapProvider;
import awsome.realistech.worldgen.utils.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkHooks;

@Mod(Reference.MODID)
public class Realistech {
	
	public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);
	
	public Realistech() {
		Registration.init();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<World> event) {
		event.addCapability(new ResourceLocation(Reference.MODID, "pluton"), new WorldgenCapProvider());
		LOGGER.info("Worldgen capability attached for " + Utils.dimensionToString(event.getObject().getDimension()));
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void addItemTooltips(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() == Items.BRICK) {
			event.getToolTip().add(1, new TranslationTextComponent("tooltip.realistech.brick").applyTextStyle(TextFormatting.GRAY).applyTextStyle(TextFormatting.ITALIC));
		}else if(stack.getItem() == Items.NETHER_BRICK) {
			event.getToolTip().add(1, new TranslationTextComponent("tooltip.realistech.brick").applyTextStyle(TextFormatting.GRAY).applyTextStyle(TextFormatting.OBFUSCATED).applyTextStyle(TextFormatting.ITALIC));
		}
	}
	
	@SubscribeEvent
	public void itemRightClickHandler(PlayerInteractEvent.RightClickItem event) {
		ItemStack stack = event.getItemStack();
		PlayerEntity player = event.getPlayer();
		World world = event.getWorld();
		if (!world.isRemote) {
			if (stack.getItem() == Registration.ROCK_ITEM.get() && stack.getCount() >= 2) {
				INamedContainerProvider containerProvider = new INamedContainerProvider() {

					@Override
					public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
						return new HandworkContainer(i, playerInv, 32, 0, 16, 16, Registration.ROCK_ITEM.get(), 1);
					}

					@Override
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("container.realistech.handwork.stone");
					}

				};
				NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, (buf) -> {
					buf.writeInt(32);
					buf.writeInt(0);
					buf.writeInt(16);
					buf.writeInt(16);
					buf.writeResourceLocation(new ResourceLocation(Reference.MODID, "rock"));
					buf.writeInt(1);
				});
			} else if (stack.getItem() == Items.CLAY_BALL && stack.getCount() >= 5) {
				INamedContainerProvider containerProvider = new INamedContainerProvider() {

					@Override
					public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
						return new HandworkContainer(i, playerInv, 0, 0, 16, 16, Items.CLAY_BALL, 5);
					}

					@Override
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("container.realistech.handwork.clay");
					}

				};
				NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, (buf) -> {
					buf.writeInt(0);
					buf.writeInt(0);
					buf.writeInt(16);
					buf.writeInt(16);
					buf.writeResourceLocation(new ResourceLocation("minecraft:clay_ball"));
					buf.writeInt(5);
				});
			}
		}
	}
}