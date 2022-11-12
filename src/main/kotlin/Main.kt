import api.defineRole
import event.MemberEvent
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

fun main(args: Array<String>) {
    for (arg in args) {
        println(arg)
    }
    val api = JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
        .enableIntents(GatewayIntent.GUILD_MEMBERS)
        .addEventListeners(MemberEvent())
        .build()
    api.awaitReady()
    val role = api.guilds.first().getRoleById(args[1]) ?: throw Exception("Role not found")
    defineRole(role)
}
