package ponzu_ika.shishenbot

import com.github.mnemotechnician.markov.MarkovChain
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
class MarkovChain : ListenerAdapter() {
    val markov = MarkovChain()
    var wakachied = mutableListOf("")
    init{
        @Throws(IOException::class)
        fun resourcesAcsess(filename:String) = this.javaClass
            .classLoader
            .getResourceAsStream(filename)
            ?.bufferedReader()
            ?.use {it.readText()}
        val shishendata = resourcesAcsess("shishenSaied/data.txt")

        // println(shishendata)
        require(shishendata != null) { "ｶﾞｯ" }
        shishendata.split("\n").forEach { data: String ->
            val dataWakachi = wakachi().wakachi(data)
            wakachied += dataWakachi
        }
        wakachied.removeAt(0)
    }
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(event.name == "genshishen") {
            event.reply(generate()).queue()
        }
    }
    fun generate():String {
        markov.train(wakachied)
        var generated = markov.generate()
        println("before: $generated")
        generated = generated.replace(Regex("""\s(?=[\p{InHiragana}\p{InKatakana}\p{InCJKUnifiedIdeographs}])"""),"")
            .replace(Regex("""(?<=[\p{InHiragana}\p{InKatakana}\p{InCJKUnifiedIdeographs}])\s"""),"")
        println("after: $generated")
        return generated
    }
}
