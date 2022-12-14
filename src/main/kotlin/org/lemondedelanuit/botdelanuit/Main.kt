package org.lemondedelanuit.botdelanuit

import org.lemondedelanuit.botdelanuit.api.roleOfMember
import org.lemondedelanuit.botdelanuit.api.welcomeChannel
import org.lemondedelanuit.botdelanuit.event.MemberEvent
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import org.lemondedelanuit.botdelanuit.commands.*

fun main(args: Array<String>) {
    val api = JDABuilder.createDefault(args[0], GatewayIntent.GUILD_MEMBERS)
        .enableIntents(GatewayIntent.GUILD_MEMBERS)
        .addEventListeners(MemberEvent(), RegisterEvent(), EconomicsEvent(), PollEvent())
        .build()
    api.awaitReady()
    api.presence.setPresence(OnlineStatus.ONLINE, Activity.watching("Le Monde de la Nuit"), false)
    val guild = api.guilds.first()!!
    roleOfMember = guild.getRoleById(args[1]) ?: throw Exception("Role not found")
    welcomeChannel = guild.getTextChannelById(args[2]) ?: throw Exception("Channel not found")
    guild.updateCommands()
        .addCommands(registerCommand, economicsCommand, pollCommand)
        .queue()
}
