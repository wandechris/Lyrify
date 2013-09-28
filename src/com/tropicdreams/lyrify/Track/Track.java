package com.tropicdreams.lyrify.Track;

import java.io.Serializable;

import com.google.api.client.util.Key;

public class Track implements Serializable {

	private static final long serialVersionUID = -5353862537273895653L;

	@Key
	public String track_id;
	
	@Key
	public String TrackName;
	
	@Key
	public String lyrics_id;
	
	@Key
	public String icon;
	
	@Key
	public String artistName;
	
	@Key
	public String albumName;
	
}
