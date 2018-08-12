package com.github.pgycode.a16.tool;

import android.graphics.Bitmap;
import android.text.TextUtils;


import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Hashtable;


public class QRHelper {
 
	public static String getReult(Bitmap mBitmap) {
		String string = null;
		if (mBitmap != null) {
			string = scanBitmap(mBitmap);
		}
		if (!TextUtils.isEmpty(string)) {
			return string;
		}
		return null;
	}
 
	private static String scanBitmap(Bitmap mBitmap) {
		Result result = scan(mBitmap);
		if (result != null) {
			return recode(result.toString());
		} else {
			return null;
		}
	}
 
	private static String recode(String str) {
		String formart = "";
		try {
			boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
					.canEncode(str);
			if (ISO) {
				formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
			} else {
				formart = str;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return formart;
	}
 
	private static Result scan(Bitmap mBitmap) {
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
		Bitmap scanBitmap = Bitmap.createBitmap(mBitmap);
 
		int px[] = new int[scanBitmap.getWidth() * scanBitmap.getHeight()];
		scanBitmap.getPixels(px, 0, scanBitmap.getWidth(), 0, 0,
				scanBitmap.getWidth(), scanBitmap.getHeight());
		RGBLuminanceSource source = new RGBLuminanceSource(
				scanBitmap.getWidth(), scanBitmap.getHeight(), px);
		BinaryBitmap tempBitmap = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		try {
			return reader.decode(tempBitmap, hints);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ChecksumException e) {
			e.printStackTrace();
		} catch (FormatException e) {
			e.printStackTrace();
		}
		return null;
	}
}