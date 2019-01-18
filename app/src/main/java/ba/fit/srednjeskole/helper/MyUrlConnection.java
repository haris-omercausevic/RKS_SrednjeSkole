package ba.fit.srednjeskole.helper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyUrlConnection {
    public enum HttpMethod{
        GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE
    }

    private static  final String TAG = "MyUrlConnection";
    public static MyApiResult request(String urlString, HttpMethod httpMethod, String postData, String contentType){
        HttpURLConnection connection = null;
        String charset = "UTF-8";
        MyApiResult result = null;

        try{
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);

            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty("Accept", contentType);
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestMethod(httpMethod.toString());

            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.setDoOutput(false);

            // Send the post body
            if(postData != null){
                connection.setDoOutput(true);
                byte[] outputBytes = postData.getBytes(charset);
                OutputStream os = connection.getOutputStream();
                os.write(outputBytes);
                os.flush();
                os.close();
            }

            int statusCode = connection.getResponseCode();
            if(statusCode == 200)
            {
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                String response = convertToString(inputStream);
                return MyApiResult.OK(response);
            }
            else{
                InputStream inputStream = new BufferedInputStream(connection.getErrorStream());
                String response = convertToString(inputStream);
                return MyApiResult.Error(statusCode, response);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return MyApiResult.Error(0, e.getMessage());
        }
        finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

    private static String convertToString(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                response.append(line);
                //response.append(line + "\n");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (reader != null){
                try {
                    reader.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
