package xmu.yida.ad.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
import xmu.yida.ad.domain.Ad;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class AdMapperTest {

    @Autowired
    AdMapper adMapper;

    @Test
    void addAd() {
        Ad ad=new Ad();
        ad.setLink("test add link!");
        ad.setName("test name");
        ad.setContent("test content");
        ad.setPicUrl("test picture url");
        ad.setBeDefault(false);
        ad.setBeEnabled(true);
        ad.setStartTime(LocalDateTime.of(2019,12,12,0,0,0));
        ad.setEndTime(LocalDateTime.of(2020,12,12,0,0,0));
        ad.setBeDeleted(false);
        adMapper.addAd(ad);
    }


    @Test
    void findAllAds() {
        List<Ad> adList=adMapper.findAllAds();
        assertNotNull(adList);
        for(Ad ad:adList){
            System.out.println(ad);
        }
    }

    @Test
    void findAdById() {
        List<Ad> adList=adMapper.findAllAds();
        if(adList.size()>0){
            Integer id=adList.get(0).getId();
            Ad ad=adMapper.findAdById(id);
            System.out.println(ad);
            assertEquals(adList.get(0),ad);
        }
    }


    @Test
    void deleteAdById() {
        List<Ad> adList=adMapper.findAllAds();
        if(adList.size()>0){
            Integer id=adList.get(0).getId();
            adMapper.deleteAdById(id);
            Ad ad=adMapper.findAdById(id);
            assertNull("删除失败",ad);
        }
    }

    @Test
    void updateAd() {
        Ad ad=new Ad();
        ad.setLink("test update link!");
        ad.setName("test name");
        ad.setContent("test content");
        ad.setPicUrl("test picture url");
        ad.setBeDefault(false);
        ad.setBeEnabled(true);
        ad.setStartTime(LocalDateTime.of(2019,12,12,0,0,0));
        ad.setEndTime(LocalDateTime.of(2020,12,12,0,0,0));
        ad.setBeDeleted(false);
        List<Ad> adList=adMapper.findAllAds();
        if(adList.size()>0){
            Integer id=adList.get(0).getId();
            adMapper.updateAd(ad);
            Ad updatedAd=adMapper.findAdById(id);
            assertNotEquals("修改失败",adList.get(id),updatedAd);
        }
    }
}