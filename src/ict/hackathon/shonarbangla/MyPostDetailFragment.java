package ict.hackathon.shonarbangla;

import ict.hackathon.shonarbangla.app.AppController;
import ict.hackathon.shonarbangla.model.AdObj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class MyPostDetailFragment extends Fragment implements OnClickListener{
	
	TextView pName,pQuantity,pDesc,pPrice,pLoc,pPhone,pDate;
	Button editButton,deleteButton;

	int  position,find_check; 
	public static List<AdObj> adObjList1 = new ArrayList<AdObj>();
	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	
	int id;
	String url= "http://10.0.2.2/JSON/delete_post.php?id=";
	   private ProgressDialog pDialog1;
	   String serverResponseMessage;
	   public String serverResponse;
	   static String response = null;
		public static final int SUCCESS = 1;
		public static final int FAILURE = 0;
		Thread t;
	
public MyPostDetailFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.my_post_detail, container, false);
         
        return rootView;
    }
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			 View v = getView();
			 
			 
			 Bundle bundle = this.getArguments();
			 position = bundle.getInt("ad_row");
			 
			 
			// if(find_check==0){
//					adObjList2=HomeFragment.adObjList;
//				 }
//				 else if(find_check==1){
//					 adObjList2=FindProductFragment.adObjList1;
//				 }			 
//			 Bundle extras = getIntent().getExtras();
//		     position= extras.getInt("ad_row"); 
		     	       
		    pName=(TextView)v.findViewById(R.id.tvPNmae);
		    pDesc=(TextView)v.findViewById(R.id.tvDesc);
		    pPrice=(TextView)v.findViewById(R.id.tvprice);
		    pLoc=(TextView)v.findViewById(R.id.tvLoc);
		    pPhone=(TextView)v.findViewById(R.id.tvPNo);
		    pDate=(TextView)v.findViewById(R.id.tvdate);
		    pQuantity=(TextView)v.findViewById(R.id.tvPQuantity);
		    
		    editButton=(Button) v.findViewById(R.id.editButton);
		    deleteButton=(Button) v.findViewById(R.id.deleteButton);
		    
		    deleteButton.setOnClickListener(this);
		    editButton.setOnClickListener(this);
		    
			if (imageLoader == null)
				imageLoader = AppController.getInstance().getImageLoader();
			NetworkImageView thumbNail = (NetworkImageView)v.findViewById(R.id.thumbnailFull);
			 
			// thumbnail image
			
			
			thumbNail.setImageUrl(HomeFragment.adObjList.get(position).getThumbnailUrl(), imageLoader);
			 
            pName.setText(HomeFragment.adObjList.get(position).getTitle());
 	        pQuantity.setText(HomeFragment.adObjList.get(position).getQuantity());
 	        pDesc.setText(HomeFragment.adObjList.get(position).getDetails());
            pPrice.setText(HomeFragment.adObjList.get(position).getRating());
 		    pLoc.setText(HomeFragment.adObjList.get(position).getLocation());
 	        pPhone.setText(HomeFragment.adObjList.get(position).getId());
    	    pDate.setText(HomeFragment.adObjList.get(position).getDate());
			 
			
			
		}

	@Override
	public void onClick(View v) {
switch (v.getId()) {
      case R.id.editButton:
    	  

    	  PostEditFragment postEditFragment = new PostEditFragment();
		    FragmentManager fragmentManager = getFragmentManager();
		    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    
		    Bundle bundle = new Bundle();
		   bundle.putInt("ad_row", position); 
		    
		    postEditFragment.setArguments(bundle);
		    
		    fragmentTransaction.replace(R.id.frame_container, postEditFragment);
		    fragmentTransaction.commit();
		 	  
	   break;
	   case R.id.deleteButton:
		
		   new AlertDialog.Builder(getActivity())
			.setTitle(getResources().getString(R.string.ClearCookies))
			.setMessage(
					getResources().getString(R.string.deleteNotice))
			.setIcon(
					getResources().getDrawable(
							android.R.drawable.ic_delete))
			.setPositiveButton(
					getResources().getString(R.string.delete_button),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							   url+=HomeFragment.adObjList.get(position).getId();
							
								if (isNetworkAvailable()) {

						 			pDialog1 = new ProgressDialog(getActivity());
						 			 
						 			pDialog1.setMessage("Loading..."); 
						 			 pDialog1.show();   
						 			RequestThread requestThread = new RequestThread();
						 			requestThread.start();
						 			
								} else {
									showAlert(getString(R.string.attention), getString(R.string.connectionError));
								}
						 	}
					})
			.setNegativeButton(
					getResources().getString(R.string.cancel),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							// Do Something Here
						}
					}).show();
		    break;

default:
	break;
}
 }
	
	class RequestThread extends Thread {
		@Override
		public void run() {

		
		     DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpEntity httpEntity = null;
	            HttpResponse httpResponse = null;
	            HttpGet httpGet = new HttpGet(url);
	            
                try {
					httpResponse = httpClient.execute(httpGet);
					httpEntity = httpResponse.getEntity();
					String jsonStr = EntityUtils.toString(httpEntity);
					JSONObject obj = new JSONObject(jsonStr);
					String success = obj.getString("success");
					if (success.equals("1")) {
						handler.sendEmptyMessage(SUCCESS);
					} else {
						handler.sendEmptyMessage(FAILURE);
					}
		            
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
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			pDialog1.dismiss();
			if (msg.what == SUCCESS) {
				showAlert(getString(R.string.success), getString(R.string.deleteSuccessfullMessage));
				
				
			    } else {
				showAlert(getString(R.string.fail), getString(R.string.deleteUnSuccessfullMessage));
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
	      new AlertDialog.Builder(getActivity())
	       .setTitle(title)
	       .setIcon(getResources().getDrawable( android.R.drawable.ic_dialog_alert))
	     	.setMessage(message)
	     	.setNeutralButton(getResources().getString(R.string.okButtonText),
	     	       new DialogInterface.OnClickListener() {
	     	       @Override
				public void onClick(DialogInterface dialog, int which) {
	     	         dialog.dismiss();
	     	        
	     	         
	     	        FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container,new MyPostFragment()).commit();

					 
	     	         
	     	        }
	     	    }).show();
	 	}
	  
}
