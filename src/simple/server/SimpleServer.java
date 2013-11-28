
package simple.server;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * Developed by North People for North People
 * All you have to do is run our super browser and type : http://localhost:8000/test1.html or test.html or test2.html or you can eneter any web site oyu want
 * but it will be weird :)
 * @author devino
 */
public class SimpleServer {

    static List<File> HtmlFiles;
    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws Exception {

    HtmlFiles = new ArrayList<File>();
    
    String rootPath = "./html_pages";
    File[] domain = new File(rootPath).listFiles();
    
    for (File file : domain) 
    {
        if (file.isFile() && file.getAbsolutePath().endsWith(".html")) 
        {
            HtmlFiles.add(file);
        }
    }
    
    HtmlFiles.get(0).getName();
    
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        for(File file : HtmlFiles)
        {
            server.createContext("/"+file.getName(), new MyHandler(file));
        }
        /*server.createContext("/test", new MyHandler());
        server.createContext("/test1", new MyHandler1());*/
        server.setExecutor(null); // creates a default executor
        server.start();
    }
   
   static class MyHandler implements HttpHandler {
       
       File page;
       
       public MyHandler(File filePage)
       {
           page=filePage;
       }
       
       static String readFile(String path, Charset encoding) throws IOException 
      {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
      }
       
        public void handle(HttpExchange t) throws IOException {
            String response = readFile(page.getAbsolutePath(),Charset.defaultCharset());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
