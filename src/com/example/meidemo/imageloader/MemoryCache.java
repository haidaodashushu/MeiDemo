package com.example.meidemo.imageloader;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoryCache {
	/**lruCache使用的缓存，为可用缓存的1/4*/
	private int maxSizeLru = 0;
	private static final int SOFT_CACHE_SIZE = 15;//软引用缓存容量
	private static LruCache<String, Bitmap> lruCache;
	private static LinkedHashMap<String, SoftReference<Bitmap>> softCache;
	Context context;
	public MemoryCache(Context context) {
		int currentMem = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();//获取允许每个应用可以使用的内存
		maxSizeLru = 1024*1024*currentMem/4;
		lruCache = new LruCache<String, Bitmap>(maxSizeLru){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				if (value!=null) {
					return value.getRowBytes()*value.getHeight();//计算图片的大小
				}
				return 0;
			}
			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				// TODO Auto-generated method stub
				super.entryRemoved(evicted, key, oldValue, newValue);
				//lruCache缓存满的时候，会根据LRU算法把最近最少使用的图片使用软引用缓存
				softCache.put(key, new SoftReference<Bitmap>(oldValue));
			}
		};
		
		softCache = new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_SIZE,0.75f,true){
			private static final long serialVersionUID = 1L;
			@Override
			protected boolean removeEldestEntry(
					java.util.Map.Entry<String, SoftReference<Bitmap>> eldest) {
				if (size()>SOFT_CACHE_SIZE) {
					return true;
				}
				return false;
			}
		};
		
	}
	
	public Bitmap getMemoryCache(String url){
		synchronized (lruCache) {
			Bitmap bitmap = lruCache.get(url);
			if (bitmap!=null) {
				//如果存在，则重新加载
				lruCache.remove(url);
				lruCache.put(url, bitmap);
				return bitmap;
			}else {
				lruCache.remove(url);
			}
		}
		
		synchronized (softCache) {
			Bitmap bitmap = softCache.get(url).get();
			if (bitmap !=null) {
				lruCache.put(url, bitmap);
				softCache.remove(url);
				return bitmap;
			}else{
				softCache.remove(url);
			}
		}
		return null;
	}
	
}
