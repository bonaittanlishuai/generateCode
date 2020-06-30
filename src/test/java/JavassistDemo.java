import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;

/**
 * @Description
 * @Author tanlishuai
 * @Date 2020-06-29 8:49
 */
public class JavassistDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        path1+="SystemEnum.class";
//        //当前项目下路径
//        File file = new File("");
//        String filePath = file.getCanonicalPath();
//        System.err.println(filePath);
        File javaFile = new File("E:\\work\\com\\mt\\newframework\\enums\\test.java");
       byte [] bytes=new byte[(int)javaFile.length()];
        char[] chars = new char[(int)javaFile.length()];
        FileInputStream fileInputStream = new FileInputStream(javaFile);
        fileInputStream.read(bytes);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        inputStreamReader.read(chars);

        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:\\work\\com\\mt\\newframework\\enums\\test1.java"));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(chars);


        JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = systemJavaCompiler.getStandardFileManager(null,null,null);
        Iterable<? extends JavaFileObject> units = standardFileManager.getJavaFileObjects(javaFile);
        systemJavaCompiler.run(null,null,null,"E:\\work\\com\\mt\\newframework\\enums\\test.java");
//        JavaCompiler.CompilationTask task = systemJavaCompiler.getTask(outputStreamWriter, standardFileManager, null, null, null, units);
//        boolean result = task.call();

        String path = JavassistDemo.class.getClassLoader().getResource("").getPath();
        System.err.println(path);
        sun.tools.java.ClassPath classPath = new sun.tools.java.ClassPath("E:\\work\\com\\mt\\newframework\\enums\\test");
       // ClassPathLoader classPathLoader = new ClassPathLoader(classPath);
        ClassLoader classPathLoader = new MyClassPathLoader("E:\\work\\");
        Class<?> aClass = classPathLoader.loadClass("com.mt.newframework.enums.test");
        System.err.println(aClass.getName());

//        new ByteArrayInputStream()

    }
}
