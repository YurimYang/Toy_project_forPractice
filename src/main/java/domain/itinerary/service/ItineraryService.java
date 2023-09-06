package domain.itinerary.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import domain.itinerary.dto.ItineraryDTO;
import domain.trip.dto.TripDTO;
import domain.trip.service.TripService;
import global.util.JsonUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItineraryService {

    private static final String JSONPATH = "src/main/resources/trip/json";

    //특정 여행의 여정을 조회하는 api
    public static List<ItineraryDTO> SearchItinerary() throws FileNotFoundException {
        //FileReader 생성
//        FileReader fr = new FileReader(JSONPATH);
//        BufferedReader br = new BufferedReader(fr);
//        //TripDTO trip = JsonUtil.fromJson(br, TripDTO.class);
        List<TripDTO> tripList = TripService.getTripListFromJson();
        //System.out.println(tripList);
        //System.out.println("-----------------");

        //Json 파일 읽어서, Itinerary객체로 변환
//        if (trip.getItineraries() != null) {
//            for(ItineraryDTO obj : trip.getItineraries()){
//                System.out.println(obj.toString());
//                ItineraryDTO itinerary = JsonUtil.fromJson(obj.toString(), ItineraryDTO.class);
//                System.out.println(itinerary.getCheckOut());
//            }
//        }

        List<ItineraryDTO> ItineraryList = new ArrayList<>();
        for (TripDTO trip : tripList) {
            if (trip.getItineraries() != null) {
                //System.out.println(trip.getItineraries());
                FileReader Fr = new FileReader(JSONPATH);
                JsonElement element = JsonParser.parseReader(Fr);
                JsonObject tripObj = element.getAsJsonObject();
                JsonArray itineraryArr = (JsonArray) tripObj.get("itineraries");
                ItineraryDTO[] array = JsonUtil.fromJson(itineraryArr.toString(),
                    ItineraryDTO[].class);
                ItineraryList = Arrays.asList(array);
            }
            System.out.println(ItineraryList);
        }

        return ItineraryList;

    }

    /**
     * json에서 특정 여정을 조회하는 메서드
     *
     * @return
     * @throws FileNotFoundException
     * @param_id 조회할 특정 여행 trip_id 값
     */
    public static List<ItineraryDTO> getItineraryListFromTrip(int TripId)
        throws FileNotFoundException {
        TripDTO trip = TripService.getTripFromJson(TripId);
        List<ItineraryDTO> ItineraryList = new ArrayList<>();
        if (trip.getItineraries() != null) {
            FileReader fr = new FileReader(JSONPATH + "/trip_" + TripId + ".json");
            JsonElement element = JsonParser.parseReader(fr);
            JsonObject tripObj = element.getAsJsonObject();
            JsonArray itineraryArr = (JsonArray) tripObj.get("itineraries");
            ItineraryDTO[] array = JsonUtil.fromJson(itineraryArr.toString(), ItineraryDTO[].class);
            ItineraryList = Arrays.asList(array);
        }
        System.out.println(ItineraryList);
        return ItineraryList;
    }

    /**
     * json에서 특정 여정을 삭제하는 메서드
     *
     * @param_id TripId, ItineraryId
     */
    public static void deleteItinerary(int TripId, int ItineraryId) throws FileNotFoundException {
        FileReader fr = new FileReader(JSONPATH + "/trip_" + TripId + ".json");
        JsonElement element = JsonParser.parseReader(fr);
        JsonObject tripObj = element.getAsJsonObject();
        JsonArray itineraryArr = tripObj.get("itineraries").getAsJsonArray();
        for (int i = 0; i < itineraryArr.size(); i++) {
            JsonObject jsonObj = itineraryArr.get(i).getAsJsonObject();
            if (jsonObj.get("itinerary_id").getAsInt() == ItineraryId) {
                itineraryArr.remove(jsonObj);
            }
        }
//        for(JsonElement obj : itineraryArr){
//            JsonObject jsonObj = obj.getAsJsonObject();
//
//        }
        ItineraryDTO[] newItinerary = JsonUtil.fromJson(itineraryArr.toString(),
            ItineraryDTO[].class);
        List<ItineraryDTO> newList = Arrays.asList(newItinerary);
        TripDTO trip = TripService.getTripFromJson(TripId);
        trip.setItineraries(newList);
        String newTrip = JsonUtil.toJson(trip);
        try {
            FileWriter file = new FileWriter(JSONPATH + "/trip_" + TripId + ".json");
            file.write(newTrip);
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteItinerary(int TripId, int ItineraryId) {
        TripDTO trip = TripService.getTripFromJson(TripId);
        List<ItineraryDTO> itineraryList = trip.getItineraries();
        for (int i = 0; i < trip.getItineraries().size(); i++) {
            if (trip.getItineraries().get(i).getId() == ItineraryId) {
                trip.getItineraries().remove(i);
            }
        }
        String newTrip = JsonUtil.toJson(trip);
        try {
            FileWriter file = new FileWriter(JSONPATH + "/trip_" + TripId + ".json");
            file.write(newTrip);
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static boolean CheckItinerary() throws FileNotFoundException {
        FileReader fr = new FileReader(
            "C:\\Users\\lg\\IdeaProjects\\toy_assign_Fial\\src\\main\\resources\\itinerary\\json\\itinerary_1.json");
        JsonElement element = JsonParser.parseReader(fr);
        JsonObject jsonObject = element.getAsJsonObject();
        JsonArray jsonArray = (JsonArray) jsonObject.get("itineraries");
        System.out.println(jsonArray);
        if (jsonArray != null) {
            return true;
        }
        return false;
    }

}
