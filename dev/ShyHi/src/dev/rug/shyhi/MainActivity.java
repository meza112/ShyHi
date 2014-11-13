package dev.rug.shyhi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ShyHiDBConnect shyHiDbConn = new ShyHiDBConnect();
        new fetchJSON().execute();
    }
  //helper method, should be seperated into own .java file 
    public String getJSON(String address){
    	StringBuilder builder = new StringBuilder();
    	HttpClient client = new DefaultHttpClient();
    	HttpGet httpGet = new HttpGet(address);
    	try{
    		HttpResponse response = client.execute(httpGet);
    		StatusLine statusLine = response.getStatusLine();
    		int statusCode = statusLine.getStatusCode();
    		if(statusCode == 200){
    			HttpEntity entity = response.getEntity();
    			InputStream content = entity.getContent();
    			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
    			String line;
    			while((line = reader.readLine()) != null){
    				builder.append(line);
    			}
    		} else {
    			Log.e(MainActivity.class.toString(),"Failed to get JSON object");
    		}
    	}catch(ClientProtocolException e){
    		e.printStackTrace();
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    	return builder.toString();
    }
    //simple tester class
    private class fetchJSON extends AsyncTask<URL, Integer, String> {
        protected String doInBackground(URL... urls) {
        	String readJSON = getJSON("http://104.236.22.60:5984/shyhi/_design/messages/_view/getMsg?key=%22user1%22");
            return readJSON;
        }
        
        @Override
        protected void onPostExecute(String result) {
        	TextView tv = (TextView) findViewById(R.id.textView1);
        	//tv.setText(result);
        	try {
				JSONObject json = new JSONObject(result);
				tv.setText(json.getJSONArray("rows").toString(1));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
  
    }
    
}
