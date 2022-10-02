package com.extraedge.mvvm.model.response
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "store_items")
data class CategoryEntity(
    // Primary key is a unique key
    // for different database.
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "category_filter_response") val categoryFilterResponse: List<CategoryFilterResponse>
):Serializable


data class CategoryFilterResponse(
    val category: String,
    val data: ArrayList<GetRocketResponse>
):Serializable


data class GetRocketResponse(
    internal val height: height? = null,
    internal val first_stage: firststage? = null,
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
):Serializable
data class height(
    var meters: Double? = null,
    var feet: Double? = null
):Serializable
data class firststage(
    val reusable: Boolean? = null,
    val engines: Int? = null,
    val fuel_amount_tons: Double? = null,
    val burn_time_sec: Double? = null,
    ):Serializable


class CategoryTypeConverter {

    @TypeConverter
    fun listToJson(value: List<CategoryFilterResponse>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<CategoryFilterResponse>::class.java).toList()
}
