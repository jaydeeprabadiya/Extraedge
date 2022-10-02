package com.extraedge.webservice

import com.extraedge.mvvm.model.APIError


class DataWrapper<T> {

    var apiError: APIError? = null
    var data: T? = null

}