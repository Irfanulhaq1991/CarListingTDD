package com.irfan.auto1.manufactureres.data.remote.datasource

import com.irfan.auto1.manufactureres.data.remote.model.ManufacturerDto
import com.irfan.auto1.manufactureres.data.remote.api.ManufacturersRemoteAPI
import com.irfan.auto1.manufactureres.data.remote.api.PagingManager
import org.json.JSONObject
import java.io.IOException

class ManufacturersRemoteDataDataSource(
    private val remoteAPiImp: ManufacturersRemoteAPI,
    private val pagingManager: PagingManager
) :
    IManufacturersRemoteDataSource {
    override suspend fun fetchManufacturers(): Result<List<ManufacturerDto>> {
        return try {
            val nextPage= pagingManager.page()
            val pageSize = pagingManager.pagSize
            val apiResponse = remoteAPiImp.getManufacturers(nextPage,pageSize)
            if (apiResponse.isSuccessful) {
                val manufacturersAsJSONObjectDeserialized = deserializeJsonBody(apiResponse.body()!!.string())
                pagingManager.setTotalPages(manufacturersAsJSONObjectDeserialized.first)
                Result.success(manufacturersAsJSONObjectDeserialized.second)
            } else
                Result.failure(Throwable(apiResponse.message()))

        } catch (e: IOException) {
            Result.failure(Throwable("No Internet"))

        } catch (e: Exception) {
            Result.failure(Throwable("Error Occurred"))
        }
    }

    private fun deserializeJsonBody(jsonString: String): Pair<Int, MutableList<ManufacturerDto>> {
        val jsonObject = JSONObject(jsonString)
        val pageCount = jsonObject.getInt("totalPageCount")
        val manufacturersJsonObj = jsonObject.getJSONObject("wkda")
        val manufacturerDTOs = mutableListOf<ManufacturerDto>()
        manufacturersJsonObj
            .keys()
            .forEach {
                manufacturerDTOs.add(ManufacturerDto(it, manufacturersJsonObj.getString(it)))
            }

        return Pair(pageCount,manufacturerDTOs)
    }


}
