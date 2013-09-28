package com.tropicdreams.lyrify;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends SherlockActivity {
	
	Button search;
	EditText trackName;
	EditText trackArtist;
	
	String name;
	String artist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		trackName = (EditText)findViewById(R.id.txt_name);
		trackArtist = (EditText)findViewById(R.id.txt_artist);
		
		search = (Button)findViewById(R.id.btn_search);
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				name = trackName.getText().toString();
				artist = trackArtist.getText().toString();
				
				Intent i = new Intent(MainActivity.this, TrackListActivity.class);
				i.putExtra("name", name);
				i.putExtra("artist", artist);
				startActivity(i);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
