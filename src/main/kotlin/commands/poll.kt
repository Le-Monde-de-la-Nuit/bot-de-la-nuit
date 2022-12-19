package commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData

val pollCommand = Commands.slash("poll", "Crée un sondage")
    .addOptions(
        OptionData(OptionType.STRING, "type", "Type du sondage", true)
            .addChoice("Oui/Non", "simple")
            .addChoice("Choix multiples", "multiple"),
        OptionData(OptionType.STRING, "question", "Question du sondage", true),
        OptionData(OptionType.STRING, "reponses", "Réponses du sondage si type multiple", false)
    )
    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE)
)

private val choices = listOf(
    "simple", "multiple"
)

class PollEvent : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != pollCommand.name) {
            return
        }
        val type = event.getOption("type")!!.asString
        if (!choices.contains(type)) {
            event.reply("Le type de sondage n'est pas valide.").setEphemeral(true).queue()
            return
        }
        when (type) {
            "simple" -> simple(event)
            "multiple" -> multiple(event)
        }
    }

    private fun simple(event: SlashCommandInteractionEvent) {
        val question = event.getOption("question")!!.asString
        val eb = EmbedBuilder()
            .setTitle(question)
            .setDescription("Répondez avec les réactions ci-dessous.")
        event.replyEmbeds(eb.build()).setEphemeral(false).queue()
        event.hook.retrieveOriginal().queue {
            it!!.addReaction(Emoji.fromFormatted("✅")).queue()
            it.addReaction(Emoji.fromFormatted("❌")).queue()
        }
    }
    private fun multiple(event: SlashCommandInteractionEvent) {
        // example string
        // :one: Un;:two: Deux;:three: Trois
        val question = event.getOption("question")!!.asString
        var responses = ""
        try {
            responses = event.getOption("reponses")!!.asString
        } catch (e: Exception) {
            event.reply("Vous devez spécifier les réponses du sondage.").setEphemeral(true).queue()
            return
        }
        var responseList: List<String>
        try {
            responseList = parseResponses(responses)
        } catch (e: Exception) {
            event.reply("Les réponses du sondage ne sont pas valides.\n" +
                    "Elles doivent être de forme \":emoji: Titre\"").setEphemeral(true).queue()
            return
        }
        var desc = "Répondez avec les réactions ci-dessous.\n"
        responseList.forEach { response ->
            val resp = response.split(";")
            desc += "> ${resp[0]} ${resp[1]}\n"
        }
        val eb = EmbedBuilder()
            .setTitle(question)
            .setDescription(desc)
        event.replyEmbeds(eb.build()).queue()
        event.hook.retrieveOriginal().queue {
            responseList.forEach { response ->
                val resp = response.split(";")
                it!!.addReaction(Emoji.fromFormatted(resp[0])).queue()
            }
        }
    }
    private fun parseResponses(responses: String): List<String> {
        val list = ArrayList<String>()
        val split = responses.split(";")
        for (s in split) {
            val split2 = s.split(" ", limit = 2)
            if (split2.size != 2) {
                throw IllegalArgumentException("Le format des réponses n'est pas valide.")
            }
            list.add("${split2[0]};${split2[1]}")
        }
        return list
    }
}
