import api.defineRole
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

fun main(args: Array<String>) {
    val api = JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
        .enableIntents(GatewayIntent.GUILD_MEMBERS)
        .build()
    api.awaitReady()
    val role = api.guilds.first().getRoleById(args[1]) ?: throw Exception("Role not found")
    defineRole(role)
}
