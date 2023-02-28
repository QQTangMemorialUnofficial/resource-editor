package com.geno1024.qqtmu.re.ds

import com.geno1024.qqtmu.re.util.Conversions.readInt32
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

                magic = stream.readInt32()
                entrySize = stream.readInt32()
                fileTableOffset = stream.readInt32().apply(::println)
                fileTableSize = stream.readInt32().apply(::println)
            }
        }
    }
}
