package utils;

public class FileUtil {
	
	//根据文件名称截取文件的类型    如.mp4 .jpg
	public static String getFileType(String fileName){
		if(ST.isNull(fileName)){
			return "";
		}
		int length = fileName.lastIndexOf(".");
		return fileName.substring(length + 1, fileName.length());
	}

	
	public static void main(String args[]){
		String fileName = "11111.jpg";
		String fileClass = FileUtil.getFileType(fileName);
		System.out.println(fileClass);
	}
}
