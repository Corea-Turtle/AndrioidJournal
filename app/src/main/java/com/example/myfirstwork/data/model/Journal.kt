package com.example.myfirstwork.data.model

import org.json.JSONArray
import org.json.JSONObject

data class Journal(
    val content: String,
    val date: String
) {
    companion object{

        fun encode(journalList: List<Journal>) : String {
            return JSONArray().apply {
                journalList.forEach {
                    put(
                        JSONObject().apply{
                            put("date", it.date)
                            put("content", it.content)
                        }
                    )
                }
            }.toString()
        }

        fun decode(json: String) : List<Journal> {
            return JSONArray(json).let { array ->
                List(array.length()) { index ->
                    val obj = array.getJSONObject(index)
                    return@List Journal(
                        date = obj.getString("date"),
                        content = obj.getString("content"))

                }
            }
        }
    }
}

//String -> List<Journal> = decode
//List<Journal> -> String = encode