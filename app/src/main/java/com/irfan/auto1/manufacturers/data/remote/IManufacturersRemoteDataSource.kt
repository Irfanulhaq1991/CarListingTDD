package com.irfan.auto1.manufacturers.data.remote

import com.irfan.auto1.common.CarInfo
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.lang.IllegalStateException


abstract class RemoteDataSource<out T> {

    abstract suspend fun onFetching(carInfo: CarInfo?): Response<ResponseBody>
    abstract fun getDto(key: String, value: String): T


    suspend fun doFetching(carInfo: CarInfo? = null): Result<List<T>> {
        return try {
            val apiResponse = onFetching(carInfo)
            if (apiResponse.isSuccessful) {
                val result =
                    deserializeJson(apiResponse.body()!!.string())
                if (result.isEmpty()) {
                    Result.failure(Throwable("No record found"))
                } else
                    Result.success(result)
            } else
                Result.failure(Throwable(apiResponse.message()))

        } catch (e: IOException) {
            Result.failure(Throwable("Please check your internet connection"))

        } catch (e: IllegalStateException) {
            Result.failure(Throwable("No more record"))
        } catch (e: JSONException) {
            Result.failure(Throwable("Request is failed"))
        }
    }


    protected open fun deserializeJson(jsonString: String): List<T> {
        val jsonObject = JSONObject(jsonString)
        val manufacturersJsonObj = jsonObject.getJSONObject("wkda")
        val manufacturerDTOs = mutableListOf<T>()
        manufacturersJsonObj
            .keys()
            .forEach {
                manufacturerDTOs.add(getDto(it, manufacturersJsonObj.getString(it)))
            }

        return manufacturerDTOs
    }

}