package com.example.meidemo.imageloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class FileCache {
	private static final String FILE_CACHE = "fileCache/ImageCache";
	private static final String WHOLESALE_CONV = "cache";
	private static final int MB = 1024*1024;
	private static final int CACHE_SIZE = 10;
	private static final int FREESPACE_SIZE_ONSD = 10;
	Context context;

	public FileCache(Context context) {
		this.context = context;
	}

	public Bitmap getBitmap(String url) {
		String filePath = getCacheFilePath() + File.separator + convertUrlToFileName(url);
		File file = new File(filePath);
		if (file.exists()) {
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
				if (bitmap==null) {
					file.delete();
				}else {
					updateFilePath(filePath);
					return bitmap;
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void saveFileCache(Bitmap btm,String url){
//		File file = new File(getCacheFilePath()+File.separator+convertUrlToFileName(url));
		if (btm==null) {
			return;
		}
		if (CACHE_SIZE > freeSpaceOnSd()) {
			return;
		}
		String fileName = convertUrlToFileName(url);
		String dir = getCacheFilePath();
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File file = new File(dirFile + File.separator+fileName);
		try {
			file.createNewFile();
			OutputStream os = new FileOutputStream(file);
			btm.compress(Bitmap.CompressFormat.JPEG, 100, os);
			os.flush();
			os.close();
		}catch(FileNotFoundException e){
			Log.i("FileCache", "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i("FileCache", "IOException");
			e.printStackTrace();
		}
	}

	private int freeSpaceOnSd(){
		StatFs fs = new StatFs(Environment.getExternalStorageDirectory().getPath());
		int freeMB = (int) (((double)fs.getAvailableBlocks()*fs.getBlockSize())/MB);
		return freeMB;
	}
	/** 将url转成文件名 **/
    private String convertUrlToFileName(String url) {
        String[] strs = url.split("/");
        return strs[strs.length - 1] + WHOLESALE_CONV;
    }
    
	/** 获取缓存目录路径 */
    public String getCacheFilePath(){
    	return getSdDir() + File.separator + FILE_CACHE;
    }
    /**获取SD卡路径*/
	public String getSdDir() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
		}
		if (sdDir != null) {
			return sdDir.toString();
		} else {
			return "";
		}
	}
	
	/**更新文件修改时间*/
	private void updateFilePath(String path) {
		File file = new File(path);
		file.setLastModified(System.currentTimeMillis());
	}
}
