package me.san.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.san.convidados.R
import me.san.convidados.constants.GuestConstants
import me.san.convidados.databinding.ActivityGuestFormBinding
import me.san.convidados.viwmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var mViewModel: GuestFormViewModel
    private var mGuestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        setListeners()
        observe()
        loadData()

        binding.rbPresent.isChecked = true

    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn_save) {
            val name = binding.edtName.text.toString()
            val presence = binding.rbPresent.isChecked
            mViewModel.save(mGuestId, name, presence)
        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mGuestId = bundle.getInt(GuestConstants.GUESTID)
            mViewModel.load(mGuestId)
        }
    }

    private fun setListeners() {

        binding.btnSave.setOnClickListener(this)

    }

    private fun observe() {
        mViewModel.saveGuest.observe(this, {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.guest.observe(this, {
            with(binding) {
                edtName.setText(it.name)
                if (it.presence) {
                    rbPresent.isChecked = true
                } else {
                    rbAbsent.isChecked = true
                }
            }
        })
    }
}