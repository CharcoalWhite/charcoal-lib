package org.charcoalwhite.charcoallib.api;

public interface EntityApi {
    default boolean hasCommandTag(String commandTag) {
        return false;
    }
}
