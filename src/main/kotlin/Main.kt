import api.defineRole
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
    val role = guild.getRoleById(args[1]) ?: throw Exception("Role not found")
    defineRole(role)
    guild.updateCommands()
        .addCommands(registerCommand).queue()
}
