package xmu.yida.ad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.yida.ad.dao.AdDao;
import xmu.yida.ad.domain.Ad;

import java.util.HashMap;
import java.util.List;

@Service
public class AdService {
    @Autowired
    AdDao adDao;

    public Ad getAdById(Integer id){
        return adDao.findAdById(id);
    }

    public List<Ad> getAllAds(Integer page, Integer limit, HashMap<String,Object> map){
        return adDao.findAllAds(page,limit,map);
    }

    public List<Ad> userGetAllAds(){
        return adDao.userFindAds();
    }

    public Ad addAd(Ad ad){
        return adDao.addAd(ad);
    }

    public Ad updateAd(Ad ad){
        return adDao.updateAd(ad);
    }
    public boolean deleteAdById(Integer id){
        return adDao.deleteAdById(id);
    }
}
