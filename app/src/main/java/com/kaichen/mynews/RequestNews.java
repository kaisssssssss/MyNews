package com.kaichen.mynews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kai on 1/21/2018.
 */

public class RequestNews extends AsyncTask<Void, Void, ArrayList<NewsItem>> {

    private NewsAdapter adapter;
    String apiURL;

    public RequestNews(NewsAdapter adapter, String apiUrl) {
        this.adapter = adapter;
        this.apiURL = apiUrl;
    }

    @Override
    protected ArrayList<NewsItem> doInBackground(Void... voids) {
        URL url = createUrl(apiURL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
            Log.i("RequestNews: ", "Made http request");
        } catch (IOException e) {
            // TODO Handle the IOException
            Log.i("RequestNews: ", "Failed to make http reques");
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<NewsItem> res = extractFeatureFromJson(jsonResponse);

        return res;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsItem> newsItems) {
        adapter.clear();
        adapter.addAll(newsItems);
    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("Create URL: ", "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private ArrayList<NewsItem> extractFeatureFromJson(String earthquakeJSON) {
        ArrayList<NewsItem> res = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = baseJsonResponse.getJSONObject("response").getJSONArray("results");

            for(int i = 0; i < featureArray.length(); i++){
                JSONObject feature = featureArray.getJSONObject(i);
                String date = feature.getString("webPublicationDate");
                String section = feature.getString("sectionName");
                String title = feature.getString("webTitle");
                String url = feature.getString("webUrl");
                Bitmap image = getImage(url);
                if(date.length() > 10)
                    date = date.substring(0, 10);

                NewsItem news = new NewsItem(title, section, "0", url, date, image);
                news.setBody(getNewsBody(url));

                res.add(news);
            }

            return res;
        } catch (JSONException e) {
            Log.e("Extract Jsone: ", "Problem parsing the earthquake JSON results", e);
        }
        return res;
    }

    private Bitmap getImage(String url){
        Bitmap mIcon11 = null;

        try{
            Document doc = Jsoup.connect(url).get();

            String imageURL = doc.select("img[class=\"maxed responsive-img\"]").attr("src");

            Log.i("image url", imageURL);
            String urldisplay = imageURL;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }catch (IOException e){
            Log.i("Get Image ", "Fail to get Image");
        }
        return null;
    }

    private String getNewsBody(String url){
        StringBuilder body = new StringBuilder();

        try{
            Document doc = Jsoup.connect(url).get();

            List<String> paras = doc.select("p").eachText();
            for(String str: paras){
                if(str.contains("pm EST ")) continue;
                body.append(str);
                body.append("\n\n");
            }

            return body.toString();
        }catch (IOException e){
            Log.i("Get Image ", "Fail to get Image");
        }
        return body.toString();
    }
}
