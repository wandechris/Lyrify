package com.tropicdreams.lyrify;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.tropicdreams.lyrify.fragments.TrackList;

import android.os.Bundle;

public class TrackListActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.track_activity);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
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
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        case android.R.id.home:
             finish();
             break;

        default:
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
	

}
