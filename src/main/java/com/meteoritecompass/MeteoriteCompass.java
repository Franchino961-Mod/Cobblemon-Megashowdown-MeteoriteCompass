package com.meteoritecompass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteoritecompass.item.MeteoriteCompassItem;
import com.meteoritecompass.worker.WorldWorkerManager;
import com.mojang.serialization.Codec;

import com.meteoritecompass.network.SearchPacket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class MeteoriteCompass implements ModInitializer {

	public static final String MODID = "meteorite_compass";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static MeteoriteCompassItem METEORITE_COMPASS;
	public static net.minecraft.item.ItemGroup METEORITE_COMPASS_TAB;
	public static net.minecraft.registry.RegistryKey<net.minecraft.item.ItemGroup> METEORITE_COMPASS_TAB_KEY;

	// Data components for storing compass state in ItemStack
	public static final ComponentType<String> STRUCTURE_ID_COMPONENT = ComponentType.<String>builder()
			.codec(Codec.STRING)
			.packetCodec(PacketCodecs.STRING)
			.build();
	public static final ComponentType<Integer> COMPASS_STATE_COMPONENT = ComponentType.<Integer>builder()
			.codec(Codec.INT)
			.packetCodec(PacketCodecs.VAR_INT)
			.build();
	public static final ComponentType<Integer> FOUND_X_COMPONENT = ComponentType.<Integer>builder()
			.codec(Codec.INT)
			.packetCodec(PacketCodecs.VAR_INT)
			.build();
	public static final ComponentType<Integer> FOUND_Z_COMPONENT = ComponentType.<Integer>builder()
			.codec(Codec.INT)
			.packetCodec(PacketCodecs.VAR_INT)
			.build();
	public static final ComponentType<Integer> SEARCH_RADIUS_COMPONENT = ComponentType.<Integer>builder()
			.codec(Codec.INT)
			.packetCodec(PacketCodecs.VAR_INT)
			.build();
	public static final ComponentType<Integer> SAMPLES_COMPONENT = ComponentType.<Integer>builder()
			.codec(Codec.INT)
			.packetCodec(PacketCodecs.VAR_INT)
			.build();
	public static final ComponentType<Integer> METEORITE_TYPE_COMPONENT = ComponentType.<Integer>builder()
			.codec(Codec.INT)
			.packetCodec(PacketCodecs.VAR_INT)
			.build();

	@Override
	public void onInitialize() {
		LOGGER.info("Meteorite Compass is initializing...");

		// Register data components
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MODID, "structure_id"), STRUCTURE_ID_COMPONENT);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MODID, "compass_state"),
				COMPASS_STATE_COMPONENT);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MODID, "found_x"), FOUND_X_COMPONENT);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MODID, "found_z"), FOUND_Z_COMPONENT);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MODID, "search_radius"),
				SEARCH_RADIUS_COMPONENT);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MODID, "samples"), SAMPLES_COMPONENT);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MODID, "meteorite_type"),
				METEORITE_TYPE_COMPONENT);

		// Register item
		METEORITE_COMPASS = Registry.register(
				Registries.ITEM,
				Identifier.of(MODID, "meteorite_compass"),
				new MeteoriteCompassItem(new Item.Settings().maxCount(1)));

		// Register custom creative tab
		METEORITE_COMPASS_TAB_KEY = net.minecraft.registry.RegistryKey.of(
				RegistryKeys.ITEM_GROUP,
				Identifier.of(MODID, "meteorite_compass_tab"));
		METEORITE_COMPASS_TAB = net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup.builder()
				.displayName(net.minecraft.text.Text.translatable("itemGroup.meteorite_compass.tab"))
				.icon(() -> new net.minecraft.item.ItemStack(METEORITE_COMPASS))
				.build();
		Registry.register(Registries.ITEM_GROUP, METEORITE_COMPASS_TAB_KEY, METEORITE_COMPASS_TAB);

		// Add to custom creative tab
		ItemGroupEvents.modifyEntriesEvent(METEORITE_COMPASS_TAB_KEY).register(content -> {
			content.add(METEORITE_COMPASS);
		});

		// Register the C2S packet type so Fabric Networking knows how to encode it.
		// Without this, sending SearchPacket throws a ClassCastException.
		PayloadTypeRegistry.playC2S().register(SearchPacket.TYPE, SearchPacket.CODEC);

		// Register the server-side handler for SearchPacket.
		ServerPlayNetworking.registerGlobalReceiver(SearchPacket.TYPE, SearchPacket::handle);

		ServerTickEvents.START_SERVER_TICK.register(server -> {
			WorldWorkerManager.tick(true);
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			WorldWorkerManager.tick(false);
		});

		// Register server stopping event
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			WorldWorkerManager.clear();
		});
	}

}
