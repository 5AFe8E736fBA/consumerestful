package w3102931.example.com.consumerestful;


import android.os.AsyncTask;
import android.os.Bundle;
//import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

//import w3102931.example.com.consumerestful.R;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // declare private variables
    private Button btnGet;
    private Button btnPut;
    private Button btnPost;
    private Button btnDelete;

    private TextView txtResult;
    private TextView txtUserId;
    private TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize variables
        txtResult  = (TextView) findViewById(R.id.txtResult);
        txtUserId  = (TextView) findViewById(R.id.txtUserId);
        txtMessage = (TextView) findViewById(R.id.txtMessage);

        btnGet    = (Button) findViewById(R.id.btnGET);
        btnPut    = (Button) findViewById(R.id.btnPUT);
        btnPost   = (Button) findViewById(R.id.btnPOST);
        btnDelete = (Button) findViewById(R.id.btnDELETE);

        // set up buttons
        btnGet.setOnClickListener(this);
        btnPut.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    // A method for setting all button clickable properties to true or false
    private void buttonClickable(boolean b) {
        btnGet.setClickable(b);
        btnPut.setClickable(b);
        btnPost.setClickable(b);
        btnDelete.setClickable(b);
    }

    // Override OnClickListener method to handle button clicks
    @Override
    public void onClick(View v) {

        buttonClickable(false);

        // get input from user as strings
        String strId = txtUserId.getText().toString();
        String message = txtMessage.getText().toString();

        // determine which button was clicked
        // in each case, create a new AsyncTask (Thread) and execute it
        // this will do an http request (GET, PUT, POST, or DELETE) on a separate thread
        switch (v.getId()){
            case R.id.btnGET:
                new HTTPRequestHandler().execute("GET", strId);
                break;

            case R.id.btnPUT:
                new HTTPRequestHandler().execute("PUT", strId, message);
                break;

            case R.id.btnPOST:
                new HTTPRequestHandler().execute("POST", strId, message);
                break;

            case R.id.btnDELETE:
                new HTTPRequestHandler().execute("DELETE", strId);
                break;
        }

    }

    // A class defining methods to handle http requests on a new thread
    private class HTTPRequestHandler extends AsyncTask<String, Void, String> {

        // create private data member
        private HttpClient httpClient = new DefaultHttpClient();
        private String baseURL = "http://10.0.2.2:8080/service/webapi/messages";

        // a method to convert an entity to a string of ascii characters for output
        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();

            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n>0) {
                byte[] b = new byte[4096];
                n =  in.read(b);
                if (n>0) out.append(new String(b, 0, n));
            }

            return out.toString();
        }

        // this method is similar to the run method of the Thread class in Java.util
        @Override
        protected String doInBackground(String... params) {

            if (params.length < 1)
                return "";


            Message message;
            String responseString = "";

            // create a Gson object for converting to/from json format
            Gson gson = new Gson();

            try {

                //hardcode username 'user' and password 'password
                //String auth = "user:password"; //authentication
                //auth = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP); //authentication
                //auth = "Basic" + auth; //authentication

                // handle request
                switch (params[0]) {
                    case "GET":

                        // set up and execute a get request
                        HttpGet httpGet = new HttpGet(baseURL + "/" + params[1]);

                        //set basic authentication
                        //httpGet.setHeader(H)//authentication
                        HttpResponse response = httpClient.execute(httpGet);
                        HttpEntity entity = response.getEntity();

                        if (entity == null)
                            return "";
                        responseString = getASCIIContentFromEntity(entity);

                        break;

                    case "POST":
                        if (params.length < 2)
                            return "";

                        // set up and execute a post request
                        HttpPost post = new HttpPost(baseURL);

                        message = new Message();
                        //Lab 4 POST method starts here

                        message.setId(Long.parseLong(params[1]));
                        message.setLatitude(Double.parseDouble(params[2]));
                        message.setLongitude(Double.parseDouble(params[3]));
                        message.setTimestamp(Long.parseLong(params[4]), System.currentTimeMillis());


                        // create a post message. Note that Gson is being used to convert a message object to json
                        StringEntity postString = new StringEntity(gson.toJson(message));
                        post.setEntity(postString);
                        post.setHeader("Content-type", "application/json");
                        response = httpClient.execute(post);
                        entity = response.getEntity();

                        if (entity == null)
                            return "";
                        responseString = getASCIIContentFromEntity(entity);
                        break;


                    case "PUT":
                        if (params.length < 2)
                            return "";

                        // set up and execute a put request
                        HttpPut put = new HttpPut(baseURL + "/" + params[1]);
                        StringEntity putString = null;

                        message = new Message();
                        //Lab 4 PUT method starts here
                        message.setId(Long.parseLong(params[1]));
                        message.setLatitude(Double.parseDouble(params[2]));
                        message.setLongitude(Double.parseDouble(params[3]));

                        putString = new StringEntity(gson.toJson(message));

                        put.setEntity(putString);
                        put.setHeader("Content-type", "application/json");
                        response = httpClient.execute(put);
                        entity = response.getEntity();

                        if (entity == null)
                            return "";
                        responseString = getASCIIContentFromEntity(entity);
                        break;

                    case "DELETE":
                        HttpDelete delete = new HttpDelete(baseURL + "/" + params[1]);
                        response = httpClient.execute(delete);
                        entity = response.getEntity();
                        if (entity == null)
                            return "";
                        responseString = getASCIIContentFromEntity(entity);
                        break;
                }

            } catch (Exception e) {
                return e.getLocalizedMessage();
            }

            return responseString;
        }

        // this method is called after the background task is finished
        @Override
        protected void onPostExecute(String results) {

            // output the response from the http request
            if (results!=null) {
                txtResult.setText(results);
            }

            buttonClickable(true);
        }


    }

}