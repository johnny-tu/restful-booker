package com.servicerocket.restfulbookingapi.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Booking {

		@Id
		private ObjectId _id;
		
		private String firstname;
		private String lastname;
		private String totalprice;
		private String depositpaid;
		private String additionalneeds;
		private BookingDates bookingdates;
		
		public Booking(ObjectId _id, String firstname, String lastname, String totalprice, String depositpaid,
				String additionalneeds, BookingDates bookingdates) 
		{
			super();
			this._id = _id;
			this.firstname = firstname;
			this.lastname = lastname;				
			this.totalprice = totalprice;
			this.depositpaid = depositpaid;
			this.additionalneeds = additionalneeds;
			this.bookingdates = bookingdates;
		}

		public String get_Id() {
			return _id.toHexString();
		}

		public void set_Id(ObjectId _id) {
			this._id = _id;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getTotalprice() {
			return totalprice;
		}

		public void setTotalprice(String totalprice) {
			this.totalprice = totalprice;
		}

		public String getDepositpaid() {
			return depositpaid;
		}

		public void setDepositpaid(String depositpaid) {
			this.depositpaid = depositpaid;
		}

		public String getAdditionalneeds() {
			return additionalneeds;
		}

		public void setAdditionalneeds(String additionalneeds) {
			this.additionalneeds = additionalneeds;
		}

		public BookingDates getBookingDates() {
			return bookingdates;
		}

		public void setBookingDates(BookingDates bookingDates) {
			this.bookingdates = bookingDates;
		}
		
}
