package ict.hackathon.shonarbangla;

import ict.hackathon.shonarbangla.app.AppController;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

public class PostEditFragment extends Fragment implements OnClickListener, OnItemSelectedListener{
	long start, stop;
	

	Spinner productSpinner, locationSpinner;
	private ProgressDialog pDialog;
	private ProgressDialog pDialog1;
	
	TextView selVersion;

    EditText pName, pQuantity, pDetails, pPrice, pPhoneNumber;
    private String name, quantity, details, price, phoneNumber, location,
		product;
    Button editPost;
    private int PICK_IMAGE_REQUEST = 1;
    
    private String imagepath=null;
   private int serverResponseCode = 0;
    private ProgressDialog dialog = null;
    private String upLoadServerUri = "http://sonarbangla.hoxty.com/UploadToServer.php";
    ImageButton imageButton; 
   String fileNAme=null;
   String username;
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
   String serverResponseMessage;
   public String serverResponse;
    int position;
Thread t; 
 
Bitmap bmp;
public PostEditFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_edit, container, false);
         
        return rootView;
    }
	


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		 View v = getView();
		//  FRAGMENT_ID = ((RelativeLayout) v.getParent()).getId();
		 
		 
		 
		 Bundle bundle = this.getArguments();
		 position = bundle.getInt("ad_row");
		 
		 
		 
    
		   
		 
		    pName = (EditText) v.findViewById(R.id.productName);
			pDetails = (EditText) v.findViewById(R.id.productDescription);
			pQuantity = (EditText) v.findViewById(R.id.producQuantity);
			pPrice = (EditText) v.findViewById(R.id.productPrice);
			pPhoneNumber = (EditText) v.findViewById(R.id.contactNumber);
			editPost = (Button) v.findViewById(R.id.editPost);
			 imageButton=(ImageButton) v.findViewById(R.id.imageButton); 
			 
			 editPost.setOnClickListener(this);
		        imageButton.setOnClickListener(this);
			
		        
		        productSpinner = (Spinner) v.findViewById(R.id.categorySpinner);
				locationSpinner = (Spinner) v.findViewById(R.id.locationSpinner);

				ArrayAdapter<CharSequence> proudectAdapter = ArrayAdapter
						.createFromResource(getActivity(), R.array.product_array,
								android.R.layout.simple_spinner_item);
				proudectAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter
						.createFromResource(getActivity(), R.array.devision_array,
								android.R.layout.simple_spinner_item);
				stateAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				productSpinner.setAdapter(proudectAdapter);
				productSpinner.setOnItemSelectedListener(this);

				locationSpinner.setAdapter(stateAdapter);
				locationSpinner.setOnItemSelectedListener(this);
				
				
				
//			    bundle.putString("id", HomeFragment.adObjList.get(position).getId()); 
//			    bundle.putString("title", HomeFragment.adObjList.get(position).getTitle()); 
//			    bundle.putString("quantity", HomeFragment.adObjList.get(position).getQuantity()); 
//			    bundle.putString("details", HomeFragment.adObjList.get(position).getDetails()); 
//			    bundle.putString("price", HomeFragment.adObjList.get(position).getRating()); 
//			    bundle.putString("location", HomeFragment.adObjList.get(position).getLocation()); 
//			    bundle.putString("category", HomeFragment.adObjList.get(position).getCategory()); 
//			    bundle.putString("phone", HomeFragment.adObjList.get(position).getPhone()); 
				
				
				pName.setText(HomeFragment.adObjList.get(position).getTitle());
				pDetails.setText(HomeFragment.adObjList.get(position).getDetails());
				pQuantity.setText(HomeFragment.adObjList.get(position).getQuantity());
				pPrice.setText(HomeFragment.adObjList.get(position).getRating());
				pPhoneNumber.setText(HomeFragment.adObjList.get(position).getPhone());
				productSpinner.setSelection(Integer.parseInt(HomeFragment.adObjList.get(position).getCategory()));
				locationSpinner.setSelection(Integer.parseInt(HomeFragment.adObjList.get(position).getLocation()));
			
				//imageButton.
				
