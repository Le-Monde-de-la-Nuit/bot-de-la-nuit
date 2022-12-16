import api.roleOfMember
import api.welcomeChannel
import commands.*
import event.MemberEvent
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent

fun main(args: Array<String>) {
    for (arg in args) {
        println(arg)
    }
    val api = JDABuilder.createDefault(args[0], GatewayIntent.GUILD_MEMBERS)
        .enableIntents(GatewayIntent.GUILD_MEMBERS)
        .addEventListeners(MemberEvent(), RegisterEvent())
        .build()
    api.awaitReady()
    api.presence.setPresence(OnlineStatus.ONLINE, Activity.watching("Le Monde de la Nuit"), false)
    val guild = api.guilds.first()!!
    roleOfMember = guild.getRoleById(args[1]) ?: throw Exception("Role not found")
    welcomeChannel = guild.getTextChannelById(args[2]) ?: throw Exception("Channel not found")
    guild.updateCommands()
        .addCommands(registerCommand).queue()
}
