package com.xbaimiao.checkchat

import com.xbaimiao.checkchat.core.Command
import com.xbaimiao.checkchat.core.impl.ConsoleCommand
import com.xbaimiao.checkchat.core.impl.DelayCommand
import com.xbaimiao.checkchat.core.impl.TellCommand
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import taboolib.common.platform.Plugin
import taboolib.common.platform.command.command
import taboolib.expansion.createHelper
import taboolib.module.chat.colored
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile

object Main : Plugin() {

    @Config(value = "config.yml", migrate = true)
    lateinit var conf: ConfigFile
        private set

    private val cache = ArrayList<ChatCommand>()

    override fun onEnable() {
        load()
        command(
            "checkchat",
            permission = "checkchat.op"
        ) {
            literal("check", optional = true) {
                dynamic {
                    suggestion<CommandSender>(uncheck = true) { _, _ ->
                        Bukkit.getOnlinePlayers().map { it.name }
                    }
                    dynamic {
                        suggestion<CommandSender> { _, _ ->
                            cache.map { it.name }
                        }
                        execute<CommandSender> { sender, args, argument ->
                            sender.sendMessage("开始监测 $argument")
                            val cmd = cache.first { it.name == argument }.also {
                                it.check()
                            }
                            Bukkit.getOnlinePlayers().forEach {
                                it.sendMessage(
                                    PlaceholderAPI.setPlaceholders(
                                        Bukkit.getOnlinePlayers().first { it.name == args.argument(-1) },
                                        cmd.message.colored()
                                    )
                                )
                            }
                        }
                    }
                }
            }
            literal("reload", optional = true) {
                execute<CommandSender> { sender, _, _ ->
                    cache.clear()
                    load()
                    sender.sendMessage("重载完成")
                }
            }
            createHelper()
        }
    }

    private fun load() {
        conf.reload()
        conf.getConfigurationSection("checks")?.let {
            for (key in it.getKeys(false)) {
                val time = it.getInt("$key.time")
                val commands = it.getStringList("$key.commands")
                val c = ArrayList<Command>()
                for (command in commands) {
                    if (command.startsWith("tell: ")) {
                        c.add(TellCommand(command.substring(6)))
                        continue
                    }
                    if (command.startsWith("delay: ")) {
                        c.add(DelayCommand(command.substring(7).toInt()))
                        continue
                    }
                    if (command.startsWith("command: ")) {
                        c.add(ConsoleCommand(command.substring(9)))
                        continue
                    }
                }
                cache.add(ChatCommand(time, c, key, it.getString("$key.message")!!))
            }
        }
    }

}