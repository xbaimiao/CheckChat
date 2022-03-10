package com.xbaimiao.checkchat.core.impl

import com.xbaimiao.checkchat.core.Command
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit

class ConsoleCommand(private val cmd: String) : Command {
    override fun execute(player: Player) {
        submit {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player_name%", player.name))
        }
    }
}