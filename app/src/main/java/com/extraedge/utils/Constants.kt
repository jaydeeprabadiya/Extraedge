package com.extraedge.utils

import android.app.Activity
import android.os.Environment
import com.extraedge.R

interface Constants {

    /*Base URL*/
    companion object {
        val BASE_URL = "https://api.spacexdata.com/v4/"

    }

    interface ApiHeaders {
        companion object {
            val API_TYPE_JSON = "application/json"
            val API_TYPE_TEXT = "text/plain"
            val AUTHORIZATION = "authorization"
            val AUTH_TOKEN = "Auth-Token"
        }
    }


    /*
   * API method
   * */
    interface ApiMethods {
        companion object {

            const val GET_ROCKET = "rockets"
            const val GET_ROCKET_DETAILS = "rockets"


        }
    }

    interface BUNDLE_KEY {
        companion object {
            const val ROKET_ID = "ROKET_ID"

        }

    }


}