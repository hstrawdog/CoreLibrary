package com.qq.readbook.net

import retrofit2.Converter
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.nio.charset.Charset
/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.net
 * @Date : 下午 1:18
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
 object JsoupConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type,
                                       annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *>? {
        return when (type) {
            Document::class.java -> JsoupConverter(retrofit.baseUrl().toString())
            else -> null
        }
    }

    private class JsoupConverter(val baseUri: String) : Converter<ResponseBody, Document?> {

        override fun convert(value: ResponseBody): Document? {
            val charset = value.contentType()?.charset() ?: Charset.forName("UTF-8")
            val parser = when (value.contentType().toString()) {
                "application/xml", "text/xml" -> Parser.xmlParser()
                else -> Parser.htmlParser()
            }
            return Jsoup.parse(value.byteStream(), charset.name(), baseUri, parser)
        }



    }

}