package ponzu_ika.shishenbot

import com.github.mnemotechnician.markov.MarkovChain
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import java.io.File
private val markov = MarkovChain()
class MarkovUser : ListenerAdapter() {
    init {
        markov.train(File("g_data.txt").readText())
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(event.name == "markovuserselected" ){
            val num = event.getOption("num",1, OptionMapping::getAsInt)
            event.deferReply().queue()
            event.hook.sendMessage(generate(num)).queue()
        }

    }
}
private fun generate(num:Int):String = runBlocking{
    var randomNum:Int
    var generated:String
    var send = ""
    for (i in 1..num) {
        randomNum =  (5..15).random()
        generated = markov.generate(limit = randomNum)
        println(randomNum)
        generated = generated.replace(Regex("""\s(?=[\p{InHiragana}\p{InKatakana}\p{InCJKUnifiedIdeographs}])|(?<=[\p{InHiragana}\p{InKatakana}\p{InCJKUnifiedIdeographs}])\s"""),"")
        send += "${i}: $generated\n"
        delay(100)
    }
    return@runBlocking send
}