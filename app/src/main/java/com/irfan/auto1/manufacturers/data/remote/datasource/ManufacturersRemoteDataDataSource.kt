package com.irfan.auto1.manufacturers.data.remote.datasource

import com.irfan.auto1.manufacturers.data.remote.model.ManufacturerDto
import com.irfan.auto1.manufacturers.data.remote.api.ManufacturersRemoteAPI
import com.irfan.auto1.manufacturers.data.remote.api.PagingManager
import org.json.JSONObject
import java.io.IOException
import java.lang.IllegalStateException

class ManufacturersRemoteDataDataSource(
    private val remoteAPiImp: ManufacturersRemoteAPI,
    private val pagingManager: PagingManager
) :
    IManufacturersRemoteDataSource {
    override suspend fun fetchManufacturers(): Result<List<ManufacturerDto>> {
        return try {
            val nextPage= pagingManager.nextPage()
            val pageSize = pagingManager.pagSize
            val apiResponse = remoteAPiImp.getManufacturers(nextPage,pageSize)
            if (apiResponse.isSuccessful) {
                val manufacturersAsJSONObjectDeserialized = deserializeJsonBody(apiResponse.body()!!.string())
                pagingManager.setTotalPages(manufacturersAsJSONObjectDeserialized.first)
                pagingManager.updateNextPage()
                Result.success(manufacturersAsJSONObjectDeserialized.second)
            } else
                Result.failure(Throwable(apiResponse.message()))

        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure(Throwable("Please check your internet connection"))

        } catch (e:IllegalStateException){
            e.printStackTrace()
            Result.failure(Throwable("No more manufacturers"))
        }catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Throwable("Request for more manufacturers is failed" ))
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
