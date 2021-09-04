package me.san.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.san.convidados.constants.GuestConstants
import me.san.convidados.databinding.FragmentPresentBinding
import me.san.convidados.view.adapter.GuestAdapter
import me.san.convidados.view.listener.GuestListener
import me.san.convidados.view.viewholder.GuestViewHolder
import me.san.convidados.viwmodel.GuestViewModel

class PresentFragment : Fragment() {

    private lateinit var mViewModel: GuestViewModel
    private lateinit var mListener: GuestListener
    private var _binding: FragmentPresentBinding? = null
    private val mAdapter = GuestAdapter()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel =
            ViewModelProvider(this).get(GuestViewModel::class.java)

        _binding = FragmentPresentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //RecyclerView
        val recycler = binding.recyclerGuest
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
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.PRESENT)
            }

        }

        mAdapter.attachListener(mListener)
        observe()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.PRESENT)
    }

    private fun observe() {
        mViewModel.guestList.observe(viewLifecycleOwner, {
            mAdapter.updateGuests(it)
        })
    }
}