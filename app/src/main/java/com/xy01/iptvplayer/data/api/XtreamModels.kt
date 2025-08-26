package com.xy01.iptvplayer.data.api

data class XtreamResponse<T>(
    val user_info: UserInfo?,
    val server_info: ServerInfo?,
    val data: T?
)

data class UserInfo(
    val username: String,
    val password: String,
    val message: String,
    val auth: Int,
    val status: String,
    val exp_date: String,
    val is_trial: String,
    val active_cons: String,
    val created_at: String,
    val max_connections: String,
    val allowed_output_formats: List<String>
)

data class ServerInfo(
    val url: String,
    val port: String,
    val https_port: String,
    val server_protocol: String,
    val rtmp_port: String,
    val timezone: String,
    val timestamp_now: Long,
    val time_now: String
)

data class CategoryResponse(
    val category_id: String,
    val category_name: String,
    val parent_id: String?
)

data class ChannelResponse(
    val num: String,
    val name: String,
    val stream_type: String,
    val stream_id: String,
    val stream_icon: String?,
    val epg_channel_id: String?,
    val added: String?,
    val category_id: String?,
    val custom_sid: String?,
    val tv_archive: Int?,
    val direct_source: String?,
    val tv_archive_duration: String?
)

data class MovieResponse(
    val num: String,
    val name: String,
    val stream_type: String,
    val stream_id: String,
    val stream_icon: String?,
    val rating: String?,
    val rating_5based: Double?,
    val added: String?,
    val category_id: String?,
    val container_extension: String?,
    val custom_sid: String?,
    val direct_source: String?
)

data class SeriesResponse(
    val num: String,
    val name: String,
    val series_id: String,
    val cover: String?,
    val plot: String?,
    val cast: String?,
    val director: String?,
    val genre: String?,
    val releaseDate: String?,
    val rating: String?,
    val rating_5based: Double?,
    val category_id: String?
)