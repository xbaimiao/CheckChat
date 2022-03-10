package com.xbaimiao.checkchat.core

import org.bukkit.entity.Player

interface Command {
    fun execute(player: Player)
}