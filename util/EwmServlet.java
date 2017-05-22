package com.hztywl.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class EwmServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3610413495112338958L;

	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		String product_id = req.getParameter("pid");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appid", "wxe2479d0393ded535");
			map.put("mch_id", "1227745902");
			map.put("time_stamp", PayUtil.getTime_stamp());
			map.put("nonce_str", PayUtil.getRandomStringByLength(32));
			map.put("product_id", product_id);
			map.put("sign", PayUtil.getSign(map));
			String url = "weixin://wxpay/bizpayurl?appid="+map.get("appid")+"&mch_id="+map.get("mch_id")+"&nonce_str="+map.get("nonce_str")+"&product_id="+map.get("product_id")+"&time_stamp="+map.get("time_stamp")+"&sign="+map.get("sign");
			System.out.println(url);
			String text = url;
			if(null == text || text.length() ==0){
				resp.getWriter().print("error");
			}
			int width = 400;
			int height = 400;
			String format = "png";
			Hashtable hints= new Hashtable();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
			MatrixToImageWriter.writeToStream(bitMatrix, format, resp.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
