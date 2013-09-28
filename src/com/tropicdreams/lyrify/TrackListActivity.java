package com.tropicdreams.lyrify;

import com.tropicdreams.lyrify.fragments.TrackList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TrackListActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.track_activity);
		
		if(findViewById(R.id.FragmentContainer) != null){
			if (savedInstanceState != null ){
				return;
			}
			TrackList listFragment = new TrackList();
			listFragment.setArguments(getIntent().getExtras());			
			getSupportFragmentManager().beginTransaction()
					.add(R.id.FragmentContainer, listFragment).commit();
		}
	}
	

}
