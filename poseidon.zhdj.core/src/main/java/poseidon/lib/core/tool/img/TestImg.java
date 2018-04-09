package poseidon.lib.core.tool.img;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/1/13.
 */
public class TestImg {
    public static void main(String[] args) throws IOException {

        BigDecimal b1 = new BigDecimal(Double.toString(1));
        BigDecimal b2 = new BigDecimal(Double.toString(2));
        BigDecimal t = new BigDecimal(b1.add(b2).doubleValue());

       /* String filePath = "D:/mnt/poseidon/static/img/temp/ttt.jpg";
        //String filePath2 = "D:/mnt/poseidon/static/img/temp/ttt_2.jpg";
        Thumbnails.of(filePath).scale(0.5f).outputQuality(0.4f).toFile(filePath);*/

        //System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());

        /*try{
            Socket theSocket = new Socket("time.nist.gov", 13); //日期时间协议
            InputStream timeStream = theSocket.getInputStream();
            StringBuffer time = new StringBuffer();
            int c;
            while((c=timeStream.read()) != -1) time.append((char)c);
            String timeString = time.toString().trim();
            System.out.println("It is " + timeString + " at time.nist.gov");
        }catch(UnknownHostException ex){
            System.out.println(ex);
        }
        catch (IOException ex){
            System.out.println(ex);
        }*/


        /*PrintWriter out= null;
        BufferedReader networkIn = null;
        try{
            Socket theSocket = new Socket("localhost", 80); //echo协议
            networkIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));

            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(theSocket.getOutputStream());

            System.out.println("Connected to echo server");

            while(true){
                String theLine = userIn.readLine();
                if(theLine.equals(".")) break;
                out.println(theLine);
                out.flush();
                System.out.println("back from server" + networkIn.readLine());
            }
        }catch(UnknownHostException ex){
            System.out.println(ex);
        }
        catch (IOException ex){
            System.out.println(ex);
        }*/
    }
}
