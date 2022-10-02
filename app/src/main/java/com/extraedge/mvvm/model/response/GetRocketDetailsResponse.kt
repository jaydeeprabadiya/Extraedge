package com.extraedge.mvvm.model.response

import java.io.Serializable

data class GetRocketDetailsResponse(
    internal val height: heights? = null,
    internal val diameter: diameter? = null,
    internal val flickr_images: ArrayList<String>? = null,
    internal val id: String? = null,
    internal val name: String? = null,
    internal val type: String? = null,
    internal val active: Boolean? = null,
    internal val stages: Int? = null,
    internal val boosters: Int? = null,
    internal val cost_per_launch: Int? = null,
    internal val success_rate_pct: Int? = null,
    internal val first_flight: String? = null,
    internal val country: String? = null,
    internal val company: String? = null,
    internal val wikipedia: String? = null,
    internal val description: String? = null
): Serializable

data class heights(
    var meters: Double? = null,
    var feet: Double? = null
): Serializable

data class diameter(
    var meters: Double? = null,
    var feet: Double? = null
): Serializable