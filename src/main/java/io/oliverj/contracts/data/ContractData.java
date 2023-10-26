package io.oliverj.contracts.data;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.compress.utils.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ContractData {

    public HashMap<String, String> contracts = new HashMap<>();
    public HashMap<String, List<String>> contract_users = new HashMap<>();

    public List<String> users = Lists.newArrayList();

    public static ContractData INSTANCE = new ContractData();
    public static void saveContract(String contractor, String contracted) {
        if(contractExists(contractor, contracted)) return;
        String uuid = UUID.randomUUID().toString();
        INSTANCE.contracts.put(uuid, contracted);
        saveContractUser(contractor, uuid);
    }

    public static void saveContractUser(String contractor, String contractid) {
        if (INSTANCE.contract_users.containsKey(contractor)) {
            INSTANCE.contract_users.get(contractor).add(contractid);
        } else {
            List<String> list = Lists.newArrayList();
            list.add(contractid);
            INSTANCE.contract_users.put(contractor, list);
            INSTANCE.users.add(contractor);
        }
    }

    public static String getContract(String contractid) {
        return INSTANCE.contracts.get(contractid);
    }

    public static List<String> getUserContracts(String UUID) {
        return INSTANCE.contract_users.get(UUID);
    }

    public static String getContractId(String UUID, String targetUUID) {
        for(String contract : getUserContracts(UUID)) {
            if(Objects.equals(getContract(contract), targetUUID)) {
                return contract;
            }
        }
        return null;
    }

    public static Boolean contractExists(String contractor, String contracted) {
        return getContract(getContractId(contractor, contracted)).equals(contracted);
    }
}
