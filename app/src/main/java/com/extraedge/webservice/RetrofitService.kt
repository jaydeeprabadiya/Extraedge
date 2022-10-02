package com.extraedge.webservice

import com.extraedge.mvvm.model.response.GetRocketDetailsResponse
import com.extraedge.mvvm.model.response.GetRocketResponse
import com.extraedge.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @GET(Constants.ApiMethods.GET_ROCKET)
    suspend fun getRocket(): Response<ArrayList<GetRocketResponse>>

    @GET(Constants.ApiMethods.GET_ROCKET_DETAILS + "/{rocketid}")
    suspend fun getRocketdetails(
        @Path("rocketid") phone: String?
    ): Response<GetRocketDetailsResponse>


}