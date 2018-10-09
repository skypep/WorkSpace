package com.cs.camera.magicfilter.filter;

import java.nio.FloatBuffer;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.Log;

import com.cs.camera.magicfilter.MagicEngine;
import com.cs.camera.magicfilter.MagicContext;
import com.cs.camera.magicfilter.OpenGlUtils;

public class FilterCameraInput extends GPUImageFilter{
	final static String TAG = "mytest.FilterCameraInput";
	
    private float[] mTextureTransformMatrix;
    private int mTextureTransformMatrixLocation;
    private int mSingleStepOffsetLocation;
    private int mParamsLocation;

    private int[] mFrameBuffers = null;
    private int[] mFrameBufferTextures = null;
    private int mFrameWidth = -1;
    private int mFrameHeight = -1;

	public int mBeautyLevel = 0; // frankie, add
	public FilterCameraInput() {
		this(0);
	}
    public FilterCameraInput(int beautyLevel_){
        //super(OpenGlUtils.readShaderFromRawResource(R.raw.default_vertex) , OpenGlUtils.readShaderFromRawResource(R.raw.default_fragment));
        super(OpenGlUtils.readShaderFromRawResource(FilterShaderConfig.getShaderFromClassName(FilterCameraInput.class.getSimpleName(), 0)) ,
                OpenGlUtils.readShaderFromRawResource(FilterShaderConfig.getShaderFromClassName(FilterCameraInput.class.getSimpleName(), 1)));
		mBeautyLevel = beautyLevel_;
    }

    protected void onInit() {
        super.onInit();
        mTextureTransformMatrixLocation = GLES20.glGetUniformLocation(getProgram(), "textureTransform");
        mSingleStepOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "singleStepOffset");
        mParamsLocation = GLES20.glGetUniformLocation(getProgram(), "params");


		Log.v(TAG, "  mTextureTransformMatrixLocation:" + mTextureTransformMatrixLocation);
		Log.v(TAG, "  mSingleStepOffsetLocation:" + mSingleStepOffsetLocation);
		Log.v(TAG, "  mParamsLocation:" + mParamsLocation);
		
