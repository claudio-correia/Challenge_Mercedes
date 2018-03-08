package mb.DataStructures;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class DataManager {

    public static final String[] ListTypes = new String[] {"model","fuel","transmission","dealer"};
    private Root root;
    private Vehicle[] AllVehicles;

    private String TmpType;
    private float TmpLatitude;
    private float TmpLongitude;

    private static AtomicLong idCounter = new AtomicLong();

    public DataManager() {


        try {
            //read json file data to String
            byte[] jsonData = Files.readAllBytes(Paths.get("dataset.JSON"));

            //create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            //convert json string to object
            root = objectMapper.readValue(jsonData, Root.class);


            AllVehicles = MergeVehicles();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Vehicle[] GetOrderedVehicles(String type) {

        if(type == null || !Arrays.asList(ListTypes).contains(type.toLowerCase())){
            return  new Vehicle[0];
        }
        TmpType = type.toLowerCase();
        Arrays.sort(AllVehicles, CompVehicles );
        return AllVehicles;
    }

    public Dealer GetClosestVehicle(String model, String fuel, String transmission, float latitude, float longitude) {

        if(model.equals("") || model == null || fuel.equals("") || fuel == null ||  transmission.equals("") || transmission == null ){
            return  null;
        }

        TmpLatitude = latitude;
        TmpLongitude = longitude;

        Dealer[] dealers = root.getDealers();

        Arrays.sort(dealers, CompDealers);

        for (Dealer d : root.getDealers()) {
            for (Vehicle v : d.getVehicles()){
                if(v.getModel().equals(model) && v.getFuel().equals(fuel) && v.getTransmission().equals(transmission))
                    return d;

            }

        }

       return  null;

    }

    public Dealer[] GetAllClosestVehicles(String model, String fuel, String transmission, float latitude, float longitude) {
        if(model.equals("") || model == null || fuel.equals("") || fuel == null ||  transmission.equals("") || transmission == null ){
            return   new Dealer[0];
        }

        TmpLatitude = latitude;
        TmpLongitude = longitude;

        Dealer[] dealers = root.getDealers();

        Arrays.sort(dealers, CompDealers);

        //List myList = new ArrayList();
        List<Dealer> myList = new ArrayList<Dealer>();
        int count = 0;


        for (Dealer d : root.getDealers()) {
            for (Vehicle v : d.getVehicles()){
                if(v.getModel().equals(model) && v.getFuel().equals(fuel) && v.getTransmission().equals(transmission)){
                    count++;
                    myList.add(d);
                    break;
                }



            }

        }

        Dealer[] res = new Dealer[count];
        int i =0;
        for (Dealer d: myList ) {
            res[i]=d;
            i++;
        }


        return  res;
    }


    public String CreateBooking(String vehicleId, String firstName, String lastName, String pickupDate) {

        String id="error";

        if (firstName.equals("") || firstName==null || lastName.equals("") || lastName ==null)
            return id;

        try {
            Vehicle v = GetVehicleById(vehicleId);


            boolean Available = VehicleAvailability(v,pickupDate);

            Available = Available && BookingAvailability(root.getBookings(),vehicleId,pickupDate);

            if(Available)
                id = Book(vehicleId,firstName,lastName,pickupDate);


        } catch (Exception e) {
            //do nothing
            //e.printStackTrace();

        }




        return id;
    }


    public String CancelBooking(String bookId, String cancelledReason) {
        String Cancel= "error";

        if(bookId.equals("")|| bookId == null || cancelledReason.equals("") || cancelledReason== null)
            return Cancel;

        for (Booking b : root.getBookings()) {
            if(bookId.equals(b.getId()) && b.getCancelledAt()==null){
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());

                b.setCancelledAt(timeStamp);
                b.setCancelledReason(cancelledReason);
                Cancel = "cancel";
            }
        }


        return Cancel;

    }

    //////////////////////////
    // auxiliary functions


    private String Book(String vehicleId, String firstName, String lastName, String pickupDate) {

        Booking b = new Booking();
        String id = createID();
        b.setId(id);
        b.setVehicleId(vehicleId);
        b.setFirstName(firstName);
        b.setLastName(lastName);
        b.setPickupDate(pickupDate);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());

        b.setCreatedAt(timeStamp);

        root.addBookings(b);

        return id;

    }




    private static String createID()
    {
        return String.valueOf(idCounter.getAndIncrement());
    }

    private boolean BookingAvailability(Booking[] bookings, String vehicleId, String pickupDate) throws ParseException {

        boolean Available = true;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = formatter.parse(pickupDate);


        for (Booking b : bookings) {
            if(b.getVehicleId().equals(vehicleId)) {
                Date TmpDate = formatter.parse(b.getPickupDate());
                if(TmpDate.equals(date) && b.getCancelledAt() == null){
                    Available=false;
                    break;
                }
            }

        }

        return Available;
    }

    private boolean VehicleAvailability(Vehicle v, String pickupDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = formatter.parse(pickupDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE", Locale.ENGLISH);
        String dayOfWeek = dateFormat.format(date).toLowerCase();

        String[] hours = v.getAvailability().get(dayOfWeek);



        if (hours==null)
            return false;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        String pickupHour;
        if(calendar.get(Calendar.MINUTE) == 0) {
            pickupHour= Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) + "00";
        }
        else {
            pickupHour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) + Integer.toString(calendar.get(Calendar.MINUTE));

        }

        for (String h : hours) {
            if(h.equals(pickupHour))
                return true;

        }


        return false;
    }




    public Vehicle GetVehicleById(String id){

        for (Dealer d : root.getDealers()) {
            for (Vehicle v : d.getVehicles()){
                if(v.getId().equals(id)){
                    return v;

                }


            }

        }
        return null;

    }


    public  Vehicle[] MergeVehicles()
    {
        // Count the number of arrays passed for merging and the total size of resulting array
        int count = 0;

        for (Dealer d : root.getDealers()) {
            count += d.getVehicles().length;
        }

        // Create new array and copy all array contents
        Vehicle[] mergedArray = (Vehicle[])  java.lang.reflect.Array.newInstance(root.getDealers()[0].getVehicles()[0].getClass(), count);
        int start = 0;

        for (Dealer d : root.getDealers()) {

            for(Vehicle v : d.getVehicles()){
                v.setDealer(d.getId());
            }


            System.arraycopy(d.getVehicles(), 0, mergedArray, start, d.getVehicles().length);
            start += d.getVehicles().length;
        }
        return mergedArray;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }


    Comparator<Dealer> CompDealers = (Dealer a, Dealer b) -> {

        double distanceA = Math.hypot(TmpLatitude-a.getLatitude(), TmpLongitude-a.getLongitude());
        double distanceB = Math.hypot(TmpLatitude-b.getLatitude(), TmpLongitude-b.getLongitude());

        return Double.compare(distanceA, distanceB);
    };

    Comparator<Vehicle> CompVehicles = (Vehicle a, Vehicle b) -> {
        int index = Arrays.asList(ListTypes).indexOf(TmpType);

        int res=0;


        for(int i=0; i<4; i++){
            res = CompareVehicle(a,b,index);
            if(res == 0){
                index = (index+1)%4;
            }
            else return res;
        }


        return res;
    };

    public int  CompareVehicle(Vehicle a,Vehicle b,int index){
        int res=0;
        switch (index){
            case 0: res=a.getModel().compareToIgnoreCase(b.getModel());
                    break;
            case 1: res=a.getFuel().compareToIgnoreCase(b.getFuel());
                    break;
            case 2: res=a.getTransmission().compareToIgnoreCase(b.getTransmission());
                    break;
            case 3: res=a.getDealer().compareToIgnoreCase(b.getDealer());
                    break;
        }
        return res;

    }


    public static AtomicLong CetCounter(){
        return idCounter;
    }


}
