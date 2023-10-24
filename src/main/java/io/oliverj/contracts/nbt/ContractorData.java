package io.oliverj.contracts.nbt;

import com.redgrapefruit.itemnbt3.CustomData;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class ContractorData implements CustomData {
    private String contractor;

    public ContractorData() {
        contractor = "";
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    @Override
    public @NotNull String getNbtCategory() {
        return "Contractor";
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbtCompound) {
        contractor = nbtCompound.getString("Contractor");
    }

    @Override
    public void writeNbt(@NotNull NbtCompound nbtCompound) {
        nbtCompound.putString("Contractor", contractor);
    }
}
