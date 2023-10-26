package io.oliverj.contracts.items;

import com.redgrapefruit.itemnbt3.DataClient;
import io.oliverj.contracts.Contracts;
import io.oliverj.contracts.nbt.ContractBoolData;
import io.oliverj.contracts.nbt.ContractCompleteData;
import io.oliverj.contracts.nbt.ContractedData;
import io.oliverj.contracts.nbt.ContractorData;
import io.oliverj.contracts.networking.NetworkIds;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ContractItem extends Item {
    public ContractItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.LECTERN)) {
            return LecternBlock.putBookIfAbsent(context.getPlayer(), world, blockPos, blockState, context.getStack()) ? ActionResult.success(world.isClient) : ActionResult.PASS;
        } else {
            return ActionResult.PASS;
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.isSneaking()) {
            ItemStack itemStack = user.getStackInHand(hand);
            MinecraftClient client = MinecraftClient.getInstance();
            if (world.isClient) return super.use(world, user, hand);
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeItemStack(itemStack);
            ServerPlayNetworking.send((ServerPlayerEntity) user, NetworkIds.OPEN_SCREEN_PACKET, buf);
            return TypedActionResult.success(itemStack, world.isClient());
        } else if (user.isSneaking()) {
            ContractBoolData contractDone = new ContractBoolData();
            ContractCompleteData contractComplete = new ContractCompleteData();

            if (world.isClient) return super.use(world, user, hand);
            PacketByteBuf buf = PacketByteBufs.create();
            ServerPlayNetworking.send((ServerPlayerEntity) user, NetworkIds.SIGN_CONTRACT_PACKET, buf);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        DataClient.use(ContractorData::new, stack, (data) -> {
            tooltip.add(new LiteralText("Contractor: " + data.getContractor()));
        });

        DataClient.use(ContractedData::new, stack, (data) -> {
            tooltip.add(new LiteralText("Contracted: " + data.getContracted()));
        });
    }

    public static boolean isValid(@Nullable NbtCompound nbt) {
        if (nbt == null) {
            return false;
        } else if (!nbt.contains("pages", 9)) {
            return false;
        } else {
            NbtList nbtList = nbt.getList("pages", 8);

            for (int i = 0; i < nbtList.size(); ++i) {
                String string = nbtList.getString(i);
                if (string.length() > 32767) {
                    return false;
                }
            }

            return true;
        }
    }

}
