package com.github.navisarv.model;
public class Employee{
		private Name name;
		public Name getName() {
			return name;
		}
		public void setName(Name name) {
			this.name = name;
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		private Address address;
		
	}