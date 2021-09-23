package com.omelchenkoaleks.storageroomorcursor.screens.add

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omelchenkoaleks.storageroomorcursor.databinding.FragmentAddAnimalBinding

class AddAnimalFragment : Fragment() {

    private var _binding: FragmentAddAnimalBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: AddAnimalViewModel by viewModels()
    private var id: Int? = null
    private var saveBtn: AppCompatButton? = null
    private var updateBtn: AppCompatButton? = null
    private var deleteBtn: AppCompatButton? = null

    interface CallBack {
        fun openAnimalsFragment()
    }

    private var callBack: CallBack? = null

    private fun textWatcher(actionTextChange: (sequence: CharSequence?) -> Unit) =
        object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                actionTextChange(sequence)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

    private val nameWatcher = textWatcher { viewModel.animal.name = it.toString() }
    private val ageWatcher = textWatcher { viewModel.animal.age = it.toString().toIntOrNull() ?: 0 }
    private val breedWatcher = textWatcher { viewModel.animal.breed = it.toString() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = context as CallBack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
        id?.let { viewModel.load(it) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAnimalBinding.inflate(inflater, container, false)

        saveBtn = binding.btnSaveAnimal
        updateBtn = binding.btnUpdateAnimal
        deleteBtn = binding.btnDeleteAnimal

        if (id !== null) {
            saveBtn?.visibility = View.INVISIBLE
            updateBtn?.visibility = View.VISIBLE
            deleteBtn?.visibility = View.VISIBLE
        }

        binding.etName.addTextChangedListener(nameWatcher)
        binding.etAge.addTextChangedListener(ageWatcher)
        binding.etBreed.addTextChangedListener(breedWatcher)

        saveBtn?.setOnClickListener {
            if (id == null)
                viewModel.addAnimal(viewModel.animal) else
                viewModel.updateAnimal(viewModel.animal)
            callBack?.openAnimalsFragment()

        }

        updateBtn?.setOnClickListener {
            viewModel.updateAnimal(viewModel.animal)
            callBack?.openAnimalsFragment()
        }

        deleteBtn?.setOnClickListener {
            viewModel.deleteAnimal(viewModel.animal)
            callBack?.openAnimalsFragment()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.animals.observe(
            viewLifecycleOwner,
            { animal ->
                animal?.let {
                    this.viewModel.animal = it
                    updateUI()
                }
            }
        )
    }

    private fun updateUI() {
        binding.etName.setText(viewModel.animal.name)
        binding.etAge.setText(viewModel.animal.age.toString())
        binding.etBreed.setText(viewModel.animal.breed)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}