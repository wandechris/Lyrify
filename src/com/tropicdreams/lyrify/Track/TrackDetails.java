package com.tropicdreams.lyrify.Track;

import java.io.Serializable;

import com.google.api.client.util.Key;

public class TrackDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1287627020542118036L;
	
	@Key
	public String status;
	
	@Key
	public Track result;

	@Override
	public String toString() {
		if (result!=null) {
			return result.toString();
		}
		return super.toString();
	}

}
