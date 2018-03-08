package mb;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CancelBookingTests {

    @Autowired
    private MockMvc mockMvc;
    //inputs
    @Test
    public void TestBadInput() throws Exception {


        MvcResult result =this.mockMvc.perform(post("/cancelbooking")
                .param("bookid", "doentExists-080a-49c4-9945-e5a05068677c")
                .param("cancelledreason","Something happen" )
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be 'error'","error", result.getResponse().getContentAsString());





    }
    @Test
    public void TestEmptyInput() throws Exception {


        MvcResult result =this.mockMvc.perform(post("/cancelbooking")
                .param("bookid", "46b59d70-080a-49c4-9945-e5a05068677c")
                .param("cancelledreason","" )
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be 'error'","error", result.getResponse().getContentAsString());




    }

    @Test
    public void TestNoInput() throws Exception {

        //no param the  defaultValue=""
        MvcResult result =this.mockMvc.perform(post("/cancelbooking")
                .param("cancelledreason","Something happen" )
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be 'error'","error", result.getResponse().getContentAsString());



    }









    //functionality
    @Test
    public void CancelBooking () throws Exception {

        //create
        MvcResult result =this.mockMvc.perform(post("/createbooking")
                .param("vehicleId", "1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4")
                .param("firstName", "Claudio")
                .param("lastName", "Correia")
                .param("pickupDate", "2018-01-01T10:00:00")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertNotEquals("it must me a number","error",result.getResponse().getContentAsString());


        //Cancel
        MvcResult result2 =this.mockMvc.perform(post("/cancelbooking")
                .param("bookid", result.getResponse().getContentAsString())
                .param("cancelledreason","I dondt have time" )
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be cancel","cancel", result2.getResponse().getContentAsString());


    }
    @Test
    public void CancelOldBooking () throws Exception {


        MvcResult result =this.mockMvc.perform(post("/cancelbooking")
                .param("bookid", "46b59d70-080a-49c4-9945-e5a05068677c")
                .param("cancelledreason","Something happen" )
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be cancel","cancel", result.getResponse().getContentAsString());


    }
    @Test
    public void FailCancelBooking () throws Exception {

        //Wrong bookid
        MvcResult result =this.mockMvc.perform(post("/cancelbooking")
                .param("bookid", "46b00d70-080a-49c4-9945-e5a05068677c")
                .param("cancelledreason","Wrong bookid" )
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be 'error'","error", result.getResponse().getContentAsString());


    }
    @Test
    public void CancelTwiceBooking () throws Exception {


        MvcResult result =this.mockMvc.perform(post("/cancelbooking")
                .param("bookid", "537b4079-36cd-490c-8b3b-07b8f5954928")
                .param("cancelledreason","Something happen 2" )
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be cancel","cancel", result.getResponse().getContentAsString());

        MvcResult result2 =this.mockMvc.perform(post("/cancelbooking")
                .param("bookid", "537b4079-36cd-490c-8b3b-07b8f5954928")
                .param("cancelledreason","Something happen again" )
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be 'error'","error", result2.getResponse().getContentAsString());

    }


}
