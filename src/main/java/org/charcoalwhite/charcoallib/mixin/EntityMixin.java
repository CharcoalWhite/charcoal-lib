package org.charcoalwhite.charcoallib.mixin;

import org.charcoalwhite.charcoallib.api.EntityApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.PositionUpdater;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityApi{
	@Inject(
		method = "addPassenger(Lnet/minecraft/entity/Entity;)V",
		at = @At("TAIL")
	)
	private void sendPacketOfAddPassenger(Entity passenger, CallbackInfo ci) {
		if (((Entity) (Object) this).isPlayer()) {
			((ServerPlayerEntity) ((Entity) (Object) this)).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(((Entity) (Object) this)));
			if (passenger.isPlayer()) {
				((ServerPlayerEntity) passenger).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(((Entity) (Object) this)));
			}
		}
	}

	@Inject(
		method = "removePassenger(Lnet/minecraft/entity/Entity;)V",
		at = @At("TAIL")
	)
	private void sendPacketOfRemovePassenger(Entity passenger, CallbackInfo ci) {
		if (((Entity) (Object) this).isPlayer()) {
			((ServerPlayerEntity) ((Entity) (Object) this)).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(((Entity) (Object) this)));
			if (passenger.isPlayer()) {
				((ServerPlayerEntity) passenger).networkHandler.sendPacket(new EntityPassengersSetS2CPacket(((Entity) (Object) this)));
			}
		}
	}
	
	@Inject(
		method = "updatePassengerPosition(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity$PositionUpdater;)V",
		at = @At("HEAD"),
		cancellable = true
	)
	private void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater, CallbackInfo ci) {
		Vec3d vec3d = ((Entity) (Object) this).getPassengerRidingPos(passenger);
        positionUpdater.accept(passenger, vec3d.x + (double) passenger.getRidingOffsetX(((Entity) (Object) this)), vec3d.y + (double) passenger.getRidingOffset(((Entity) (Object) this)), vec3d.z + (double) passenger.getRidingOffsetZ(((Entity) (Object) this)));
		ci.cancel();
	}
	
	// @ModifyArg(method = "updatePassengerPosition(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity$PositionUpdater;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity$PositionUpdater;accept(Lnet/minecraft/entity/Entity;DDD)V"), index = 1)
	// private double offsetByRidingOffsetX(Entity passenger, PositionUpdater positionUpdater, double x) {
	// 	return x + (double)passenger.getRidingOffsetX(((Entity) (Object) this));
	// }

	// @ModifyArg(method = "updatePassengerPosition(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity$PositionUpdater;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity$PositionUpdater;accept(Lnet/minecraft/entity/Entity;DDD)V"), index = 3)
	// private double offsetByRidingOffsetZ(Entity passenger, PositionUpdater positionUpdater, double z) {
	// 	return z + (double)passenger.getRidingOffsetZ(((Entity) (Object) this));
	// }

	@Override
	public float getRidingOffsetX(Entity vehicle) {
		return ((Entity) (Object) this).getUnscaledRidingOffsetX(vehicle);
	}

	@Override
	public float getRidingOffsetZ(Entity vehicle) {
		return ((Entity) (Object) this).getUnscaledRidingOffsetZ(vehicle);
	}

	@Override
    public boolean hasCommandTag(String commandTag) {
        return ((Entity) (Object) this).getCommandTags().contains(commandTag);
    }
}
