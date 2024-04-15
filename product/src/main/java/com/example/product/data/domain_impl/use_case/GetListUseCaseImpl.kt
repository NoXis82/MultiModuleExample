package com.example.product.data.domain_impl.use_case

import com.example.product.data.api.repository.ListDataSource
import com.example.product.data.domain_impl.mapper.mapToListData
import com.example.product.domain.models.ProductList
import com.example.product.domain.use_case.GetListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListUseCaseImpl @Inject constructor(
    private val dataSource: ListDataSource
) : GetListUseCase {

    override fun getProductList(): Flow<ProductList> =
        flow {
            try {
                val initialData = dataSource.getList().mapToListData()
                emit(initialData)
            } catch (e: Exception) {
                emit(ProductList(null, null, null))
            }
        }
}