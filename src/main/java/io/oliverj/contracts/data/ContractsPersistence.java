package io.oliverj.contracts.data;

import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import io.oliverj.contracts.Contracts;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ContractsPersistence extends PersistentState {

    public HashMap<String, String> contracts = new HashMap<>();
    public HashMap<String, List<String>> user_contracts = new HashMap<>();
    public List<String> users = Lists.newArrayList();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.put("contracts", (NbtElement) contracts);
        nbt.put("user_contracts", (NbtElement) user_contracts);
        nbt.put("users", (NbtElement) users);
        return nbt;
    }

    public static ContractsPersistence createFromNbt(NbtCompound tag) {
        ContractsPersistence state = new ContractsPersistence();
        state.contracts = (HashMap<String, String>) tag.get("contracts");
        state.user_contracts = (HashMap<String, List<String>>) tag.get("user_contracts");
        state.users = (List<String>) tag.get("users");
        return state;
    }
    public static ContractsPersistence getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager();
        Supplier<ContractsPersistence> supplier = Suppliers.ofInstance(new ContractsPersistence());
        ContractsPersistence state = persistentStateManager.getOrCreate(ContractsPersistence::createFromNbt, supplier, Contracts.MOD_ID);
        state.markDirty();
        return state;
    }
}
