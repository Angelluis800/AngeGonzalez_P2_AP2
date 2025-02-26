package edu.ucne.angegonzalez_p2_ap2.data.remote

import edu.ucne.angegonzalez_p2_ap2.data.remote.dto.DepositoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Response

interface DepositoManagerApi {

    @GET("api/Depositos")
    suspend fun getDepositos(): Response<List<DepositoDto>>

    @GET("api/Depositos/{id}")
    suspend fun getDeposito(@Path("id") id: Int): Response<DepositoDto>

    @POST("api/Depositos")
    suspend fun saveDeposito(@Body depositoDto: DepositoDto): Response<DepositoDto>

    @PUT("api/Depositos/{id}")
    suspend fun updateDeposito(
        @Path("id") id: Int,
        @Body depositoDto: DepositoDto
    ): Response<DepositoDto>

    @DELETE("api/Depositos/{id}")
    suspend fun deleteDeposito(@Path("id") id: Int): Response<Unit>
}