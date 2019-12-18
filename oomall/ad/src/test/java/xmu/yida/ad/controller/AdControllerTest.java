package xmu.yida.ad.controller;

import org.junit.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdControllerTest{

    @Autowired
    protected MockMvc mockMvc;



    @Test
    void adminFindAdList() throws Exception{
        MvcResult response=mockMvc.perform(get("/ads").contentType("application/json;charset=UTF-8"))
                .andReturn();

        JSONObject jsonObject=new JSONObject(response.getResponse().getContentAsString());
        JSONArray jsonArray=(JSONArray)jsonObject.get("data");

        if(jsonArray.length()>0){
            System.out.println(jsonArray.get(2));
        }

        
        Assert.assertNotNull(jsonObject.get("errno"));
        Assert.assertEquals(jsonObject.get("errno"),0);

    }

    @Test
    void adminCreateAd() {
    }

    @Test
    void adminFindAdById() {
    }

    @Test
    void adminUpdateAd() {
    }

    @Test
    void adminDeleteAd() {
    }

    @Test
    void userFindAdList() {
    }
}