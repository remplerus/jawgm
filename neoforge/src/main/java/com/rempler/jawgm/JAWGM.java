package com.rempler.jawgm;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@Mod(Constants.MOD_ID)
public class JAWGM {

    public JAWGM(IEventBus eventBus) {
        NeoForge.EVENT_BUS.addListener(this::onLevelEvent);
    }

    private void onLevelEvent(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
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