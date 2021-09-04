package me.san.convidados.service.repository

import android.content.Context
import me.san.convidados.model.GuestModel

class GuestRepository (context: Context) {

    //Acesso ao banco de dados
    private val mDatabase = GuestDataBase.getDatabase(context).guestDAO()

    /**
     * Carrega convidado
     */
    fun get(id: Int): GuestModel {
        return mDatabase.get(id)
    }

    /**
     * Insere convidado
     */
    fun save(guest: GuestModel): Boolean {
        return mDatabase.save(guest) > 0
    }

    /**
     * Faz a listagem de todos os convidados
     */
    fun getAll(): List<GuestModel> {
        return mDatabase.getInvited()
    }

    /**
     * Faz a listagem de todos os convidados presentes
     */
    fun getPresents(): List<GuestModel> {
        return mDatabase.getPresents()
    }

    /**
     * Faz a listagem de todos os convidados ausentes
     */
    fun getAbsents(): List<GuestModel> {
        return mDatabase.getAbsents()
    }

    /**
     * Atualiza o convidade
     */
    fun update(guest: GuestModel): Boolean {
        return mDatabase.update(guest) > 0
    }

    /**
     * Deleta o convidado
     */
    fun delete(guest: GuestModel) {
        return mDatabase.delete(guest)
    }

}