package com.tosturi.floversemod.event;

import com.tosturi.floversemod.Config;
import com.tosturi.floversemod.data.TigerGirlVillageData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TigerGirlRespawnManager {

    /** Schedule a respawn for the given bell position using the configured delay. */
    public void schedule(BlockPos bell, long currentTick, TigerGirlVillageData data) {
        long fireTick = currentTick + Config.RESPAWN_DELAY_TICKS.get();
        data.schedulePendingRespawn(bell, fireTick);
    }

    /**
     * Returns all bell positions whose respawn timer has elapsed and removes them
     * from the persistent store.
     */
    public List<BlockPos> collectDue(ServerLevel level, TigerGirlVillageData data) {
        long currentTick = level.getGameTime();
        List<BlockPos> due = new ArrayList<>();
        for (Map.Entry<BlockPos, Long> entry : data.getPendingRespawns().entrySet()) {
            if (currentTick >= entry.getValue()) {
                due.add(entry.getKey());
            }
        }
        due.forEach(data::clearPendingRespawn);
        return due;
    }
}
