package com.tosturi.floversemod.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class TigerGirlEntity extends AbstractVillager {

    public TigerGirlEntity(EntityType<? extends AbstractVillager> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D) // Как у игрока/жителя
                .add(Attributes.MOVEMENT_SPEED, 0.5D) // Немного быстрее обычного жителя
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    protected void rewardTradeXp(@NonNull MerchantOffer offer) {
        // Нам пока не нужен опыт за торговлю, как у обычных жителей
    }

    @Override
    protected void updateTrades(@NonNull ServerLevel level) {
        // Здесь мы позже пропишем логику продажи Флориков
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NonNull ServerLevel level, @NonNull AgeableMob otherParent) {
        // Девочка-тигрица уникальна, она не размножается
        return null;
    }
}