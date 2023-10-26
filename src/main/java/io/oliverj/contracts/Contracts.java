package io.oliverj.contracts;

import io.oliverj.contracts.data.ContractsPersistence;
import io.oliverj.contracts.registry.EnchantmentRegistry;
import io.oliverj.contracts.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Contracts implements ModInitializer {

    public static final String MOD_ID = "contracts";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final ItemGroup CONTRACTS = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "contracts"),
            () -> new ItemStack(ItemRegistry.CONTRACT));

    @Override
    public void onInitialize() {
        ItemRegistry.registerItems();
        EnchantmentRegistry.registerEnchantments();
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ContractsPersistence serverState = ContractsPersistence.getServerState(server);
        });
    }
}
