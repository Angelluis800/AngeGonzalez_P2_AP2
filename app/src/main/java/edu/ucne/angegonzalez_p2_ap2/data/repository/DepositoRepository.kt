package edu.ucne.angegonzalez_p2_ap2.data.repository

import android.util.Log
import edu.ucne.angegonzalez_p2_ap2.data.remote.RemoteDataSource
import edu.ucne.angegonzalez_p2_ap2.data.remote.Resource
import edu.ucne.angegonzalez_p2_ap2.data.remote.dto.DepositoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DepositoRepository @Inject constructor (
    private val remoteDataSource: RemoteDataSource
){
    fun getAllArticulos(): Flow<Resource<List<DepositoDto>>> = flow {
        try {
            emit(Resource.Loading())
            val response = remoteDataSource.getDepositos()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("La respuesta está vacía"))
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: IOException) {
            Log.e("DepositoRepository", "IOException: ${e.message}")
            emit(Resource.Error("Error de conexión, verifica tu Internet"))
        } catch (e: HttpException) {
            Log.e("DepositoRepository", "HttpException: ${e.message()}")
            emit(Resource.Error("Error HTTP ${e.code()}: ${e.message()}"))
        } catch (e: Exception) {
            Log.e("DepositoRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error inesperado: ${e.message}"))
        }
    }

    fun find(id: Int): Flow<Resource<DepositoDto>> = flow {
        try {
            emit(Resource.Loading())
            val response = remoteDataSource.getDeposito(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("El Deposito no fue encontrado"))
            } else {
                emit(Resource.Error("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("DepositoRepository", "Error en find: ${e.message}")
            emit(Resource.Error("Error inesperado: ${e.message}"))
        }
    }

    fun save(deposito: DepositoDto): Flow<Resource<DepositoDto>> = flow {
        try {
            emit(Resource.Loading())
            val response = remoteDataSource.saveDeposito(deposito)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("No se pudo guardar el deposito"))
            } else {
                emit(Resource.Error("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("DepositoRepository", "Error en save: ${e.message}")
            emit(Resource.Error("Error inesperado: ${e.message}"))
        }
    }

    fun update(id: Int, deposito: DepositoDto): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val response = remoteDataSource.updateDeposito(id, deposito)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("DepositoryRepository", "Error en update: ${e.message}")
            emit(Resource.Error("Error inesperado: ${e.message}"))
        }
    }

    fun delete(depositoId: Int): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val response = remoteDataSource.deleteDeposito(depositoId)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("DepositoRepository", "Error en delete: ${e.message}")
            emit(Resource.Error("Error inesperado: ${e.message}"))
        }
    }
}