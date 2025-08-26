package com.xy01.iptvplayer.utils

import com.xy01.iptvplayer.data.model.Profile
import org.junit.Assert.assertEquals
import org.junit.Test

class StreamUrlBuilderTest {

    private val testProfile = Profile(
        id = "test-profile-id",
        name = "Test Profile",
        serverUrl = "http://test.server.com:8080",
        username = "testuser",
        password = "testpass"
    )

    @Test
    fun testBuildLiveStreamUrl() {
        val streamId = "profile123_456"
        val expectedUrl = "http://test.server.com:8080/live/testuser/testpass/456.ts"
        val actualUrl = StreamUrlBuilder.buildLiveStreamUrl(testProfile, streamId)
        assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun testBuildMovieStreamUrl() {
        val streamId = "profile123_789"
        val expectedUrl = "http://test.server.com:8080/movie/testuser/testpass/789.mp4"
        val actualUrl = StreamUrlBuilder.buildMovieStreamUrl(testProfile, streamId)
        assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun testBuildSeriesStreamUrl() {
        val seriesId = "profile123_321"
        val expectedUrl = "http://test.server.com:8080/series/testuser/testpass/321.mp4"
        val actualUrl = StreamUrlBuilder.buildSeriesStreamUrl(testProfile, seriesId)
        assertEquals(expectedUrl, actualUrl)
    }

    @Test
    fun testBuildServerUrl_withHttp() {
        val input = "http://server.com:8080/"
        val expected = "http://server.com:8080"
        val actual = StreamUrlBuilder.buildServerUrl(input)
        assertEquals(expected, actual)
    }

    @Test
    fun testBuildServerUrl_withoutProtocol() {
        val input = "server.com:8080"
        val expected = "http://server.com:8080"
        val actual = StreamUrlBuilder.buildServerUrl(input)
        assertEquals(expected, actual)
    }
}