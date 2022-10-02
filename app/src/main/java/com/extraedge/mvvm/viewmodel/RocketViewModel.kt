package com.extraedge.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.extraedge.mvvm.model.APIError
import com.extraedge.mvvm.model.response.CategoryEntity
import com.extraedge.mvvm.model.response.GetRocketDetailsResponse
import com.extraedge.mvvm.model.response.GetRocketResponse
import com.extraedge.room.StoreDatabase
import com.extraedge.utils.Logger
import com.extraedge.webservice.DataWrapper
import com.extraedge.webservice.RetrofitFactory
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

class RocketViewModel (app: Application) : AndroidViewModel(app) {
    private var TAG = this::class.java.simpleName

    val service = RetrofitFactory.get()


    fun getCategoryData(): LiveData<DataWrapper<ArrayList<GetRocketResponse>>> {
        val mDataWrapperCategory = DataWrapper<ArrayList<GetRocketResponse>>()
        val mResponseData = MutableLiveData<DataWrapper<ArrayList<GetRocketResponse>>>()

        CoroutineScope(Dispatchers.IO).launch {
            val withCategoryResponse: Response<ArrayList<GetRocketResponse>>?
            try {
                withCategoryResponse = service.getRocket()

                withContext(Dispatchers.Main) {
                    try {
                        if (withCategoryResponse!!.isSuccessful && withCategoryResponse.code() == 200){
                            mDataWrapperCategory.data = withCategoryResponse.body()
                            mResponseData.value = mDataWrapperCategory
                        }else {
                            Logger.e(TAG, "Error :: " + withCategoryResponse.code())
                            mDataWrapperCategory.apiError =
                                APIError(withCategoryResponse.code(), withCategoryResponse.message())
                            mResponseData.value = mDataWrapperCategory
                        }
                    } catch (e: HttpException) {
                        Logger.e(TAG, "HttpException :: " + e.message)
                        mDataWrapperCategory.apiError =
                            APIError(100, withCategoryResponse.message())
                        mResponseData.value = mDataWrapperCategory
                    } catch (e: Throwable) {
                        Logger.e(TAG, "Throwable :: " + e.message)
                        mDataWrapperCategory.apiError =
                            APIError(100, withCategoryResponse.message())
                        mResponseData.value = mDataWrapperCategory
                    } catch (e: ConnectException) {
                        Logger.e(TAG, "ConnectException :: " + e.message)

                        mDataWrapperCategory.apiError =
                            APIError(100, withCategoryResponse.message())
                        mResponseData.value = mDataWrapperCategory
                    }
                }
            } catch (e: Throwable) {
                Logger.e(TAG, "Error! ${e.message}")
                mDataWrapperCategory.apiError = APIError(100, e.message!!)

                withContext(Dispatchers.Main) {
                    mResponseData.value = mDataWrapperCategory
                }
            }
        }

        return mResponseData
    }

    fun getRocketdetailsData(rocketid:String): LiveData<DataWrapper<GetRocketDetailsResponse>> {
        val mDataWrapperCategory = DataWrapper<GetRocketDetailsResponse>()
        val mResponseData = MutableLiveData<DataWrapper<GetRocketDetailsResponse>>()

        CoroutineScope(Dispatchers.IO).launch {
            val withCategoryResponse: Response<GetRocketDetailsResponse>?
            try {
                withCategoryResponse = service.getRocketdetails(rocketid)

                withContext(Dispatchers.Main) {
                    try {
                        if (withCategoryResponse!!.isSuccessful && withCategoryResponse.code() == 200){
                            mDataWrapperCategory.data = withCategoryResponse.body()
                            mResponseData.value = mDataWrapperCategory
                        }else {
                            Logger.e(TAG, "Error :: " + withCategoryResponse.code())
                            mDataWrapperCategory.apiError =
                                APIError(withCategoryResponse.code(), withCategoryResponse.message())
                            mResponseData.value = mDataWrapperCategory
                        }
                    } catch (e: HttpException) {
                        Logger.e(TAG, "HttpException :: " + e.message)
                        mDataWrapperCategory.apiError =
                            APIError(100, withCategoryResponse.message())
                        mResponseData.value = mDataWrapperCategory
                    } catch (e: Throwable) {
                        Logger.e(TAG, "Throwable :: " + e.message)
                        mDataWrapperCategory.apiError =
                            APIError(100, withCategoryResponse.message())
                        mResponseData.value = mDataWrapperCategory
                    } catch (e: ConnectException) {
                        Logger.e(TAG, "ConnectException :: " + e.message)

                        mDataWrapperCategory.apiError =
                            APIError(100, withCategoryResponse.message())
                        mResponseData.value = mDataWrapperCategory
                    }
                }
            } catch (e: Throwable) {
                Logger.e(TAG, "Error! ${e.message}")
                mDataWrapperCategory.apiError = APIError(100, e.message!!)

                withContext(Dispatchers.Main) {
                    mResponseData.value = mDataWrapperCategory
                }
            }
        }

        return mResponseData
    }

    fun insertCategoryInfo(entity: CategoryEntity){
        val userDao = StoreDatabase.getAppDatabase(getApplication())?.getStoreDao()
        userDao?.insert(entity)
    }


    //Here we initialized allStoreItems function with repository
    fun allStoreItems(): LiveData<List<CategoryEntity>>? {
        val userDao = StoreDatabase.getAppDatabase((getApplication()))?.getStoreDao()
        return userDao?.getAllStoreItems()
    }




}