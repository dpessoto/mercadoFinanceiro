package pessoto.android.mercadofinanceiro.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class HubResponse(response: JsonObject) {
    private var json: JSONObject = JSONObject(Gson().toJson(response))
    private var result: JSONArray = json.getJSONObject("message").getJSONArray("result")
    private val length by lazy { result.length() - 1 }

    fun getResult(): Pair<JSONArray, IntRange>? {
        return if (json.optBoolean("status") && length > 0) {
            Pair(result, 0..length)
        } else {
            return null
        }
    }
}