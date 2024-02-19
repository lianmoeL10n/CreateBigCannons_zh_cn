package rbasamoyai.createbigcannons.compat.rei;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.rei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.contraptions.piston.MechanicalPistonBlock;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import rbasamoyai.createbigcannons.crafting.boring.CannonDrillBlock;
import rbasamoyai.createbigcannons.crafting.boring.DrillBoringBlockRecipe;
import rbasamoyai.createbigcannons.index.CBCBlocks;

import static com.simibubi.create.compat.rei.category.CreateRecipeCategory.basicSlot;

public class DrillBoringCategory extends CBCBlockRecipeCategory<DrillBoringBlockRecipe> {

	public DrillBoringCategory(Info<DrillBoringBlockRecipe> info) {
		super(info);
	}

	@Override
	public void draw(DrillBoringBlockRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
		int scale = 23;
		AllGuiTextures.JEI_SHADOW.render(stack, 35, 28);
		AllGuiTextures.JEI_SHADOW.render(stack, 97, 36);
		AllGuiTextures.JEI_LONG_ARROW.render(stack, 54, 54);
		stack.pushPose();
		stack.translate(45, 35, 10);
		stack.mulPose(Vector3f.XP.rotationDegrees(-12.5f));
		stack.mulPose(Vector3f.YP.rotationDegrees(60.0f));
		AnimatedKinetics.defaultBlockElement(CBCBlocks.CANNON_DRILL.getDefaultState().setValue(CannonDrillBlock.STATE, MechanicalPistonBlock.PistonState.RETRACTED))
			.rotateBlock(0, 180, 0)
			.atLocal(0, 0, 0)
			.scale(scale)
			.render(stack);

		BlockState shaft = AllBlocks.SHAFT.getDefaultState();
		Direction.Axis axis = shaft.getValue(BlockStateProperties.AXIS);

		AnimatedKinetics.defaultBlockElement(shaft)
			.rotateBlock(0, AnimatedKinetics.getCurrentAngle(), -90)
			.atLocal(0, 0, 0)
			.scale(scale)
			.render(stack);

		List<ItemStack> ingredients = recipe.ingredients();
		Block block = !ingredients.isEmpty() && ingredients.get(0).getItem() instanceof BlockItem item ? item.getBlock() : Blocks.BARRIER;

		BlockState state = block.defaultBlockState();
		if (state.hasProperty(BlockStateProperties.FACING)) {
			state = state.setValue(BlockStateProperties.FACING, Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE));
		}
		if (state.hasProperty(BlockStateProperties.AXIS)) {
			state = state.setValue(BlockStateProperties.AXIS, axis);
		}

		AnimatedKinetics.defaultBlockElement(state)
			.rotateBlock(90, 0, AnimatedKinetics.getCurrentAngle())
			.atLocal(0, 0, 2)
			.scale(scale)
			.render(stack);

		AnimatedKinetics.defaultBlockElement(AllBlocks.MECHANICAL_BEARING.getDefaultState())
			.rotateBlock(0, 0, 0)
			.atLocal(0, 0, 3)
			.scale(scale)
			.render(stack);

		AnimatedKinetics.defaultBlockElement(AllPartialModels.BEARING_TOP)
			.rotateBlock(-90, 0, AnimatedKinetics.getCurrentAngle())
			.atLocal(0, 0, 3)
			.scale(scale)
			.render(stack);

		AnimatedKinetics.defaultBlockElement(AllPartialModels.SHAFT_HALF)
			.rotateBlock(0, 0, AnimatedKinetics.getCurrentAngle())
			.atLocal(0, 0, 3)
			.scale(scale)
			.render(stack);
		stack.popPose();
	}

	@Override
	public void addWidgets(CBCDisplay<DrillBoringBlockRecipe> display, List<Widget> ingredients, Point origin) {
		DrillBoringBlockRecipe recipe = display.getRecipe();
		ingredients.add(basicSlot(21, 51, origin).markInput().entries(EntryIngredients.ofItemStacks(recipe.ingredients())).backgroundEnabled(true));
		ingredients.add(basicSlot(141, 51, origin).markOutput().entry(EntryStacks.of(recipe.getResultBlock())).backgroundEnabled(true));
	}

}