        setBeautyLevel(mBeautyLevel);
    }

    public void setTextureTransformMatrix(float[] mtx){
        mTextureTransformMatrix = mtx;
    }
	/*
    @Override
    public int onDrawFrame(int textureId) {
        GLES20.glUseProgram(mGLProgId);
        runPendingOnDrawTasks();
        if(!isInitialized()) {
            return OpenGlUtils.NOT_INIT;
        }
        mGLCubeBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribPosition, 2, GLES20.GL_FLOAT, false, 0, mGLCubeBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribPosition);
        mGLTextureBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribTextureCoordinate, 2, GLES20.GL_FLOAT, false, 0, mGLTextureBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribTextureCoordinate);
		
        GLES20.glUniformMatrix4fv(mTextureTransformMatrixLocation, 1, false, mTextureTransformMatrix, 0);

        if(textureId != OpenGlUtils.NO_TEXTURE){
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
            GLES20.glUniform1i(mGLUniformTexture, 0);
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glDisableVertexAttribArray(mGLAttribPosition);
        GLES20.glDisableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        return OpenGlUtils.ON_DRAWN;
    }
	*/
    @Override
    public int onDrawFrame(int textureId, FloatBuffer vertexBuffer, FloatBuffer textureBuffer) {
        GLES20.glUseProgram(mGLProgId);
        runPendingOnDrawTasks();
        if(!isInitialized()) {
            return OpenGlUtils.NOT_INIT;
        }
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribPosition);
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribTextureCoordinate, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribTextureCoordinate);
		
        GLES20.glUniformMatrix4fv(mTextureTransformMatrixLocation, 1, false, mTextureTransformMatrix, 0);

        if(textureId != OpenGlUtils.NO_TEXTURE){
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
            GLES20.glUniform1i(mGLUniformTexture, 0);
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
		
        GLES20.glDisableVertexAttribArray(mGLAttribPosition);	// restore
        GLES20.glDisableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
		
        return OpenGlUtils.ON_DRAWN;
    }
    public int onDrawToTexture(final int textureId) {
        if(mFrameBuffers == null)
            return OpenGlUtils.NO_TEXTURE;
		/** frankie, 2018.02.02, must use program frist , 
		 * as runPendingOnDrawTasks will set some variable about this shader program, 
		 * or there will be 0x502 error !!!*/
		GLES20.glUseProgram(mGLProgId); // frankie, add
        runPendingOnDrawTasks();
        GLES20.glViewport(0, 0, mFrameWidth, mFrameHeight);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[0]);
        //GLES20.glUseProgram(mGLProgId); // frankie, rm
        if(!isInitialized()) {
            return OpenGlUtils.NOT_INIT;
        }
        mGLCubeBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribPosition, 2, GLES20.GL_FLOAT, false, 0, mGLCubeBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribPosition);
        mGLTextureBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribTextureCoordinate, 2, GLES20.GL_FLOAT, false, 0, mGLTextureBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glUniformMatrix4fv(mTextureTransformMatrixLocation, 1, false, mTextureTransformMatrix, 0);

        if(textureId != OpenGlUtils.NO_TEXTURE){
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
            GLES20.glUniform1i(mGLUniformTexture, 0);
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);	// frankie, note, here if wait the draw finished ... 
		
        GLES20.glDisableVertexAttribArray(mGLAttribPosition);
        GLES20.glDisableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
		
        GLES20.glViewport(0, 0, mOutputWidth, mOutputHeight);
        return mFrameBufferTextures[0];
    }
    public int onDrawToTexture_(final int textureId, FloatBuffer vertexBuffer, FloatBuffer textureBuffer) { // frankie, add
        if(mFrameBuffers == null)
            return OpenGlUtils.NO_TEXTURE;
		/** frankie, 2018.02.02, must use program frist , 
		 * as runPendingOnDrawTasks will set some variable about this shader program, 
		 * or there will be 0x502 error !!!*/
		GLES20.glUseProgram(mGLProgId); // frankie, add
        runPendingOnDrawTasks();
        GLES20.glViewport(0, 0, mFrameWidth, mFrameHeight);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[0]);
        //GLES20.glUseProgram(mGLProgId); // frankie, rm
        if(!isInitialized()) {
            return OpenGlUtils.NOT_INIT;
        }
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribPosition);
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribTextureCoordinate, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glUniformMatrix4fv(mTextureTransformMatrixLocation, 1, false, mTextureTransformMatrix, 0);

        if(textureId != OpenGlUtils.NO_TEXTURE){
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
            GLES20.glUniform1i(mGLUniformTexture, 0);
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);	// frankie, note, here if wait the draw finished ... 
		
        GLES20.glDisableVertexAttribArray(mGLAttribPosition);
        GLES20.glDisableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
		
        GLES20.glViewport(0, 0, mOutputWidth, mOutputHeight);
        return mFrameBufferTextures[0];
    }

    public void initCameraFrameBuffer(int width, int height) {
		Log.v(TAG, "initCameraFrameBuffer, width=" + width + " height=" + height);
        if(mFrameBuffers != null && (mFrameWidth != width || mFrameHeight != height)) {
            destroyFramebuffers();
		}
		
        if (mFrameBuffers == null) {
            mFrameWidth = width;
            mFrameHeight = height;
            mFrameBuffers = new int[1];
            mFrameBufferTextures = new int[1];

            GLES20.glGenFramebuffers(1, mFrameBuffers, 0);
            GLES20.glGenTextures(1, mFrameBufferTextures, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFrameBufferTextures[0]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                    GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[0]);
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                    GLES20.GL_TEXTURE_2D, mFrameBufferTextures[0], 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        }
    }

    public void destroyFramebuffers() {
        if (mFrameBufferTextures != null) {
            GLES20.glDeleteTextures(1, mFrameBufferTextures, 0);
            mFrameBufferTextures = null;
        }
        if (mFrameBuffers != null) {
            GLES20.glDeleteFramebuffers(1, mFrameBuffers, 0);
            mFrameBuffers = null;
        }
        mFrameWidth = -1;
        mFrameHeight = -1;
    }

    private void setTexelSize(final float w, final float h) {
		Log.v(TAG, "setTexelSize, w=" + w + ", h=" + h);
        setFloatVec2(mSingleStepOffsetLocation, new float[] {2.0f / w, 2.0f / h});
    }

    @Override
    public void onInputSizeChanged(final int width, final int height) {
    	Log.v(TAG, "onInputSizeChanged, width=" + width + " height=" + height);
        super.onInputSizeChanged(width, height);
        setTexelSize(width, height);
    }

    public void setBeautyLevel(int level){
		Log.v(TAG, "setBeautyLevel: " + level + " at location:" + mParamsLocation);
/*
        switch (level) {
            case 0:
                setFloat(mParamsLocation, 0.0f);
                break;
            case 1:
                setFloat(mParamsLocation, 1.0f);
                break;
            case 2:
                setFloat(mParamsLocation, 0.8f);
                break;
            case 3:
                setFloat(mParamsLocation,0.6f);
                break;
            case 4:
                setFloat(mParamsLocation, 0.4f);
                break;
            case 5:
                setFloat(mParamsLocation,0.33f);
                break;
            default:
                break;
        }
*/
		setBeautyLevel_ext(level);
    }
	public void setBeautyLevel_ext(int level){
		if(level > 100) {
			level = 100;
		}
		if(level < 0) {
			level = 0;
		}
		if(level != 0 && level != 100) {
			level = 100 - level;
		}
		float param = (float)level/(float)100;
		Log.v(TAG, "setBeautyLevel_ext, param: " + param + " at location:" + mParamsLocation);
		setFloat(mParamsLocation, param);
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyFramebuffers();
    }

	public void onBeautyLevelChanged() {
		setBeautyLevel(mBeautyLevel);
	}
    public void onBeautyLevelChanged(int level_){
		mBeautyLevel = level_;
        setBeautyLevel(level_);
    }

}
