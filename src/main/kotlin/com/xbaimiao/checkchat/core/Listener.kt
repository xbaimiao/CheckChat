package com.xbaimiao.checkchat.core

import com.xbaimiao.checkchat.ChatCommand
import org.bukkit.event.player.AsyncPlayerChatEvent
import taboolib.common.platform.event.SubscribeEvent

object Listener {

    @SubscribeEvent
    fun chat(event: AsyncPlayerChatEvent) {
        val message = event.message
        for (check in ChatCommand.checks) {
            if (message == check.key) {
                check.value.run(event.player)
            }
        }
    }

}