package com.irfan.auto1.model.data.remote

import com.irfan.auto1.common.CarInfo
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ModelsRemoteDataSource(private val remoteApi: ModelRemoteApi) : IModelsRemoteDataSource {
    override suspend fun fetchModels(carInfo: CarInfo): Result<List<ModelDto>> {
        return try {
            val response = remoteApi.fetchModels(carInfo.manufacturer.id)
            if (response.isSuccessful) {
                val modelDtoDTOs = deserializeJsonBody(response.body()!!.string())
                if (modelDtoDTOs.isEmpty())
                    Result.failure(Throwable("No Model Found"))
                else
                    Result.success(modelDtoDTOs)
            } else
                Result.failure(Throwable(response.message()))
        } catch (e: IOException) {
            Result.failure(Throwable("Please Check your Internet Connection"))
        } catch (e: JSONException) {
            Result.failure(Throwable("Request for model is failed" ))
        }

    }

    private fun deserializeJsonBody(jsonString: String): List<ModelDto> {
        val jsonObject = JSONObject(jsonString)
        val pageCount = jsonObject.getInt("totalPageCount")
        val manufacturersJsonObj = jsonObject.getJSONObject("wkda")
        val modelDtoDTOs = mutableListOf<ModelDto>()
        manufacturersJsonObj
            .keys()
            .forEach {
                modelDtoDTOs.add(ModelDto(it, manufacturersJsonObj.getString(it)))
            }

        return modelDtoDTOs
    }
}
