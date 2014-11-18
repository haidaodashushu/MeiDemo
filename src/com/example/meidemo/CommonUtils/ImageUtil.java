package com.example.meidemo.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class ImageUtil {
	/**
	 * 剪切图片并绘制给定大小的图片
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap cutBit(Bitmap bitmap, int w, int h) {
		Bitmap newbitMap = createScaledBitmap(bitmap, w, h, ScalingLogic.CROP);
//		newbitMap = decodeBitmap(newbitMap,w, h, ScalingLogic.FIT);
		return newbitMap;
	}
	/**
	 * 缩放图片
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBit(Bitmap bitmap, int w, int h){
		Bitmap newBitmap = null;
//		newBitmap=decodeFile("a.jpg", w, h, ScalingLogic.CROP);
		newBitmap = decodeBitmap(bitmap, w, h, ScalingLogic.FIT);
		return newBitmap;
	}
	public static Bitmap createScaledBitmap(Bitmap unscaledBitmap,
			int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(),
				unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
		Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(),
				unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
		Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(),
				dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(scaledBitmap);
		canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(
				Paint.FILTER_BITMAP_FLAG));
		return scaledBitmap;
	}

	public static enum ScalingLogic {
		CROP, FIT, SCALE_CROP
	}
	/**
	 * 计算原图片的剪切大小
	 * @param srcWidth
	 * @param srcHeight
	 * @param dstWidth
	 * @param dstHeight
	 * @param scalingLogic crop表示按目标图片大小计算。 fit表示全部大小
	 * @return
	 */
	public static Rect calculateSrcRect(int srcWidth, int srcHeight,
			int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.CROP) {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;
			if (srcAspect > dstAspect) {
				final int srcRectWidth = (int) (srcHeight * dstAspect);
				final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
				return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth,
						srcHeight);
			} else {
				final int srcRectHeight = (int) (srcWidth / dstAspect);
				final int scrRectTop = (int) (srcHeight - srcRectHeight) / 2;
				return new Rect(0, scrRectTop, srcWidth, scrRectTop
						+ srcRectHeight);
			}
		} else {
			return new Rect(0, 0, srcWidth, srcHeight);
		}
	}
	/**
	 * 计算目标图片大小
	 * @param srcWidth
	 * @param srcHeight
	 * @param dstWidth
	 * @param dstHeight
	 * @param scalingLogic
	 * @return
	 */
	public static Rect calculateDstRect(int srcWidth, int srcHeight,
			int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.FIT) {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;
			if (srcAspect > dstAspect) {
				return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
			} else {
				return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
			}
		} else {
			return new Rect(0, 0, dstWidth, dstHeight);
		}
	}
	/**
	 * 缩放图片
	 * @param pathName
	 * @param dstWidth
	 * @param dstHeight
	 * @param scalingLogic
	 * @return
	 */
	public static Bitmap decodeFile(String pathName, int dstWidth,
			int dstHeight, ScalingLogic scalingLogic) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		
		BitmapFactory.decodeFile(pathName, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = calculateSampleSize(options.outWidth,
				options.outHeight, dstWidth, dstHeight, scalingLogic);
		Bitmap unscaledBitmap = BitmapFactory.decodeFile(pathName, options);
		return unscaledBitmap;
	}
	public static Bitmap decodeAssets(Context context,String pathName, int dstWidth,
			int dstHeight, ScalingLogic scalingLogic){
		Options options = new Options();
		options.inJustDecodeBounds = true;
		InputStream is = null;
		try {
			is = context.getAssets().open(pathName);
			BitmapFactory.decodeStream(is, null, options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		BitmapFactory.decodeFile(pathName, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = calculateSampleSize(options.outWidth,
				options.outHeight, dstWidth, dstHeight, scalingLogic);
//		Bitmap unscaledBitmap = BitmapFactory.decodeFile(pathName, options);
		Bitmap unscaledBitmap = BitmapFactory.decodeStream(is, null, options);
		return unscaledBitmap;
	}
	/**
	 * 缩放图片
	 * @param bitmap
	 * @param dstWidth
	 * @param dstHeight
	 * @param scalingLogic
	 * @return
	 */
	public static Bitmap decodeBitmap(Bitmap bitmap, int dstWidth,
			int dstHeight, ScalingLogic scalingLogic) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		byte[] arrays = convertBitmap2Bytes(bitmap);
		BitmapFactory.decodeByteArray(arrays, 0, arrays.length, options);
//		BitmapFactory.decodeFile(pathName, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = calculateSampleSize(options.outWidth,
				options.outHeight, dstWidth, dstHeight, scalingLogic);
		Bitmap unscaledBitmap = BitmapFactory.decodeByteArray(arrays, 0, arrays.length, options);
		return unscaledBitmap;
	}
	/**
	 * 计算压缩比
	 * @param srcWidth
	 * @param srcHeight
	 * @param dstWidth
	 * @param dstHeight
	 * @param scalingLogic
	 * @return
	 */
	public static int calculateSampleSize(int srcWidth, int srcHeight,
			int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
		if (scalingLogic == ScalingLogic.FIT) {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;
			if (srcAspect > dstAspect) {
				return srcWidth / dstWidth;
			} else {
				return srcHeight / dstHeight;
			}
		} else {
			final float srcAspect = (float) srcWidth / (float) srcHeight;
			final float dstAspect = (float) dstWidth / (float) dstHeight;
			if (srcAspect > dstAspect) {
				return srcHeight / dstHeight;
			} else {
				return srcWidth / dstWidth;
			}
		}
	}
	
	public static byte[] convertBitmap2Bytes(Bitmap bitmap){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		return bos.toByteArray();
	}
}
