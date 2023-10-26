package io.oliverj.contracts;

import io.oliverj.contracts.data.ContractData;
import io.oliverj.contracts.data.ContractsPersistence;
import io.oliverj.contracts.registry.EnchantmentRegistry;
import io.oliverj.contracts.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Contracts implements ModInitializer {

    public static final String MOD_ID = "contracts";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public ContractData contractData = ContractData.INSTANCE;

    public static final ItemGroup CONTRACTS = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "contracts"),
            () -> new ItemStack(ItemRegistry.CONTRACT));

    @Override
    public void onInitialize() {
        ItemRegistry.registerItems();
        EnchantmentRegistry.registerEnchantments();
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            ContractsPersistence serverState = ContractsPersistence.getServerState(server);
            serverState.contracts = contractData.contracts;
            serverState.users = contractData.users;
            serverState.user_contracts = contractData.contract_users;
        });
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ContractsPersistence serverState = ContractsPersistence.getServerState(server);
            contractData.contracts = serverState.contracts;
            contractData.contract_users = serverState.user_contracts;
            contractData.users = serverState.users;
        });
    }
}