//				if (imageLoader == null)
//					imageLoader = AppController.getInstance().getImageLoader();
			//	NetworkImageView thumbNail = (NetworkImageView)v.findViewById(R.id.thumbnailFull);
				 
				// 	thumbNail.setImageUrl(HomeFragment.adObjList.get(position).getThumbnailUrl(), imageLoader);
				
				
				
//				try {
//					URL url=new URL(HomeFragment.adObjList.get(position).getThumbnailUrl());
//				 	  bmp=BitmapFactory.decodeStream(url.openConnection().getInputStream());
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//                imageButton.setImageBitmap(bmp);
				
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton:
		    Intent intent1 = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		    startActivityForResult(intent1, 1);
		 	 break;
					
		case R.id.editPost:
			 
		    name = pName.getText().toString();
			quantity = pQuantity.getText().toString();
			details = pDetails.getText().toString();
			price = pPrice.getText().toString();
			phoneNumber = pPhoneNumber.getText().toString();
			location = Integer.toString(locationSpinner.getSelectedItemPosition());
			product = Integer.toString(productSpinner.getSelectedItemPosition());
		 	// String a=getResources().getStringArray(R.array.product_array)[3];
			 
			
			
		      
				 	if (!name.equals("") && !quantity.equals("")  && !details.equals("")
							&& !price.equals("") && !phoneNumber.equals("")) {
				 		
				 		if (isNetworkAvailable()) {

				 			pDialog1 = new ProgressDialog(getActivity());
				 			 
				 			pDialog1.setMessage("Loading...");
				 			 pDialog1.show();   
				 			t = new Thread(r);
				 	    	showDialog();
						} else {
							showAlert(getString(R.string.attention), getString(R.string.connectionError));
						}
				 	     	
					} else {
						
				 	      
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
		   
		            	}///End Data complete check
		      break;
                default:
		    	break;
				}
	}
  
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		       Uri selectedImageUri = data.getData();
	            imagepath = getPath(selectedImageUri);
	             Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
	              File f = new File(imagepath);
	               fileNAme=f.getName();
	                Bitmap resizedBitmap = Bitmap.createScaledBitmap(
	            	 bitmap, 100, 100, false);
	                  imageButton.setImageBitmap(resizedBitmap); 
		 
	}
	  @Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	   public String getPath(Uri uri) {
           String[] projection = { MediaColumns.DATA };
           Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
           int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
           cursor.moveToFirst();
           return cursor.getString(column_index);
       }
	
	Runnable r = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(
					"http://10.0.2.2/JSON/UpdatePost.php");
