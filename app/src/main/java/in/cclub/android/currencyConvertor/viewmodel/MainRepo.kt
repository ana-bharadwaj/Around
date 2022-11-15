package `in`.cclub.android.currencyConvertor.viewmodel

import `in`.cclub.android.currencyConvertor.helper.Resource
import `in`.cclub.android.currencyConvertor.model.ApiResponse
import `in`.cclub.android.currencyConvertor.network.ApiDataSource
import `in`.cclub.android.currencyConvertor.network.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepo  @Inject constructor(private val apiDataSource: ApiDataSource) : BaseDataSource() {

    //Using coroutines flow to get the response from
    suspend fun getConvertedData(access_key: String, from: String, to: String, amount: Double): Flow<Resource<ApiResponse>> {
        return flow {
            emit(safeApiCall { apiDataSource.getConvertedRate(access_key, from, to, amount) })
        }.flowOn(Dispatchers.IO)
    }


}