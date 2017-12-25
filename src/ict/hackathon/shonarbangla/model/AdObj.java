package ict.hackathon.shonarbangla.model;

public class AdObj {
	
	
	private String title, thumbnailUrl;
 	private String rating; 
private String quantity,location,date,phone,details,username,category , id,imageNmae;





public String getImageNmae() {
	return imageNmae;
}

public void setImageNmae(String imageNmae) {
	this.imageNmae = imageNmae;
}

public AdObj() {
}

public AdObj(String name, String thumbnailUrl,  String rating,String category) {
	this.title = name;
	this.thumbnailUrl = thumbnailUrl;
 
	this.rating = rating; 
	this.category=category;
}
 
public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}



	public String getQuantity() {
	return quantity;
      }

public void setQuantity(String quantity) {
	this.quantity = quantity;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getDetails() {
	return details;
}

public void setDetails(String details) {
	this.details = details;
}


	public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	 

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}




}
