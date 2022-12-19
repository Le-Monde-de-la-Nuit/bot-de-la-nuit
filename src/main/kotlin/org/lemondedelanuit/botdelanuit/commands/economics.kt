package org.lemondedelanuit.botdelanuit.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.yaml.snakeyaml.Yaml

val economicsCommand = Commands.slash("economics", "Commandes d'économie")
    .addOptions(
        OptionData(OptionType.STRING, "type", "Type d'objet", true)
            .addChoice("Distributeur", "distributeur")
    )

private val choices = listOf(
    "distributeur"
)

class EconomicsEvent : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != economicsCommand.name) {
            return
        }
        val type = event.getOption("type")!!.asString
        if (!choices.contains(type)) {
            event.reply("Le type d'objet n'est pas valide.").setEphemeral(true).queue()
            return
        }
        when (type) {
            "distributeur" -> distributeur(event)
        }
    }

    private fun distributeur(event: SlashCommandInteractionEvent) {
        val yaml = Yaml()
        try {
            val input = javaClass.classLoader.getResourceAsStream("data/economics.yml") ?: error("Impossible de trouver le fichier")
            val data: Map<String, List<String>> = yaml.load(input)
            if (!data.contains("distributeur")) {
                error("Le fichier YAML n'est pas valide : aucune clef 'distributeur' trouvée.")
            }
            val lists = data["distributeur"]!!
            event.reply(lists.random()).setEphemeral(false).queue()
        } catch (e: Exception) {
            event.reply("Une erreur est survenue.").setEphemeral(true).queue()
            org.lemondedelanuit.botdelanuit.utils.error(e.message ?: "Une erreur inconnue est survenue.")
        }
    }
}
