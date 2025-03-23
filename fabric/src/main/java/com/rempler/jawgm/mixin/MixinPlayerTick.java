package com.rempler.jawgm.mixin;

import com.rempler.jawgm.CommonClass;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class MixinPlayerTick {

    private final Player player = (Player) (Object) this;

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void tick(CallbackInfo info) {
        if (!player.level().isClientSide) {
            ServerLevel level = (ServerLevel) player.level();
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (level.isRaining()) {
                float random = level.random.nextInt(0, 10000);
                if (random <= 200) {
                    Inventory inventory = serverPlayer.getInventory();
                    if (inventory.getFreeSlot() == -1) {
                        return;
                    }
                    if (serverPlayer.getMainHandItem().is(Items.BUCKET) || serverPlayer.getOffhandItem().is(Items.BUCKET)) {
                        if (serverPlayer.getOffhandItem().is(Items.BUCKET)) {
                            CommonClass.newFunction(inventory.offhand, serverPlayer.getOffhandItem().getCount(), Items.BUCKET);
                        } else {
                            CommonClass.newFunction(inventory.items, serverPlayer.getMainHandItem().getCount(), Items.BUCKET);
                        }
                        inventory.add(Items.WATER_BUCKET.getDefaultInstance());
                    } else if (serverPlayer.getMainHandItem().is(Items.GLASS_BOTTLE) || serverPlayer.getOffhandItem().is(Items.GLASS_BOTTLE)) {
                        if (serverPlayer.getOffhandItem().is(Items.GLASS_BOTTLE)) {
                            CommonClass.newFunction(inventory.offhand, serverPlayer.getOffhandItem().getCount(), Items.GLASS_BOTTLE);
                        } else {
                            CommonClass.newFunction(inventory.items, serverPlayer.getMainHandItem().getCount(), Items.GLASS_BOTTLE);
                        }
                        inventory.add(PotionContents.createItemStack(Items.POTION, Potions.WATER));
                    }
                }
            }
        }
    }
}
