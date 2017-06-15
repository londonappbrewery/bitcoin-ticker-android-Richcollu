package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String AUD_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCAUD";
    private final String BRL_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCBRL";
    private final String CAD_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCCAD";
    private final String CNY_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCCNY";
    private final String EUR_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCEUR";
    private final String GBP_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCGBP";
    private final String HKD_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCHKD";
    private final String JPY_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCJPY";
    private final String PLN_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCPLN";
    private final String RUB_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCRUB";
    private final String SEK_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCSEK";
    private final String USD_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCUSD";
    private final String ZAR_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCZAR";

    private final String[] ARRAY = {AUD_URL, BRL_URL, CAD_URL, CNY_URL, EUR_URL, GBP_URL, HKD_URL, JPY_URL, PLN_URL, RUB_URL, SEK_URL, USD_URL,ZAR_URL};



    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Bitcoin", "" + parent.getItemAtPosition(position));
                letsDoSomeNetworking(ARRAY[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin", "Nothing selected");
            }

        });



    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON: " + response.toString());
                bitcoinData newData = bitcoinData.fromJson(response);
                mPriceTextView.setText(newData.getValue());

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin", "Fail response: " + response);
                Log.e("ERROR", e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }

        });



    }


}
