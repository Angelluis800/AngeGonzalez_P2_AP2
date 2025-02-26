package edu.ucne.angegonzalez_p2_ap2.data.remote

import edu.ucne.angegonzalez_p2_ap2.data.remote.dto.DepositoDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val depositoManagerApi: DepositoManagerApi
) {
    suspend fun getDepositos() = depositoManagerApi.getDepositos()
    suspend fun getDeposito(id: Int) = depositoManagerApi.getDeposito(id)
    suspend fun saveDeposito(depositoDto: DepositoDto) = depositoManagerApi.saveDeposito(depositoDto)
    suspend fun updateDeposito(id: Int, depositoDto: DepositoDto) = depositoManagerApi.updateDeposito(id, depositoDto)
    suspend fun deleteDeposito(id: Int) = depositoManagerApi.deleteDeposito(id)
}