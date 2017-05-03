package com.mohamadamin.cleantvmaze.network

import java.io.InputStream
import java.nio.charset.Charset

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/3/17.
 */
object ResourceReader {

    fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
        return this.bufferedReader(charset).use {
            it.readText()
        }
    }

    fun getJsonFromResource(resourceName: String, classLoader: ClassLoader) =
            classLoader.getResourceAsStream(resourceName).readTextAndClose()

}