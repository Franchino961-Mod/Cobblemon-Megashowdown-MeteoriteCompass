package com.meteoritecompass.item;

import java.util.List;

import com.meteoritecompass.MeteoriteCompass;
import com.meteoritecompass.util.CompassState;
import com.meteoritecompass.util.StructureUtils;
import com.meteoritecompass.worker.SearchWorkerManager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;

public class MeteoriteCompassItem extends Item {

	private SearchWorkerManager workerManager;

	/**
	 * Set by the client entrypoint to open the structure selection GUI.
	 * Kept as a Runnable to avoid any reference to client-only classes in this
	 * common (server+client) class, which would crash a dedicated server.
	 */
	public static java.util.function.Consumer<ItemStack> screenOpener = null;

	public MeteoriteCompassItem(Settings settings) {
		super(settings);
		workerManager = new SearchWorkerManager();
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);

		// Shift + Right Click = Reset compass
		if (player.isSneaking()) {
			workerManager.stop();
			workerManager.clear();
			setState(stack, CompassState.INACTIVE);
			return TypedActionResult.consume(stack);
		}

		// Right Click = Open structure selection GUI (client-side only, via callback)
		if (world.isClient() && screenOpener != null) {
			screenOpener.accept(stack);
		}

		return TypedActionResult.consume(stack);
	}

	@Override
	public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack,
			ItemStack newStack) {
		if (getState(oldStack) == getState(newStack)) {
			return false;
		}
		return super.allowComponentsUpdateAnimation(player, hand, oldStack, newStack);
	}

	public void searchForMeteoriteStructures(World world, PlayerEntity player, BlockPos pos, ItemStack stack,
			Identifier targetId) {
		setSearching(stack, targetId);
		stack.set(MeteoriteCompass.SEARCH_RADIUS_COMPONENT, 0);

		if (world instanceof ServerWorld serverWorld) {
			// Search ONLY for the requested structure ID, not all of them!
			Structure targetStructure = StructureUtils.getStructureForId(serverWorld, targetId);

			if (targetStructure == null) {
				MeteoriteCompass.LOGGER.error("Unknown meteorite structure: " + targetId);
				setNotFound(stack, 0, 0);
				return;
			}

			List<Structure> structures = List.of(targetStructure);

			workerManager.stop();
			workerManager.createWorkers(serverWorld, player, stack, structures, pos);
			boolean started = workerManager.start();

			if (!started) {
				setNotFound(stack, 0, 0);
			}
		}
	}

	public void succeed(ItemStack stack, Identifier structureId, int x, int z, int samples,
			boolean displayCoordinates) {
		setFound(stack, structureId, x, z, samples);
		workerManager.clear();
	}

	public void fail(ItemStack stack, int radius, int samples) {
		workerManager.pop();
		boolean started = workerManager.start();
		if (!started) {
			setNotFound(stack, radius, samples);
		}
	}

	public boolean isActive(ItemStack stack) {
		return getState(stack) != CompassState.INACTIVE;
	}

	public void setSearching(ItemStack stack, Identifier structureId) {
		stack.set(MeteoriteCompass.STRUCTURE_ID_COMPONENT, structureId.toString());
		stack.set(MeteoriteCompass.COMPASS_STATE_COMPONENT, CompassState.SEARCHING.getID());
	}

	public void setFound(ItemStack stack, Identifier structureId, int x, int z, int samples) {
		stack.set(MeteoriteCompass.COMPASS_STATE_COMPONENT, CompassState.FOUND.getID());
		stack.set(MeteoriteCompass.STRUCTURE_ID_COMPONENT, structureId.toString());
		stack.set(MeteoriteCompass.FOUND_X_COMPONENT, x);
		stack.set(MeteoriteCompass.FOUND_Z_COMPONENT, z);
		stack.set(MeteoriteCompass.SAMPLES_COMPONENT, samples);

		// Set meteorite type (0 = Megaroid, 1 = Mega Site)
		if (structureId.equals(StructureUtils.MEGAROID)) {
			stack.set(MeteoriteCompass.METEORITE_TYPE_COMPONENT, 0);
		} else if (structureId.equals(StructureUtils.MEGA_SITE)) {
			stack.set(MeteoriteCompass.METEORITE_TYPE_COMPONENT, 1);
		}
	}

	public void setNotFound(ItemStack stack, int searchRadius, int samples) {
		stack.set(MeteoriteCompass.COMPASS_STATE_COMPONENT, CompassState.NOT_FOUND.getID());
		stack.set(MeteoriteCompass.SEARCH_RADIUS_COMPONENT, searchRadius);
		stack.set(MeteoriteCompass.SAMPLES_COMPONENT, samples);
	}

	public void setState(ItemStack stack, CompassState state) {
		stack.set(MeteoriteCompass.COMPASS_STATE_COMPONENT, state.getID());
	}

	public CompassState getState(ItemStack stack) {
		if (stack.contains(MeteoriteCompass.COMPASS_STATE_COMPONENT)) {
			return CompassState.fromID(stack.get(MeteoriteCompass.COMPASS_STATE_COMPONENT));
		}
		return CompassState.INACTIVE;
	}

	public int getFoundStructureX(ItemStack stack) {
		if (stack.contains(MeteoriteCompass.FOUND_X_COMPONENT)) {
			return stack.get(MeteoriteCompass.FOUND_X_COMPONENT);
		}
		return 0;
	}

	public int getFoundStructureZ(ItemStack stack) {
		if (stack.contains(MeteoriteCompass.FOUND_Z_COMPONENT)) {
			return stack.get(MeteoriteCompass.FOUND_Z_COMPONENT);
		}
		return 0;
	}

	public Identifier getStructureId(ItemStack stack) {
		if (stack.contains(MeteoriteCompass.STRUCTURE_ID_COMPONENT)) {
			return Identifier.of(stack.get(MeteoriteCompass.STRUCTURE_ID_COMPONENT));
		}
		return Identifier.of("", "");
	}

	public int getSearchRadius(ItemStack stack) {
		if (stack.contains(MeteoriteCompass.SEARCH_RADIUS_COMPONENT)) {
			return stack.get(MeteoriteCompass.SEARCH_RADIUS_COMPONENT);
		}
		return -1;
	}

	public int getSamples(ItemStack stack) {
		if (stack.contains(MeteoriteCompass.SAMPLES_COMPONENT)) {
			return stack.get(MeteoriteCompass.SAMPLES_COMPONENT);
		}
		return -1;
	}

	public int getDistanceToMeteorite(PlayerEntity player, ItemStack stack) {
		return StructureUtils.getHorizontalDistanceToLocation(player, getFoundStructureX(stack),
				getFoundStructureZ(stack));
	}

}
