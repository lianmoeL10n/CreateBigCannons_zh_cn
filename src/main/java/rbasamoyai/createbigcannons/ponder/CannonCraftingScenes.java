package rbasamoyai.createbigcannons.ponder;

import java.util.function.UnaryOperator;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.utility.Pointing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import rbasamoyai.createbigcannons.CBCBlocks;
import rbasamoyai.createbigcannons.CBCItems;
import rbasamoyai.createbigcannons.crafting.builtup.CannonBuilderHeadBlock;
import rbasamoyai.createbigcannons.crafting.incomplete.IncompleteCannonBlock;

public class CannonCraftingScenes {

	public static void cannonCasting(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("cannon_crafting/cannon_casting", "Cannon Casting");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		
		scene.markAsFinished();
	}
	
	public static void cannonBoring(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("cannon_crafting/cannon_boring", "Boring Holes in Cast Cannons");
		scene.configureBasePlate(6, 0, 3);
		scene.world.showSection(util.select.cuboid(util.grid.zero(), util.grid.at(9, 0, 3)), Direction.UP);
		
		Selection latheGearDown = util.select.position(9, 0, 0);
		Selection latheGearUp = util.select.fromTo(9, 1, 1, 8, 1, 1);
		scene.world.showSection(latheGearDown, Direction.UP);
		scene.world.showSection(latheGearUp, Direction.UP);
		ElementLink<WorldSectionElement> cannonPiece = scene.world.showIndependentSection(util.select.fromTo(7, 1, 1, 5, 1, 1), Direction.UP);
		
		int rpm = -32 * 360;
		scene.world.setKineticSpeed(latheGearDown, 16);
		scene.world.setKineticSpeed(latheGearUp, -32);
		scene.world.rotateBearing(util.grid.at(8, 1, 1), rpm, 1200);
		scene.world.rotateSection(cannonPiece, rpm, 0, 0, 1200);
		scene.idle(10);
		
		Selection drillGearDown = util.select.position(4, 0, 3);
		Selection drillGearUp = util.select.fromTo(3, 1, 0, 3, 1, 3).add(util.select.position(3, 2, 2));
		scene.world.showSection(drillGearDown, Direction.NORTH);
		scene.world.showSection(drillGearUp, Direction.NORTH);
		ElementLink<WorldSectionElement> drillPiston = scene.world.showIndependentSection(util.select.fromTo(0, 3, 1, 4, 3, 1), Direction.NORTH);
		scene.world.moveSection(drillPiston, util.vector.of(0, -2, 0), 0);
		
		scene.idle(30);
		
		scene.overlay.showText(60)
			.text("Cannon Drills are used to bore out cast cannons.")
			.pointAt(util.vector.centerOf(3, 1, 1));
		scene.idle(60);
		
		drillGearUp.add(util.select.position(2, 2, 2));
		Selection pump = util.select.position(2, 2, 1);
		scene.world.showSection(util.select.fromTo(0, 1, 1, 2, 2, 2), Direction.DOWN);
		scene.world.showSection(util.select.position(3, 2, 1), Direction.DOWN);
		scene.idle(20);
		scene.world.setKineticSpeed(drillGearUp, -32);
		scene.world.setKineticSpeed(pump, 32);
		scene.idle(10);
		scene.overlay.showText(80)
			.text("In addition to rotational force, they require water to operate, and consume more as they speed up.")
			.pointAt(util.vector.topOf(0, 1, 2))
			.colored(PonderPalette.BLUE);
		scene.idle(80);
		
		scene.addKeyframe();
		
		scene.world.setKineticSpeed(drillGearDown, 16);
		scene.world.setKineticSpeed(drillGearUp, -32);
		scene.world.moveSection(drillPiston, util.vector.of(1, 0, 0), 8);
		scene.idle(8);
		
		scene.world.moveSection(drillPiston, util.vector.of(2, 0, 0), 213);
		scene.world.propagatePipeChange(util.grid.at(2, 2, 1));
		scene.idle(20);
		
		scene.overlay.showText(60)
			.text("The speed of the drill must match the speed of the lathe to do work.")
			.pointAt(util.vector.blockSurface(util.grid.at(5, 1, 1), Direction.WEST))
			.colored(PonderPalette.RED);
		scene.idle(87);
		
		scene.addKeyframe();
		
		scene.world.modifyBlock(util.grid.at(5, 1, 1), copyPropertyTo(FACING, CBCBlocks.CAST_IRON_CANNON_BARREL.getDefaultState()), false);
		scene.idle(20);
		scene.overlay.showText(60)
			.text("Bored cannon blocks drop scrap items.")
			.pointAt(util.vector.centerOf(5, 1, 1))
			.colored(PonderPalette.GREEN);
		scene.idle(20);
		
		scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(5, 1, 1), Pointing.DOWN).withItem(CBCItems.CAST_IRON_NUGGET.asStack()), 40);
		scene.idle(67);
		
		scene.world.modifyBlock(util.grid.at(6, 1, 1), copyPropertyTo(FACING, CBCBlocks.CAST_IRON_CANNON_CHAMBER.getDefaultState()), false);
		scene.idle(10);
		
		scene.world.setKineticSpeed(drillGearDown, -16);
		scene.world.setKineticSpeed(drillGearUp, 32);
		scene.world.moveSection(drillPiston, util.vector.of(-3, 0, 0), 24);
		scene.idle(20);
		
		scene.markAsFinished();
	}
	
	public static void cannonBuilding(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("cannon_crafting/cannon_building", "Building Built-Up Cannons");
		scene.configureBasePlate(6, 0, 3);
		scene.world.showSection(util.select.cuboid(util.grid.zero(), util.grid.at(9, 0, 3)), Direction.UP);
		
		Selection builderGearDown = util.select.position(4, 0, 3);
		Selection builderGearUp = util.select.fromTo(3, 1, 1, 3, 1, 3);
		scene.world.showSection(builderGearDown, Direction.UP);
		scene.world.showSection(builderGearUp, Direction.UP);
		ElementLink<WorldSectionElement> builderPiston = scene.world.showIndependentSection(util.select.fromTo(0, 2, 1, 3, 2, 1), Direction.UP);
		scene.world.moveSection(builderPiston, util.vector.of(0, -1, 0), 0);
		scene.idle(15);
		
		scene.world.showSection(util.select.fromTo(6, 1, 1, 8, 1, 1), Direction.DOWN);
		scene.idle(30);
		
		scene.overlay.showText(80)
			.text("Cannon Builders are used to put together the layers of built-up cannons.")
			.pointAt(util.vector.centerOf(3, 1, 1));
		scene.idle(60);
		
		ElementLink<WorldSectionElement> layer1 = scene.world.showIndependentSection(util.select.fromTo(6, 1, 2, 7, 1, 2), Direction.DOWN);
		scene.world.moveSection(layer1, util.vector.of(-2, 0, -1), 0);
		scene.idle(40);
		
		scene.overlay.showText(80)
			.text("Pulse the Cannon Builder with power to toggle its attachment state.")
			.pointAt(util.vector.centerOf(3, 1, 1));
		scene.idle(20);
		BlockPos leverPos = util.grid.at(3, 1, 0);
		scene.world.showSection(util.select.position(leverPos), null);
		scene.idle(10);
		scene.world.modifyBlock(leverPos, setStateValue(POWERED, true), false);
		scene.effects.createRedstoneParticles(leverPos, 0xFF0000, 10);
		BlockPos headPos = util.grid.at(3, 2, 1);
		scene.world.modifyBlock(headPos, setStateValue(ATTACHED, true), false);
		scene.idle(15);
		scene.world.modifyBlock(leverPos, setStateValue(POWERED, false), false);
		scene.idle(15);
		scene.world.setKineticSpeed(builderGearDown, 16);
		scene.world.setKineticSpeed(builderGearUp, -32);
		
		scene.world.moveSection(builderPiston, util.vector.of(2, 0, 0), 32);
		scene.world.moveSection(layer1, util.vector.of(2, 0, 0), 32);
		scene.idle(40);
		
		scene.world.modifyBlock(leverPos, setStateValue(POWERED, true), false);
		scene.effects.createRedstoneParticles(leverPos, 0xFF0000, 10);
		scene.world.modifyBlock(headPos, setStateValue(ATTACHED, false), false);
		scene.idle(15);
		scene.world.modifyBlock(leverPos, setStateValue(POWERED, false), false);
		scene.idle(15);
		
		scene.world.setKineticSpeed(builderGearDown, -16);
		scene.world.setKineticSpeed(builderGearUp, 32);
		scene.world.moveSection(builderPiston, util.vector.of(-2, 0, 0), 32);
		scene.idle(40);
		
		ElementLink<WorldSectionElement> layer2 = scene.world.showIndependentSection(util.select.position(6, 1, 0), Direction.DOWN);
		scene.world.moveSection(layer2, util.vector.of(-2, 0, 1), 0);
		scene.idle(20);
		scene.world.modifyBlock(leverPos, setStateValue(POWERED, true), false);
		scene.effects.createRedstoneParticles(leverPos, 0xFF0000, 10);
		scene.world.modifyBlock(headPos, setStateValue(ATTACHED, true), false);
		scene.idle(15);
		scene.world.modifyBlock(leverPos, setStateValue(POWERED, false), false);
		scene.idle(15);
		scene.world.setKineticSpeed(builderGearDown, 16);
		scene.world.setKineticSpeed(builderGearUp, -32);
		
		scene.world.moveSection(builderPiston, util.vector.of(2, 0, 0), 32);
		scene.world.moveSection(layer2, util.vector.of(2, 0, 0), 32);
		scene.idle(40);
		
		scene.overlay.showText(80)
			.attachKeyFrame()
			.text("When grabbing layers from built-up blocks, the outermost layer will always be grabbed")
			.colored(PonderPalette.RED)
			.pointAt(util.vector.topOf(6, 1, 1));
		scene.idle(20);
		
		scene.world.setKineticSpeed(builderGearDown, -16);
		scene.world.setKineticSpeed(builderGearUp, 32);
		scene.world.moveSection(builderPiston, util.vector.of(-2, 0, 0), 32);
		scene.world.moveSection(layer2, util.vector.of(-2, 0, 0), 32);
		scene.idle(40);
		
		scene.world.setKineticSpeed(builderGearDown, 16);
		scene.world.setKineticSpeed(builderGearUp, -32);
		scene.world.moveSection(builderPiston, util.vector.of(2, 0, 0), 32);
		scene.world.moveSection(layer2, util.vector.of(2, 0, 0), 32);
		scene.idle(40);
		
		scene.world.modifyBlock(leverPos, setStateValue(POWERED, true), false);
		scene.effects.createRedstoneParticles(leverPos, 0xFF0000, 10);
		scene.world.modifyBlock(headPos, setStateValue(ATTACHED, false), false);
		scene.idle(15);
		scene.world.modifyBlock(leverPos, setStateValue(POWERED, false), false);
		scene.idle(15);
		scene.world.setKineticSpeed(builderGearDown, -16);
		scene.world.setKineticSpeed(builderGearUp, 32);
		scene.world.moveSection(builderPiston, util.vector.of(-2, 0, 0), 32);
		scene.idle(60);
		
		scene.markAsFinished();
	}
	
	public static void finishingBuiltUpCannons(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("cannon_crafting/finishing_built_up_cannons", "Finishing Built-Up Cannons");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		
		scene.markAsFinished();
	}
	
	public static void incompleteCannonBlocks(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("cannon_crafting/incomplete_cannon_blocks", "Incomplete Cannon Blocks");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		scene.idle(20);
		
		scene.world.showSection(util.select.fromTo(2, 1, 1, 2, 1, 4), Direction.DOWN);
		scene.idle(20);
		
		BlockPos incompletePos = util.grid.at(2, 1, 2);
		scene.overlay.showText(60)
			.text("Some bored cannon blocks need additional items to be completed.")
			.pointAt(util.vector.centerOf(incompletePos));
		scene.idle(80);
		
		scene.overlay.showText(120)
			.text("These are usually cannon breech blocks.")
			.colored(PonderPalette.BLUE);
		scene.idle(20);
		
		scene.overlay.showControls(new InputWindowElement(util.vector.topOf(incompletePos), Pointing.DOWN).withItem(CBCBlocks.INCOMPLETE_CAST_IRON_SLIDING_BREECH.asStack()), 40);
		scene.idle(60);
		
		scene.world.modifyBlock(util.grid.at(2, 1, 4), copyPropertyTo(FACING, CBCBlocks.STEEL_CANNON_BARREL.getDefaultState()), true);
		scene.world.modifyBlock(util.grid.at(2, 1, 3), copyPropertyTo(FACING, CBCBlocks.STEEL_CANNON_CHAMBER.getDefaultState()), true);
		scene.world.modifyBlock(incompletePos, copyPropertyTo(FACING, CBCBlocks.INCOMPLETE_STEEL_SCREW_BREECH.getDefaultState()), true);
		scene.overlay.showControls(new InputWindowElement(util.vector.topOf(incompletePos), Pointing.DOWN).withItem(CBCBlocks.INCOMPLETE_STEEL_SCREW_BREECH.asStack()), 40);
		scene.idle(60);
		
		scene.world.modifyBlock(util.grid.at(2, 1, 4), copyPropertyTo(FACING, CBCBlocks.CAST_IRON_CANNON_BARREL.getDefaultState()), true);
		scene.world.modifyBlock(util.grid.at(2, 1, 3), copyPropertyTo(FACING, CBCBlocks.CAST_IRON_CANNON_CHAMBER.getDefaultState()), true);
		scene.world.modifyBlock(incompletePos, copyPropertyTo(FACING, CBCBlocks.INCOMPLETE_CAST_IRON_SLIDING_BREECH.getDefaultState().setValue(ALONG_FIRST, true)), true);
		scene.idle(20);
		
		scene.addKeyframe();
		
		scene.overlay.showText(60)
			.text("Use the listed items on the block to complete it.")
			.pointAt(util.vector.centerOf(incompletePos))
			.colored(PonderPalette.GREEN);
		scene.idle(20);
		
		scene.overlay.showControls(new InputWindowElement(util.vector.topOf(incompletePos), Pointing.DOWN).rightClick().withItem(AllBlocks.SHAFT.asStack()), 40);
		scene.idle(10);
		scene.world.modifyBlock(incompletePos, setStateValue(IncompleteCannonBlock.STAGE_2, 1), false);
		scene.idle(40);
		
		Selection deployerGearDown = util.select.position(3, 0, 5);
		Selection deployerGearUp = util.select.fromTo(4, 1, 2, 4, 1, 5);
		scene.world.showSection(deployerGearDown, Direction.WEST);
		scene.idle(5);
		scene.world.showSection(deployerGearUp, Direction.WEST);
		scene.idle(15);
		
		BlockPos deployerPos = util.grid.at(4, 1, 2);
		scene.overlay.showText(80)
			.text("Deployers can also complete incomplete cannon blocks.")
			.pointAt(util.vector.centerOf(deployerPos))
			.colored(PonderPalette.BLUE);
		scene.idle(20);
		ItemStack breechblock = CBCItems.CAST_IRON_SLIDING_BREECHBLOCK.asStack();
		Selection deployer = util.select.position(deployerPos);
		scene.overlay.showControls(new InputWindowElement(util.vector.topOf(deployerPos), Pointing.DOWN).withItem(breechblock), 40);
		scene.idle(30);
		scene.world.modifyTileNBT(deployer, DeployerTileEntity.class, tag -> tag.put("HeldItem", breechblock.serializeNBT()));
		scene.idle(15);
		
		scene.world.setKineticSpeed(deployerGearDown, -16);
		scene.world.setKineticSpeed(deployerGearUp, 32);
		scene.world.moveDeployer(deployerPos, 1, 25);
		scene.idle(25);
		
		scene.world.modifyBlock(incompletePos, copyPropertyTo(FACING, CBCBlocks.CAST_IRON_SLIDING_BREECH.getDefaultState().setValue(ALONG_FIRST, true)), false);
		
		scene.idle(10);
		scene.world.modifyTileNBT(deployer, DeployerTileEntity.class, tag -> tag.put("HeldItem", ItemStack.EMPTY.serializeNBT()));
		scene.world.setKineticSpeed(deployerGearDown, 16);
		scene.world.setKineticSpeed(deployerGearUp, -32);
		scene.world.moveDeployer(deployerPos, -1, 25);
		scene.idle(35);
		
		scene.markAsFinished();
	}
	
	public static void basinFoundry(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("cannon_crafting/basin_foundry", "Incomplete Cannon Blocks");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		
		
		
		scene.markAsFinished();
	}
	
	private static final DirectionProperty FACING = BlockStateProperties.FACING;
	private static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	private static final BooleanProperty ATTACHED = CannonBuilderHeadBlock.ATTACHED;
	private static final BooleanProperty ALONG_FIRST = DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE;
	
	private static <T extends Comparable<T>> UnaryOperator<BlockState> copyPropertyTo(Property<T> property, BlockState newState) {
		return state -> state.hasProperty(property) && newState.hasProperty(property) ? newState.setValue(property, state.getValue(property)) : newState;
	}
	
	private static <T extends Comparable<T>> UnaryOperator<BlockState> setStateValue(Property<T> property, T value) {
		return state -> state.hasProperty(property) ? state.setValue(property, value) : state;
	}
	
	private static UnaryOperator<BlockState> setBlock(BlockState newState) {
		return state -> newState;
	}
	
}
