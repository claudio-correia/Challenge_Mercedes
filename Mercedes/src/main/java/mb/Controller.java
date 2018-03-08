package mb;


import mb.DataStructures.DataManager;
import mb.DataStructures.Dealer;
import mb.DataStructures.Vehicle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class Controller {


    private DataManager DataManager = new DataManager();


    @RequestMapping(value = "/getvehicles", method =RequestMethod.GET)
    public Vehicle[] GetVehicles(@RequestParam(value="type", defaultValue="Model") String type) {
        return DataManager.GetOrderedVehicles(type);
    }

    @RequestMapping(value = "/getclosestvehicle", method =RequestMethod.GET)
    public Dealer GetClosestVehicle(
            @RequestParam(value="model", defaultValue="") String model,
            @RequestParam(value = "fuel", defaultValue="") String fuel,
            @RequestParam(value = "transmission", defaultValue="") String transmission,
            @RequestParam(value = "latitude", defaultValue="") float latitude,
            @RequestParam(value = "longitude", defaultValue="") float longitude
            )
    {


        return DataManager.GetClosestVehicle(model,fuel,transmission,latitude,longitude);

    }
    @RequestMapping(value = "/getallclosestvehicles", method =RequestMethod.GET)
    public Dealer[] GetAllClosestVehicles(
            @RequestParam(value="model", defaultValue="") String model,
            @RequestParam(value = "fuel", defaultValue="") String fuel,
            @RequestParam(value = "transmission", defaultValue="") String transmission,
            @RequestParam(value = "latitude", defaultValue="") float latitude,
            @RequestParam(value = "longitude", defaultValue="") float longitude
    )
    {


        return DataManager.GetAllClosestVehicles(model,fuel,transmission,latitude,longitude);

    }

    @RequestMapping(value = "/createbooking", method =RequestMethod.POST)
    public String CreateBooking(
            @RequestParam(value="vehicleId", defaultValue="") String vehicleId,
            @RequestParam(value="firstName", defaultValue="") String firstName,
            @RequestParam(value="lastName", defaultValue="") String lastName,
            @RequestParam(value="pickupDate", defaultValue="") String pickupDate
    ) {

        String res = DataManager.CreateBooking(vehicleId,firstName,lastName,pickupDate);

        return res;


    }

    @RequestMapping(value = "/cancelbooking", method =RequestMethod.POST)
    public String CancelBooking(
            @RequestParam(value="bookid", defaultValue="") String BookId,
            @RequestParam(value="cancelledreason", defaultValue="") String cancelledReason
    ) {

        String res = DataManager.CancelBooking(BookId,cancelledReason);

        return res;


    }

}
