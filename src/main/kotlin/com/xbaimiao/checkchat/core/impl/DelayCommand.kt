package com.xbaimiao.checkchat.core.impl

import com.xbaimiao.checkchat.core.Command
import org.bukkit.entity.Player

class DelayCommand(val int: Int) : Command {

    override fun execute(player: Player) {
        Thread.sleep(int * 1000L)
    }

}