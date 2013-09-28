package com.tropicdreams.lyrify.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tropicdreams.lyrify.adapter.TrackAdapter;
import com.tropicdreams.lyrify.managers.AlertDialogManager;
import com.tropicdreams.lyrify.managers.ConnectionDetector;
import com.tropicdreams.lyrify.managers.Requests;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.view.View;
import android.widget.ListView;

public class TrackList extends ListFragment {

	// KEY Strings
	public static String KEY_TRACK_ID = "track_id"; // id of the place
	public static String KEY_TRACK_NAME = "track_name"; // name of the place
	public static String KEY_LYRICS_ID = "lyrics_id"; // name of the place
	// public static String KEY_ICON = "icon"; // name of the place
	public static String KEY_ARTIST_NAME = "artist_name"; // Place area name
	public static String KEY_ALBUM_NAME = "album_name"; // Place area name

	Intent i;

	TrackAdapter adapter ;
		
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	// contacts JSONArray
	JSONArray trackList = null;
	
	Boolean isInternetPresent = false;
	
	ConnectionDetector cd;
	
	ProgressDialog pDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		cd = new ConnectionDetector(getActivity());
		
		// Check if Internet present
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(getActivity(), "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		
		Load h = new Load();
		h.execute();

				
	
	}
	public ArrayList<HashMap<String, String>> trackListItems = new ArrayList<HashMap<String, String>>();
	
	class Load extends AsyncTask<String, Void, JSONObject> {
		
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Track List..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}


	    protected JSONObject doInBackground(String... urls) {
	        try {
	        	
	        	i = getActivity().getIntent();
	        	String tName = i.getExtras().getString("name");
	        	String tArtist = i.getExtras().getString("artist");
	        	
	        	Requests request = new Requests();

	        	JSONObject json = request.searchTrack(tName, tArtist);
	        	
	        	return json;
	        } catch (Exception e) {
	           // this.exception = e;
	            return null;
	        }
			
	    }

	    protected void onPostExecute(JSONObject feed) {
	    	try {
	    		JSONObject message = feed.getJSONObject("message");
	    		JSONObject body = message.getJSONObject("body");
	    		JSONObject header = message.getJSONObject("header");
	    		int statusCode = header.getInt("status_code");
	    		
	    		if(statusCode == 200 ){
	    			
	    			// Getting Array of Contacts
					trackList = body.getJSONArray("track_list");
					
					// looping through All Tracks
					for(int i = 0; i < trackList.length(); i++){
						JSONObject c = trackList.getJSONObject(i);
						JSONObject track = c.getJSONObject("track");
						
						
						// Storing each json item in variable
						String trackId = track.getString(KEY_TRACK_ID);
						String trackName = track.getString(KEY_TRACK_NAME);
						String artistName = track.getString(KEY_ARTIST_NAME);
						String albumName = track.getString(KEY_ALBUM_NAME);
						String lyricsId = track.getString(KEY_LYRICS_ID);
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						
						// adding each child node to HashMap key => value
						map.put(KEY_TRACK_ID, trackId);
						map.put(KEY_TRACK_NAME, trackName);
						map.put(KEY_ARTIST_NAME, artistName);
						map.put(KEY_ALBUM_NAME, albumName);
						map.put(KEY_LYRICS_ID, lyricsId);

						// adding HashList to ArrayList
						trackListItems.add(map);
					}
	    			
	    		}else if(statusCode == 400){
					alert.showAlertDialog(getActivity(), "Lyrify",
							"Sorry no places found. Try to change the track name",
							false);
				}
				else if(statusCode == 401)
				{
					alert.showAlertDialog(getActivity(), "Lyrify Error",
							"oops... Try again",
							false);
				}
				else if(statusCode == 402)
				{
					alert.showAlertDialog(getActivity(), "Lyrify Error",
							"you've exceeded daily requests limits . try again tommorrow",
							false);
				}
				else if(statusCode == 403)
				{
					alert.showAlertDialog(getActivity(), "Lyrify Error",
							"You are not authorized to perform this operation",
							false);
				}
				
				else if(statusCode == 500)
				{
					alert.showAlertDialog(getActivity(), "Lyrify Error",
							"Our system is a bit busy at the moment and your request can’t be satisfied",
							false);
				}
				
				else
				{
					alert.showAlertDialog(getActivity(), "Lyrify Error",
							"Sorry error occured try again.",
							false);
				}
	    		
	    		
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			/**
			 * Updating parsed JSON data into ListView
			 * */
		  adapter = new TrackAdapter(getActivity(), trackListItems);

			setListAdapter(adapter);
			
			pDialog.dismiss();
	    }
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// String LyricId = trackListItems.get(position).get(KEY_LYRICS_ID); 
	}
	
	
}
