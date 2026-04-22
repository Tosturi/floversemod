package com.tosturi.floversemod.entity.custom;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class TigerGirlEntity extends AbstractVillager {

    public TigerGirlEntity(EntityType<? extends AbstractVillager> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D) // Немного быстрее обычного жителя
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    private static final ResourceKey<TradeSet> TIGER_GIRL_TRADES = ResourceKey.create(
            Registries.TRADE_SET,
            Identifier.fromNamespaceAndPath("floversemod",  "tiger_girl_trades")
    );

    @Override
    public @NonNull InteractionResult mobInteract(@NonNull Player player, @NonNull InteractionHand hand) {
        if (this.isAlive() && !this.isTrading()) {
            if (hand == InteractionHand.MAIN_HAND) {
                if (!this.level().isClientSide()) {
                    if (this.getOffers().isEmpty()) {
                        this.updateTrades((ServerLevel) this.level());
                    }

                    this.setTradingPlayer(player);
                    this.openTradingScreen(player, this.getDisplayName(), 1);

                    return InteractionResult.SUCCESS_SERVER;
                } else  {
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
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
        this.addOffersFromTradeSet(level, this.getOffers(), TIGER_GIRL_TRADES);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NonNull ServerLevel level, @NonNull AgeableMob otherParent) {
        // Девочка-тигрица уникальна, она не размножается
        return null;
    }
}