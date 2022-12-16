import api.roleOfMember
import api.welcomeChannel
import commands.*
import event.MemberEvent
import net.dv8tion.jda.api.JDABuilder
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
    val guild = api.guilds.first()!!
    roleOfMember = guild.getRoleById(args[1]) ?: throw Exception("Role not found")
    welcomeChannel = guild.getTextChannelById(args[2]) ?: throw Exception("Channel not found")
    guild.updateCommands()
        .addCommands(registerCommand).queue()
}
