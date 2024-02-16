package ponzu_ika.shishenbot

import kotlinx.coroutines.runBlocking

class RawDataProcess {
    fun init() {
        val file = "shishen_messages.txt"
        val raw = fileInputStream(file)
        val writer = fileOutputStream(file)
        val regexHttps = Regex("""((https?:www\.)|(https?://)|(www\.))[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9]{1,6}(/[-a-zA-Z0-9()@:%_+.~#?&/=]*)?""")
        val regexMentions = Regex("""<@[0-9]+>""")

        var line:String?
        var regexedtext:String?
        while(raw.readLine().also { line = it } != null) {
            regexedtext = line?.replace(regexHttps,"")?.replace(regexMentions,"")
                ?.replace(Regex("""(?<=\n)[\s　]|[\s　](?=\n)|```(\n|.){0,600}```"""),"")
            line?.let {writer.write("$regexedtext\n") }
        }
        writer.flush()
        writer.close()
    }
    fun meCabProcess(path: String) {
        //val file = resourcesAcsess(path)
        val file = fileInputStream(path)
        val writer = fileOutputStream(path)
        var line:String?
        while (file.readLine().also { line = it }!=null) {
            line?.let { writer.write("${wakachi(it).trim()}\n") }
        }
        writer.flush()
        writer.close()
    }
    fun wakachiWrite() {
        init()
        meCabProcess("data.txt")
        meCabProcess("g_shishen_messages.txt")
    }
}

fun main() = runBlocking {
    RawDataProcess().wakachiWrite()
}