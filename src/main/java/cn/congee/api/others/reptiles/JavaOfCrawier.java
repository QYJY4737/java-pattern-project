package cn.congee.api.others.reptiles;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬虫的基本流程
 *     1. 明确首页URL.
 *     2. 发送请求, 获取数据.
 *        方式1: 原生态的JDK方式, get请求.
 *        方式2: 原生态的JDK方式, post请求.
 *        方式3: 浏览器对象HttpClient, get请求.
 *        方式4: 浏览器对象HttpClient, post请求.
 *     3. 解析数据.
 *        Jsoup技术,  4种方式,  掌握第2种.
 *     4. 保存数据.
 *        JDBC
 *
 * @Author: yang
 * @Date: 2020-12-11 6:14
 */
@Slf4j
public class JavaOfCrawier {

    /**
     * 方式1: 原生态的JDK方式, get请求.
     */
    public static void originalJdkByGet(){
        try{
            //1. 明确首页URL.
            String indexUrl = "https://www.jd.com/";
            //2. 发送请求, 获取数据.
            //方式1: 原生态的JDK方式, get请求.
            //2.1 根据首页URL, 将其封装成URL对象.
            URL url = new URL(indexUrl);
            //2.2 发送请求, 并获取连接对象.
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //2.3 根据连接对象, 获取可以读取响应数据的 输入流对象.
            InputStream is = conn.getInputStream();
            //2.4 具体的读取数据的操作, IO流代码.
            int len;
            byte[] bys = new byte[8192];
            while ((len = is.read(bys)) !=-1){
                String s = new String(bys,0,len);
                System.out.println(s);
            }

            //释放资源
            is.close();
        }catch (Exception e){
            log.error("方式1爬取失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 方式2: 原生态的JDK方式, post请求.
     */
    public static void originalJdkByPost(){
        try{
            //1.明确首页的url地址
            String indexUrl = "https://www.jd.com/";
            //2. 发送请求, 获取数据.

            //方式2: 原生态的JDK方式, post请求.
            //2.1 根据首页URL, 将其封装成URL对象.
            URL url = new URL(indexUrl);
            // //2.2 发送请求, 并获取连接对象.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //2.3 设置请求方式为: post方式,  默认是: get方式, 所以可以不用设置.  大写post
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);     //原生态的JDK发送请求, 获取数据的方式, 输出流是禁用的, 需要手动开启.

            /**
             * 小结:
             * 当需要发送请求体的时候, 需要使用输出流来进行写出, 同时需要注意, 原生jdk,默认将输出流是关闭的
             * 需要获取响应体的时候, 需要使用输入流来获取
             */
            //获取输出流, 可以给服务器写参数.
            OutputStream os = conn.getOutputStream();
            os.write("username=root&password=123456".getBytes());

            //4.根据连接对象获取输入流
            InputStream is = conn.getInputStream();
            //5. 接下来具体的IO流操作
            int len;
            byte[] bys = new byte[8192];
            while ((len = is.read(bys)) != -1){
                System.out.println(new String(bys, 0, len));
            }

            //释放资源
            is.close();
            os.close();
        }catch (Exception e){
            log.error("方式2爬取失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 方式3: 浏览器对象HttpClient, get请求.
     */
    public static void httpclientByGet(){
        try{
            //1. 明确首页URL.
            String indexUrl = "https://www.jd.com/";

            //2. 发送请求, 获取数据.
            //2.1 获取HttpClient浏览器对象, 相当于创建一个浏览器.
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2.2 创建HttpGet对象, 封装的是 get请求方式所有要提交的数据.
            HttpGet httpGet = new HttpGet(indexUrl);
            //2.3 设置请求头, 即: 标明当前模拟的是什么浏览器.
            httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
            //2.4 通过浏览器对象HttpClient, 发送请求HttpGet, 获取响应对象. HttpResponse.
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            //2.5 判断响应状态是否成功, 即: 是否是200
            if (httpResponse.getStatusLine().getStatusCode() == 200) {

                //2.6 通过 响应对象获取 响应体数据, 并打印.
                HttpEntity entity = httpResponse.getEntity();

                //细节: 如果获取的是图片, 音频, 视频等,必须通过IO方式获取, 即: 如下方式
                //InputStream is = entity.getContent();

                //细节: 如果获取的是文本, 则有特殊方式, 如下.
                String s = EntityUtils.toString(entity, "utf-8");
                System.out.println(s);
            }
            //2.8 释放资源
            httpClient.close();

            //3. 解析数据.
            //4. 保存数据.
        }catch (ClientProtocolException cpe){
            log.error("方式3爬取失败: " + cpe.getMessage());
            cpe.printStackTrace();
        }catch (IOException io){
            log.error("方式3爬取失败: " + io.getMessage());
            io.printStackTrace();
        }
    }

    /**
     * 方式4: 浏览器对象HttpClient, post请求.
     */
    public static void httpclientByPost(){
        try{
            //1.明确首页url地址
            String indexUrl = "https://www.jd.com/";
            //2.发送请求  获取数据
            //方式4: 浏览器对象HttpClient, post方式        掌握
            //2.1 创建HttpClient对象,模拟浏览器.
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2.2 创建HttpPost对象, 封装的是所有的请求url地址信息.
            HttpPost httpPost = new HttpPost(indexUrl);
            //2.3 设置请求头, 模拟具体的浏览器, 谷歌, 火狐, IE......
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");

            //细节: 设置Post方式的 请求体对象, 封装具体的请求参数.
            //a. 创建List集合, 用来存储 具体的请求参数(BasicNameValuePair类型)
            List<BasicNameValuePair> list = new ArrayList<>();
            //b. 把具体的请求参数, 添加到List集合中.
            list.add(new BasicNameValuePair("username","liuafu"));
            list.add(new BasicNameValuePair("password","123456"));
            list.add(new BasicNameValuePair("age","23"));
            //c. 获取具体的请求体对象(经过URL编码的)
            HttpEntity requestEntity = new UrlEncodedFormEntity(list);
            //d. 把编码后的具体的请求体对象, 设置到 请求对象中.
            httpPost.setEntity(requestEntity);


            //2.4 发送请求, 获取数据(具体获取的是一个: Response响应对象, 包含响应行, 头, 体所有的信息)  浏览器发送请求 传入url信息
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //2.5 判断响应状态码, 看是否是200(表示响应成功)
            if (response.getStatusLine().getStatusCode() == 200){
                //2.7 获取响应体对象(这个才是我们具体要解析的数据)
                HttpEntity entity = response.getEntity();

                //2.8 从响应体对象中, 获取具体的 数据.
                //通用格式, 图片, 音频, 视频, 文本文档都行.
                //InputStream is = entity.getContent();
                //如果是纯文本数据, 可以通过如下的方式获取
                String html = EntityUtils.toString(entity, "utf-8");
                System.out.println(html);
            }

            //释放资源
            response.close();
            httpClient.close();
        }catch (UnsupportedEncodingException ue){
            log.error("方式4爬取失败: " + ue.getMessage());
            ue.printStackTrace();
        }catch (ClientProtocolException cpe){
            log.error("方式4爬取失败: " + cpe.getMessage());
            cpe.printStackTrace();
        }catch (IOException io){
            log.error("方式4爬取失败: " + io.getMessage());
            io.printStackTrace();
        }
    }

    /**
     * 演示通过Jsoup对象如何解析数据.
     * Jsoup解析数据的方式:
     *      解析数据的概述:
     *         对 html的数据进行解析操作, 从中找到需要进行保存数据的内容
     *      Jsoup解释:
     *         jsoup 是一款Java 的HTML解析器,可直接解析某个URL地址、HTML文本内容.
     * 		   它提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作来解析html操作
     *         具体的解析数据的方式:
     *             方式2: 将一个html的文本, 直接转换为document对象  (最常用的方式)
     *                 Jsoup.parse();
     *
     *     细节:
     *         使用Jsoup对象之前, 需要先导包.  pom依赖
     */
    public static void jsoup(){
        //1. 明确首页url地址
        String indexUrl = "https://www.jd.com/";

        //  发送请求, 获取数据, 只在测试中使用, 一般不直接来获取
        /*Document document1 = Jsoup.connect(indexUrl).get();
        System.out.println(document1);*/

        //使用jsoup 获取对应文档的document对象 (方式二)___将一个html的文本, 直接转换为document对象  (最常用的方式)
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>方式二:通过jsoup获取document对象</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    \n" +
                "</body>\n" +
                "</html>";

        Document document = Jsoup.parse(html);
        //输出头信息
        String title = document.title();
        System.out.println(title);
        //体信息
        //System.out.println(document.body());
    }

    /**
     * 案例: 爬取网易的某一篇新闻信息.
     * 网易新闻网址: news.163.com
     * 爬取的数据: 标题, 时间, 来源, 正文, 作者
     */
    public static void demo(){
        try{
            //1.明确首页url地址
            String indexUrl = "https://news.163.com/20/1121/01/FRU05J7T0001899O.html";

            //2. 发送请求,获取数据.

            //3. 解析数据.
            //3.1 获取要爬取的URL对应的 Document对象形式.
            Document document = Jsoup.connect(indexUrl).get();

            //3.2 解析: 标题
            Elements titel = document.select(".post_content_main h1");
            String text = titel.text();
            System.out.println("标题:" + text);


            //3.3 解析: 时间
            Elements time = document.select(".post_content_main > .post_time_source");
            String etime = time.text();     //2020-11-19 10:10:55　来源: 环球网 举报
            System.out.println("时间: " + etime.split("　")[0]);

            //3.4 解析: 来源
            String source = etime.split("　")[1].replace(" 举报", "");
            System.out.println(source);

            //3.5 解析: 正文
            Elements pelement = document.select("div[id='endText'][class='post_text'] p");
            for (Element element : pelement) {
                System.out.println(element.text());
            }

            //3.6 解析作者
            String author = document.select(".ep-editor").text();
            System.out.println(author);
        }catch (IOException io){
            log.error("爬取新闻失败: " +io.getMessage());
            io.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        jsoup();
        demo();
    }


}
