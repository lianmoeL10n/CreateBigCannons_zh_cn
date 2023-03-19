package rbasamoyai.createbigcannons.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.base.CBCRegistries;
import rbasamoyai.createbigcannons.config.CBCConfigs;
import rbasamoyai.createbigcannons.datagen.assets.CBCBlockPartialsGen;
import rbasamoyai.createbigcannons.datagen.assets.CBCLangGen;
import rbasamoyai.createbigcannons.datagen.loot.CBCLootTableProvider;
import rbasamoyai.createbigcannons.datagen.recipes.*;
import rbasamoyai.createbigcannons.datagen.values.BlockHardnessProvider;
import rbasamoyai.createbigcannons.fabric.network.CBCNetworkFabric;
import rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.FluidBlob;
import rbasamoyai.createbigcannons.ponder.CBCPonderIndex;
import rbasamoyai.createbigcannons.ponder.CBCPonderTags;

public class CreateBigCannonsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CreateBigCannons.init();
        CBCRegistries.init();
        CBCConfigs.registerConfigs((t, c) -> ModLoadingContext.registerConfig(CreateBigCannons.MOD_ID, t, c));

        CreateBigCannons.REGISTRATE.register();
        CBCNetworkFabric.init();
        FluidBlob.registerDefaultBlobEffects();

        ModConfigEvent.LOADING.register(CBCConfigs::onLoad);
        ModConfigEvent.RELOADING.register(CBCConfigs::onReload);

        this.registerSerializers();
    }
    public static void gatherData(FabricDataGenerator gen, ExistingFileHelper helper) {
            BlockRecipeProvider.registerAll(gen);
            gen.addProvider(new CBCCraftingRecipeProvider(gen));
            gen.addProvider(new CBCCompactingRecipeProvider(gen));
            gen.addProvider(new MeltingRecipeProvider(gen));
            gen.addProvider(new CBCMixingRecipeProvider(gen));
            gen.addProvider(new CBCLootTableProvider(gen));
            gen.addProvider(new CBCSequencedAssemblyRecipeProvider(gen));
            gen.addProvider(new CBCCuttingRecipeProvider(gen));
            gen.addProvider(new BlockHardnessProvider(CreateBigCannons.MOD_ID, gen));
            CBCLangGen.prepare();
            gen.addProvider(new CBCBlockPartialsGen(gen, helper));
            CBCPonderTags.register();
            CBCPonderIndex.register();
            CBCPonderIndex.registerLang();
    }

    private void registerSerializers() {
        EntityDataSerializers.registerSerializer(FluidBlob.FLUID_STACK_SERIALIZER);
    }

}