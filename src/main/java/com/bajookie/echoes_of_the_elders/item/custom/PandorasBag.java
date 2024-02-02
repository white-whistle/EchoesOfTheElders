package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.screen.PandorasBagScreenHandler;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Soulbound;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PandorasBag extends Item implements IArtifact {
    private static final String BAG_INVENTORY = ModIdentifier.string("inventory");

    public PandorasBag() {
        super(new FabricItemSettings().rarity(Rarity.RARE).maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);

        if (Soulbound.notOwner(stack, user)) return TypedActionResult.fail(stack);

        if (world.isClient) return TypedActionResult.success(stack);

        var nbt = stack.getNbt();
        if (nbt == null) return TypedActionResult.fail(stack);

        var invNbt = nbt.get(BAG_INVENTORY);
        if (invNbt == null) return TypedActionResult.fail(stack);

        var inv = new SimpleInventory(3 * 9) {
            @Override
            public void onClose(PlayerEntity player) {
                super.onClose(player);

                if (this.isEmpty()) {
                    stack.decrement(1);
                    return;
                }

                var invNbt = this.toNbtList();
                var nbt = stack.getNbt();
                nbt.put(BAG_INVENTORY, invNbt);
            }
        };
        inv.readNbtList((NbtList) invNbt);

        // open inventory
        user.openHandledScreen(new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return TextUtil.translatable("screen.echoes_of_the_elders.pandoras_bag.title");
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                return new PandorasBagScreenHandler(syncId, playerInventory, inv);
            }
        });

        return TypedActionResult.success(stack);
    }

    public static void setBagInventory(ItemStack bag, SimpleInventory inventory) {
        var nbt = bag.getOrCreateNbt();
        nbt.put(BAG_INVENTORY, inventory.toNbtList());
    }

    @Override
    public boolean shouldDrop() {
        return false;
    }

    @Override
    public boolean canArtifactMerge() {
        return false;
    }
}
