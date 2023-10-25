package io.oliverj.contracts.data;

import com.google.gson.*;
import io.oliverj.contracts.Contracts;
import org.apache.commons.compress.utils.Lists;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ContractData {

    public static HashMap<String, String> contracts = new HashMap<String, String>();
    public static HashMap<String, List<String>> contract_users = new HashMap<String, List<String>>();

    public static List<String> users = Lists.newArrayList();
    public static void saveContract(String contractor, String contracted) {
        if(contractExists(contractor, contracted)) return;
        String uuid = UUID.randomUUID().toString();
        contracts.put(uuid, contracted);
        saveContractUser(contractor, uuid);
        export_data();
    }

    public static void saveContractUser(String contractor, String contractid) {
        try {
            import_data();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contract_users.containsKey(contractor)) {
            contract_users.get(contractor).add(contractid);
        } else {
            List<String> list = Lists.newArrayList();
            list.add(contractid);
            contract_users.put(contractor, list);
            users.add(contractor);
        }
        export_data();
    }

    public static String getContract(String contractid) {
        try {
            import_data();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return contracts.get(contractid);
    }

    public static List<String> getUserContracts(String UUID) {
        return contract_users.get(UUID);
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

    private static void export_data() {
        try {
            import_data();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        StringBuilder contractString = new StringBuilder(gson.toJson(contracts));

        for (String user : users) {
            HashMap<String, List<String>> user_data = new HashMap<String, List<String>>();
            user_data.put(user, getUserContracts(user));
            String userString = gson.toJson(user_data);
            contractString.append(userString);
        }

        try (FileWriter file = new FileWriter(ClassLoader.class.getResource("data/store-data/contracts.json").toString())) {
            Contracts.LOGGER.info(contractString.toString());
            file.write(contractString.toString());
            file.flush();
        } catch (IOException e) {
            Contracts.LOGGER.error("Failed to write contract data: "+ e.toString());
        }
    }

    private static void import_data() throws FileNotFoundException {
        Gson gson = new Gson();
        if (ClassLoader.class.getResource("data/store-data/contract.json") != null) {
            HashMap jsondata = gson.fromJson(new BufferedReader(new FileReader(Objects.requireNonNull(ClassLoader.class.getResource("data/store-data/contracts.json")).toString())).toString(), HashMap.class);
            contracts = (HashMap<String, String>) jsondata.get("contracts");
            contract_users = (HashMap<String, List<String>>) jsondata.get("contract-users");
            users = (List<String>) jsondata.get("users");
        }
    }
}
