package ict.hackathon.shonarbangla;




import ict.hackathon.shonarbangla.HomeFragment.RequestThread;
import ict.hackathon.shonarbangla.adapter.CustomListAdapter;
import ict.hackathon.shonarbangla.model.AdObj;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class FaqFragment extends Fragment   {
  public static List<AdObj> adObjList = new ArrayList<AdObj>();
  ImageButton imageButton; 
  TextView Check;
public FaqFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
         
        return rootView;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		 View v = getView();
		 	 
			
	}

  
	
}