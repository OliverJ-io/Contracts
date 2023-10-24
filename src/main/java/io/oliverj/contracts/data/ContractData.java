package io.oliverj.contracts.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ContractData {

    public static HashMap<String, String> contracts = new HashMap<String, String>();
    public static void saveContract(String contractor, String contracted) {
        contracts.put(contractor, contracted);
        export_data();
    }

    public static String getContract(String contractor) {
        import_data();
        return contracts.get(contractor);
    }

    private static void export_data() {
        import_data();
        JsonObject contracts = new JsonObject();
        contracts.add("contracts", contracts);

        try (FileWriter file = new FileWriter(ClassLoader.class.getResource("assets/contracts/store-data/contracts.json").toString())) {
            file.write(contracts.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void import_data() {
        Gson gson = new Gson();
        contracts = gson.fromJson(ClassLoader.class.getResource("assets/contracts/store-data/contracts.json").toString(), HashMap.class);
    }
}
