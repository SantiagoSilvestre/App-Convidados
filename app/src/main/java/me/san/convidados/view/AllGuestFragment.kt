package me.san.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.san.convidados.constants.GuestConstants
import me.san.convidados.databinding.FragmentAllBinding
import me.san.convidados.view.adapter.GuestAdapter
import me.san.convidados.view.listener.GuestListener
import me.san.convidados.viwmodel.GuestViewModel

class AllGuestFragment : Fragment() {

    private lateinit var guestViewModel: GuestViewModel
    private lateinit var mListener: GuestListener
    private var _binding: FragmentAllBinding? = null
    private val mAdapter = GuestAdapter()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        guestViewModel =
            ViewModelProvider(this).get(GuestViewModel::class.java)

        _binding = FragmentAllBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //RecyclerView
        val recycler = binding.recyclerAllGuest
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                guestViewModel.delete(id)
                guestViewModel.load(GuestConstants.FILTER.EMPTY)
            }

        }

        mAdapter.attachListener(mListener)
        observe()

        return root
    }

    override fun onResume() {
        super.onResume()
        guestViewModel.load(GuestConstants.FILTER.EMPTY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        guestViewModel.guestList.observe(viewLifecycleOwner, {
            mAdapter.updateGuests(it)
        })
    }
}