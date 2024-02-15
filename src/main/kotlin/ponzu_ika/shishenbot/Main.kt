package ponzu_ika.shishenbot

import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File
import javax.security.auth.login.LoginException

class Main : ListenerAdapter() {
    val GUILD_ID = "987312320554348574"


    fun initial(token: String) {
        try {
            val jda = JDABuilder.createLight(token,
                GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(MarkovChain())
                .setActivity(Activity.playing("開発ing"))
                .addEventListeners(this)
                .build()

            jda.awaitReady()

            val guild = jda.getGuildById(GUILD_ID)!!
            val testcommand = Commands.slash("genshishen","ししぇんが言ってそうで言ってないことを言わせます")

            guild.updateCommands()
                .addCommands(testcommand)
                .queue()
        }catch (e: LoginException){
            e.printStackTrace()
        }

    }

    override fun onReady(event: ReadyEvent) {
        println("起動し又")
    }

}

fun main() = runBlocking {
    Main().initial(File("token").readText())
}