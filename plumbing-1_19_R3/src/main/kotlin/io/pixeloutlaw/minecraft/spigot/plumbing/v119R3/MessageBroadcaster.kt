package io.pixeloutlaw.minecraft.spigot.plumbing.v119R3

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractMessageBroadcaster
import net.minecraft.nbt.NBTTagCompound
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

object MessageBroadcaster : AbstractMessageBroadcaster() {
    override fun convertItemStackToJson(itemStack: ItemStack): String {
        val nbtTagCompound = NBTTagCompound()
        val nmsItemStack = CraftItemStack.asNMSCopy(itemStack)
        return nmsItemStack.b(nbtTagCompound).p("tag").toString()
    }
}
