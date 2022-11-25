package com.homework.calculatortask

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.homework.calculatortask.databinding.FragmentFirstBinding
import kotlin.math.exp

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var inputBuilder = StringBuilder()
    private var expression = StringBuilder()
    private var result: Double? = null
    private var operation = Operation.Nothing

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentFirstBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.zero.setOnClickListener { binding.zero.onClickNumbers() }
        binding.one.setOnClickListener { binding.one.onClickNumbers() }
        binding.two.setOnClickListener { binding.two.onClickNumbers() }
        binding.three.setOnClickListener { binding.three.onClickNumbers() }
        binding.four.setOnClickListener { binding.four.onClickNumbers() }
        binding.five.setOnClickListener { binding.five.onClickNumbers() }
        binding.six.setOnClickListener { binding.six.onClickNumbers() }
        binding.seven.setOnClickListener { binding.seven.onClickNumbers() }
        binding.eight.setOnClickListener { binding.eight.onClickNumbers() }
        binding.nine.setOnClickListener { binding.nine.onClickNumbers() }
        binding.point.setOnClickListener { binding.point.onClickNumbers() }

        binding.backspace.setOnClickListener {
            if ("$inputBuilder".isBlank()) return@setOnClickListener

            inputBuilder.deleteCharAt(inputBuilder.lastIndex)
            binding.input.text = inputBuilder.toEditable()
        }

        binding.clear.setOnClickListener {
            inputBuilder.clear()
            binding.input.text.clear()
        }

        binding.clearText.setOnClickListener {
            binding.output.text = ""
            inputBuilder.clear()
            expression.clear()
            result = null
            binding.input.text.clear()
            operation = Operation.Nothing
            binding.operationView.text = ""
        }

        binding.plus.setOnClickListener {
            operation.execute()
            operation = Operation.Plus
            binding.operationView.text = "+"
        }

        binding.minus.setOnClickListener {
            operation.execute()
            operation = Operation.Minus
            binding.operationView.text = "-"
        }

        binding.multiply.setOnClickListener {
            operation.execute()
            operation = Operation.Multiple
            binding.operationView.text = "*"
        }

        binding.divide.setOnClickListener {
            operation.execute()
            operation = Operation.Divide
            binding.operationView.text = "/"
        }

        binding.equals.setOnClickListener {
            operation.execute()
            operation = Operation.Equals
            binding.operationView.text = ""
        }


        binding.plusminus.setOnClickListener {
            if ("$inputBuilder".isBlank()) return@setOnClickListener
            if ("$inputBuilder".startsWith("-")) inputBuilder.deleteCharAt(0)
            else inputBuilder.insert(0, "-")
            binding.input.text = inputBuilder.toEditable()
        }
    }

    private fun Button.onClickNumbers() {
        if (this.text == "." && "$inputBuilder" == "") inputBuilder.append(0)
        inputBuilder.append(this.text)
        binding.input.text = inputBuilder.toEditable()
    }


    fun Operation.execute() {
        when (this) {
            Operation.Plus -> operationPlus()
            Operation.Minus -> operationMinus()
            Operation.Divide -> operationDivide()
            Operation.Multiple -> operationMultiple()
            Operation.Equals -> operationEquals()
            Operation.Nothing -> operationNothing()
        }
    }

    fun operationPlus() {
        if (inputBuilder.toString().isBlank()) return

        expression.append(" + $inputBuilder")
        result = result!! + "$inputBuilder".toDouble()

        binding.output.text = "$expression = $result"
        inputBuilder.clear()
        binding.input.text.clear()
    }

    fun operationMinus() {
        if (inputBuilder.toString().isBlank()) return


        expression.append(" - $inputBuilder")
        result = result!! - "$inputBuilder".toDouble()


        binding.output.text = "$expression = $result"
        inputBuilder.clear()
        binding.input.text.clear()
    }

    fun operationMultiple() {
        if (inputBuilder.toString().isBlank()) return

        expression.append(" * $inputBuilder")
        result = result!! * "$inputBuilder".toDouble()


        binding.output.text = "$expression = $result"
        inputBuilder.clear()
        binding.input.text.clear()
    }

    fun operationDivide() {
        if (inputBuilder.toString().isBlank()) return

        if ("$inputBuilder".toDouble() == 0.0) {
            Toast.makeText(context, "/0 is impossible", Toast.LENGTH_SHORT).show()
            return
        }
        expression.append(" / $inputBuilder")
        result = result!! / "$inputBuilder".toDouble()

        binding.output.text = "$expression = $result"
        inputBuilder.clear()
        binding.input.text.clear()
    }

    fun operationEquals() {
        if (result == null) return

        expression.clear()
        expression.append(result)

        binding.output.text = "$expression"
        inputBuilder.clear()
        binding.input.text.clear()
    }

    fun operationNothing() {
        if ("$inputBuilder".isBlank()) return
        expression.append("$inputBuilder")
        result = "$inputBuilder".toDouble()
        binding.output.text = "$expression"
        inputBuilder.clear()
        binding.input.text.clear()
    }
}

fun StringBuilder.toEditable(): Editable =
    Editable.Factory.getInstance().newEditable(this.toString())

enum class Operation { Plus, Minus, Divide, Multiple, Equals, Nothing }
