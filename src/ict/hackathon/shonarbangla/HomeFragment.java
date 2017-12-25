package ict.hackathon.shonarbangla;

 

 

import ict.hackathon.shonarbangla.adapter.CustomListAdapter;
import ict.hackathon.shonarbangla.app.AppController;
import ict.hackathon.shonarbangla.model.AdObj;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class HomeFragment extends Fragment {
	
	ListView listView;
	private CustomListAdapter adapter;
	public static List<AdObj> adObjList = new ArrayList<AdObj>();
	  
	private ProgressDialog pDialog; 
	 private static final String url = "http://sonarbangla.hoxty.com/data.php";
	
	
	 
	
public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
         
        return rootView;
    }
	
	
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			
			 View v = getView();
			 	
				
				if(adObjList!=null){
				 pDialog = new ProgressDialog(getActivity());
				 // Showing progress dialog before making http request
			 	 pDialog.setMessage("Loading...");
				 pDialog.show();
				RequestThread requestThread = new RequestThread();
				requestThread.start();
				}
				 listView = (ListView) v.findViewById(R.id.list);
					adapter = new CustomListAdapter(getActivity(), adObjList);
					listView.setAdapter(adapter);
				
				
				
				
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						
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
									ad_obj.setImageNmae( obj.getString("image"));
									ad_obj.setRating(obj.getString("price"));
									ad_obj.setQuantity(obj.getString("quantity"));
									ad_obj.setLocation(obj.getString("location"));
									ad_obj.setDetails(obj.getString("details"));
									ad_obj.setUsername(obj.getString("user_name"));
                                 	ad_obj.setCategory(obj.getString("category"));
                                  	ad_obj.setDate(obj.getString("date"));
									ad_obj.setPhone(obj.getString("phone"));
									// adding movie to add_obj array
									adObjList.add(ad_obj);
								
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
	 
}