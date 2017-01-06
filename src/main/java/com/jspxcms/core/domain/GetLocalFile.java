package com.jspxcms.core.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
 
public class GetLocalFile {
	public static String DIR_SQL="C:\\youli\\";
	static{
		Object dirTemp = System.getProperty("cbl.sqldir");
		if(dirTemp!=null&&!dirTemp.toString().trim().equals("")){
			 DIR_SQL = dirTemp.toString();
		}
	}
    
	//将文件所有多行内容组装成一个string，中间加上空格
	public static String readTxtFile(String filePath) {
		String returnStr = "";
		try {
			String encoding = "UTF-8";
//			filePath = GetLocalFile.class.getResource("/").toString().substring(6) + filePath;
			filePath = DIR_SQL + filePath;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = "";
				while ((lineTxt = bufferedReader.readLine()) != null) {
					returnStr = returnStr +" "+lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错"+filePath);
			e.printStackTrace();
		}
		return returnStr;
	}
	//将文件所有多行内容组装成一个string，中间不加上空格
		public static String readTxtFileNoSpace(String filePath) {
			String returnStr = "";
			try {
				String encoding = "UTF-8";
//				filePath = GetLocalFile.class.getResource("/").toString().substring(6) + filePath;
				filePath = DIR_SQL + filePath;
				File file = new File(filePath);
				if (file.isFile() && file.exists()) { // 判断文件是否存在
					InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = "";
					while ((lineTxt = bufferedReader.readLine()) != null) {
						returnStr = returnStr +lineTxt;
					}
					read.close();
				} else {
					System.out.println("找不到指定的文件"+filePath);
				}
			} catch (Exception e) {
				System.out.println("读取文件内容出错"+filePath);
				e.printStackTrace();
			}
			return returnStr;
		}
	//将文件所有多行内容组装成一个String List
		public static List<String> readTxtFileList(String filePath) {
			List<String> returnList = new ArrayList<String>();
			try {
				String encoding = "UTF-8";
//				filePath = GetLocalFile.class.getResource("/").toString().substring(6) + filePath;
				filePath = DIR_SQL + filePath;
				File file = new File(filePath);
				if (file.isFile() && file.exists()) { // 判断文件是否存在
					InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = "";
					while ((lineTxt = bufferedReader.readLine()) != null) {
						returnList.add(lineTxt);
					}
					read.close();
				} else {
					System.out.println("找不到指定的文件"+filePath);
				}
			} catch (Exception e) {
				System.out.println("读取文件内容出错"+filePath);
				e.printStackTrace();
			}
			return returnList;
		}
    public static void main(String argv[]){
        
        System.out.println(GetLocalFile.class.getResource("/"));
        String filePath = "sql/failurelist.txt";
        System.out.println(readTxtFileList(filePath));
        for(int i=0;i<readTxtFileList(filePath).size();i++){
        	System.out.println(readTxtFileList(filePath).get(i));
        }
    }
}
