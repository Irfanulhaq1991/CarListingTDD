package com.irfan.auto1

import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ManufacturersRemoteService(private val remoteAPiImp: ManufacturersRemoteAPI) :
    IManufacturersRemoteService<JSONObject> {
    override suspend fun fetchManufacturers(): Result<JSONObject> {
        return try {
            val apiResponse = remoteAPiImp.getManufacturers()
            if (apiResponse.isSuccessful) {
                val manufacturersAsJSONObject = processResponseBody(apiResponse.body()!!.string())
                Result.success(manufacturersAsJSONObject)
            }else
                Result.failure(Throwable(apiResponse.message()))

        } catch (e: IOException) {
            Result.failure(Throwable("No Internet"))

        } catch (e:Exception){
            Result.failure(Throwable("Error Occurred"))
        }
    }

    private fun processResponseBody(jsonString: String): JSONObject {
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getJSONObject("wkda")

    }

}
