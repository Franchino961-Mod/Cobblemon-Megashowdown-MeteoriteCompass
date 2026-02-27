package com.meteoritecompass.client;

import com.meteoritecompass.MeteoriteCompass;
import com.meteoritecompass.item.MeteoriteCompassItem;
import com.meteoritecompass.util.CompassState;
import com.meteoritecompass.util.StructureUtils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class MeteoriteCompassClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		MeteoriteCompass.LOGGER.info("Meteorite Compass Client is initializing...");

		// Register HUD rendering for compass info
		HudRenderCallback.EVENT.register((context, tickCounter) -> {
			MinecraftClient client = MinecraftClient.getInstance();
			if (client.player == null)
				return;

			ItemStack stack = client.player.getMainHandStack();
			if (stack.isEmpty() || stack.getItem() != MeteoriteCompass.METEORITE_COMPASS) {
				stack = client.player.getOffHandStack();
			}

			if (!stack.isEmpty() && stack.getItem() == MeteoriteCompass.METEORITE_COMPASS) {
				renderCompassInfo(context, client, stack);
			}
		});

		// Register the screen opener callback so right-clicking opens the GUI.
		// This stays here (client entrypoint) to avoid referencing client-only classes
		// inside the common MeteoriteCompassItem class.
		MeteoriteCompassItem.screenOpener = (stack) -> MinecraftClient.getInstance()
				.setScreen(new com.meteoritecompass.gui.MeteoriteCompassScreen(stack));

		// Register the model predicate that drives the needle animation.
		// The JSON model uses overrides keyed on "meteorite_compass:compass_angle"
		// (0-1).
		ModelPredicateProviderRegistry.register(
				MeteoriteCompass.METEORITE_COMPASS,
				Identifier.of(MeteoriteCompass.MODID, "compass_angle"),
				MeteoriteCompassClient::getCompassAngle);
	}

	private void renderCompassInfo(net.minecraft.client.gui.DrawContext context, MinecraftClient client,
			ItemStack stack) {
		if (!stack.contains(MeteoriteCompass.COMPASS_STATE_COMPONENT)) {
			return;
		}

		int state = stack.getOrDefault(MeteoriteCompass.COMPASS_STATE_COMPONENT, CompassState.INACTIVE.getID());
		int x = context.getScaledWindowWidth() / 2;
		int y = context.getScaledWindowHeight() / 2 + 20;

		if (state == CompassState.SEARCHING.getID()) {
			String structureId = stack.getOrDefault(MeteoriteCompass.STRUCTURE_ID_COMPONENT, "");
			int samples = stack.getOrDefault(MeteoriteCompass.SAMPLES_COMPONENT, 0);
			int radius = stack.getOrDefault(MeteoriteCompass.SEARCH_RADIUS_COMPONENT, 0);
			String meteoriteType = StructureUtils.getMeteoriteName(Identifier.of(structureId));

			Text text = Text.translatable("string.meteorite_compass.status.searching", meteoriteType, samples, radius);
			context.drawCenteredTextWithShadow(client.textRenderer, text, x, y, 0xFFFFFF);
		} else if (state == CompassState.FOUND.getID()) {
			String structureId = stack.getOrDefault(MeteoriteCompass.STRUCTURE_ID_COMPONENT, "");
			int foundX = stack.getOrDefault(MeteoriteCompass.FOUND_X_COMPONENT, 0);
			int foundZ = stack.getOrDefault(MeteoriteCompass.FOUND_Z_COMPONENT, 0);
			String meteoriteType = StructureUtils.getMeteoriteName(Identifier.of(structureId));

			if (client.player != null) {
				BlockPos playerPos = client.player.getBlockPos();
				int distance = StructureUtils.getHorizontalDistanceToLocation(playerPos, foundX, foundZ);

				Text text = Text.translatable("string.meteorite_compass.status.found", meteoriteType, distance);
				context.drawCenteredTextWithShadow(client.textRenderer, text, x, y, 0x00FF00);
			}
		} else if (state == CompassState.NOT_FOUND.getID()) {
			int samples = stack.getOrDefault(MeteoriteCompass.SAMPLES_COMPONENT, 0);
			int radius = stack.getOrDefault(MeteoriteCompass.SEARCH_RADIUS_COMPONENT, 0);

			Text text = Text.translatable("string.meteorite_compass.status.not_found", radius, samples);
			context.drawCenteredTextWithShadow(client.textRenderer, text, x, y, 0xFF0000);
		}
	}

	// Compass needle angle calculation — registered as a model predicate.
	// Returns a value in [0, 1) that selects one of the 32 compass frames.
	public static float getCompassAngle(ItemStack stack, ClientWorld world, LivingEntity entity, int seed) {
		if (!stack.contains(MeteoriteCompass.COMPASS_STATE_COMPONENT)) {
			return (float) Math.random();
		}

		int state = stack.getOrDefault(MeteoriteCompass.COMPASS_STATE_COMPONENT, CompassState.INACTIVE.getID());

		if (state == CompassState.FOUND.getID() && entity != null) {
			int foundX = stack.getOrDefault(MeteoriteCompass.FOUND_X_COMPONENT, 0);
			int foundZ = stack.getOrDefault(MeteoriteCompass.FOUND_Z_COMPONENT, 0);

			double dx = foundX - entity.getX();
			double dz = foundZ - entity.getZ();

			// atan2 gives the angle from +X axis, in radians.
			// Minecraft: yaw 0 = South (+Z), increases clockwise when viewed from above.
			// atan2(dz, dx) → degrees → +90 to align South → subtract entity yaw.
			double angleToTarget = Math.toDegrees(Math.atan2(dz, dx)) + 90.0;
			double relativeAngle = angleToTarget - entity.getYaw();

			// Normalize to [0, 1)
			return MathHelper.floorMod((float) relativeAngle, 360.0f) / 360.0f;
		} else if (state == CompassState.SEARCHING.getID()) {
			// Spin while searching
			return (float) (((System.currentTimeMillis() / 50) % 360) / 360.0);
		}

		// Random wobble when inactive / not found
		return (float) Math.random();
	}
}
