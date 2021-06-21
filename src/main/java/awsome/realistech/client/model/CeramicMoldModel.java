package awsome.realistech.client.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;

import awsome.realistech.Reference;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.BakedItemModel;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ItemTextureQuadConverter;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelTransformComposition;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelTransform;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;

public final class CeramicMoldModel implements IModelGeometry<CeramicMoldModel> {
	
	private static final float NORTH_Z_INNER = 8.504f / 16f;
	private static final float SOUTH_Z_INNER = 7.496f / 16f;
	private static final float NORTH_Z_FLUID = 7.498f / 16f;
	private static final float SOUTH_Z_FLUID = 8.502f / 16f;

	@Nonnull
	private final Fluid fluid;
	private final boolean flipGas;
	private final boolean tint;
	
	public CeramicMoldModel(Fluid fluid, boolean flipGas, boolean tint) {
		this.fluid = fluid;
		this.flipGas = flipGas;
		this.tint = tint;
	}
	
	public CeramicMoldModel withFluid(Fluid fluid) {
		return new CeramicMoldModel(fluid, flipGas, tint);
	}
	
	private static Material getMaterial(IModelConfiguration owner, String name) {
		Material location = owner.resolveTexture(name);
		if (MissingTextureSprite.getLocation().equals(location.getTextureLocation())) {
			return null;
		}
		return location;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery,
			Function<Material, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform,
			ItemOverrideList overrides, ResourceLocation modelLocation) {
		
		Material particleLocation = getMaterial(owner, "particle");
	    // front texture, full 3D
	    Material baseLocation = getMaterial(owner, "base");
	    // inner (texture in the middle, flat)
	    Material fluidMaskLocation = getMaterial(owner, "fluid");
	    // inner (texture in the back, flat)
	    Material innerLocation = getMaterial(owner, "inner");

	    // determine the transforms to use
	    IModelTransform transformsFromModel = owner.getCombinedTransform();
	    ImmutableMap<TransformType, TransformationMatrix> transformMap = transformsFromModel != null ?
	                                                                     PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(transformsFromModel, modelTransform)) :
	                                                                     PerspectiveMapWrapper.getTransforms(modelTransform);
	    // particle has fallback if null based on a few other sprites
	    TextureAtlasSprite particleSprite = particleLocation != null ? spriteGetter.apply(particleLocation) : null;
	    // if the fluid is lighter than air, will manipulate the initial state to be rotated 180 deg to turn it upside down
	    if (flipGas && fluid != Fluids.EMPTY && fluid.getAttributes().isLighterThanAir()) {
	      modelTransform = new ModelTransformComposition(modelTransform, new SimpleModelTransform(new TransformationMatrix(null, new Quaternion(0, 0, 1, 0), null, null)));
	    }

