package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class Convert {
	/**
	 * 将Office文档转换为PDF 需要openoffice服务
	 * 需要配置String command ！！！
	 * @param destFile
	 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf
	 * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0,
	 *         则表示操作成功; 返回1, 则表示转换失败
	 */
	private String officeFile;
	private String pdfFile;
	private String swfFile;
	Convert(){}
	public Convert(String of,String pf,String sf)
	{
		officeFile=of;
		pdfFile=pf;
		swfFile=sf;
	}
	
	public int office2PDF() {
		try {
			File inputFile = new File(officeFile);
			if (!inputFile.exists()) {
				System.out.println("file not exists");
				return -1;// 找不到源文件, 则返回-1
			}

			// 如果目标路径不存在, 则新建该路径
			File outputFile = new File(pdfFile);
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			// 启动OpenOffice的服务
			String command = "/opt/openoffice4/program/soffice -headless -accept='socket,host=127.0.0.1,port=8100;urp;' -nofirststartwizard";
			Process pro = Runtime.getRuntime().exec(command);
			// 连接服务端口
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(
					"127.0.0.1", 8100);
			connection.connect();

			// convert
			DocumentConverter converter = new OpenOfficeDocumentConverter(
					connection);
			converter.convert(inputFile, outputFile);

			// close the connection
			connection.disconnect();
			// 关闭OpenOffice服务的进程
			pro.destroy();
			System.out.println("success");
			return 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("can't convert");
		return 1;
	}
	
	public int pdf2swf()
	{
		try {
			File inputFile = new File(pdfFile);
			if (!inputFile.exists()) {
				System.out.println("file not exists");
				return -1;// 找不到源文件, 则返回-1
			}

			// 如果目标路径不存在, 则新建该路径
			File outputFile = new File(swfFile);
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			// 启动pdf2swf
			//				  pdf2swf -s languagedir=/usr/local/xpdf-chinese-simplified -T 9 -s poly2bitmap -s zoom=150 -s flashversion=9 /root/Desktop/word2pdf.pdf -o /root/Desktop/pdf2swf.swf"
			String command = "pdf2swf -s languagedir=/usr/local/xpdf-chinese-simplified -T 9 -s poly2bitmap -s zoom=150 -s flashversion=9 "+ pdfFile+ " -o "+swfFile;
			Process pro = Runtime.getRuntime().exec(command);
			// 关闭pdf2swf服务的进程
			BufferedReader  br =  new  BufferedReader (new  InputStreamReader(pro.getInputStream()));  
			String msg = null;
			while  ((msg =  br .readLine())  !=  null)  {  
			     System.out.println(msg );  
			}  
			pro.destroy();
			System.out.println("swf2pdf success");
			return 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("can't convert pdf2swf");
		return 1;
	}
	 public static void main(String[] args) {
	 Convert c=new Convert("/root/Desktop/word.doc","/root/Desktop/word2pdf.pdf","/root/Desktop/pdf2swf.swf");
	 c.office2PDF();
	 c.pdf2swf();
//		 String command="pdf2swf -s languagedir=/usr/local/xpdf-chinese-simplified -T 9 -s poly2bitmap -s zoom=150 -s flashversion=9 /root/Desktop/word2pdf.pdf -o /root/Desktop/pdf2swf.swf";
//		 try {
//			Process pro = Runtime.getRuntime().exec(command);
//			
//			
//			BufferedReader  br =  new  BufferedReader (new  InputStreamReader(pro.getInputStream()));  
//			String msg = null;
//			while  ((msg =  br .readLine())  !=  null)  {  
//			     System.out.println(msg );  
//			}  
//			
//			pro.destroy();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//			// 关闭pdf2swf服务的进程
		
	 }
}
