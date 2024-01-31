package io.pixeloutlaw.minecraft.spigot.plumbing.v120R2

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractMessageBroadcaster
import net.minecraft.nbt.NBTTagCompound
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

public object MessageBroadcaster : AbstractMessageBroadcaster() {
    override fun convertItemStackToJson(itemStack: ItemStack): String {
        val nbtTagCompound = NBTTagCompound()
        val nmsItemStack = CraftItemStack.asNMSCopy(itemStack)
        return nmsItemStack.b(nbtTagCompound).p("tag").toString()
    }
}
