package io.pixeloutlaw.minecraft.spigot.plumbing.v117R1

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractMessageBroadcaster
import net.minecraft.nbt.CompoundTag
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

public object MessageBroadcaster : AbstractMessageBroadcaster() {
    override fun convertItemStackToJson(itemStack: ItemStack): String {
        val nbtTagCompound = CompoundTag()
        val nmsItemStack = CraftItemStack.asNMSCopy(itemStack)
        return nmsItemStack.save(nbtTagCompound).getCompound("tag").toString()
    }
}
