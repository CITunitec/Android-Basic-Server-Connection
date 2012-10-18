package com.conex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View;


public class AY003Activity extends Activity {
    /** Called when the activity is first created. */
	
	//Declare variables in the class scope. 
	EditText et;
	TextView tv1;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the .xml file with the content.
        setContentView(R.layout.conex);
        
        //Initialize buttons linking with .xml components
    	Button b1 = (Button)findViewById(R.id.button1);
        Button b2 = (Button)findViewById(R.id.button2);
        Button b3 = (Button)findViewById(R.id.button3);
        
        //Set the listener to each button.
        b1.setOnClickListener(myButtonListener);
        b2.setOnClickListener(myButtonListener);
        b3.setOnClickListener(myButtonListener);

        }
        
    private OnClickListener myButtonListener = new OnClickListener() {
    	
    	public void onClick(View v) {
    		 //Initialize other components from the .xml file
    		 EditText et = (EditText)findViewById(R.id.editText1);
    	     TextView tv1 = (TextView)findViewById(R.id.textView1);
    	    
    	     //According to which button, the URL of the required .php file changes.
    		String phpdoc = "";
    		//Server location
    		String IP = "server.address.net";
    		
          	switch(v.getId()) {
          		//Using the reference ids chosen in the .xml file
               case R.id.button1:
              	 phpdoc = IP+":8080/php/value.php";
              	 break;
               case R.id.button2:
                   phpdoc = IP+":8080/php/conexion.php";
                   break;
               case R.id.button3:
            	   phpdoc = IP+":8080/php/recibirpreventas.php";
                   break;
          	 }
    		 try{
                 httpclient=new DefaultHttpClient();	
                 httppost= new HttpPost("http://"+phpdoc); // Make sure the url is correct.
                 
                 //Initialize value pairs for data.
                  nameValuePairs = new ArrayList<NameValuePair>(1);
                  // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
              
                  // $Edittext_value = $_POST['Edittext_value'];
                  nameValuePairs.add(new BasicNameValuePair("Edittext_value",et.getText().toString().trim()));  
                  //Add your data.
                  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                  //Execute the request.
                  response=httpclient.execute(httppost);

                  //Retrieve the server response.
                  ResponseHandler<String> responseHandler = new BasicResponseHandler();
                  String response = httpclient.execute(httppost, responseHandler);
                  tv1.setText("Valor grabado en PHP : " + response);
      
              }catch (UnsupportedEncodingException e) {
            	  e.printStackTrace();
            	  tv1.setText("Unsupported Encoding : " + e.getMessage()+ ". PHP response: " +response);
              } catch (ClientProtocolException e) {
                  tv1.setText("Client Protocol : " + e.getMessage()+ ". PHP response: " +response);
            	  e.printStackTrace();
              } catch(UnknownHostException e){
                  tv1.setText("Server not found : " + e.getMessage()+ ". PHP response: " +response);
            	  e.printStackTrace();
              } catch (ConnectTimeoutException e){
                  tv1.setText("Connection timeout : " + e.getMessage()+ ". PHP response : " +response);
            	  e.printStackTrace();
              }catch (IOException e) {
            	  e.printStackTrace();
                  tv1.setText("Input/Output Exception : " + e.getMessage()+ ". PHP response : " +response);
              }

           }
    		
    	};
    	

    }
