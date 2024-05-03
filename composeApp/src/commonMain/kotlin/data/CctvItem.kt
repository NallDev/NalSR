package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CctvItem(

    @SerialName("cctv_name")
    val cctvName: String? = null,

    @SerialName("lng")
    val lng: String? = null,

    @SerialName("stream_cctv")
    val streamCctv: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("lat")
    val lat: String? = null
)