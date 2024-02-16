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
    val GUILD_ID = "1136216422356373507"


    fun initial(token: String) {
        try {
            val jda = JDABuilder.createLight(token,
                GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(
                    MarkovNormal(),
                    MarkovUser()
                )
                .setActivity(Activity.playing("開発ing"))
                .addEventListeners(this)
                .build()

            jda.awaitReady()

            val guild = jda.getGuildById(GUILD_ID)!!
            val markovnormal =
                Commands.slash("markovnormal","ししぇんが言ってそうで言ってないことを言わせます")
                    .addOptions( OptionData(OptionType.INTEGER,"num","1~10まで言わせる回数を選べる無くても動く")
                        .setRequiredRange(1,10))


            val markovuserselected =
                Commands.slash("markovuserselected","みんなが選んだししぇん語録の中から言わせます")
                    .addOptions(OptionData(OptionType.INTEGER,"num","1~10まで言わせる回数を選べる無くても動く")
                        .setRequiredRange(1,10))
            guild.updateCommands().addCommands(
                markovnormal,
                markovuserselected).queue()
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