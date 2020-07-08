import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-29 9:42
 */
public class MyClassPathLoader extends ClassLoader {
    private String rootDir;

    public MyClassPathLoader(String rootDir){
        super(null);
        this.rootDir=rootDir;
    }

    public Class findClass(String var1) throws ClassNotFoundException {
        byte[] var2 = this.loadClassData(var1);
        return this.defineClass(var1, var2, 0, var2.length);
    }

    private byte[] loadClassData(String var1)  {
        FileInputStream fileInputStream=null;
        try {
            String fileName = var1.replace('.', File.separatorChar) + ".class";
            File file = new File(rootDir + fileName);
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            byteArrayInputStream.read();
            fileInputStream.read(bytes);
            return bytes;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
       // ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream();
    }

}
