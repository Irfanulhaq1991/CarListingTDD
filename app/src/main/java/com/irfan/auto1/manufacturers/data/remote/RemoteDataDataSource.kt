package com.irfan.auto1.manufacturers.data.remote

import com.irfan.auto1.common.CarInfo
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class ManufacturersRemoteDataSource(
    private val remoteAPiImp: ManufacturersRemoteAPI,
    private val pagingManager: PagingManager
) : RemoteDataSource<ManufacturerDto>() {

    override suspend fun onFetching(carInfo: CarInfo?): Response<ResponseBody> {
        val nextPage = pagingManager.nextPage()
        val pageSize = pagingManager.pagSize
        return remoteAPiImp.getManufacturers(nextPage, pageSize)
    }

    override fun getDto(key: String, value: String): ManufacturerDto {
        return ManufacturerDto(key,value)
    }

    override fun deserializeJson(jsonString: String): List<ManufacturerDto> {
        val jsonObject = JSONObject(jsonString)
        val pageCount = jsonObject.getInt("totalPageCount")
        pagingManager.setTotalPages(pageCount)
        return super.deserializeJson(jsonString)
    }

}