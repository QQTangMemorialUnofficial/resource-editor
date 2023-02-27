package com.geno1024.qqtmu.re.ds

import com.geno1024.qqtmu.re.util.Conversions.toInt32
import java.io.File
import java.io.FileInputStream

class IndexFile
{
    class IndexFileEntry
    {

    }

    lateinit var stream: FileInputStream

    var magic: Int = 0
    var entrySize: Int = 0
    var fileTableOffset: Int = 0
    var fileTableSize: Int = 0

    companion object
    {
        fun read(file: File): IndexFile
        {
            return IndexFile().apply {
                stream = file.inputStream()

                magic = stream.readNBytes(4).toInt32()
                entrySize = stream.readNBytes(4).toInt32()
                fileTableOffset = stream.readNBytes(4).toInt32().apply(::println)
                fileTableSize = stream.readNBytes(4).toInt32().apply(::println)
            }
        }
    }
}
