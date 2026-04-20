package com.tosturi.floversemod.entity.client;

import com.tosturi.floversemod.entity.custom.TigerGirlEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.npc.VillagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;



public class PlaceholderRenderer extends MobRenderer<TigerGirlEntity, VillagerRenderState, VillagerModel> {

    private static final Identifier VILLAGER_LOCATION =
            Identifier.withDefaultNamespace("textures/entity/villager/villager.png");

    public PlaceholderRenderer(EntityRendererProvider.Context context) {
        // Используем стандартную модель жителя, адаптированную под RenderState
        super(context, new VillagerModel(context.bakeLayer(ModelLayers.VILLAGER)), 0.5f);
    }

    // 1. Создаем объект состояния
    @Override
    public @NonNull VillagerRenderState createRenderState() {
        return new VillagerRenderState();
    }

    // 2. Копируем данные из сущности в состояние (здесь можно передавать анимации и т.д.)
    @Override
    public void extractRenderState(@NonNull TigerGirlEntity entity, @NonNull VillagerRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        // Здесь ванильный код жителя заполняет данные о профессии, но мы пока оставим базу
    }

    // 3. Тот самый метод, на который ругалась Java
    // В новых версиях он принимает State, а не саму Entity
    @Override
    public @NonNull Identifier getTextureLocation(@NonNull VillagerRenderState state) {
        return VILLAGER_LOCATION;
    }
}


//public class TigerGirlRenderer extends VillagerRenderer<TigerGirlEntity> {
//}
