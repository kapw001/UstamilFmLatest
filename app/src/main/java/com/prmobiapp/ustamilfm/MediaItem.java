package com.prmobiapp.ustamilfm;

public class MediaItem {
	String title;
	String artist;
	String album;
	String path;
	String ShowTime;
	String ShowEndTime;
	String sTime;
	String eTime;
	String urlImage;
	int duration;
	int albumId;
	String composer;
	String TamilDes;
	String rjimage;
	String favorite;
	boolean f;

	String programStartTime;

	public String getProgramStartTime() {
		return programStartTime;
	}

	public void setProgramStartTime(String programStartTime) {
		this.programStartTime = programStartTime;
	}

	public void setRjimage(String rjimage) {
		this.rjimage = rjimage;
	}

	public String getRjimage() {
		return rjimage;
	}

	public String getTamilDes() {
		return TamilDes;
	}

	public void setTamilDes(String tamilDes) {
		TamilDes = tamilDes;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public void setF(boolean f) {
		this.f = f;
	}

	public boolean isF() {
		return f;
	}

	public String getShowEndTime() {
		return ShowEndTime;
	}

	public void setShowEndTime(String showEndTime) {
		ShowEndTime = showEndTime;
	}

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}



	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}


	@Override
	public String toString() {
		return title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getfavorite() {

		return this.favorite;
	}

	public void setfavorite(String favoritevalue) {

		this.favorite = favoritevalue;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public void setShowTime(String showTime) {
		ShowTime = showTime;
	}
	public String getShowTime() {
		return ShowTime;
	}
}
