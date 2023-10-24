package io.oliverj.contracts.nbt;

import com.redgrapefruit.itemnbt3.CustomData;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class ContractedData implements CustomData {

    private String contracted;

    public ContractedData() {
        contracted = "";
    }

    public String getContracted() {
        return contracted;
    }

    public void setContracted(String contracted) {
        this.contracted = contracted;
    }

    @Override
    public @NotNull String getNbtCategory() {
        return "Contracted";
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbtCompound) {
        contracted = nbtCompound.getString("Contracted");
    }

    @Override
    public void writeNbt(@NotNull NbtCompound nbtCompound) {
        nbtCompound.putString("Contracted", contracted);
    }
}
