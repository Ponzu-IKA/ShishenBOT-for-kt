package ponzu_ika.shishenbot

import net.moraleboost.mecab.Lattice
import net.moraleboost.mecab.Node
import net.moraleboost.mecab.impl.StandardTagger


fun wakachi(text: String): String {
    val tagger = StandardTagger("-Owakati")

    val lattice: Lattice = tagger.createLattice()
    lattice.setSentence(text)
    tagger.parse(lattice)
    var node: Node? = lattice.bosNode()
    var str = ""
    while (node != null) {
        val surface = node.surface()
        node = node.next()
        str += "$surface "
    }
    lattice.destroy()
    tagger.destroy()
    return str
}