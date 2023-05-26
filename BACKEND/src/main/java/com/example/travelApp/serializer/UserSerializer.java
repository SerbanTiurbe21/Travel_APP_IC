package com.example.travelApp.serializer;

import com.example.travelApp.entities.Trip;
import com.example.travelApp.entities.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Set;

public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("userId", user.getUserId());
        jsonGenerator.writeStringField("name", user.getName());
        jsonGenerator.writeStringField("email", user.getEmail());

        jsonGenerator.writeFieldName("trips");
        jsonGenerator.writeStartArray();
        Set<Trip> trips = user.getTrips();
        if (trips != null) {
            for (Trip trip : trips) {
                jsonGenerator.writeStartObject();
                if(trip.getId() != null){
                    jsonGenerator.writeNumberField("id", trip.getId());
                }else{
                    jsonGenerator.writeNullField("id");
                }

                if(trip.getTripName() != null){
                    jsonGenerator.writeStringField("trip_name", trip.getTripName());
                }else{
                    jsonGenerator.writeNullField("trip_name");
                }

                if(trip.getStartDate() != null){
                    jsonGenerator.writeStringField("start_date", trip.getStartDate());
                }else{
                    jsonGenerator.writeNullField("start_date");
                }

                if(trip.getEndDate() != null){
                    jsonGenerator.writeStringField("end_date", trip.getEndDate());
                }else{
                    jsonGenerator.writeNullField("end_date");
                }

                if(trip.getDestination() != null){
                    jsonGenerator.writeStringField("destination", trip.getDestination());
                }else{
                    jsonGenerator.writeNullField("destination");
                }

                if(trip.getTripType() != null){
                    jsonGenerator.writeStringField("trip_type", trip.getTripType());
                }else{
                    jsonGenerator.writeNullField("trip_type");
                }

                if(trip.getPrice() != null){
                    jsonGenerator.writeNumberField("price", trip.getPrice());
                }else{
                    jsonGenerator.writeNullField("price");
                }

                if(trip.getPrice() != null){
                    jsonGenerator.writeNumberField("rating", trip.getRating());
                }else{
                    jsonGenerator.writeNullField("rating");
                }

                if(trip.getPhotoUri() != null){
                    jsonGenerator.writeStringField("photo_uri", trip.getPhotoUri());
                }else{
                    jsonGenerator.writeNullField("rating");
                }

                if(trip.getTemperature() != null){
                    jsonGenerator.writeNumberField("temperature", trip.getTemperature());
                }else{
                    jsonGenerator.writeNullField("temperature");
                }

                if (trip.getIsFavourite() != null) {
                    jsonGenerator.writeNumberField("is_favourite", trip.getIsFavourite() ? 1 : 0);
                } else {
                    jsonGenerator.writeNullField("is_favourite");
                }
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
