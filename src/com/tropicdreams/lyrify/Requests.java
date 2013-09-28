package com.tropicdreams.lyrify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class Requests {


	private static final String API_KEY = "6a5808dcb6e5ddbbff15be9625730298";

	private static final String TRACK_SEARCH_URL = "http://api.musixmatch.com/ws/1.1/track.search?";
	private static final String LYRIC_SEARCH_URL = "http://api.musixmatch.com/ws/1.1/track.lyrics.get?";
	
	private String KEY_TRACK = "q_track=";
	private String KEY_ARTIST = "q_artist=";
	private String KEY_TRACK_ID = "track_id=";
	
	private String name;
	private String artist;
	private String track;
	
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	
	public JSONObject searchTrack(String trackName, String trackArtist) {

		this.artist = trackArtist.replaceAll(" ", "%20");
		this.name = trackName.replaceAll(" ", "%20");
		
		String url = TRACK_SEARCH_URL+KEY_TRACK+name+"&"+KEY_ARTIST+artist+"&f_has_lyrics=1&apikey="+API_KEY;

		// Making HTTP request
				try {
					// defaultHttpClient
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);

					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();			

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					json = sb.toString();
				} catch (Exception e) {
					Log.e("Buffer Error", "Error converting result " + e.toString());
				}

				// try parse the string to a JSON object
				try {
					jObj = new JSONObject(json);
				} catch (JSONException e) {
					Log.e("JSON Parser", "Error parsing data " + e.toString());
				}

				// return JSON String
				return jObj;

	}
	
	public JSONObject getLyric(String trackId) {

		this.track = trackId;
		String url = LYRIC_SEARCH_URL+KEY_TRACK_ID+track+"&apikey="+API_KEY;

		// Making HTTP request
				try {
					// defaultHttpClient
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);

					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();			

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					json = sb.toString();
				} catch (Exception e) {
					Log.e("Buffer Error", "Error converting result " + e.toString());
				}

				// try parse the string to a JSON object
				try {
					jObj = new JSONObject(json);
				} catch (JSONException e) {
					Log.e("JSON Parser", "Error parsing data " + e.toString());
				}

				// return JSON String
				return jObj;

	}
}
