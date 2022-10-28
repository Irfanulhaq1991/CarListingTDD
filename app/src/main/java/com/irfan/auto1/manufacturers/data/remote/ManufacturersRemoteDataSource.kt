package com.irfan.auto1.manufacturers.data.remote

import com.irfan.auto1.common.CarInfo
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class ManufacturersRemoteDataSource(
    private val remoteAPiImp: ManufacturersRemoteAPI,
    private val pagingManager: PagingManager
) : RemoteDataSource<CarManufacturerDto>() {

    override suspend fun onFetching(carInfo: CarInfo?): Response<ResponseBody> {
        val nextPage = pagingManager.nextPage()
        val pageSize = pagingManager.pagSize
        return remoteAPiImp.getManufacturers(nextPage, pageSize)
    }

    override fun getDto(key: String, value: String): CarManufacturerDto {
        return CarManufacturerDto(key,value)
    }

    override fun deserializeJson(jsonString: String): List<CarManufacturerDto> {
        val jsonObject = JSONObject(jsonString)
        val pageCount = jsonObject.getInt("totalPageCount")
        pagingManager.setTotalPages(pageCount)
        pagingManager.updateNextPage()
        return super.deserializeJson(jsonString)
    }

}
