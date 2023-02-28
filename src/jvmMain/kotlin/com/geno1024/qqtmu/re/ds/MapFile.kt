package com.geno1024.qqtmu.re.ds

import com.geno1024.qqtmu.re.util.Conversions.readFloat32
import com.geno1024.qqtmu.re.util.Conversions.readInt16
import com.geno1024.qqtmu.re.util.Conversions.readInt32
import java.io.File
import java.io.FileInputStream

class MapFile
{
    lateinit var stream: FileInputStream

    var version: Int = 0
    lateinit var mode: Mode
    var maxPlayer: Int = 0
    var width: Int = 15
    var height: Int = 13

    lateinit var layerHard: MutableList<MutableList<Int>>
    lateinit var layerBlock: MutableList<MutableList<Int>>
    lateinit var layerGround: MutableList<MutableList<Int>>

    lateinit var dropItems: MutableList<DropItem>
    
    lateinit var itemPos: MutableList<Point>
    lateinit var recoverPos1: MutableList<Point>
    lateinit var recoverPos2: MutableList<Point>
    lateinit var specialPos: MutableList<Point>

    companion object
    {
        fun read(file: File): MapFile
        {
            return MapFile().apply {
                stream = file.inputStream()

                version = stream.readInt32()
                mode = with(stream.readInt32()) {
                    Mode.values().first { it.id == this }
                }
                maxPlayer = stream.readInt32()
                if (version == 4)
                {
                    width = stream.readInt32()
                    height = stream.readInt32()
                }
                layerHard = MutableList(height) { MutableList(width) { 0 } }
                layerBlock = MutableList(height) { MutableList(width) { 0 } }
                layerGround = MutableList(height) { MutableList(width) { 0 } }
                (0 until height).map { y ->
                    (0 until width).map { x ->
                        layerHard[y][x] = stream.readInt32()
                    }
                }
                (0 until height).map { y ->
                    (0 until width).map { x ->
                        layerBlock[y][x] = stream.readInt32()
                    }
                }
                (0 until height).map { y ->
                    (0 until width).map { x ->
                        layerGround[y][x] = stream.readInt32()
                    }
                }
                dropItems = MutableList(stream.readInt32()) {
                    DropItem(
                        stream.readInt32(),
                        stream.readInt32(),
                        stream.readInt32(),
                        stream.readFloat32()
                    )
                }
                itemPos = MutableList(stream.readInt32()) {
                    Point(stream.readInt16(), stream.readInt16())
                }
                recoverPos1 = MutableList(stream.readInt32()) {
                    Point(stream.readInt16(), stream.readInt16())
                }
                recoverPos2 = MutableList(stream.readInt32()) {
                    Point(stream.readInt16(), stream.readInt16())
                }
                specialPos = MutableList(stream.readInt32()) {
                    Point(stream.readInt16(), stream.readInt16())
                }
            }
        }
    }

    var sep = 4

    fun MutableList<Point>.toMat() = MutableList(height) { MutableList(width) { 0 } }.apply {
        this@toMat.forEach { (x, y) ->
            this[x][y] = 1
        }
    }

    override fun toString(): String = "Map(" +
            "version = $version, " +
            "mode = $mode, " +
            "maxPlayer = $maxPlayer,\n" +
            "layerHard = ${" ".repeat(width + sep - "layerHard = , ".length)}, layerBlock = ${" ".repeat(width + sep - "layerBlock = , ".length)}, layerGround =\n" +
            (0 until height).joinToString(separator = "\n") { lineNo ->
                layerHard[lineNo].joinToString(separator = "") { i -> if (i == 0) "." else "X" } + " ".repeat(sep) +
                        layerBlock[lineNo].joinToString(separator = "") { i -> if (i == 0) "." else "X" } + " ".repeat(sep) +
                        layerGround[lineNo].joinToString(separator = "") { i -> if (i == 0) "." else "X" }
            } + ",\n" +
            "dropItems = $dropItems,\n" +
            "itemPos = ${" ".repeat(width + sep - "itemPos = , ".length)}, recoverPos1 = ${" ".repeat(width + sep - "recoverPos1 = , ".length)}, recoverPos2 = ${" ".repeat(width + sep - "recoverPos2 = , ".length)}, specialPos =\n" +
            listOf(itemPos.toMat(), recoverPos1.toMat(), recoverPos2.toMat(), specialPos.toMat()).let { (itemMat, recoverMat1, recoverMat2, specialMat) ->
                (0 until height).joinToString(separator = "\n") { lineNo ->
                    itemMat[lineNo].joinToString(separator = "") { i -> if (i == 0) "." else "X" } + " ".repeat(sep) +
                            recoverMat1[lineNo].joinToString(separator = "") { i -> if (i == 0) "." else "X" } + " ".repeat(sep) +
                            recoverMat2[lineNo].joinToString(separator = "") { i -> if (i == 0) "." else "X" } + " ".repeat(sep) +
                            specialMat[lineNo].joinToString(separator = "") { i -> if (i == 0) "." else "X" }
                }
            } +
            ")"


    @Suppress("unused")
    enum class Mode(val id: Int)
    {
        Normal(1),
        Bomb(2),
        Bun(3),
        Match(4),
        Treasure(5),
        Sculpture(6),
        Machine(7),
        Box(8),
        Tutorial(9),
        Pve(12),
        Tank(13)
    }

    data class DropItem(
        var id: Int,
        var min: Int,
        var max: Int,
        var rate: Float
    )

    data class Point(
        var x: Int,
        var y: Int
    )
}
