package com.irfan.auto1.year.data.remote

import com.irfan.auto1.year.domain.model.CarInfo
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class CarYearsRemoteDataSource(private val remoteApi: CarYearsRemoteApi) :
    ICarYearsRemoteDataSource {
    override suspend fun fetchCarYears(carInfo: CarInfo): Result<List<CarYearDto>> {
        return try {
            val response = remoteApi.fetchCarYears(carInfo.manufacturer.id,carInfo.model.id)
            if (response.isSuccessful) {
                val modelDtoDTOs = deserializeJsonBody(response.body()!!.string())
                if (modelDtoDTOs.isEmpty())
                    Result.failure(Throwable("No Car Year Found"))
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

    private fun deserializeJsonBody(jsonString: String): List<CarYearDto> {
        val jsonObject = JSONObject(jsonString)
        val manufacturersJsonObj = jsonObject.getJSONObject("wkda")
        val carYearDtos = mutableListOf<CarYearDto>()
        manufacturersJsonObj
            .keys()
            .forEach {
                carYearDtos.add(CarYearDto(it, manufacturersJsonObj.getString(it)))
            }

        return carYearDtos
    }
}
