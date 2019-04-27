package com.github.navisarv.model;
public class Address{
		private String city;
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public ZipCode getZip() {
			return zip;
		}
		public void setZip(ZipCode zip) {
			this.zip = zip;
		}
		private String state;
		private ZipCode zip;
	}