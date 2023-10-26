package io.oliverj.contracts.client;

import com.redgrapefruit.itemnbt3.DataClient;
import io.oliverj.contracts.Contracts;
import io.oliverj.contracts.data.ContractData;
import io.oliverj.contracts.items.ContractItem;
import io.oliverj.contracts.nbt.ContractBoolData;
import io.oliverj.contracts.nbt.ContractCompleteData;
import io.oliverj.contracts.nbt.ContractedData;
import io.oliverj.contracts.nbt.ContractorData;
import io.oliverj.contracts.networking.NetworkIds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class ContractsClient implements ClientModInitializer {

    private static final KeyBinding signContract = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.contracts.sign", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "category.contracts.contract"));

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkIds.OPEN_SCREEN_PACKET, ((client, handler, buf, responseSender) -> {
            ItemStack itemStack = buf.readItemStack();
            Hand hand = Hand.MAIN_HAND;
            client.execute(() -> {
                MinecraftClient.getInstance().setScreen(new BookEditScreen(MinecraftClient.getInstance().player, itemStack, hand));
            });
        }));
        ClientPlayNetworking.registerGlobalReceiver(NetworkIds.SIGN_CONTRACT_PACKET, ((client, handler, buf, responseSender) -> {
            assert client.player != null;
            DataClient.use(ContractorData::new, client.player.getStackInHand(Hand.MAIN_HAND), (data) -> {
                data.setContractor(client.player.getUuidAsString());
            });
            DataClient.use(ContractBoolData::new, client.player.getStackInHand(Hand.MAIN_HAND), (data) -> {
                data.setContractBool(true);
            });
        }));
        ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
            while (signContract.wasPressed()) {
                assert client1.player != null;
                assert !client1.player.getStackInHand(Hand.MAIN_HAND).isOf(new ContractItem(new FabricItemSettings()));
                DataClient.use(ContractedData::new, client1.player.getStackInHand(Hand.MAIN_HAND), (data) -> {
                    data.setContracted(client1.player.getUuidAsString());
                    String contractor = String.valueOf(Objects.requireNonNull(client1.player.getStackInHand(Hand.MAIN_HAND).getNbt()).get("Contractor"));
                    ContractData.saveContract(contractor, data.getContracted());
                });
                DataClient.use(ContractCompleteData::new, client1.player.getStackInHand(Hand.MAIN_HAND), (data) -> {
                    data.setContractBool(true);
                });
                //ContractData.saveContract(new ContractorData().getContractor(), new ContractedData().getContracted());
            }
        });
    }
}
