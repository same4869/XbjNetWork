//package com.xbjnw.wangxun.xbjnetwork.web;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.xbjnw.wangxun.xbjnetwork.BBLog;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * 图片加载器
// *
// * @author Lijj
// *
// */
//public class WenbaImageLoader {
//	private static WenbaImageLoader mInstance;
//
//	private final int DEFAULT_WIDTH = 720;// 默认的图片宽度
//	private final int DEFAULT_HEIGHT = 1280;// 默认的图片高度
//
//	public static WenbaImageLoader getInstance(Context context) {
//		if (mInstance == null) {
//			synchronized (WenbaImageLoader.class) {
//				if (mInstance == null) {
//					mInstance = new WenbaImageLoader(context);
//				}
//			}
//		}
//		return mInstance;
//	}
//
//	private WenbaImageLoader(Context context) {
//		int memory = AppInfoUtils.getAppMemory(context);
//		BBLog.d("ljj", "memory : "+memory+"M");
//		int memoryCacheSize = memory * 1024 * 1024 / 10;
//		BBLog.d("ljj", "memoryCacheSize : "+memoryCacheSize/1024/1024+"M");
//
//		// 初始化ImageLoader，配置各个参数
//		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
//		builder.diskCacheExtraOptions(480, 800, null)
//				.denyCacheImageMultipleSizesInMemory()
//				.diskCache(new UnlimitedDiskCache(new File(CacheStoreUtil.getImagesDir(context).getAbsolutePath()), null, new Md5FileNameGenerator()))
//				.diskCacheSize(100 * 1024 * 1024)
//				.memoryCache(new LruMemoryCache(memoryCacheSize))
//				.memoryCacheSize(memoryCacheSize)
//				.tasksProcessingOrder(QueueProcessingType.FIFO)
//				.threadPriority(Thread.NORM_PRIORITY - 1);
//
//		if(Constants.IS_DEBUG){
//			builder.writeDebugLogs();
//		}
//
//		ImageLoader.getInstance().init(builder.build());
//	};
//
//	/**
//	 * 获得ImageLoader 对象
//	 *
//	 * @return
//	 */
//	private ImageLoader getImageLoader() {
//		return ImageLoader.getInstance();
//	}
//
//	/**
//	 * 获得默认的DisplayImageOptions
//	 *
//	 * @return
//	 */
//	private DisplayImageOptions getDefaultOptions(int defaultResId, boolean cacheOnDisk) {
//		return new DisplayImageOptions.Builder().showImageForEmptyUri(defaultResId).showImageOnLoading(defaultResId).showImageOnFail(defaultResId)
//				.cacheInMemory(true).cacheOnDisk(cacheOnDisk).bitmapConfig(Bitmap.Config.RGB_565).build();
//	}
//
//	private boolean isCacheOnDisk(String uri) {
//		switch (Scheme.ofUri(uri)) {
//		case HTTP:
//		case HTTPS:
//			return true;
//		case CONTENT:
//		case FILE:
//		case ASSETS:
//		case DRAWABLE:
//		case UNKNOWN:
//		default:
//			return false;
//		}
//	}
//
//	/**
//	 * 加载url显示图片到ImageView，支持网络或本地sd卡图片
//	 *
//	 * @param uri
//	 * @param imageView
//	 * @param defaultResId
//	 */
//	public void displayImage(String uri, ImageView imageView, int defaultResId) {
//		if (uri != null && uri.startsWith("/")) {
//			uri = Scheme.FILE.wrap(uri);
//		}
//		getImageLoader().displayImage(uri, imageView, getDefaultOptions(defaultResId, isCacheOnDisk(uri)));
//	}
//
//	/**
//	 * 加载url显示图片到ImageView，支持网络或本地sd卡图片
//	 *
//	 * @param uri
//	 * @param imageView
//	 */
//	public void displayImage(String uri, ImageView imageView) {
//		displayImage(uri, imageView, R.mipmap.comm_feedlist_thumbnail_default);
//	}
//
//	/**
//	 * 从Content provider中加载图片显示到ImageView
//	 *
//	 * @param uri
//	 * @param imageView
//	 */
//	public void displayImageByContentProvider(String uri, ImageView imageView) {
//		getImageLoader().displayImage(uri, imageView, getDefaultOptions(R.mipmap.comm_feedlist_thumbnail_default, false));
//	}
//
//	/**
//	 * 根据uri，通过接口回调加载图片
//	 *
//	 * @param uri
//	 * @param width
//	 * @param height
//	 * @param listener
//	 */
//	public void loadImage(String uri, int width, int height, final WenbaImageLoadingListener listener) {
//		if (uri != null && uri.startsWith("/")) {
//			uri = Scheme.FILE.wrap(uri);
//		}
//		Log.d("kkkkkkkk", "loadImage --> uri --> " + uri + " isCacheOnDisk(uri) --> " + isCacheOnDisk(uri));
//		getImageLoader().loadImage(uri, new ImageSize(width, height), getDefaultOptions(R.mipmap.comm_feedlist_thumbnail_default, isCacheOnDisk(uri)),
//				new ImageLoadingListener() {
//
//					@Override
//					public void onLoadingStarted(String paramString, View paramView) {
//						if (null != listener) {
//							listener.onLoadingStarted(paramString, paramView);
//						}
//					}
//
//					@Override
//					public void onLoadingFailed(String paramString, View paramView, FailReason paramFailReason) {
//						if (null != listener) {
//							listener.onLoadingFailed(paramString, paramView);
//						}
//					}
//
//					@Override
//					public void onLoadingComplete(String paramString, View paramView, Bitmap paramBitmap) {
//						if (null != listener) {
//							listener.onLoadingComplete(paramString, paramView, paramBitmap);
//						}
//					}
//
//					@Override
//					public void onLoadingCancelled(String paramString, View paramView) {
//						if (null != listener) {
//							listener.onLoadingCancelled(paramString, paramView);
//						}
//					}
//				});
//	}
//
//	/**
//	 * 根据uri，通过接口回调加载图片
//	 *
//	 * @param uri
//	 * @param listener
//	 */
//	public void loadImage(String uri, final WenbaImageLoadingListener listener) {
//		loadImage(uri, DEFAULT_WIDTH, DEFAULT_HEIGHT, listener);
//	}
//
//	/**
//	 * 从Assets中加载图片显示到ImageView
//	 *
//	 * @param uri
//	 * @param imageView
//	 */
//	public void displayImageByAssets(String uri, ImageView imageView) {
//		uri = Scheme.ASSETS.wrap(uri);
//
//		getImageLoader().displayImage(uri, imageView);
//	}
//
//	/**
//	 * 根据key值检查对应的文件是否在sd卡存在
//	 *
//	 * @param key
//	 * @return
//	 */
//	public boolean isDiskCache(String key) {
//		if (key == null) {
//			return false;
//		}
//		DiskCache diskCache = getImageLoader().getDiskCache();
//		File file = diskCache.get(key);
//		if (file != null && file.isFile() && file.exists()) {
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * 根据key值获取对应sd卡的图片
//	 *
//	 * @param key
//	 * @return
//	 */
//	public Bitmap getDiskCache(String key) {
//		if (key == null) {
//			return null;
//		}
//		DiskCache diskCache = getImageLoader().getDiskCache();
//		File file = diskCache.get(key);
//		if (file != null && file.isFile() && file.exists()) {
//			return BitmapFactory.decodeFile(file.getAbsolutePath());
//		}
//		return null;
//	}
//
//	/**
//	 * 根据key值保存bitmap到sd卡中
//	 *
//	 * @param key
//	 * @param bitmap
//	 * @return
//	 */
//	public boolean saveBitmapToDiskCache(String key, Bitmap bitmap) {
//		if(key == null || bitmap == null) {
//			return false;
//		}
//
//		if(bitmap.isRecycled()){
//			return false;
//		}
//
//		DiskCache diskCache = getImageLoader().getDiskCache();
//		try {
//			return diskCache.save(key, bitmap);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	/**
//	 * 根据key值清理sd卡对应的文件
//	 *
//	 * @param key
//	 * @return
//	 */
//	public boolean clearDiskCache(String key) {
//		if (key == null) {
//			return false;
//		}
//		DiskCache diskCache = getImageLoader().getDiskCache();
//		return diskCache.remove(key);
//	}
//
//	/**
//	 * 清理sd缓存文件
//	 */
//	public void clearDiskCache() {
//		getImageLoader().clearDiskCache();
//	}
//
//	/**
//	 * 清理内存缓存文件
//	 */
//	public void clearMemoryCache() {
//		getImageLoader().clearMemoryCache();
//	}
//
//	/**
//	 * 销毁Imageloader
//	 */
//	public void destroy() {
//		getImageLoader().destroy();
//		mInstance = null;
//	}
//
//	class Md5FileNameGenerator implements FileNameGenerator {
//
//		@Override
//		public String generate(String path) {
//			return StringUtil.md5(path);
//		}
//
//	}
//
//	public static abstract class WenbaImageLoadingListener {
//		public void onLoadingStarted(String uri, View imageView) {
//		};
//
//		public void onLoadingFailed(String uri, View imageView) {
//		};
//
//		public abstract void onLoadingComplete(String uri, View imageView, Bitmap bitmap);
//
//		public void onLoadingCancelled(String uri, View imageView) {
//		};
//	}
//
//}
