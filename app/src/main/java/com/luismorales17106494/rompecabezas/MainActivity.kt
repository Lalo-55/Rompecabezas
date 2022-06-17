package com.luismorales17106494.rompecabezas

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.luismorales17106494.rompecabezas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivDrag1.setOnLongClickListener(longClickListener)
        binding.ivDrag2.setOnLongClickListener(longClickListener)
        binding.ivDra3.setOnLongClickListener(longClickListener)

        binding.ivTarget1.setOnDragListener(dragListener)
        binding.ivTarget2.setOnDragListener(dragListener)
        binding.ivTarge3.setOnDragListener(dragListener)


    }

    private val longClickListener = View.OnLongClickListener { view ->
        val item = ClipData.Item(view.tag as? CharSequence)

        val dragData = ClipData(
            view.tag as CharSequence,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item
        )
        val myShadow = MyDragShadowBuilder(view)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(dragData, myShadow, null, 0)
        } else {
            view.startDrag(dragData, myShadow, null, 0)
        }

        true
    }

    private val dragListener = View.OnDragListener{
        view, dragEvent ->
        val receiverView : ImageView = view as ImageView

        when(dragEvent.action){
            DragEvent.ACTION_DRAG_STARTED ->{
                binding.tvTitle.text = "Empezaste a arrastra Imagen"
             true
            }
            DragEvent.ACTION_DRAG_ENTERED ->{
                binding.tvTitle.text = "Entraste a la Imagen"
                if (receiverView.tag as String == dragEvent.clipDescription.label ){
                    binding.tvTitle.text = "Imagen Correcta"
                    receiverView.setColorFilter(Color.MAGENTA)
                }else{
                    binding.tvTitle.text = "Error! "
                    receiverView.setColorFilter(Color.RED)

                }
             true
            }
            DragEvent.ACTION_DRAG_LOCATION ->{
             true
            }
            DragEvent.ACTION_DRAG_EXITED ->{
                binding.tvTitle.text = "Saliste Imagen"
             true
            }
            DragEvent.ACTION_DROP ->{
                binding.tvTitle.text = "Soltaste la imagen"
             true
            }
            DragEvent.ACTION_DRAG_ENDED ->{
                binding.tvTitle.text = "Dejaste de arrastrar la imagen"
             true
            }

            else -> false
        }



    }


    private class MyDragShadowBuilder(view: View) : View.DragShadowBuilder(view) {
        override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
            outShadowSize!!.set(view.width, view.height)
            outShadowTouchPoint!!.set(view.width / 2, view.height / 2)
        }

        override fun onDrawShadow(canvas: Canvas?) {
            view.draw(canvas)
        }


    }
}