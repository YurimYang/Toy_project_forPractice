package domain.itinerary.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.itinerary.dto.ItineraryDTO;
import domain.itinerary.service.ItineraryService;
import global.util.JsonUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ItineraryController {

    public static void main(String[] args) throws FileNotFoundException {

        //ItineraryService.CheckItinerary();
        //ItineraryService.getItineraryListFromTrip(1);
        ItineraryService.DeleteItinerary(1,1);


    }

}
