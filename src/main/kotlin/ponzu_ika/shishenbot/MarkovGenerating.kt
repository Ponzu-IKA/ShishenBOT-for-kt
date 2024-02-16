package ponzu_ika.shishenbot

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionMapping

val japaneseRegex = Regex("""\s(?=[\p{InHiragana}\p{InKatakana}\p{InCJKUnifiedIdeographs}])|(?<=[\p{InHiragana}\p{InKatakana}\p{InCJKUnifiedIdeographs}])\s""")
val discordMDRegex = Regex("""(?=[#+\-*_~|`>])""")

class MarkovGenerating : ListenerAdapter(){
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name == "markov") {
            val num = event.getOption("max", 1, OptionMapping::getAsInt)
            val limit = event.getOption("limit", 7, OptionMapping::getAsInt)
            val lower_limit = event.getOption("lower_limit", 5, OptionMapping::getAsInt)
            val mode = event.getOption("mode","normal",OptionMapping::getAsString)
            println(mode)
            event.deferReply().queue()
            event.hook.sendMessage(generate(num,lower_limit..limit,mode)).queue()
        }

    }
    fun generate(num:Int,limitRange: IntRange,mode:String):String{
        var randomNum:Int
        var send = ""
        for (i in 1..num) {
            randomNum =  (limitRange).random()
            if(mode == "normal")
                send += "${i}: ${normalGen(randomNum)}\n"
            if(mode=="userselc")
                send += "${i}: ${userselcGen(randomNum)}\n"
        }
        return send
    }
}