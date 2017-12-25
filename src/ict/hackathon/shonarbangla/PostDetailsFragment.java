package ict.hackathon.shonarbangla;

 

import java.util.ArrayList;
import java.util.List;

import ict.hackathon.shonarbangla.app.AppController;
import ict.hackathon.shonarbangla.model.AdObj;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PostDetailsFragment extends Fragment implements OnClickListener{
	
	TextView pName,pQuantity,pDesc,pPrice,pLoc,pPhone,pDate;
	Button callButton;

	int  position,find_check; 
	public static List<AdObj> adObjList1 = new ArrayList<AdObj>();
	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
public PostDetailsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.post_detail_fragment, container, false);
         
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
		    
		    callButton=(Button) v.findViewById(R.id.callButton);
		    callButton.setOnClickListener(this);
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
	public void onClick(View arg0) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		 	callIntent.setData(Uri.parse("tel:"+HomeFragment.adObjList.get(position).getPhone()));
			startActivity(callIntent);
	}
	
	 
}
