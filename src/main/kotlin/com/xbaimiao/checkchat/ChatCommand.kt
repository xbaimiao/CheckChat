package com.xbaimiao.checkchat

import com.xbaimiao.checkchat.core.Command
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored

data class ChatCommand(
    val time: Int,
    val commands: List<Command>,
    val name: String,
    val message: String
) {

    companion object {
        val checks = HashMap<String, ChatCommand>()
    }

    private val gets = ArrayList<String>()

    fun check() {
        checks[name] = this
        submit(delay = time * 20L) {
            checks.remove(name)
            gets.clear()
        }
    }

    fun run(player: Player) {
        if (gets.contains(player.name)) {
            return
        }
        gets.add(player.name)
        submit(async = true) {
            for (command in commands) {
                command.execute(player)
            }
        }
    }

}