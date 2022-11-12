package event

import api.Member
import api.getMember
import api.getRole
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MemberEvent : ListenerAdapter() {

    @Override
    fun messageSent(event: MessageReceivedEvent) {
        val member = event.member ?: return
        if (member.user.isBot || member.roles.contains(getRole())) return
        val code = member.nickname

        val membreDeLaNuit = getMember(member)

        if (code == null) {
            badNickname(membreDeLaNuit, event.channel)
            return
        }
        val numeric = try {
            code.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
        if (!numeric) {
            badNickname(membreDeLaNuit, event.channel)
            return
        }
    }

    private fun badNickname(membre: Member, messageChannel: MessageChannel) {
        membre.addBadNickname()
        if (membre.getBadNickname() >= 3) {
            val member = membre.member
            member.user.openPrivateChannel().queue { channel ->
                channel.sendMessage("Vous avez été kick du serveur car vous n'aviez pas mis votre identifiant" +
                        " comme pseudonyme après 3 avertissements.").queue()
            }
            member.kick("Mauvais pseudonyme").queue()
            membre.resetBadNickname()
            messageChannel.sendMessage("Le membre ${member.asMention} a été kick car il n'avait pas mis son identifiant" +
                    " comme pseudonyme après 3 avertissements.").queue()
            return
        }
        messageChannel.sendMessage("${membre.member.asMention}, vous devez mettre votre identifiant comme pseudonyme.").queue()
    }
}