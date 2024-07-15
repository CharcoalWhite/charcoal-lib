package org.charcoalwhite.charcoallib.mixin;

import net.minecraft.entity.Entity;
import org.charcoalwhite.charcoallib.api.EntityApi;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityApi{
	@Override
    public boolean hasCommandTag(String commandTag) {
        return ((Entity) (Object)this).getCommandTags().contains(commandTag);
    }
}
