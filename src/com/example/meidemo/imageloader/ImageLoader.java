package com.example.meidemo.imageloader;


/**
 * 图片缓存：分为3级缓存</p>
 * 1、内存缓存，读取速度快，打算采用两层缓存：LruCache缓存和软引用
 * ，lruCache不会被轻易回收，存取常用数据,软引用存储图片。</p>
 * 2、本地文件缓存。将下载的文件缓存到本地。</p>
 * 3、网络请求获取
 * @author Administrator
 *
 */
public class ImageLoader {

}
