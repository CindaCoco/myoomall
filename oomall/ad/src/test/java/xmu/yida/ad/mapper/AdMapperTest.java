package xmu.yida.ad.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.yida.ad.domain.Ad;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AdMapperTest {

    @Autowired
    AdMapper adMapper;

    @Test
    void addAd() {
        Ad ad=new Ad();
        ad.setName("new");
        ad.setPicUrl("666.jpg");
        ad.setContent("content");
        ad.setLink("lalal");
        ad.setBeDefault(false);
        ad.setBeDeleted(false);
        ad.setBeEnabled(true);
        ad.setStartTime(LocalDateTime.of(2019,10,1,12,0,0));
        ad.setEndTime(LocalDateTime.of(2019,12,31,0,0,0));
        ad.setGmtCreate(LocalDateTime.now());
        ad.setGmtModified(LocalDateTime.now());
        System.out.println(ad);
        boolean result=adMapper.addAd(ad);
        assertTrue(result);
    }

    @Test
    void findAdById() {
        Ad ad=adMapper.findAdById(129);
        assertEquals("一筷幸福",ad.getName());
    }

    @Test
    void userFindAllAds() {
        List<Ad> adList=adMapper.userFindAllAds();
        assertTrue(adList.size()>0);
    }

    @Test
    void adminFindAllAds() {
        HashMap<String,Object> map=new HashMap<>(2);
        map.put("name","一筷幸福");
        List<Ad> adList=adMapper.adminFindAllAds(map);
        assertEquals(1,adList.size());
    }

    @Test
    void deleteAdById() {
        boolean result=adMapper.deleteAdById(147);
        assertTrue(result);
    }

    @Test
    void updateAd() {
        Ad ad=new Ad();
        ad.setId(147);
        ad.setName("new");
        ad.setPicUrl("666.jpg");
        ad.setContent("content");
        ad.setLink("lalal");
        ad.setBeDefault(false);
        ad.setBeDeleted(false);
        ad.setBeEnabled(true);
        ad.setStartTime(LocalDateTime.of(2019,10,1,12,0,0));
        ad.setEndTime(LocalDateTime.of(2019,12,31,0,0,0));
        ad.setGmtCreate(LocalDateTime.now());
        ad.setGmtModified(LocalDateTime.now());
        System.out.println(ad);
        boolean result=adMapper.updateAd(ad);
        assertTrue(result);
    }
}