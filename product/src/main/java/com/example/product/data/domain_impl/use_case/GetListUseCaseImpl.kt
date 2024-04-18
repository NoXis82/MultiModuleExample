package com.example.product.data.domain_impl.use_case

import com.example.product.data.api.repository.ListDataSource
import com.example.product.data.domain_impl.mapper.mapToListData
import com.example.product.domain.models.ProductList
import com.example.product.domain.until.DataError
import com.example.product.domain.until.Result
import com.example.product.domain.use_case.GetListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetListUseCaseImpl @Inject constructor(
    private val dataSource: ListDataSource
) : GetListUseCase {

    override fun getProductList(): Flow<Result<ProductList, DataError.NetworkError>> =
        flow {
            try {
                val initialData = dataSource.getList().mapToListData()
                emit(Result.Success(initialData))
            } catch (e: HttpException) {
                when (e.code()) {
                    404 -> emit(Result.Error(DataError.NetworkError.NO_INTERNET))
                    else -> emit(Result.Error(DataError.NetworkError.SERVER_ERROR))
                }
            } catch (err: IOException) {
                emit(Result.Error(DataError.NetworkError.SERVER_ERROR))
            }
        }
}