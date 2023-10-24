package io.oliverj.contracts.nbt;

import com.redgrapefruit.itemnbt3.CustomData;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class ContractBoolData implements CustomData {

    private Boolean contractstep;

    public ContractBoolData() {
        contractstep = false;
    }

    public Boolean getContractBool() {
        return contractstep;
    }

    public void setContractBool(Boolean contractstep) {
        this.contractstep = contractstep;
    }

    @Override
    public @NotNull String getNbtCategory() {
        return "ContractStep";
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbtCompound) {
        nbtCompound.getBoolean("ContractStep");
    }

    @Override
    public void writeNbt(@NotNull NbtCompound nbtCompound) {
        nbtCompound.putBoolean("ContractStep", contractstep);
    }
}
