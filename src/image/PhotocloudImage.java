package image;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PhotocloudImage {
	
	private String username;
	private String imgPath;
	private String thumbnail;
	private String caption;
	private HashMap<String, String> comments;
	private ImageEnum imageEnum;
	private String imageUUID;
	
	private int likeCount;
	private int dislikeCount;
	
	
	/**
	 * Constructor for new images
	 * 
	 * @param username
	 * @param imgPath
	 * @param thumbnail
	 * @param caption
	 * @param comments
	 * @param imageEnum
	 */
	public PhotocloudImage(String username, String imgPath, String thumbnail, String caption, int likeCount, int dislikeCount, HashMap<String, String> comments, ImageEnum imageEnum) {
		this.username = username;
		this.imgPath = imgPath;
		this.thumbnail = thumbnail;
		this.caption = caption;
		this.likeCount = likeCount;
		this.dislikeCount = dislikeCount;
		this.comments = comments;
		this.imageEnum = imageEnum;
		
		imageUUID = UUID.randomUUID().toString();
	}
	
	
	/**
	 * Constructor for existing images
	 * 
	 * @param username
	 * @param imgPath
	 * @param thumbnail
	 * @param caption
	 * @param comments
	 * @param imageEnum
	 * @param imageUUID
	 */
	public PhotocloudImage(String username, String imgPath, String thumbnail, String caption, int likeCount, int dislikeCount, HashMap<String, String> comments, ImageEnum imageEnum, String imageUUID) {
		this.username = username;
		this.imgPath = imgPath;
		this.thumbnail = thumbnail;
		this.caption = caption;
		this.likeCount = likeCount;
		this.dislikeCount = dislikeCount;
		this.comments = comments;
		this.imageEnum = imageEnum;
		this.imageUUID = imageUUID;
	}

	
	//
	// Getters
	//


	public String getUsername() {
		return username;
	}


	public String getImgPath() {
		return imgPath;
	}


	public String getThumbnail() {
		return thumbnail;
	}


	public String getCaption() {
		return caption;
	}
	
	
	public int getLikeCount() {
		return likeCount;
	}
	
	
	public int getDislikeCount() {
		return dislikeCount;
	}


	public HashMap<String, String> getComments() {
		return comments;
	}


	public ImageEnum getImageEnum() {
		return imageEnum;
	}


	public String getImageUUID() {
		return imageUUID;
	}
	
	
	//
	// Setters
	//
	

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}


	public void setCaption(String caption) {
		this.caption = caption;
	}


	public void setComments(HashMap<String, String> comments) {
		this.comments = comments;
	}


	public void setImageEnum(ImageEnum imageEnum) {
		this.imageEnum = imageEnum;
	}
	
	
	public void likeImage() {
		likeCount++;
	}
	
	
	public void dislikeImage() {
		dislikeCount++;
	}
}
