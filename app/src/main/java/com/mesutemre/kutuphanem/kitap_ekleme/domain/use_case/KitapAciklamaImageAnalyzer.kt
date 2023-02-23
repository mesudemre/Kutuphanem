package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import javax.inject.Inject

class KitapAciklamaImageAnalyzer @Inject constructor() : ImageAnalysis.Analyzer {

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    var onReadText: (String) -> Unit = {}
    var onFailureReadText: (Exception) -> Unit = {}

    operator fun invoke(
        image: ImageProxy, onReadText: (String) -> Unit, onFailureReadText: (Exception) -> Unit
    ) {
        this.onReadText = onReadText
        this.onFailureReadText = onFailureReadText
        this.analyze(image)
    }

    override fun analyze(image: ImageProxy) {
        val capturedImage = image.image
        capturedImage?.let {
            val img = InputImage.fromMediaImage(it, image.imageInfo.rotationDegrees)
            recognizer.process(img)
                .addOnSuccessListener { text ->
                    val str = java.lang.StringBuilder()
                    for (block in text.textBlocks) {
                        for (line in block.lines) {
                            str.append(line.text)
                            str.append(" ")
                        }
                    }
                    onReadText(str.toString())
                }
                .addOnFailureListener { exception ->
                    onFailureReadText(exception)
                }
        }
    }
}