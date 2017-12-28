/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netease.nrtc.effect.video;

/**
 * A more generalized 9x9 Gaussian blur filter
 * blurSize value ranging from 0.0 on up, with a default of 1.0
 */
public class GPUImageBeautifyFilter extends GPUImageTwoPassTextureSamplingFilter {
    public static final String FRAGMENT_SHADER = "uniform sampler2D inputImageTexture;\n\nconst lowp int GAUSSIAN_SAMPLES = 9;\n\nvarying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\nhighp float halfPixelWidth = 1.0/512.0;\nhighp float distanceNormalization=9.0;\nvoid main(){\n   highp vec4 centralColor;\n   highp float gaussianWeightTotal;\n   highp vec4 sum = vec4(0.0);\n   highp vec4 sampleColor;\n   highp float distanceFromCentralColor;\n   highp float gaussianWeight;\n   centralColor = texture2D(inputImageTexture, blurCoordinates[4]);\n   gaussianWeightTotal = 0.18;\n   sum = centralColor * 0.18;\n   sampleColor = texture2D(inputImageTexture, blurCoordinates[0]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalization, 1.0);\n   gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(inputImageTexture, blurCoordinates[1]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalization, 1.0);\n   gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(inputImageTexture, blurCoordinates[2]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalization, 1.0);\n   gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(inputImageTexture, blurCoordinates[3]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalization, 1.0);\n   gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(inputImageTexture, blurCoordinates[5]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalization, 1.0);\n   gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(inputImageTexture, blurCoordinates[6]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalization, 1.0);\n   gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(inputImageTexture, blurCoordinates[7]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalization, 1.0);\n   gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(inputImageTexture, blurCoordinates[8]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalization, 1.0);\n   gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   highp vec4 texColour = sum / gaussianWeightTotal;\n   gl_FragColor =vec4(texColour.rgb,texColour.a);\n}\n";
    public static final String VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nconst int GAUSSIAN_SAMPLES = 9;\n\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\n\nvarying vec2 textureCoordinate;\nvarying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n\nvoid main()\n{\n\tgl_Position = position;\n\ttextureCoordinate = inputTextureCoordinate.xy;\n\t// Calculate the positions for the blur\n\tint multiplier = 0;\n\tvec2 blurStep;\nvec2 singleStepOffset = vec2(texelHeightOffset, texelWidthOffset);\nfor (int i = 0; i < GAUSSIAN_SAMPLES; i++)\n   {\n\t\tmultiplier = (i - ((GAUSSIAN_SAMPLES - 1) / 2));\n// Blur in x (horizontal)\nblurStep = float(multiplier) * singleStepOffset;\n\t\tblurCoordinates[i] = inputTextureCoordinate.xy + blurStep;\n\t}\n}\n";
    protected float mBlurSize = 1.0F;

    public GPUImageBeautifyFilter()
    {
        this(1.0F);
    }

    public GPUImageBeautifyFilter(float paramFloat)
    {
        super(VERTEX_SHADER, FRAGMENT_SHADER, VERTEX_SHADER, FRAGMENT_SHADER);
        this.mBlurSize = paramFloat;
    }

    public float getHorizontalTexelOffsetRatio()
    {
        return this.mBlurSize;
    }

    public float getVerticalTexelOffsetRatio()
    {
        return this.mBlurSize;
    }

    public void onInit()
    {
        super.onInit();
    }

    public void onInitialized()
    {
        super.onInitialized();
        setBlurSize(this.mBlurSize);
    }

    public void setBlurSize(float paramFloat)
    {
        this.mBlurSize = paramFloat;
        runOnDraw(new Runnable()
        {
            public void run()
            {
                GPUImageBeautifyFilter.this.initTexelOffsets();
            }
        });
    }

    public void setRadius(int paramInt)
    {
        this.mBlurSize = (float)(1.0D * paramInt / 100.0D);
        setBlurSize(this.mBlurSize);
    }
}