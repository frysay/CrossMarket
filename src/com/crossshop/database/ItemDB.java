package com.crossshop.database;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemDB implements Parcelable {

	private long id; // Unique id of the object in the database
	private String itemId; // Item id code NOTE: it should be unique
	private String category; // name of the item
	private String name; // name of the item
	private String brand; // name of the brand
	private int image; // the position of the image
	private double prize; // prize of the item
	private int discount; // % of the discount on this item from 0 to 100 (0 means no discount)
	private String description; // a description of the item

	public ItemDB() {

	}

	public ItemDB(ItemDB item) {
		this.itemId = item.getItemId();
		this.category = item.getCategory();
		this.name = item.getNameAndMarket();
		this.brand = item.getBrand();
		this.image = item.getImage();
		this.prize = item.getPrize();
		this.discount = item.getDiscount();
		this.description = item.getDesciption();
	}

	public ItemDB(Parcel in) {
		id = in.readLong();
		itemId = in.readString();
		category = in.readString();
		name = in.readString();
		brand = in.readString();
		image = in.readInt();
		prize = in.readDouble();
		discount = in.readInt();
		description = in.readString();
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId (String itemId) {
		this.itemId = itemId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name.substring(0, name.indexOf("%"));
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameAndMarket(String name, String market) {
		this.name = name + "%" + market;
	}

	public String getNameAndMarket() {
		return name;
	}

	public String getMarket() {
		return name.substring(name.indexOf("%")+1, name.length());
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public double getPrize() {
		return prize;
	}

	public void setPrize(double prize) {
		this.prize = prize;
	}

	public int getDiscount() {
		return this.discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getDesciption() {
		return description;
	}

	public void setDesciption(String desciption) {
		this.description = desciption;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(itemId);
		dest.writeString(category);
		dest.writeString(name);
		dest.writeString(brand);
		dest.writeInt(image);
		dest.writeDouble(prize);
		dest.writeInt(discount);
		dest.writeString(description);
	}

	public static final Parcelable.Creator<ItemDB> CREATOR = new Parcelable.Creator<ItemDB>() {

		@Override
		public ItemDB createFromParcel(Parcel source) {
			return new ItemDB(source);
		}

		@Override
		public ItemDB[] newArray(int size) {
			return new ItemDB[size];
		}
	};

	// just used for testing purposes
	@Override
	public String toString() {
		return "1 - Id: " + id + "\n2 - Item: " + itemId + "\n3 - Category: " + category+ "\n4 - Name: " + name + "\n5 - Brand: " + brand + "\n6 - Image: " + image + "\n7 - Prize: " + prize + "\n8 - Discount: " + discount + "\n9 - Description: " + description + "\n10 - Fine!";
	}
}
