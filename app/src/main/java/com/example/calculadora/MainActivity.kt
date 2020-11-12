package com.example.calculadora

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    companion object {
        const val MODE_DEC = 0
        const val MODE_BIN = 1
        const val MODE_OCT = 2
        const val MODE_HEX = 3
    }

    private var mode = MODE_DEC
    private var num1Introduced = false
    private var num1 = 0;
    private var num2 = 0;
    private var resetOnNext = false
    private var orientation = 0

    private fun hexCharToInt(char: Char): Int {
        return when (char) {
            'A' -> 10
            'B' -> 11
            'C' -> 12
            'D' -> 13
            'E' -> 14
            'F' -> 15
            else -> char.toString().toInt()
        }
    }

    private fun hexToDec(num: String): Int {
        var dec = 0
        for ((pow, x) in (num.length - 1 downTo 0).withIndex()) {
            val value = hexCharToInt(num[x])
            val exponential = 16.0.pow(pow.toDouble()).toInt() * value
            dec += exponential
        }
        return dec
    }

    private fun decToHex(dec: Int): String {
        return Integer.toHexString(dec).toUpperCase(Locale.ROOT)
    }

    private fun octToDec(num: String): Int {
        var oct = Integer.parseInt(num)
        var dec = 0
        var i = 0

        while (oct != 0) {
            dec += (oct % 10 * 8.0.pow(i.toDouble())).toInt()
            ++i
            oct /= 10
        }

        return dec
    }

    private fun decToOct(num: Int): String {
        return Integer.toOctalString(num)
    }

    private fun binToDec(num: String): Int {
        var bin = Integer.parseInt(num)
        var dec = 0
        var i = 0
        var remind: Int

        while (bin != 0) {
            remind = bin % 10
            bin /= 10
            dec += (remind * 2.0.pow(i.toDouble())).toInt()
            ++i
        }
        return dec
    }

    private fun decToBin(num: Int): String {
        return Integer.toBinaryString(num)
    }

    private fun calculate(num1: Int, num2: Int): Int {
        when (textViewOperator.text) {
            "+" -> {
                val result = num1 + num2
                if (result.toString().length > 8) {
                    return -1
                } else {
                    return result
                }
            }
            "-" -> {
                val result = num1 - num2
                if (result < 0) {
                    return 0
                }
                if (result.toString().length > 8) {
                    return -1;
                } else {
                    return result
                }
            }
            "*" -> {
                val result = num1 * num2
                if (result.toString().length > 8) {
                    return -1
                } else {
                    return result
                }
            }
            "/" -> {
                if (num2 == 0) {
                    textViewNumbers.text = getString(R.string.error)
                    return -1
                }
                val result = num1 / num2
                if (result.toString().length > 8) {
                    return -1
                } else {
                    return result
                }
            }
        }
        return -1
    }

    private fun setEnabledForDECButtons() {
        button0.isEnabled = true
        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = true
        button9.isEnabled = true
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            buttonA.isEnabled = false
            buttonB.isEnabled = false
            buttonC.isEnabled = false
            buttonD.isEnabled = false
            buttonE.isEnabled = false
            buttonF.isEnabled = false
            buttonHEX.isEnabled = true
            buttonDEC.isEnabled = false
            buttonBIN.isEnabled = true
            buttonOCT.isEnabled = true
        }
    }

    private fun setEnabledForBINButtons() {
        button0.isEnabled = true
        button1.isEnabled = true
        button2.isEnabled = false
        button3.isEnabled = false
        button4.isEnabled = false
        button5.isEnabled = false
        button6.isEnabled = false
        button7.isEnabled = false
        button8.isEnabled = false
        button9.isEnabled = false
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            buttonA.isEnabled = false
            buttonB.isEnabled = false
            buttonC.isEnabled = false
            buttonD.isEnabled = false
            buttonE.isEnabled = false
            buttonF.isEnabled = false
            buttonHEX.isEnabled = true
            buttonDEC.isEnabled = true
            buttonBIN.isEnabled = false
            buttonOCT.isEnabled = true
        }
    }

    private fun setEnabledForOCTButtons() {
        button0.isEnabled = true
        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = false
        button9.isEnabled = false
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            buttonA.isEnabled = false
            buttonB.isEnabled = false
            buttonC.isEnabled = false
            buttonD.isEnabled = false
            buttonE.isEnabled = false
            buttonF.isEnabled = false
            buttonHEX.isEnabled = true
            buttonDEC.isEnabled = true
            buttonBIN.isEnabled = true
            buttonOCT.isEnabled = false
        }
    }

    private fun setEnabledForHEXButtons() {
        button0.isEnabled = true
        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = true
        button9.isEnabled = true
        buttonA.isEnabled = true
        buttonB.isEnabled = true
        buttonC.isEnabled = true
        buttonD.isEnabled = true
        buttonE.isEnabled = true
        buttonF.isEnabled = true
        buttonDEC.isEnabled = true
        buttonBIN.isEnabled = true
        buttonHEX.isEnabled = false
        buttonOCT.isEnabled = true
    }

    private fun setButtonListeners() {

        buttonEquals.setOnClickListener {
            if (num1Introduced) {
                val text = textViewNumbers.text.toString()
                when (mode) {
                    MODE_DEC -> num2 = Integer.parseInt(text)
                    MODE_BIN -> num2 = binToDec(text)
                    MODE_OCT -> num2 = octToDec(text)
                    MODE_HEX -> num2 = hexToDec(text)
                }
                val result = calculate(num1, num2)
                if (result >= 0) {
                    when (mode) {
                        MODE_DEC -> textViewNumbers.text = result.toString()
                        MODE_BIN -> textViewNumbers.text = decToBin(result)
                        MODE_OCT -> textViewNumbers.text = decToOct(result)
                        MODE_HEX -> textViewNumbers.text = decToHex(result)
                    }
                } else {
                    textViewNumbers.text = getString(R.string.error)
                }
                num1Introduced = false
            }
            textViewOperator.text = ""
        }

        // Change mode buttons
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            buttonDEC.setOnClickListener {
                if (textViewNumbers.text == getString(R.string.error)) {
                    textViewNumbers.text = getString(R.string.defaultTextViewValue)
                } else {
                    when (mode) {
                        MODE_BIN -> {
                            val num = textViewNumbers.text.toString()
                            val output = binToDec(num).toString()
                            if (output.length < 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                        MODE_OCT -> {
                            val num = textViewNumbers.text.toString()
                            val output = octToDec(num).toString()
                            if (output.length < 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                        MODE_HEX -> {
                            val num = textViewNumbers.text.toString()
                            val output = hexToDec(num).toString()
                            if (output.length < 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                    }
                }
                mode = MODE_DEC
                setEnabledButtonsByMode()
            }

            buttonBIN.setOnClickListener {
                if (textViewNumbers.text == getString(R.string.error)) {
                    textViewNumbers.text = getString(R.string.defaultTextViewValue)
                } else {
                    when (mode) {
                        MODE_DEC -> {
                            val num = Integer.parseInt(textViewNumbers.text.toString())
                            val output = decToBin(num)
                            if (output.length <= 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                        MODE_OCT -> {
                            val num = octToDec(textViewNumbers.text.toString())
                            val output = decToBin(num)
                            if (output.length <= 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                        MODE_HEX -> {
                            val num = hexToDec(textViewNumbers.text.toString())
                            val output = decToBin(num)
                            if (output.length <= 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                    }
                }
                mode = MODE_BIN
                setEnabledButtonsByMode()
            }

            buttonOCT.setOnClickListener {
                if (textViewNumbers.text == getString(R.string.error)) {
                    textViewNumbers.text = getString(R.string.defaultTextViewValue)
                } else {
                    when (mode) {
                        MODE_BIN -> {
                            val num = binToDec(textViewNumbers.text.toString())
                            val output = decToOct(num)
                            if (output.length < 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                        MODE_DEC -> {
                            val num = Integer.parseInt(textViewNumbers.text.toString())
                            val output = decToOct(num)
                            if (output.length < 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                        MODE_HEX -> {
                            val num = hexToDec(textViewNumbers.text.toString())
                            val output = decToOct(num)
                            if (output.length < 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                    }
                }
                mode = MODE_OCT
                setEnabledButtonsByMode()
            }

            buttonHEX.setOnClickListener {
                if (textViewNumbers.text == getString(R.string.error)) {
                    textViewNumbers.text = getString(R.string.defaultTextViewValue)
                } else {
                    when (mode) {
                        MODE_BIN -> {
                            val num = binToDec(textViewNumbers.text.toString())
                            val output = decToHex(num)
                            if (output.length < 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                        MODE_DEC -> {
                            val num = Integer.parseInt(textViewNumbers.text.toString())
                            val output = decToHex(num)
                            if (output.length < 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                        MODE_OCT -> {
                            val num = octToDec(textViewNumbers.text.toString())
                            val output = decToHex(num)
                            if (output.length < 8) {
                                textViewNumbers.text = output
                            } else {
                                textViewNumbers.text = getString(R.string.error)
                            }
                        }
                    }
                }
                mode = MODE_HEX
                setEnabledButtonsByMode()
            }
        }

        // Adds to string.
        button0.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text != "0" && textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("0")
            resetOnNext = false
        }
        button1.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text == "0") textViewNumbers.text = ""
            if (textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("1")
            resetOnNext = false
        }
        button2.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text == "0") textViewNumbers.text = ""
            if (textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("2")
            resetOnNext = false
        }
        button3.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text == "0") textViewNumbers.text = ""
            if (textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("3")
            resetOnNext = false
        }
        button4.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text == "0") textViewNumbers.text = ""
            if (textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("4")
            resetOnNext = false
        }
        button5.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text == "0") textViewNumbers.text = ""
            if (textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("5")
            resetOnNext = false
        }
        button6.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text == "0") textViewNumbers.text = ""
            if (textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("6")
            resetOnNext = false
        }
        button7.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text == "0") textViewNumbers.text = ""
            if (textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("7")
            resetOnNext = false
        }
        button8.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text == "0") textViewNumbers.text = ""
            if (textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("8")
            resetOnNext = false
        }
        button9.setOnClickListener {
            if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
            if (textViewNumbers.text == "0") textViewNumbers.text = ""
            if (textViewNumbers.text.length < 8) textViewNumbers.text =
                textViewNumbers.text.toString().plus("9")
            resetOnNext = false
        }

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            buttonA.setOnClickListener {
                if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
                if (textViewNumbers.text == "0") textViewNumbers.text = ""
                if (textViewNumbers.text.length < 8) textViewNumbers.text =
                    textViewNumbers.text.toString().plus("A")
                resetOnNext = false
            }
            buttonB.setOnClickListener {
                if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
                if (textViewNumbers.text == "0") textViewNumbers.text = ""
                if (textViewNumbers.text.length < 8) textViewNumbers.text =
                    textViewNumbers.text.toString().plus("B")
                resetOnNext = false
            }
            buttonC.setOnClickListener {
                if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
                if (textViewNumbers.text == "0") textViewNumbers.text = ""
                if (textViewNumbers.text.length < 8) textViewNumbers.text =
                    textViewNumbers.text.toString().plus("C")
                resetOnNext = false
            }
            buttonD.setOnClickListener {
                if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
                if (textViewNumbers.text == "0") textViewNumbers.text = ""
                if (textViewNumbers.text.length < 8) textViewNumbers.text =
                    textViewNumbers.text.toString().plus("D")
                resetOnNext = false
            }
            buttonE.setOnClickListener {
                if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
                if (textViewNumbers.text == "0") textViewNumbers.text = ""
                if (textViewNumbers.text.length < 8) textViewNumbers.text =
                    textViewNumbers.text.toString().plus("E")
                resetOnNext = false
            }
            buttonF.setOnClickListener {
                if (resetOnNext || textViewNumbers.text == getString(R.string.error)) textViewNumbers.text = ""
                if (textViewNumbers.text == "0") textViewNumbers.text = ""
                if (textViewNumbers.text.length < 8) textViewNumbers.text =
                    textViewNumbers.text.toString().plus("F")
                resetOnNext = false
            }
        }

        //Operators
        buttonPlus.setOnClickListener {
            if (textViewNumbers.text == getString(R.string.error)) {
                textViewNumbers.text = resources.getText(R.string.defaultTextViewValue)
            }
            textViewOperator.text = "+"
            if (!num1Introduced) {
                val text = textViewNumbers.text.toString()
                when(mode) {
                    MODE_DEC -> num1 = Integer.parseInt(text)
                    MODE_BIN -> num1 = binToDec(text)
                    MODE_OCT -> num1 = octToDec(text)
                    MODE_HEX -> num1 = hexToDec(text)
                }
                num1Introduced = true
            }
            resetOnNext = true
        }
        buttonMinus.setOnClickListener {
            if (textViewNumbers.text == getString(R.string.error)) {
                textViewNumbers.text = resources.getText(R.string.defaultTextViewValue)
            }
            textViewOperator.text = "-"
            if (!num1Introduced) {
                val text = textViewNumbers.text.toString()
                when(mode) {
                    MODE_DEC -> num1 = Integer.parseInt(text)
                    MODE_BIN -> num1 = binToDec(text)
                    MODE_OCT -> num1 = octToDec(text)
                    MODE_HEX -> num1 = hexToDec(text)
                }
                num1Introduced = true
            }
            resetOnNext = true
        }
        buttonMult.setOnClickListener {
            if (textViewNumbers.text == getString(R.string.error)) {
                textViewNumbers.text = resources.getText(R.string.defaultTextViewValue)
            }
            textViewOperator.text = "*"
            if (!num1Introduced) {
                val text = textViewNumbers.text.toString()
                when(mode) {
                    MODE_DEC -> num1 = Integer.parseInt(text)
                    MODE_BIN -> num1 = binToDec(text)
                    MODE_OCT -> num1 = octToDec(text)
                    MODE_HEX -> num1 = hexToDec(text)
                }
                num1Introduced = true
            }
            resetOnNext = true
        }
        buttonDiv.setOnClickListener {
            if (textViewNumbers.text == getString(R.string.error)) {
                textViewNumbers.text = resources.getText(R.string.defaultTextViewValue)
            }
            textViewOperator.text = "/"
            if (!num1Introduced) {
                val text = textViewNumbers.text.toString()
                when(mode) {
                    MODE_DEC -> num1 = Integer.parseInt(text)
                    MODE_BIN -> num1 = binToDec(text)
                    MODE_OCT -> num1 = octToDec(text)
                    MODE_HEX -> num1 = hexToDec(text)
                }
                num1Introduced = true
            }
            resetOnNext = true
        }

        //Clear
        buttonClear.setOnClickListener {
            textViewOperator.text = ""
            textViewNumbers.text = resources.getText(R.string.defaultTextViewValue)
            num1Introduced = false
        }
    }

    private fun setEnabledButtonsByMode() {
        when (mode) {
            MODE_DEC -> setEnabledForDECButtons()
            MODE_BIN -> setEnabledForBINButtons()
            MODE_OCT -> setEnabledForOCTButtons()
            MODE_HEX -> setEnabledForHEXButtons()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        orientation = resources.configuration.orientation
        setEnabledButtonsByMode()
        setButtonListeners()
    }
}