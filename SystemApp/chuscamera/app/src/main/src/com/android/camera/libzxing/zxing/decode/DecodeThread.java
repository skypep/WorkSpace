/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.camera.libzxing.zxing.decode;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.camera.libzxing.zxing.activity.ActivityController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import com.chus.camera.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * This thread does all the heavy lifting of decoding the images.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class DecodeThread extends Thread {
    static final String TAG = "CAM_Scan.decode";
	
    public static final String BARCODE_BITMAP = "barcode_bitmap";
	public static final String BARCODE_JPEG = "barcode_jpeg";

    public static final int BARCODE_MODE = 0X100;
    public static final int QRCODE_MODE = 0X200;
    public static final int ALL_MODE = 0X300;

    private final ActivityController activity;
    private final Map<DecodeHintType, Object> hints;
    private final CountDownLatch handlerInitLatch;
    private Handler handler;

    public DecodeThread(ActivityController activity, int decodeMode) {

        this.activity = activity;
        handlerInitLatch = new CountDownLatch(1);

        hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

        Collection<BarcodeFormat> decodeFormats = new ArrayList<BarcodeFormat>();
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.AZTEC));
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.PDF_417));

        switch (decodeMode) {
            case BARCODE_MODE:
                decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
                break;

            case QRCODE_MODE:
                decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
                break;

            case ALL_MODE:
                decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
                decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
                break;

            default:
                break;
        }

        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
    }

    public Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(activity, hints);
        handlerInitLatch.countDown();
        Looper.loop();
    }

    /**************************************************************************************/
    /** // frankie, **/
    /*public*/private static class DecodeHandler extends Handler {

        private final ActivityController activity;
        private final MultiFormatReader multiFormatReader;
        private boolean running = true;

        public DecodeHandler(ActivityController activity, Map<DecodeHintType, Object> hints) {
            multiFormatReader = new MultiFormatReader();
            multiFormatReader.setHints(hints);
            this.activity = activity;
        }

        private void bundleThumbnail(PlanarYUVLuminanceSource source, Bundle bundle) {
//            DisplayMetrics dm = activity.getc.getDisplayMetrics();
//            int heigth = dm.heightPixels;
//            int width = dm.widthPixels;
            int[] pixels = source.renderThumbnail();
            int width = source.getThumbnailWidth();
            int height = source.getThumbnailHeight();
            Bitmap bitmap = Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            bundle.putByteArray(DecodeThread.BARCODE_JPEG, out.toByteArray());
        }

        @Override
        public void handleMessage(Message message) {
            if (!running) {
                return;
            }
            if (message.what == R.id.decode) {
                decode((byte[]) message.obj, message.arg1, message.arg2);

            } else if (message.what == R.id.quit) {
                running = false;
                Looper.myLooper().quit();

            }
        }

        /**
         * Decode the data within the viewfinder rectangle, and time how long it
         * took. For efficiency, reuse the same reader objects from one decode to
         * the next.
         *
         * @param data   The YUV preview frame.
         * @param width  The width of the preview frame.
         * @param height The height of the preview frame.
         */
        private static final boolean PREVIEW_DATA_ROTATE_90_EN = true; // false;
        private void decode(byte[] data, int width, int height) {
            Point res_ = activity.getPreviewResolution();

			Log.v(TAG, "PreviewResolution 1: " + res_.x + " * " + res_.y);
			Log.v(TAG, "PreviewResolution 2: " + width + " * " + height);
			Log.v(TAG, "data.length : " + data.length);

            Point size = res_;
            byte[] preview_data = data;

            int width_ = res_.x;
            int height_ = res_.y;
            if(PREVIEW_DATA_ROTATE_90_EN) { // ** frankie, rotate NV21 90 clockwise
				byte[] rotatedData = new byte[preview_data.length];
				int Y_size = width_ * height_;
				int new_width = height_;
				int new_height = width_;
				for(int y = 0; y < height_; y++) {
					for(int x = 0; x < width_; x++) {
						int uv_x = x / 2;
						uv_x *= 2;
						byte Y = preview_data[y*width_ + x];
						byte Cr = preview_data[Y_size + (y >> 1) * (width_) + uv_x + 0];
						byte Cb = preview_data[Y_size + (y >> 1) * (width_) + uv_x + 1];

						// new location 
						int new_x = height_ - 1 - y;
						int new_y = x;
						int new_uv_x = new_x / 2;
						new_uv_x *= 2;
						rotatedData[new_y*new_width + new_x] = Y;
						rotatedData[Y_size + (new_y>>1) * (new_width) + new_uv_x + 0] = Cr;
						rotatedData[Y_size + (new_y>>1) * (new_width) + new_uv_x + 1] = Cb;
					}
				}

	            size.x = new_width;
	            size.y = new_height;
                preview_data = rotatedData;
			}

			if(false) // frankie, 2017.07.11, for some preview image information
			{
				Handler resultHandler = activity.getHandler();
				if(resultHandler != null) {
					Message message = Message.obtain(resultHandler, R.id.decode_some_infomation, -1, -1, null);
					message.sendToTarget();
				}
			}
            Result rawResult = null;
            PlanarYUVLuminanceSource source = buildLuminanceSource(preview_data, size.x, size.y);
            Log.v(TAG, "source size:" + source.getWidth() + " * " + source.getHeight());
            if (source != null) {
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    rawResult = multiFormatReader.decodeWithState(bitmap);
                } catch (ReaderException re) {
                    // continue
                } finally {
                    multiFormatReader.reset();
                }
            }

            Handler handler = activity.getHandler();
            if (rawResult != null) {
                // Don't log the barcode contents for security.
                if (handler != null) {
                    Message message = Message.obtain(handler, R.id.decode_succeeded, rawResult);
                    Bundle bundle = new Bundle();
                    bundleThumbnail(source, bundle);
                    message.setData(bundle);
                    message.sendToTarget();
                }
            } else {
                if (handler != null) {
                    Message message = Message.obtain(handler, R.id.decode_failed);
                    message.sendToTarget();
                }
            }

        }

        /**
         * A factory method to build the appropriate LuminanceSource object based on
         * the format of the preview buffers, as described by Camera.Parameters.
         *
         * @param data   A preview frame.
         * @param width  The width of the image.
         * @param height The height of the image.
         * @return A PlanarYUVLuminanceSource instance.
         */
        public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
            Rect rect = activity.getCropRect();
            if (rect == null) {
                return null;
            }
            Log.v(TAG, "getCropRect, l:" + rect.left + " t:" + rect.top + " t:" + rect.right + " b:" + rect.bottom);
            Log.v(TAG, "getCropRect : " + (rect.right - rect.left) + " * " + (rect.bottom - rect.top));
            // Go ahead and assume it's YUV rather than die.
            return new PlanarYUVLuminanceSource(data, width, height, 
            	rect.left, rect.top, rect.width(), rect.height(), 
            	false);
        }

    }
}
