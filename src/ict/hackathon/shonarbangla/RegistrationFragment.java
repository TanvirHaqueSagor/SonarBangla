package ict.hackathon.shonarbangla;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources.NotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationFragment  extends Fragment implements OnClickListener{

	String PhoneNumber,Password,ConfirmPasssword;
	EditText phoneNumberEditText,passwordEditText,confirmPasswordEditText;
	Button registratioButton;
	Thread t; 

	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	  
	private ProgressDialog pDialog1;
	   String serverResponseMessage;
	   public String serverResponse;
	
public RegistrationFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
         
        return rootView;
    }
	
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			
			
			 View v = getView();
			
			 phoneNumberEditText=(EditText) v.findViewById(R.id.etPhoneRegistration);
			 passwordEditText=(EditText) v.findViewById(R.id.etPasswordRegistration);
			 confirmPasswordEditText =(EditText) v.findViewById(R.id.etConfirmPasswordRegistration);
			 registratioButton=(Button) v.findViewById(R.id.registrationButton);
			 
			 registratioButton.setOnClickListener(this);
	 	}

	@Override
	public void onClick(View arg0) {
		 PhoneNumber=phoneNumberEditText.getText().toString();
		 Password=passwordEditText.getText().toString();
		 ConfirmPasssword=confirmPasswordEditText.getText().toString();
		 int ln=Password.length();
		 
		 
		  if(!PhoneNumber.equals("") && !Password.equals("") && !ConfirmPasssword.equals("")){
		  if(ln>=8){
          if(Password.equals(ConfirmPasssword)){
	
	
        	  if (isNetworkAvailable()) {

		 			pDialog1 = new ProgressDialog(getActivity());
		 			 
		 			pDialog1.setMessage("Loading...");
		 			 pDialog1.show();   
		 			t = new Thread(r);
		 	    	showDialog();
				} else {
					showAlert(getString(R.string.attention), getString(R.string.connectionError));
				}
	  }
else{
	new AlertDialog.Builder(getActivity())
    .setTitle(getResources().getString(R.string.ClearCookies))
    .setIcon(getResources().getDrawable( android.R.drawable.ic_dialog_alert))
  	.setMessage(getResources().getString(R.string.passwordMissmashAlartMessage))
   
  	.setNeutralButton(getResources().getString(R.string.okButtonText),
  	       new DialogInterface.OnClickListener() {
  	       @Override
		public void onClick(DialogInterface dialog, int which) {
  	       dialog.dismiss();
  	        }
  	    }).show();
	    }
		 }
		 else{
			 new AlertDialog.Builder(getActivity())
			    .setTitle(getResources().getString(R.string.ClearCookies))
			    .setIcon(getResources().getDrawable( android.R.drawable.ic_dialog_alert))
			  	.setMessage(getResources().getString(R.string.passwordlengthAlartMessage))
			  	.setNeutralButton(getResources().getString(R.string.okButtonText),
			     new DialogInterface.OnClickListener() {
			     @Override
				public void onClick(DialogInterface dialog, int which) {
			     dialog.dismiss();
			     }
		      }).show();
		 }
		 
		 }
		 else{
		        new AlertDialog.Builder(getActivity())
		       .setTitle(getResources().getString(R.string.ClearCookies))
		       .setIcon(getResources().getDrawable( android.R.drawable.ic_dialog_alert))
		     	.setMessage(getResources().getString(R.string.AlartMessage))
		     	.setNeutralButton(getResources().getString(R.string.okButtonText),
		     	       new DialogInterface.OnClickListener() {
		     	       @Override
					public void onClick(DialogInterface dialog, int which) {
		     	       dialog.dismiss();
		     	        }
		     	    }).show();
	   }
	}
	 
	Runnable r = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(
					"http://10.0.2.2/JSON/sign_up.php");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("phonenumber", PhoneNumber));
			params.add(new BasicNameValuePair("password", Password));  
			 
               post.setEntity(new UrlEncodedFormEntity(params));
		       HttpResponse response = client.execute(post);
		   	if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(entity);
				Log.d("JSON Response", jsonStr);

				JSONObject obj = new JSONObject(jsonStr);
				String success = obj.getString("success");
				serverResponse = obj.getString("message");
				if(serverResponse.equals("ok")){
					serverResponse=getResources().getString(R.string.registrationSuccess);
				}
				 	
				if (success.equals("1")) {
					handler.sendEmptyMessage(SUCCESS);
				} else {
					handler.sendEmptyMessage(FAILURE);
				}

			} else {

				handler.sendEmptyMessage(FAILURE);
			}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};


	private void showDialog() throws NotFoundException {
		new AlertDialog.Builder(getActivity())
				.setTitle(getResources().getString(R.string.ClearCookies))
				.setMessage(
						getResources().getString(R.string.ClearCookieQuestion))
				.setIcon(
						getResources().getDrawable(
								android.R.drawable.ic_input_add))
				.setPositiveButton(
						getResources().getString(R.string.PostiveYesButton),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								  
								t.start();
						 	
				        	}
						})
				.setNegativeButton(
						getResources().getString(R.string.NegativeNoButton),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Do Something Here
								pDialog1.dismiss();
								
							}
						}).show();
	}
	

Handler handler = new Handler() {
	@Override
	public void handleMessage(android.os.Message msg) {
		pDialog1.dismiss();
		if (msg.what == SUCCESS) {
			showAlert(getString(R.string.success), serverResponse);
			
			passwordEditText.setText("");
			phoneNumberEditText.setText("");
			confirmPasswordEditText.setText(""); 
		    } else {
			showAlert(getString(R.string.fail), serverResponse);
		    }
	}
};

public boolean isNetworkAvailable() {
	// network state
	ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo netInfo = manager.getActiveNetworkInfo();
	if (netInfo != null) {
		if (netInfo.isAvailable() && netInfo.isAvailable()) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}
 
public void showAlert(String title, String message) {
	AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
	alert.setTitle(title);
	alert.setMessage(message);
	alert.show();
}
	  
}
