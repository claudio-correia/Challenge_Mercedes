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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FindAllTheClosestDealerTests {

    @Autowired
    private MockMvc mockMvc;

    //inputs
    @Test
    public void TestBadInput() throws Exception {

        MvcResult result =this.mockMvc.perform(get("/getallclosestvehicles")
                .param("model", "adas")
                .param("fuel", "ELECTRIC")
                .param("transmission", "AUTO")
                .param("latitude", "1232")
                .param("longitude", "-1230")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be empty","[]", result.getResponse().getContentAsString());

        result =this.mockMvc.perform(get("/getallclosestvehicles")
                .param("model", "AMG")
                .param("fuel", "ELECTRIC")
                .param("transmission", "AUTsO")
                .param("latitude", "1232")
                .param("longitude", "-1230")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be empty","[]", result.getResponse().getContentAsString());


    }
    @Test
    public void TestEmptyInput() throws Exception {

        MvcResult result =this.mockMvc.perform(get("/getallclosestvehicles")
                .param("model", "adas")
                .param("fuel", "ELECTRIC")
                .param("transmission", "")
                .param("latitude", "1232")
                .param("longitude", "-1230")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be empty","[]", result.getResponse().getContentAsString());



    }

    @Test
    public void TestNoInput() throws Exception {

        //no param the  defaultValue=""
        MvcResult result =this.mockMvc.perform(get("/getallclosestvehicles")
                .param("fuel", "ELECTRIC")
                .param("transmission", "")
                .param("latitude", "1232")
                .param("longitude", "-1230")
        )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be empty","[]", result.getResponse().getContentAsString());



    }









    //functionality
    @Test
    public void FindClosestDealer () throws Exception {

        MvcResult result =this.mockMvc.perform(get("/getallclosestvehicles")
                .param("model", "AMG")
                .param("fuel", "ELECTRIC")
                .param("transmission", "AUTO")
                .param("latitude", "0")
                .param("longitude", "0")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("846679bd-5831-4286-969b-056e9c89d74c"))
                .andExpect(jsonPath("$[1].id").value("bbcdbbad-5d0b-45ef-90ac-3581b997e063"))
                .andExpect(jsonPath("$[2].id").value("d4f4d287-1ad6-4968-a8ff-e9e0009ad5d1"))
                .andReturn();


    }
    //random values
    @Test
    public void FindClosestDealer2 () throws Exception {

        MvcResult result =this.mockMvc.perform(get("/getallclosestvehicles")
                .param("model", "E")
                .param("fuel", "ELECTRIC")
                .param("transmission", "AUTO")
                .param("latitude", "42")
                .param("longitude", "-7")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("d4f4d287-1ad6-4968-a8ff-e9e0009ad5d1"))
                .andExpect(jsonPath("$[1].id").value("846679bd-5831-4286-969b-056e9c89d74c"))
                .andReturn();


    }

    //over the exact location of the dealer
    @Test
    public void FindClosestDealer3 () throws Exception {

        MvcResult result =this.mockMvc.perform(get("/getallclosestvehicles")
                .param("model", "E")
                .param("fuel", "GASOLINE")
                .param("transmission", "MANUAL")
                .param("latitude", "38.746721")
                .param("longitude", "-9.229837")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("bbcdbbad-5d0b-45ef-90ac-3581b997e063"))

                .andReturn();


    }

    //the dealer is not the closest one
    @Test
    public void FindClosestDealer4 () throws Exception {

        MvcResult result =this.mockMvc.perform(get("/getallclosestvehicles")
                .param("model", "E")
                .param("fuel", "GASOLINE")
                .param("transmission", "AUTO")
                .param("latitude", "38.746721")
                .param("longitude", "-9.229837")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("d4f4d287-1ad6-4968-a8ff-e9e0009ad5d1"))

                .andReturn();


    }

    //no car
    @Test
    public void FindClosestDealerNoCar () throws Exception {

        MvcResult result =this.mockMvc.perform(get("/getallclosestvehicles")
                .param("model", "E")
                .param("fuel", "ELECTRIC")
                .param("transmission", "MANUAL")
                .param("latitude", "-70")
                .param("longitude", "100")
        )
                .andExpect(status().isOk())

                .andReturn();

        assertEquals( "It must be null, the car", "[]", result.getResponse().getContentAsString());

    }



}
