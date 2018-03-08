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
public class GetVehiclesTests {

    @Autowired
    private MockMvc mockMvc;

    //inputs
    @Test
    public void TestBadInput() throws Exception {

        MvcResult result =this.mockMvc.perform(get("/getvehicles").param("type", "dsa"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals( "It must be empty","[]", result.getResponse().getContentAsString());

    }
    @Test
    public void TestEmptyInput() throws Exception {
        //the de   defaultValue="Model"
        this.mockMvc.perform(get("/getvehicles").param("type", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("778a04fd-0a6a-4dc7-92bb-a7517608efc2"))
                .andExpect(jsonPath("$[1].id").value("502ca69a-616b-43ce-9491-a6e20fc75a12"))
                .andExpect(jsonPath("$[2].id").value("d5d0aabc-c0de-4f38-badc-759f96f5fca3"))
                .andExpect(jsonPath("$[3].id").value("893d97bf-5a9d-4926-ace3-39ad0585c912"))
                .andExpect(jsonPath("$[4].id").value("f0bd3390-58ae-4510-987e-a5bfe14973ff"))
                .andExpect(jsonPath("$[5].id").value("d723b0bd-8eb0-4826-bf5d-44754005d174"))
                .andExpect(jsonPath("$[6].id").value("1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4"))
                .andExpect(jsonPath("$[7].id").value("768a73af-4336-41c8-b1bd-76bd700378ce"))
                .andExpect(jsonPath("$[8].id").value("875f00fa-9f67-44ea-bb26-75ff375fdd3f"))
                .andExpect(jsonPath("$[9].id").value("136fbb51-8a06-42fd-b839-c01ab87e2c6c"))
                .andExpect(jsonPath("$[10].id").value("44a36bfa-ec8f-4448-b4c2-809203bdcb9e"))
                .andReturn();
    }

    @Test
    public void TestNoInput() throws Exception {
        //the de   defaultValue="Model"
        this.mockMvc.perform(get("/getvehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("778a04fd-0a6a-4dc7-92bb-a7517608efc2"))
                .andExpect(jsonPath("$[1].id").value("502ca69a-616b-43ce-9491-a6e20fc75a12"))
                .andExpect(jsonPath("$[2].id").value("d5d0aabc-c0de-4f38-badc-759f96f5fca3"))
                .andExpect(jsonPath("$[3].id").value("893d97bf-5a9d-4926-ace3-39ad0585c912"))
                .andExpect(jsonPath("$[4].id").value("f0bd3390-58ae-4510-987e-a5bfe14973ff"))
                .andExpect(jsonPath("$[5].id").value("d723b0bd-8eb0-4826-bf5d-44754005d174"))
                .andExpect(jsonPath("$[6].id").value("1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4"))
                .andExpect(jsonPath("$[7].id").value("768a73af-4336-41c8-b1bd-76bd700378ce"))
                .andExpect(jsonPath("$[8].id").value("875f00fa-9f67-44ea-bb26-75ff375fdd3f"))
                .andExpect(jsonPath("$[9].id").value("136fbb51-8a06-42fd-b839-c01ab87e2c6c"))
                .andExpect(jsonPath("$[10].id").value("44a36bfa-ec8f-4448-b4c2-809203bdcb9e"))
                .andReturn();
    }


    //functionality
    @Test
    public void GetAllVehiclesByModel() throws Exception {

        MvcResult result =this.mockMvc.perform(get("/getvehicles").param("type", "Model"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("778a04fd-0a6a-4dc7-92bb-a7517608efc2"))
                .andExpect(jsonPath("$[1].id").value("502ca69a-616b-43ce-9491-a6e20fc75a12"))
                .andExpect(jsonPath("$[2].id").value("d5d0aabc-c0de-4f38-badc-759f96f5fca3"))
                .andExpect(jsonPath("$[3].id").value("893d97bf-5a9d-4926-ace3-39ad0585c912"))
                .andExpect(jsonPath("$[4].id").value("f0bd3390-58ae-4510-987e-a5bfe14973ff"))
                .andExpect(jsonPath("$[5].id").value("d723b0bd-8eb0-4826-bf5d-44754005d174"))
                .andExpect(jsonPath("$[6].id").value("1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4"))
                .andExpect(jsonPath("$[7].id").value("768a73af-4336-41c8-b1bd-76bd700378ce"))
                .andExpect(jsonPath("$[8].id").value("875f00fa-9f67-44ea-bb26-75ff375fdd3f"))
                .andExpect(jsonPath("$[9].id").value("136fbb51-8a06-42fd-b839-c01ab87e2c6c"))
                .andExpect(jsonPath("$[10].id").value("44a36bfa-ec8f-4448-b4c2-809203bdcb9e"))
                .andReturn();


    }

   @Test
   public void GetAllVehiclesByFuel() throws Exception {

       this.mockMvc.perform(get("/getvehicles").param("type", "fuel"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value("d5d0aabc-c0de-4f38-badc-759f96f5fca3"))
               .andExpect(jsonPath("$[1].id").value("768a73af-4336-41c8-b1bd-76bd700378ce"))
               .andExpect(jsonPath("$[2].id").value("778a04fd-0a6a-4dc7-92bb-a7517608efc2"))
                //save time....
               .andExpect(jsonPath("$[9].id").value("44a36bfa-ec8f-4448-b4c2-809203bdcb9e"))
               .andExpect(jsonPath("$[10].id").value("502ca69a-616b-43ce-9491-a6e20fc75a12"));

   }
    @Test
    public void GetAllVehiclesByTransmission() throws Exception {

        this.mockMvc.perform(get("/getvehicles").param("type", "transmission"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("d5d0aabc-c0de-4f38-badc-759f96f5fca3"))
                .andExpect(jsonPath("$[1].id").value("768a73af-4336-41c8-b1bd-76bd700378ce"))
                .andExpect(jsonPath("$[2].id").value("778a04fd-0a6a-4dc7-92bb-a7517608efc2"))
                //save time....
                .andExpect(jsonPath("$[9].id").value("44a36bfa-ec8f-4448-b4c2-809203bdcb9e"))
                .andExpect(jsonPath("$[10].id").value("502ca69a-616b-43ce-9491-a6e20fc75a12"));

    }

    @Test
    public void GetAllVehiclesByDealer() throws Exception {

        this.mockMvc.perform(get("/getvehicles").param("type", "dealer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("d5d0aabc-c0de-4f38-badc-759f96f5fca3"))
                .andExpect(jsonPath("$[1].id").value("1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4"))
                .andExpect(jsonPath("$[2].id").value("768a73af-4336-41c8-b1bd-76bd700378ce"))
                //save time....
                .andExpect(jsonPath("$[9].id").value("875f00fa-9f67-44ea-bb26-75ff375fdd3f"))
                .andExpect(jsonPath("$[10].id").value("136fbb51-8a06-42fd-b839-c01ab87e2c6c"));

    }


}
