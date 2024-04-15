package com.example.product.data.domain_impl.di

import com.example.product.data.api.repository.ListDataSource
import com.example.product.data.domain_impl.use_case.GetListUseCaseImpl
import com.example.product.domain.use_case.GetListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetInitialHomeUseCase(
        dataSource: ListDataSource,
//        @IODispatcher dispatcher: CoroutineContext
    ): GetListUseCase {
        return GetListUseCaseImpl(dataSource)//, dispatcher)
    }

}