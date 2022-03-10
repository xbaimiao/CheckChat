package com.xbaimiao.checkchat.core.impl

import com.xbaimiao.checkchat.core.Command
import org.bukkit.entity.Player
import taboolib.module.chat.colored

class TellCommand(private val string: String) : Command {

    override fun execute(player: Player) {
        player.sendMessage(string.colored())
    }

}