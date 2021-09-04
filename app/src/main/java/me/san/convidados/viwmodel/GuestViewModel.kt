package me.san.convidados.viwmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.san.convidados.constants.GuestConstants
import me.san.convidados.model.GuestModel
import me.san.convidados.service.repository.GuestRepository

class GuestViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mGuestRepository = GuestRepository(mContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()

    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int) {
        when (filter) {
            GuestConstants.FILTER.EMPTY -> {
                mGuestList.value = mGuestRepository.getAll()
            }
            GuestConstants.FILTER.PRESENT -> {
                mGuestList.value = mGuestRepository.getPresents()
            }
            else -> {
                mGuestList.value = mGuestRepository.getAbsents()
            }
        }

    }

    fun delete(id: Int) {
        val guest = mGuestRepository.get(id)
        mGuestRepository.delete(guest)
    }
}