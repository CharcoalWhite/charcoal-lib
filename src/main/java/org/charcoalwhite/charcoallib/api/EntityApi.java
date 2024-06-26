package org.charcoalwhite.charcoallib.api;

import net.minecraft.entity.Entity;

public interface EntityApi {
    default float getRidingOffsetX(Entity vehicle) {
        return 0.0f;
    }

    default float getRidingOffsetZ(Entity vehicle) {
        return 0.0f;
    }

    default float getUnscaledRidingOffsetX(Entity vehicle) {
        return 0.0f;
    }

    default float getUnscaledRidingOffsetZ(Entity vehicle) {
        return 0.0f;
    }

    default boolean hasCommandTag(String commandTag) {
        return false;
    }
}
