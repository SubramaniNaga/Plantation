package com.fresh.mind.plantation.fragment.menu_page;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomEditeText;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.searchlocation.LocationAdapter;
import com.fresh.mind.plantation.searchlocation.PlaceJSONParsersA;
import com.fresh.mind.plantation.searchlocation.PlacesAutoCompleteAdapter;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.ContactUs;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static com.fresh.mind.plantation.R.string.location;
import static com.fresh.mind.plantation.R.string.mobileNumber;
import static com.fresh.mind.plantation.R.string.reset;


/**
 * Created by AND I5 on 11-03-2017.
 */
public class FeedBack extends Fragment {
    private View rootView;
    private CustomEditeText mName, mMobileNo, mSuggestion, mLocation, mEmail;
    private CustomTextView mSubmit;
    public static AutoCompleteTextView acLocation;
    private ContactUs contactUs;
    public ArrayList<HashMap<String, String>> mLocationList = new ArrayList<>();
    private String location, email;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,256}" +
            "@" +
            "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
            "(" +
            "." +
            "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
            ")+"
    );
    private Spinner mDistrictName;


    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).setFeedback(getActivity().getResources().getString(R.string.Feedback));
        rootView = inflater.inflate(R.layout.plant_feedbak, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        contactUs = new ContactUs(getActivity());

        mName = (CustomEditeText) rootView.findViewById(R.id.mNameTxt);
        mMobileNo = (CustomEditeText) rootView.findViewById(R.id.mMobileNumbertxt);
        mSuggestion = (CustomEditeText) rootView.findViewById(R.id.mSuggestionBoxTxt);
        mEmail = (CustomEditeText) rootView.findViewById(R.id.mEmail);
        mLocation = (CustomEditeText) rootView.findViewById(R.id.mLocation);
        mSubmit = (CustomTextView) rootView.findViewById(R.id.mSubmit);
        //acLocation = (AutoCompleteTextView) rootView.findViewById(R.id.acLocation);
        acLocation = (AutoCompleteTextView) rootView.findViewById(R.id.acLocation);
        mDistrictName = (Spinner) rootView.findViewById(R.id.sp_district_name);
        acLocation.setThreshold(1);
        acLocation.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.adapter_single_view));
        Point pointSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(pointSize);
        acLocation.setDropDownWidth(pointSize.x);
        acLocation.setDropDownHeight(pointSize.y);
        final ArrayList<HashMap<String, String>> mDistrictEmails = contactUs.getDistrictWithEmails(languages);
        mDistrictName.setAdapter(new ItemDistrictFeedBack(getActivity(), mDistrictEmails));
        /*for (int i = 0; i < mDistrictEmails.size(); i++) {
            Log.d("sjkjdlsdlsd", "" + mDistrictEmails.get(i).get("districtName") + "  " + mDistrictEmails.get(i).get("Email"));
        }*/

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mSubmit.setBackground(getActivity().getDrawable(R.drawable.riiple_oval));
        }
        mDistrictName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = mDistrictEmails.get(position).get("districtName");
                email = mDistrictEmails.get(position).get("Email");
                Log.d("sdlksldskld", "" + location + "  " + email);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        acLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mSearchPlace = String.valueOf(s).trim();
                Log.d("SearchingLopcatoin", mSearchPlace);
                String mAddress = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + mSearchPlace + "&radius=1000&sensor=true&key=AIzaSyC1Axsjv4lHwqDZOIBiTO8kTJjBQuNL6bk";
                /*Log.d("SampleMainActivity ", " " + mAddress);*/
                PlacesTask placesTask = new PlacesTask();
                placesTask.execute(mAddress);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mSubmit.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           String mNameStr = "", mMobileStr = "", mSuggestionStr = "";
                                           mNameStr = mName.getText().toString().trim();
                                           mMobileStr = mMobileNo.getText().toString().trim();
                                           mSuggestionStr = mSuggestion.getText().toString().trim();
                                           // String email = mEmail.getText().toString();
                                           // String location = acLocation.getText().toString();

                                           Log.d("dlkskoie", " name " + mNameStr + " mobil " + mMobileStr + " loca " + location + " ema " + email + " sug " + mSuggestionStr);
                                           if (mNameStr.isEmpty() && mMobileStr.isEmpty() && mSuggestionStr.isEmpty()) {
                                               Toast.makeText(getActivity(), getActivity().getString(R.string.enterAllField), Toast.LENGTH_SHORT).show();
                                           } else {
                                               if (mNameStr.isEmpty()) {
                                                   Toast.makeText(getActivity(), getActivity().getString(R.string.hint_Name), Toast.LENGTH_SHORT).show();
                                               } else {
                                                   if (mMobileStr.isEmpty() || mMobileStr.length() < 10) {
                                                       Toast.makeText(getActivity(), getActivity().getString(R.string.hint_MobileNumber), Toast.LENGTH_SHORT).show();
                                                   } else {
                                                       if (location != null && !location.isEmpty() && !getActivity().getString(R.string.select_dis).equals(location)) {
                                                           /*if (email != null && !email.isEmpty()) {
                                                               if (checkEmail(email)) {*/
                                                           if (mSuggestionStr != null && !mSuggestionStr.isEmpty()) {
                                                               new SendFeedBack(mNameStr, mMobileStr, mSuggestionStr, email, location).execute();
                                                           } else {
                                                               Toast.makeText(getActivity(), getString(R.string.enter_sug), Toast.LENGTH_SHORT).show();
                                                           }
                                                               /*} else {
                                                                   Toast.makeText(getActivity(), getString(R.string.hint_email_validation), Toast.LENGTH_SHORT).show();
                                                               }
                                                           } else {
                                                               Toast.makeText(getActivity(), getActivity().getString(R.string.hint_Email), Toast.LENGTH_SHORT).show();
                                                           }*/
                                                       } else {
                                                           Toast.makeText(getActivity(), getActivity().getString(R.string.select_dis), Toast.LENGTH_SHORT).show();
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                   }

        );

        return rootView;
    }


    @SuppressLint("LongLogTag")
    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }

    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Location Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        }


        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background ", e.toString());
            }
            return data;
        }


        @Override
        protected void onPostExecute(String result) {
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }

    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;


        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            PlaceJSONParsersA placeJsonParser = new PlaceJSONParsersA();
            try {
                jObject = new JSONObject(jsonData[0]);
                places = placeJsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {
            try {
                mLocationList.clear();
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        HashMap<String, String> hmPlace = list.get(i);
                        String name = hmPlace.get("place_name");
                        HashMap<String, String> mHash = new HashMap<String, String>();
                        mHash.put("mPlaceName", name);
                        mLocationList.add(mHash);
                    }
                    acLocation.setAdapter(new LocationAdapter(getActivity(), R.layout.location_adapter, mLocationList));
                } else {
                    Log.d("LocatioSize", " Zero");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class SendFeedBack extends AsyncTask<String, Void, String> {
        private final String mNameStr;
        private final String location;
        private final String email;
        private String mMobileNoStr;
        private final String mSuggestionStr;
        ProgressDialog progressDialog;

        public SendFeedBack(String mNameStr, String mMobileStr, String mSuggestionStr, String email, String location) {
            this.mNameStr = mNameStr;
            this.mMobileNoStr = mMobileStr;
            this.mSuggestionStr = mSuggestionStr;
            this.location = location;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle(null);
            progressDialog.setMessage(getActivity().getString(R.string.feedbackSend));
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String result = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Config.urlFeedback);
            InputStream is = null;
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("task", "feedback"));
            nameValuePairs.add(new BasicNameValuePair("name", "" + mNameStr));
            nameValuePairs.add(new BasicNameValuePair("mobileNumber", "" + mMobileNoStr));
            nameValuePairs.add(new BasicNameValuePair("query", "" + mSuggestionStr));
            nameValuePairs.add(new BasicNameValuePair("email", "" + email));
            nameValuePairs.add(new BasicNameValuePair("location", "" + location));
            Log.d("nameValuePairsFeedBack", "" + nameValuePairs);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                result = sb.toString();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ResponesbackSerrver", "" + s);
            progressDialog.dismiss();
            if (s != null) {
                progressDialog = null;
                try {
                    JSONObject parentObject = new JSONObject(s);
                    boolean status = parentObject.getBoolean("status");
                    String message = parentObject.getString("message");
                    if (status) {
                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                        mName.setText("");
                        mMobileNo.setText("");
                        mSuggestion.setText("");
                        mName.setFocusable(true);

                    } else {
                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ItemDistrictFeedBack extends BaseAdapter {
        private final ArrayList<HashMap<String, String>> mDistrictEmails;
        private FragmentActivity mContext;

        public ItemDistrictFeedBack(FragmentActivity activity, ArrayList<HashMap<String, String>> mDistrictEmails) {


            this.mContext = activity;
            this.mDistrictEmails = mDistrictEmails;
        }

        @Override
        public int getCount() {
            return mDistrictEmails.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_spinner_1, null);

            final TextView treeTypeName = (TextView) convertView.findViewById(R.id.mSPinnerText);
            treeTypeName.setText("" + mDistrictEmails.get(position).get("districtName"));
           /* treeTypeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    location = treeTypeName.getText().toString();
                    email = mDistrictEmails.get(position).get("Email");
                    Log.d("sdlksldskld", "" + mDistrictEmails.get(position).get("Email"));
                }
            });*/

            return convertView;
        }
    }

}
