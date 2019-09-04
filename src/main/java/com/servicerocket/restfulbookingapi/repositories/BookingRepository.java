package com.servicerocket.restfulbookingapi.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.servicerocket.restfulbookingapi.models.Booking;


public interface BookingRepository extends MongoRepository<Booking, String> {
	Booking findBy_id(ObjectId _id);
}
