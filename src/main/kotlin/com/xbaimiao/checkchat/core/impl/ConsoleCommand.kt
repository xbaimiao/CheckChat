package com.xbaimiao.checkchat.core.impl

import com.xbaimiao.checkchat.core.Command
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.xbaimiao.util.Random

class ConsoleCommand(private val cmd: String) : Command {
    override fun execute(player: Player) {
        submit {
            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                Random.evals<Double>(cmd.replace("%player_name%", player.name))
            )
        }
    }
}