package io.pixeloutlaw.minecraft.spigot.plumbing.v120R3

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractMessageBroadcaster
import net.minecraft.nbt.CompoundTag
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

public object MessageBroadcaster : AbstractMessageBroadcaster() {
    override fun convertItemStackToJson(itemStack: ItemStack): String {
        val nbtTagCompound = CompoundTag()
        val nmsItemStack = CraftItemStack.asNMSCopy(itemStack)
        return nmsItemStack.save(nbtTagCompound).getCompound("tag").toString()
    }
}