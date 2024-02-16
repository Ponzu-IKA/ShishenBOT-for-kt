package ponzu_ika.shishenbot

import com.github.mnemotechnician.markov.MarkovChain
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File
private val markov = MarkovChain()
class MarkovUser{
    init {
        markov.train(File("g_data.txt").readText())
    }
}
fun userselcGen(randomNum:Int):String = runBlocking{
    var generated:String = markov.generate(limit = randomNum)
    println(randomNum)
    generated = generated
            .replace(japaneseRegex,"")
            .replace(discordMDRegex,"""\\""")
    delay(100)
    return@runBlocking generated
}