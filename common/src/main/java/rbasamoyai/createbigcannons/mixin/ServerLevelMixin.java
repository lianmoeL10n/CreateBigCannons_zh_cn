package rbasamoyai.createbigcannons.mixin;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import rbasamoyai.createbigcannons.cannon_loading.CanLoadBigCannon;
import rbasamoyai.createbigcannons.remix.HasFragileContraption;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {

	ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, Holder<DimensionType> dimensionTypeRegistration,
					 Supplier<ProfilerFiller> profiler, boolean isClientSide, boolean isDebug, long biomeZoomSeed) {
		super(levelData, dimension, dimensionTypeRegistration, profiler, isClientSide, isDebug, biomeZoomSeed);
	}

	@ModifyReturnValue(method = "addFreshEntity", at = @At("RETURN"))
	private boolean createbigcannons$addFreshEntity(boolean original, Entity entity) {
		// Lazy check but whatever
		if (original && entity instanceof AbstractContraptionEntity ace && ace.getContraption() instanceof HasFragileContraption hfc
			&& !CanLoadBigCannon.intersectionLoadingEnabled()) {
			CanLoadBigCannon.checkForIntersectingBlocks(this, ace, hfc.createbigcannons$getEncounteredBlocks());
		}
		return original;
	}

}