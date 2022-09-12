package rbasamoyai.createbigcannons.datagen;

import java.util.function.Consumer;

import com.simibubi.create.AllItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import rbasamoyai.createbigcannons.CBCBlocks;
import rbasamoyai.createbigcannons.CBCItems;
import rbasamoyai.createbigcannons.CBCTags;

public class CBCCraftingRecipeProvider extends RecipeProvider {

	public CBCCraftingRecipeProvider(DataGenerator gen) {
		super(gen);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> cons) {
		ShapedRecipeBuilder.shaped(CBCItems.IMPACT_FUZE.get())
		.define('T', CBCTags.ItemCBC.IMPACT_FUZE_HEAD).define('R', Items.REDSTONE)
		.pattern("T")
		.pattern("R")
		.unlockedBy("has_impact_fuze_head", has(CBCTags.ItemCBC.IMPACT_FUZE_HEAD))
		.save(cons);
		
		ShapedRecipeBuilder.shaped(CBCItems.TIMED_FUZE.get())
		.define('I', Items.IRON_INGOT).define('M', AllItems.PRECISION_MECHANISM.get()).define('R', Items.REDSTONE)
		.pattern("I")
		.pattern("M")
		.pattern("R")
		.unlockedBy(getHasName(AllItems.PRECISION_MECHANISM.get()), has(AllItems.PRECISION_MECHANISM.get()))
		.save(cons);
		
		ShapedRecipeBuilder.shaped(CBCItems.EMPTY_POWDER_CHARGE.get())
		.define('W', ItemTags.WOOL).define('S', Items.STRING)
		.pattern("WSW")
		.pattern("W W")
		.pattern("WSW")
		.unlockedBy("has_wool", has(ItemTags.WOOL))
		.save(cons);;
		
		ShapelessRecipeBuilder.shapeless(CBCItems.PACKED_GUNPOWDER.get())
		.requires(Items.GUNPOWDER, 9)
		.unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER))
		.save(cons);
		
		ShapelessRecipeBuilder.shapeless(CBCBlocks.POWDER_CHARGE.get())
		.requires(CBCItems.PACKED_GUNPOWDER.get(), 3)
		.requires(CBCItems.EMPTY_POWDER_CHARGE.get())
		.unlockedBy(getHasName(CBCItems.PACKED_GUNPOWDER.get()), has(CBCItems.PACKED_GUNPOWDER.get()))
		.save(cons);	
	}
	
	@Override public String getName() { return "Create Big Cannons Recipes: Crafting"; }
	
}
