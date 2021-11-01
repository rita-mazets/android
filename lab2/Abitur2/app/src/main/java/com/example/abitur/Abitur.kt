package com.example.abitur
import org.json.*
import java.io.Serializable

class Abitur(var surname : String, var name : String,
           var middlename : String, var marks : List<Int>):Serializable{



    constructor(jsonObject: JSONObject) :
            this(jsonObject.getString("su"), jsonObject.getString("na"),
                jsonObject.getString("mi"), getArrayFrom(jsonObject.getJSONArray("ma"))){ }

    companion object {
        fun getNullAbitur(): Abitur {
            return Abitur("", "", "", listOf())
        }


        private fun getArrayFrom(jsonArray: JSONArray): List<Int> {
            var result = mutableListOf<Int>()
            for (i in 0 until jsonArray.length()) {
                result.add(jsonArray.getInt(i))
            }
            return result
        }


        fun getAbiturs(str: String): List<Abitur> {
            var abiturs = mutableListOf<Abitur>()
            var json_arr = JSONArray(str)
            for (i in 0 until json_arr.length()) {
                abiturs.add(Abitur(json_arr.getJSONObject(i)))
            }

            return abiturs
        }

        fun getJSON(abiturs: List<Abitur>): String {
            val json_arr = JSONArray()
            for (i in abiturs) {
                var abitur = JSONObject()
                var j_marks = JSONArray()

                for (j in i.marks) {
                    j_marks.put(j)
                }

                abitur.put("su", i.surname).put("na", i.name)
                    .put("mi", i.middlename).put("ma", j_marks)
                json_arr.put(abitur)
            }

            return json_arr.toString()
        }
    }

    fun setup(abitur: Abitur){
        surname = abitur.surname;
        name= abitur.name;
        middlename= abitur.middlename;
        marks= abitur.marks;
    }
}