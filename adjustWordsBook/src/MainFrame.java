import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainFrame{
    
    public static void main(String args[]) throws IOException{
    	//每种编码的乱码形式不一样，utf8字符占用字节从1-4byte，稍微要复杂一些
    	File wordFile = new File("utf8_words.txt");
    	InputStream is = new FileInputStream(wordFile);
    	final int BYTE_LEN = 45;
    	byte[] bytes = new byte[BYTE_LEN];
    	int bjishutmp = 0;
    	byte readbyte;
    	while((readbyte = (byte)is.read()) != -1){
    		bytes[bjishutmp++] = readbyte;
    		//解决回车问题
    		if(readbyte == 10){
    			if(bjishutmp == 1){
    				System.out.println("");
    			}else if(bytes[bjishutmp - 2] != 10){    				
    				System.out.println(new String(Arrays.copyOfRange(bytes, 0, bjishutmp)));
    			}else{
    				System.out.println();
    			}
    			bjishutmp = 0;
    			continue;
    		}
    		
    		if(bjishutmp == BYTE_LEN){
    			//如果最后一个字符解析出来是乱码，则看前面几个解析是不是乱码，一直可往前推4个
    			int checkLMPos = BYTE_LEN;
    			String tc = null;
    			do{
    				String tmpStr = new String(Arrays.copyOfRange(bytes, 0, checkLMPos));
    				tc = ""+tmpStr.charAt(tmpStr.length() - 1);
    				checkLMPos--;
    			}while("�".equals(tc));
    			checkLMPos++;
    			if(checkLMPos == BYTE_LEN){
    				System.out.println(new String(bytes));
    				bjishutmp = 0;
    			}else{
    				System.out.println(new String(Arrays.copyOfRange(bytes, 0, checkLMPos)));
    				for(int i = 0; i < BYTE_LEN - checkLMPos; i++){
    					bytes[i] = bytes[checkLMPos + i];
    				}
    				bjishutmp = BYTE_LEN - checkLMPos;
    			}
    		}
    	}
    	System.out.println(new String(bytes,0,bjishutmp));
    }
    
    public static void clearBytes(byte[] bytes){
    	for(byte b : bytes){
    		b = 0;
    	}
    }
}
