package io.oliverj.contracts.client;

import io.netty.channel.ChannelHandler;
import io.oliverj.contracts.networking.NetworkIds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.S2CPlayChannelEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.screenhandler.client.ClientNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class ContractsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkIds.OPEN_SCREEN_PACKET, ((client, handler, buf, responseSender) -> {
            ItemStack itemStack = buf.readItemStack();
            Hand hand = Hand.MAIN_HAND;
            client.execute(() -> {
                MinecraftClient.getInstance().setScreen(new BookEditScreen(MinecraftClient.getInstance().player, itemStack, hand));
            });
        }));
    }
}
