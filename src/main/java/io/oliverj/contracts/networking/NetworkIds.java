package io.oliverj.contracts.networking;

import io.oliverj.contracts.Contracts;
import net.minecraft.util.Identifier;

public class NetworkIds {
    public static final Identifier OPEN_SCREEN_PACKET = new Identifier(Contracts.MOD_ID, "screen_open");
    public static final Identifier SIGN_CONTRACT_PACKET = new Identifier(Contracts.MOD_ID, "contract_bind");
}
