package org.charcoalwhite.charcoallib.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.charcoalwhite.charcoallib.api.PlayerEntityApi;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerEntityApi{
	@Override
	public void addPassenger(Entity passenger) {
		((Entity)(Object)this).addPassenger(passenger);
		((ServerPlayerEntity)(Object)this).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(((Entity)(Object)this)));
		if (passenger.isPlayer()) {
			((ServerPlayerEntity)passenger).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(((Entity)(Object)this)));
		}
	}

	@Override
	public void removePassenger(Entity passenger) {
		((Entity)(Object)this).removePassenger(passenger);
		((ServerPlayerEntity)(Object)this).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(((Entity)(Object)this)));
		if (passenger.isPlayer()) {
			((ServerPlayerEntity)passenger).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(((Entity)(Object)this)));
		}
	}
}
