package io.oliverj.contracts.items;

import io.oliverj.contracts.data.ContractData;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

import java.util.Objects;

public class ContractedEnchantment extends Enchantment {
    public ContractedEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity) {
            if (Objects.equals(target.getUuidAsString(), ContractData.getContract(user.getUuidAsString()))) {
                target.damage(DamageSource.MAGIC, ((LivingEntity) target).getHealth());
            }
        }

        super.onTargetDamaged(user, target, level);
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }
}
