package commands

import api.roleOfMember
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

val registerCommand = Commands.slash("register", "Enregistre un utilisateur")
    .addOption(OptionType.USER, "user", "L'utilisateur à enregistrer", true)
    .addOption(OptionType.INTEGER, "id", "L'identifiant de l'utilisateur", true)
    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_ROLES)
)

class RegisterEvent : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != registerCommand.name) {
            return
        }
        val user = event.getOption("user")!!.asUser
        val guild = event.guild!!
        val member = guild.getMember(user)!!
        // update member
        guild.addRoleToMember(member, roleOfMember!!).queue()
        member.modifyNickname(event.getOption("id")!!.toString()).queue()
        event.reply("L'utilisateur ${user.asTag} a été enregistré").setEphemeral(true).queue()
    }
}