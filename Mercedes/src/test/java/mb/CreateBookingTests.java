package mb;

import mb.DataStructures.DataManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateBookingTests {

    @Autowired
    private MockMvc mockMvc;
    //inputs
    @Test
    public void TestBadInput() throws Exception {


        MvcResult result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4")
                .param("firstName", "Claudio")
                .param("lastName", "Correia")
                .param("pickupDate", "2018-01-021T10:00:00")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "it must be error","error", result.getResponse().getContentAsString());




    }

    @Test
    public void TestNoCarInput() throws Exception {


        MvcResult result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "noIdExist-5f6f-42a7-a4ca-de7e498d9ce4")
                .param("firstName", "Claudio")
                .param("lastName", "Correia")
                .param("pickupDate", "2018-01-01T10:00:00")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "it must be error","error", result.getResponse().getContentAsString());



    }
    @Test
    public void TestEmptyInput() throws Exception {


        MvcResult result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "")
                .param("firstName", "Claudio")
                .param("lastName", "Correia")
                .param("pickupDate", "2018-01-01T10:00:00")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "it must be error","error", result.getResponse().getContentAsString());

         result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4")
                .param("firstName", "Claudio")
                .param("lastName", "")
                .param("pickupDate", "2018-01-01T10:00:00")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "it must be error","error", result.getResponse().getContentAsString());

         result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4")
                .param("firstName", "Claudio")
                .param("lastName", "Correia")
                .param("pickupDate", "")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "it must be error","error", result.getResponse().getContentAsString());

    }


    @Test
    public void TestNoInput() throws Exception {


        MvcResult result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4")
                .param("firstName", "Claudio")
                .param("pickupDate", "2018-01-01T10:00:00")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "it must be error","error", result.getResponse().getContentAsString());



    }





    //functionality
    @Test
    public void A_CreateBooking () throws Exception {


        MvcResult result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4")
                .param("firstName", "Claudio")
                .param("lastName", "Correia")
                .param("pickupDate", "2018-01-01T10:00:00")
        )
                .andExpect(status().isOk())
                .andReturn();

        int Counter = DataManager.CetCounter().intValue()-1;
        assertEquals( "It must be "+Counter+", it is the first booking",Integer.toString(Counter), result.getResponse().getContentAsString());


    }
    @Test
    public void B_CreateBooking () throws Exception {


        MvcResult result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "768a73af-4336-41c8-b1bd-76bd700378ce")
                .param("firstName", "Claudio")
                .param("lastName", "Correia")
                .param("pickupDate", "2019-03-04T10:30:00")
        )
                .andExpect(status().isOk())
                .andReturn();

        int Counter = DataManager.CetCounter().intValue()-1;
        assertEquals( "It must be "+Counter+", it is the second booking", Integer.toString(Counter),result.getResponse().getContentAsString());
    }

    @Test
    public void C_ConflictCreateBooking () throws Exception {

        MvcResult result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "768a73af-4336-41c8-b1bd-76bd700378ce")
                .param("firstName", "Joao")
                .param("lastName", "Pereira")
                .param("pickupDate", "2019-03-04T10:30:00")
        )
                .andExpect(status().isOk())
                .andReturn();


        assertEquals( "It must be 'error', already exist a booking", "error",result.getResponse().getContentAsString());
    }
    @Test
    public void D_CreateBookingInNextSlot () throws Exception {


        MvcResult result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4")
                .param("firstName", "Claudio")
                .param("lastName", "Correia")
                .param("pickupDate", "2018-01-01T10:30:00")
        )
                .andExpect(status().isOk())
                .andReturn();

        int Counter = DataManager.CetCounter().intValue()-1;
        assertEquals( "It must be "+Counter+", it is the first booking in this slot",Integer.toString(Counter), result.getResponse().getContentAsString());


    }


}
