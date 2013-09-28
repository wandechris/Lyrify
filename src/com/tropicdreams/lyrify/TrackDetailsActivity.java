package com.tropicdreams.lyrify;

import org.json.JSONException;
import org.json.JSONObject;

import com.tropicdreams.lyrify.managers.AlertDialogManager;
import com.tropicdreams.lyrify.managers.ConnectionDetector;
import com.tropicdreams.lyrify.managers.Requests;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class TrackDetailsActivity extends Activity {
	Intent i;
	TextView name;
	TextView artist;
	TextView lyric;
	
	AlertDialogManager alert = new AlertDialogManager();

	Boolean isInternetPresent = false;
	
	ConnectionDetector cd;
	
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.track_details);
		
		Load y = new Load();
		y.execute();
		
		name = (TextView)findViewById(R.id.list_name);
		artist = (TextView)findViewById(R.id.list_vic);
		lyric = (TextView)findViewById(R.id.lyricview);
		
	}
	
	String tName;
	String tArtist;
	
class Load extends AsyncTask<String, Void, JSONObject> {
		
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = new ProgressDialog(TrackDetailsActivity.this);
			pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Track Lyrics..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}


	    protected JSONObject doInBackground(String... urls) {
	        try {
	        	
	        	i = TrackDetailsActivity.this.getIntent();
	        	tName = i.getExtras().getString("name");
	        	tArtist = i.getExtras().getString("artist");
	        	String lyrics = i.getExtras().getString("lyric");
	        	
	        	Requests request = new Requests();

	        	JSONObject json = request.getLyric(lyrics);
	        	
	        	return json;
	        } catch (Exception e) {
	           // this.exception = e;
	            return null;
	        }
			
	    }

	    protected void onPostExecute(JSONObject feed) {
	    	name.setText(tName);
        	artist.setText(tArtist);
	    	try {
	    		JSONObject message = feed.getJSONObject("message");
	    		JSONObject body = message.getJSONObject("body");
	    		JSONObject header = message.getJSONObject("header");
	    		int statusCode = header.getInt("status_code");
	    		
	    		if(statusCode == 200 ){
						JSONObject lyrics = body.getJSONObject("lyrics");
						
						String trackLyrics = lyrics.getString("lyrics_body");
						lyric.setText(trackLyrics);
						lyric.setMovementMethod(new ScrollingMovementMethod());
	    			
	    		}else if(statusCode == 400){
					alert.showAlertDialog(TrackDetailsActivity.this, "Lyrify",
							"Sorry no places found. Try to change the track name",
							false);
				}
				else if(statusCode == 401)
				{
					alert.showAlertDialog(TrackDetailsActivity.this, "Lyrify Error",
							"oops... Try again",
							false);
				}
				else if(statusCode == 402)
				{
					alert.showAlertDialog(TrackDetailsActivity.this, "Lyrify Error",
							"you've exceeded daily requests limits . try again tommorrow",
							false);
				}
				else if(statusCode == 403)
				{
					alert.showAlertDialog(TrackDetailsActivity.this, "Lyrify Error",
							"You are not authorized to perform this operation",
							false);
				}
				
				else if(statusCode == 500)
				{
					alert.showAlertDialog(TrackDetailsActivity.this, "Lyrify Error",
							"Our system is a bit busy at the moment and your request can’t be satisfied",
							false);
				}
				
				else
				{
					alert.showAlertDialog(TrackDetailsActivity.this, "Lyrify Error",
							"Sorry error occured try again.",
							false);
				}
	    		
	    		
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			pDialog.dismiss();
	    }
	}
	

}
