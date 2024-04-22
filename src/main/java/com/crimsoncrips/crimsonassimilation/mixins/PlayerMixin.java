package com.crimsoncrips.crimsonassimilation.mixins;


import com.crimsoncrips.crimsonassimilation.misc.CrimsonAdvancementTriggerRegistry;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;


@Mixin(ServerPlayer.class)
public abstract class PlayerMixin extends Player {


    String[] witherID = new String[10];

    String[] wardenID = new String[5];


    int withercount = 0;
    int witherReset = 0;

    int reset = 0;
    int wardencount = 0;

    public PlayerMixin(Level p_250508_, BlockPos p_250289_, float p_251702_, GameProfile p_252153_) {
        super(p_250508_, p_250289_, p_251702_, p_252153_);
    }


    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer)(Object)this;
        Iterator var4 = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(20,5,20)).iterator();

        while (var4.hasNext()) {
            boolean witherallowed = false;
            boolean wardenallowed = false;
            Entity entity = (Entity) var4.next();
            if (entity instanceof Warden && wardencount < 5) {
                String uuid = String.valueOf(entity.getId());
                if (wardencount <= 0){
                    wardenID[wardencount++] = uuid;
                } else {
                    for (int i = 0; i < wardencount; i++) {
                        if (wardenID[i].equalsIgnoreCase(uuid)) {
                            wardenallowed = false;
                            break;
                        } else wardenallowed = true;

                    }
                    if (wardenallowed) {
                        wardenID[wardencount++] = uuid;
                        reset = 0;
                    }
                }

            } else if (entity instanceof WitherBoss && withercount < 10) {
                String uuid = String.valueOf(entity.getId());
                if (withercount <= 0){
                    witherID[withercount++] = uuid;
                } else {
                    for (int i = 0; i < withercount; i++) {
                        if (witherID[i].equalsIgnoreCase(uuid)) {
                            witherallowed = false;
                            break;
                        } else witherallowed = true;

                    }
                    if (witherallowed) {
                        witherID[withercount++] = uuid;
                        witherReset = 0;
                    }
                }

            }


        }
        reset++;
        if (reset >= 100) {
            wardenID = new String[5];
            reset = 0;
            wardencount = 0;

            witherID = new String[10];
            witherReset = 0;
            withercount = 0;
        }
        if (wardencount >= 5) {
            CrimsonAdvancementTriggerRegistry.WARDEN_HELL.trigger(player);
        }
        if (withercount >= 10) {
            witherID = new String[10];
            CrimsonAdvancementTriggerRegistry.WITHER_HELL.trigger(player);
        }

        if (this.experienceLevel >= 100) CrimsonAdvancementTriggerRegistry.XPERIENCED.trigger(player);
    }



}
