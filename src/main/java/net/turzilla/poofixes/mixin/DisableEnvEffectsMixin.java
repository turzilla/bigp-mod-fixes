package net.turzilla.poofixes.mixin;

import handyfon.immersivetoolaging.DirtUtil;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.item.ItemStack; // used to disable addDirt
import handyfon.immersivetoolaging.DirtType; // used to disable addDirt
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "handyfon.immersivetoolaging.Immersivetoolaging", remap = false)
public class DisableEnvEffectsMixin {

    @Redirect(
            method = "processEnvironmentAndMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lhandyfon/immersivetoolaging/DirtUtil;addDirt(Lnet/minecraft/world/item/ItemStack;Lhandyfon/immersivetoolaging/DirtType;I)V"
            ),
            remap = false
    )
    private void cancelAddDirt(ItemStack stack, DirtType type, int amount) {
        // just cancels the method call
    }

    @Redirect(
            method = "handleGenericDamageSplash",
            at = @At(
                    value = "INVOKE",
                    target = "Lhandyfon/immersivetoolaging/DirtUtil;addDirt(Lnet/minecraft/world/item/ItemStack;Lhandyfon/immersivetoolaging/DirtType;I)V"
            ),
            remap = false
    )
    private static void modifySplashAddDirt(ItemStack stack, DirtType type, int amount) {
        if (type == DirtType.INK) {
            return;
        }
        // dont add if its ink

        int multiplier = (type == DirtType.BLOOD) ? 2 : 1;
        // if its blood then multiply
        DirtUtil.addDirt(stack, type, amount*multiplier);
    }
}