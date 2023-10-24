package io.oliverj.contracts.registry;

import io.oliverj.contracts.Contracts;
import io.oliverj.contracts.items.ContractedEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentRegistry {
    public static final Enchantment CONTRACTED = registerEnchantment("contracted", new ContractedEnchantment());

    private static Enchantment registerEnchantment(String name, Enchantment ench) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(Contracts.MOD_ID, name), ench);
    }

    public static void registerEnchantments() {
        Contracts.LOGGER.info("Registering Mod Enchantments for " + Contracts.MOD_ID);
    }
}
