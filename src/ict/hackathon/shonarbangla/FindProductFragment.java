package ict.hackathon.shonarbangla;
 
import ict.hackathon.shonarbangla.HomeFragment.RequestThread;
import ict.hackathon.shonarbangla.adapter.CustomListAdapter;
import ict.hackathon.shonarbangla.app.AppController;
import ict.hackathon.shonarbangla.model.AdObj;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class FindProductFragment extends Fragment implements  OnClickListener, OnItemSelectedListener{
	Spinner productSpinner, locationSpinner;
	Button SearchButton;
	String location,product;
	
	  ///   private String   location, product;
	  private ProgressDialog dialog;
	    // private String url = "http://10.0.2.2/JSON/find.php?location=1";
	      
	       private static  String url = "http://10.0.2.2/JSON/find.php?location=1";
	       public static List<AdObj> adObjList1 = new ArrayList<AdObj>();
           ListView listView1;
           private CustomListAdapter adapter;
           private ProgressDialog pDialog;
	       Thread t; 
	    
	    
public FindProductFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		   View rootView = inflater.inflate(R.layout.fragment_find_product, container, false);
         return rootView;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		 View v = getView();
		 	
			
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
		
		 
		 
		 
		   SearchButton=(Button) v.findViewById(R.id.searchButton);
		   SearchButton.setOnClickListener(this);
		   
		   
			    listView1 = (ListView) v.findViewById(R.id.findListView);
				adapter = new CustomListAdapter(getActivity(), adObjList1);
				listView1.setAdapter(adapter);
			
			
			
				

				listView1. setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						HomeFragment.adObjList=adObjList1;
						
					    	 PostDetailsFragment postDetailsFragment = new PostDetailsFragment();
						    FragmentManager fragmentManager = getFragmentManager();
						    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
						    Bundle bundle = new Bundle();
						    bundle.putInt("ad_row", position);
						 
						    
						    postDetailsFragment.setArguments(bundle);
						
						    fragmentTransaction.replace(R.id.frame_container, postDetailsFragment);
						    fragmentTransaction.commit();
					}
				});
				
				
				
			
			
	}



class RequestThread extends Thread {
	@Override
	public void run() {
		 
		// Creating volley request obj
		JsonArrayRequest movieReq = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						 
						hidePDialog();

						// Parsing json
						for (int i = response.length()-1; i>=0 ; i--) {
							try {

								JSONObject obj = response.getJSONObject(i);
								AdObj ad_obj = new AdObj();
								
								ad_obj.setId(obj.getString("id"));
								ad_obj.setTitle(obj.getString("title"));
								ad_obj.setThumbnailUrl("http://sonarbangla.hoxty.com/image/"+ obj.getString("image"));
								ad_obj.setRating(obj.getString("price"));
								ad_obj.setQuantity(obj.getString("quantity"));
								ad_obj.setLocation(obj.getString("location"));
								ad_obj.setDetails(obj.getString("details"));
								ad_obj.setUsername(obj.getString("user_name"));
                             	ad_obj.setCategory(obj.getString("category"));
                              	ad_obj.setDate(obj.getString("date"));
								ad_obj.setPhone(obj.getString("phone"));
								// adding movie to add_obj array
								adObjList1.add(ad_obj);
                          } catch (JSONException e) {
								e.printStackTrace();
							}
                   	}
                    adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						
						hidePDialog();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(movieReq);

	}
}

private void hidePDialog() {
	if (pDialog != null) {
		pDialog.dismiss();
		pDialog = null;
	}
}

@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	

	location = Integer.toString(locationSpinner.getSelectedItemPosition());
	product = Integer.toString(productSpinner.getSelectedItemPosition());
	
	 url = "http://10.0.2.2/JSON/find.php?location="+location+"&category="+product;
	
	if(adObjList1!=null){
		 	
		adObjList1.clear();
		 pDialog = new ProgressDialog(getActivity());
		 // Showing progress dialog before making http request
	 	 pDialog.setMessage("Loading...");
		 pDialog.show();
		RequestThread requestThread = new RequestThread();
		requestThread.start();
		}
}

public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	
}

public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
	
}
	
}
