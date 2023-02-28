package com.geno1024.qqtmu.re.util

import java.io.InputStream

object Conversions
{
    fun InputStream.readInt32(): Int = read() + (read() shl 8) + (read() shl 16) + (read() shl 24)

    fun InputStream.readInt16(): Int = read() + (read() shl 8)

    fun InputStream.readFloat32(): Float = Float.fromBits(readInt32())
}
