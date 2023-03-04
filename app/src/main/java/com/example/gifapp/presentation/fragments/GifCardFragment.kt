package com.example.gifapp.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.gifapp.Constants
import com.example.gifapp.MainActivity
import com.example.gifapp.databinding.FragmentGifCardBinding
import com.example.gifapp.presentation.viewmodels.GifCardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifCardFragment : Fragment() {
    //Карточка гифки
    private lateinit var bind: FragmentGifCardBinding
    private val title = "Card"
    private var isAttached = false
    private fun rateTranslate(r: String): String {
        when (r.lowercase()) {
            "g" -> return "0+"
            "pg" -> return "6+"
            "pg-13" -> return "12+"
            "r" -> return "16+"
            "nc-17" -> return "18+"
        }
        return ""
    }

    companion object {
        fun newInstance() = GifCardFragment()
    }

    private val viewModel: GifCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity?)?.setActionBarTitle(title)
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bind = FragmentGifCardBinding.inflate(inflater, container, false)
        return bind.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRecord(arguments?.getString("id")!!).observe(requireActivity()) {
            if (it == null)
                viewModel.getCard(arguments?.getString("id")!!)
            else {
                bind.gifTitleTxt.text = it.title
                if (it.height.toInt() <= it.width.toInt()) {
                    bind.cardGifImg.layoutParams.apply {
                        width = 0
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                } else {
                    bind.cardGifImg.layoutParams.apply {
                        width = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = 0
                    }
                }
                if (isAttached) {
                    Glide.with(this).load(it.url).into(bind.cardGifImg)
                    bind.ratingTxt.text = rateTranslate(it.rating)
                    if (it.username.isNotEmpty())
                        bind.usernameTxt.text = "@" + it.username
                }
            }
        }
        viewModel.cardResp.observe(requireActivity()) {
            if (it != null) {
                bind.gifTitleTxt.text = it.data.title
                if (it.data.images.original.height.toInt() <= it.data.images.original.width.toInt()) {
                    bind.cardGifImg.layoutParams.apply {
                        width = 0
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                } else {
                    bind.cardGifImg.layoutParams.apply {
                        width = ViewGroup.LayoutParams.WRAP_CONTENT
                        height = 0
                    }
                }

                Glide.with(this).load(it.data.images.original.url).into(bind.cardGifImg)
                bind.ratingTxt.text = rateTranslate(it.data.rating)
                if (it.data.username.isNotEmpty())
                    bind.usernameTxt.text = "@" + it.data.username
            } else Toast.makeText(
                bind.root.context,
                Constants.connectionError,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isAttached = true
    }

    override fun onDetach() {
        super.onDetach()
        isAttached = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        (activity as MainActivity?)?.onSupportNavigateUp()
        return true
    }
}