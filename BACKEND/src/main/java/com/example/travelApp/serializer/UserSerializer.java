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
                jsonGenerator.writeNumberField("id", trip.getId());
                jsonGenerator.writeStringField("trip_name", trip.getTripName());
                jsonGenerator.writeStringField("start_date", trip.getStartDate());
                jsonGenerator.writeStringField("end_date", trip.getEndDate());
                jsonGenerator.writeStringField("destination", trip.getDestination());
                jsonGenerator.writeStringField("trip_type", trip.getTripType());
                jsonGenerator.writeNumberField("price", trip.getPrice());
                jsonGenerator.writeNumberField("rating", trip.getRating());
                jsonGenerator.writeStringField("photo_uri", trip.getPhotoUri());
                jsonGenerator.writeNumberField("temperature", trip.getTemperature());
                jsonGenerator.writeNumberField("is_favourite", trip.getIsFavourite() ? 1 : 0);
                // Add other trip fields if necessary
                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
