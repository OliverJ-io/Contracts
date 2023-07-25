package io.oliverj.contracts.registry;

import io.oliverj.contracts.Contracts;
import io.oliverj.contracts.items.ContractItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
    public static final Item CONTRACT = registerItem("contract",
            new ContractItem(new FabricItemSettings().group(Contracts.CONTRACTS)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Contracts.MOD_ID, name), item);
    }

    public static void registerItems() {
        Contracts.LOGGER.info("Registering Mod Items for " + Contracts.MOD_ID);
    }
}