if(fileNAme.equals("")){
	fileNAme= HomeFragment.adObjList.get(position).getImageNmae();
}

			
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("id", HomeFragment.adObjList.get(position).getId()));
			params.add(new BasicNameValuePair("title", name));
			
			params.add(new BasicNameValuePair("image", fileNAme));
			
			params.add(new BasicNameValuePair("price", price));
			params.add(new BasicNameValuePair("quantity", quantity));
			params.add(new BasicNameValuePair("location", location));
			params.add(new BasicNameValuePair("category", product));

			params.add(new BasicNameValuePair("phone", phoneNumber));
			params.add(new BasicNameValuePair("date", ""));
			params.add(new BasicNameValuePair("details", details));
			
			
			params.add(new BasicNameValuePair("user_name",username));
			
			if(imagepath!=null)
			   uploadFile(imagepath);
			   
			   
               post.setEntity(new UrlEncodedFormEntity(params));
		       HttpResponse response = client.execute(post);
		   	if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(entity);
				Log.d("JSON Response", jsonStr);

				JSONObject obj = new JSONObject(jsonStr);
				String success = obj.getString("success");
				serverResponse = obj.getString("message");
				if (success.equals("1")) {
					handler.sendEmptyMessage(SUCCESS);
				} else {
					handler.sendEmptyMessage(FAILURE);
				}

			} else {
				serverResponse = "Server Error: "
						+ response.getStatusLine().getStatusCode();
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

	// **************** Alart Dialog****************

  
	
	
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
						//	    Toast.makeText(getApplicationContext(),  "Information Saved Successfully..!", Toast.LENGTH_LONG).show();
							
								
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
// ********************End alart*********************

//*************image upload Functtion***************
    
public int uploadFile(String sourceFileUri) {
      
      
     String fileName = sourceFileUri;

     HttpURLConnection conn = null;
     DataOutputStream dos = null;  
     String lineEnd = "\r\n";
     String twoHyphens = "--";
     String boundary = "*****";
     int bytesRead, bytesAvailable, bufferSize;
     byte[] buffer;
     int maxBufferSize = 1 * 1024 * 1024; 
     File sourceFile = new File(sourceFileUri); 
      
     if (!sourceFile.isFile()) {
           return 0;
       
     }
     else
     {
          try { 
               
                // open a URL connection to the Servlet
              FileInputStream fileInputStream = new FileInputStream(sourceFile);
              URL url = new URL(upLoadServerUri);
               
              // Open a HTTP  connection to  the URL
              conn = (HttpURLConnection) url.openConnection(); 
              conn.setDoInput(true); // Allow Inputs
              conn.setDoOutput(true); // Allow Outputs
              conn.setUseCaches(false); // Don't use a Cached Copy
              conn.setRequestMethod("POST");
              conn.setRequestProperty("Connection", "Keep-Alive");
              conn.setRequestProperty("ENCTYPE", "multipart/form-data");
              conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
              conn.setRequestProperty("uploaded_file", fileName); 
               
              dos = new DataOutputStream(conn.getOutputStream());
     
              dos.writeBytes(twoHyphens + boundary + lineEnd); 
              dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                        + fileName + "\"" + lineEnd);
               
              dos.writeBytes(lineEnd);
     
              // create a buffer of  maximum size
              bytesAvailable = fileInputStream.available(); 
     
              bufferSize = Math.min(bytesAvailable, maxBufferSize);
              buffer = new byte[bufferSize];
     
              // read file and write it into form...
              bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                 
              while (bytesRead > 0) {
                   
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                 
               }
     
              // send multipart form data necesssary after file data...
              dos.writeBytes(lineEnd);
              dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
     
              // Responses from the server (code and message)
              serverResponseCode = conn.getResponseCode();
              serverResponseMessage = conn.getResponseMessage();
                

            
              if(serverResponseCode == 200){
                   
                  getActivity().runOnUiThread(new Runnable() {
                       @Override
					public void run() {
                         
                          
                           Toast.makeText(getActivity().getApplicationContext(), "File Upload Complete.", Toast.LENGTH_SHORT).show();
                       }
                   });                
              }    
               
              //close the streams //
              fileInputStream.close();
              dos.flush();
              dos.close();
                
         } catch (MalformedURLException ex) {
              
           
             ex.printStackTrace();
              
             getActivity().runOnUiThread(new Runnable() {
                 @Override
				public void run() {
                    
                     Toast.makeText(getActivity().getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
                 }
             });
              
              
         } catch (Exception e) {
              
             
             e.printStackTrace();
              
             getActivity(). runOnUiThread(new Runnable() {
                 @Override
				public void run() {
                   
                     Toast.makeText(getActivity().getApplicationContext(), "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                 }
             });
            
         }
       
          
         return serverResponseCode; 
          
      } // End else block 
    }
	
Handler handler = new Handler() {
	@Override
	public void handleMessage(android.os.Message msg) {
		pDialog1.dismiss();
		if (msg.what == SUCCESS) {
			showAlert(getString(R.string.success), serverResponse);
			
			pName.setText("");
			pQuantity.setText("");
			pDetails.setText("");
			pPrice.setText("");
			pPhoneNumber.setText("");
			productSpinner.setSelection(0);
			locationSpinner.setSelection(0);
		    imageButton.setImageResource(R.drawable.ic_add_image_button);
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
