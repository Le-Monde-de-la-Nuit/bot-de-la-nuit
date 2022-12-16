package commands

import api.roleOfMember
import api.welcomeChannel
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

val registerCommand = Commands.slash("register", "Enregistre un utilisateur")
    .addOption(OptionType.MENTIONABLE, "user", "L'utilisateur à enregistrer", true)
    .addOption(OptionType.INTEGER, "id", "L'identifiant de l'utilisateur", true)
    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_ROLES)
)

class RegisterEvent : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != registerCommand.name) {
            return
        }
        val user = event.getOption("user")!!
        val member: Member
        try {
            member = user.asMember!!
        } catch (e: Exception) {
            event.reply("L'utilisateur n'est pas un membre du serveur.").setEphemeral(true).queue()
            return
        }
        val guild = event.guild!!
        // update member
        guild.addRoleToMember(member, roleOfMember!!).queue()
        member.modifyNickname(event.getOption("id")!!.asLong.toString()).queue()
        event.reply("L'utilisateur ${member.user.asTag} a été enregistré").setEphemeral(true).queue()
        welcomeChannel!!.sendMessage("Bienvenue ${member.asMention} dans Le Monde de la Nuit !").queue()
    }
}