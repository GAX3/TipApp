package com.example.tipapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tipapp.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        mBinding.tieAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!p0.isNullOrEmpty()){
                    val number: Double = p0.toString().toDouble()
                    calculatePercents(number)
                }else{
                    calculatePercents(0.0)
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        mBinding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {

            override fun onStartTrackingTouch(slider: Slider) {
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(slider: Slider) {
                var totalAmount = mBinding.tieAmount.text
                if(!totalAmount.isNullOrEmpty()){
                    calculatePercents(totalAmount.toString().toDouble())
                }
            }
        })

        mBinding.btnTest.setOnClickListener {
            var res = mBinding.slider.value
            val number: Int = res.toInt()
            var result = number.toBigDecimal().divide(100.toBigDecimal())

            Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun calculatePercents(total: Double){
        var slideValue = mBinding.slider.value.toInt()
        var percent: BigDecimal? = slideValue.toBigDecimal().divide(100.toBigDecimal())
        var per = percent?.toDouble()

        var result = total + total * per!!

        mBinding.tvTipResult.text = "$ $slideValue --- $percent"
        mBinding.tvTotalResult.text = "$ " + (result).toString()
    }
}