	    // start building quads
	    TransformationMatrix transform = modelTransform.getRotation();
	    ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
	    if (fluid == Fluids.EMPTY) {
	      // if no fluid, just render the inner sprite, looks better
	      // use base if no inner sprite
	      if (innerLocation == null) innerLocation = baseLocation;
	      // this sprite will be used as particle
	      if (particleSprite == null) particleSprite = spriteGetter.apply(innerLocation);

	      builder.addAll(ItemLayerModelPatch.getQuadsForSprites(ImmutableList.of(innerLocation), transform, spriteGetter));
	    } else {
	      // base is the outer cover, but is also the only layer in full 3D
	      if (baseLocation != null) {
	        builder.addAll(ItemLayerModelPatch.getQuadsForSprites(ImmutableList.of(baseLocation), transform, spriteGetter));
	      }

	      // fluid is next one in
	      TextureAtlasSprite fluidSprite = spriteGetter.apply(ForgeHooksClient.getBlockMaterial(fluid.getAttributes().getStillTexture()));
	      if (particleSprite == null) particleSprite = fluidSprite;
	      if (fluidMaskLocation != null && fluidSprite != null) {
	        TextureAtlasSprite templateSprite = spriteGetter.apply(fluidMaskLocation);
	        if (templateSprite != null) {
	          int color = tint ? fluid.getAttributes().getColor() : 0xFFFFFFFF;
	          builder.addAll(ItemTextureQuadConverter.convertTexture(transform, templateSprite, fluidSprite, NORTH_Z_FLUID, Direction.NORTH, color, 1));
	          builder.addAll(ItemTextureQuadConverter.convertTexture(transform, templateSprite, fluidSprite, SOUTH_Z_FLUID, Direction.SOUTH, color, 1));
	        }
	      }
	      // inner is at the back of the model behind the fluid
	      // needs to be about a pixel back or in hand it gets cut off
	      // inventory will not see this regardless
	      if (innerLocation != null) {
	        // inner (the actual item around the other two)
	        TextureAtlasSprite innerSprite = spriteGetter.apply(innerLocation);
	        builder.add(ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, NORTH_Z_INNER, innerSprite, Direction.NORTH, 0xFFFFFFFF, 2));
	        builder.add(ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, SOUTH_Z_INNER, innerSprite, Direction.SOUTH, 0xFFFFFFFF, 2));
	      }
	    }

	    return new BakedModel(bakery, owner, this, builder.build(), particleSprite, Maps.immutableEnumMap(transformMap), Maps.newHashMap(), transform.isIdentity(), modelTransform, owner.isSideLit());
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner,
			Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
		Set<Material> texs = Sets.newHashSet();
	    texs.add(owner.resolveTexture("particle"));
	    texs.add(owner.resolveTexture("base"));
	    texs.add(owner.resolveTexture("inner"));
	    texs.add(owner.resolveTexture("fluid"));
	    return texs;
	}
	
	public enum Loader implements IModelLoader<CeramicMoldModel> {
		INSTANCE;
		
		@Override
		public IResourceType getResourceType() {
			return VanillaResourceType.MODELS;
		}
		
		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {
			
		}
		
		@Override
		public void onResourceManagerReload(IResourceManager resourceManager,
				Predicate<IResourceType> resourcePredicate) {
			
		}

		@Override
		public CeramicMoldModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			Fluid fluid = null;
			if (modelContents.has("fluid")) {
				ResourceLocation fluidName = new ResourceLocation(modelContents.get("fluid").getAsString());
				fluid = ForgeRegistries.FLUIDS.getValue(fluidName);
			}
			if (fluid == null) fluid = Fluids.EMPTY;

			// if true, flips gasses in the bucket
			boolean flip = false;
			if (modelContents.has("flipGas")) {
				flip = modelContents.get("flipGas").getAsBoolean();
			}
			// if true, tints the fluid. Not really sure why you would not want this
			boolean tint = true;
			if (modelContents.has("applyTint")) {
				tint = modelContents.get("applyTint").getAsBoolean();
			}
			// create new model with correct liquid
			return new CeramicMoldModel(fluid, flip, tint);
		}
		
	}
	
	private static final class ContainedFluidOverrideHandler extends ItemOverrideList {
		private static final ResourceLocation REBAKE_LOCATION = new ResourceLocation(Reference.MODID, "bucket_override");
		private final ModelBakery bakery;
		private ContainedFluidOverrideHandler(ModelBakery bakery)
		{
			this.bakery = bakery;
		}

		@Override
		public IBakedModel getModelWithOverrides(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
			return FluidUtil.getFluidContained(stack)
					.map(fluidStack -> {
						BakedModel model = (BakedModel)originalModel;

						Fluid fluid = fluidStack.getFluid();
						String name = fluid.getRegistryName().toString();

						if (!model.cache.containsKey(name)) {
							CeramicMoldModel parent = model.parent.withFluid(fluid);
							IBakedModel bakedModel = parent.bake(model.owner, bakery, ModelLoader.defaultTextureGetter(), model.originalTransform, model.getOverrides(), REBAKE_LOCATION);
							model.cache.put(name, bakedModel);
							return bakedModel;
						}

						return model.cache.get(name);
					})
					// not a fluid item apparently
					.orElse(originalModel); // empty bucket
		}
	}
	
	private static final class BakedModel extends BakedItemModel {
	    private final IModelConfiguration owner;
	    private final CeramicMoldModel parent;
	    private final Map<String, IBakedModel> cache; // contains all the baked models since they'll never change
	    private final IModelTransform originalTransform;

	    private BakedModel(ModelBakery bakery,
	                       IModelConfiguration owner, CeramicMoldModel parent,
	                       ImmutableList<BakedQuad> quads,
	                       TextureAtlasSprite particle,
	                       ImmutableMap<TransformType, TransformationMatrix> transforms,
	                       Map<String, IBakedModel> cache,
	                       boolean untransformed,
	                       IModelTransform originalTransform, boolean isSideLit) {
	      super(quads, particle, transforms, new ContainedFluidOverrideHandler(bakery), untransformed, isSideLit);
	      this.owner = owner;
	      this.parent = parent;
	      this.cache = cache;
	      this.originalTransform = originalTransform;
	    }
	  }

}
