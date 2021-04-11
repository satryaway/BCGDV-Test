package com.satryaway.bcgdvtest.api

import retrofit2.await

class ApiRepository {
    var client: ApiService = Api.webService

    suspend fun search(key: String) = client.searchSong(key).await()
}