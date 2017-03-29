package com.fresh.mind.plantation.fragment.menu_page;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomEditeText;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;

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
import java.util.ArrayList;
import java.util.List;

import static com.fresh.mind.plantation.Constant.Config.url;


/**
 * Created by AND I5 on 11-03-2017.
 */
public class FeedBack extends Fragment {
    private View rootView;
    private CustomEditeText mName, mMobileNo, mSuggestion;
    private CustomTextView mSubmit;

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
        mName = (CustomEditeText) rootView.findViewById(R.id.mNameTxt);
        mMobileNo = (CustomEditeText) rootView.findViewById(R.id.mMobileNumbertxt);
        mSuggestion = (CustomEditeText) rootView.findViewById(R.id.mSuggestionBoxTxt);
        mSubmit = (CustomTextView) rootView.findViewById(R.id.mSubmit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mNameStr = "", mMobileStr = "", mSuggestionStr = "";
                mNameStr = mName.getText().toString().trim();
                mMobileStr = mMobileNo.getText().toString().trim();
                mSuggestionStr = mSuggestion.getText().toString().trim();
                Log.d("assad", " " + mNameStr + "  " + mMobileStr + " " + mSuggestionStr);
                if (mNameStr.isEmpty() && mMobileStr.isEmpty() && mSuggestionStr.isEmpty()) {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.enterAllField), Toast.LENGTH_SHORT).show();
                } else {
                    if (mNameStr.isEmpty()) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.hint_Name), Toast.LENGTH_SHORT).show();
                    } else {
                        if (mMobileStr.isEmpty() || mMobileStr.length() < 10) {
                            Toast.makeText(getActivity(), getActivity().getString(R.string.hint_MobileNumber), Toast.LENGTH_SHORT).show();
                        } else {
                            if (mSuggestionStr.isEmpty()) {
                                Toast.makeText(getActivity(), getActivity().getString(R.string.hint_Feedback), Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("susdsdsd", "Success");
                                //Toast.makeText(getActivity(), "Its not implementing", Toast.LENGTH_SHORT).show();
                                new SendFeedBack(mNameStr, mMobileStr, mSuggestionStr).execute();
                            }
                        }
                    }
                }
            }
        });


        return rootView;
    }

    class SendFeedBack extends AsyncTask<String, Void, String> {
        private final String mNameStr;
        private String mMobileNoStr;
        private final String mSuggestionStr;
        ProgressDialog progressDialog;

        public SendFeedBack(String mNameStr, String mMobileStr, String mSuggestionStr) {
            this.mNameStr = mNameStr;
            this.mMobileNoStr = mMobileStr;
            this.mSuggestionStr = mSuggestionStr;
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
            mMobileNoStr = "subbu@gmail.com";
            nameValuePairs.add(new BasicNameValuePair("email", "" + mMobileNoStr));
            nameValuePairs.add(new BasicNameValuePair("query", "" + mSuggestionStr));
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
            Log.d("ResponesFromFeedbackSerrver", "" + s);
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

                    } else {
                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
