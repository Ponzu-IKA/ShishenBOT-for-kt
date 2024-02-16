package ponzu_ika.shishenbot

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File
import javax.security.auth.login.LoginException

class Main : ListenerAdapter() {
    val GUILD_ID = "987312320554348574"
    init {
        MarkovUser()
        MarkovNormal()
    }

    fun initial(token: String) {
        try {
            val jda = JDABuilder.createLight(token,
                GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(
                    MarkovGenerating()
                )
                .setActivity(Activity.playing("開発ing"))
                .addEventListeners(this)
                .build()

            jda.awaitReady()

            val guild = jda.getGuildById(GUILD_ID)!!
            val markovnormal =
                Commands.slash("markov","ししぇんが言ってそうで言ってないことを言わせます")
                    .addOptions( OptionData(OptionType.INTEGER,"max","1～10まで言わせる回数を選べる無くても動く")
                        .setRequiredRange(1,10))
                    .addOptions(OptionData(OptionType.INTEGER,"limit","単語数の上限。5～50単語")
                        .setRequiredRange(5,50))
                    .addOptions(OptionData(OptionType.INTEGER,"lower_limit","単語数の下限。1～20単語")
                        .setRequiredRange(1,20))
                    .addOptions(OptionData(OptionType.STRING,"mode","モードチェンジ:「通常」はししぇんが言った全てから 「選りすぐり」は、shishen語保管庫から")
                        .addChoice("1.通常","normal").addChoice("2.選りすぐり","userselc"))
            guild.updateCommands().addCommands(
                markovnormal).queue()
        }catch (e: LoginException){
            e.printStackTrace()
        }

    }

    override fun onReady(event: ReadyEvent) {
        println("起動し又")
    }

}

fun main()  {
    Main().initial(File("token").readText())
}