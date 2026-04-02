package net.turzilla.poofixes.mixin;

import handyfon.immersivetoolaging.DirtUtil;
import net.minecraft.world.item.ItemStack; // used for addDirt
import handyfon.immersivetoolaging.DirtType; // used for addDirt
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
        // just cancel the method call
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
        // dont add if its ink
        if (type == DirtType.INK) {
            return;
        }

        // if its blood then multiply
        int multiplier = (type == DirtType.BLOOD) ? 2 : 1;
        DirtUtil.addDirt(stack, type, amount*multiplier);
    }
}