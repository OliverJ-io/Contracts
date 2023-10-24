package io.oliverj.contracts.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.HashMap;

public class ContractData {

    public static HashMap<String, String> contracts = new HashMap<String, String>();
    public static void saveContract(String contractor, String contracted) {
        contracts.put(contractor, contracted);
        export_data();
    }

    public static String getContract(String contractor) {
        try {
            import_data();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return contracts.get(contractor);
    }

    private static void export_data() {
        try {
            import_data();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonObject contracts = new JsonObject();
        contracts.add("contracts", contracts);

        try (FileWriter file = new FileWriter(ClassLoader.class.getResource("assets/contracts/store-data/contracts.json").toString())) {
            file.write(contracts.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void import_data() throws FileNotFoundException {
        Gson gson = new Gson();
        contracts = gson.fromJson(new BufferedReader(new FileReader(ClassLoader.class.getResource("assets/contracts/store-data/contracts.json").toString())).toString(), HashMap.class);
    }
}
