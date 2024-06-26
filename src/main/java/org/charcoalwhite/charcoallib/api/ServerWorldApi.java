package org.charcoalwhite.charcoallib.api;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;

public interface ServerWorldApi {
    default <T extends Entity> List<? extends T> getEntitiesByClass(Class<T> entityClass, Predicate<? super T> predicate) {
        return Lists.newArrayList();
    }
}
