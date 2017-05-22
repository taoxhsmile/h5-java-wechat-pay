package com.hztywl.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class PayUtil {
	public static String getTime_stamp(){
		String stamp="1472275204";
		/*Date d=new Date();
		d.getTime();*/
		return stamp;
	
	}
	public static String getRandomStringByLength(int len){
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
	}
	public static String getSign(Map<String, Object> map){
		ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!="" && !entry.toString().equals("return_code") && !entry.toString().equals("return_msg") && !entry.toString().equals("result_code")){
            	System.out.println();
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        
        String result = sb.toString();
        result += "key=73baf0c0c55c26f7c8a11202d3e4926d";
        //Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
	}
	
}
