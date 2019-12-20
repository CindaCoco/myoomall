package xmu.yida.ad.dao;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.yida.ad.domain.Ad;
import xmu.yida.ad.mapper.AdMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Repository
public class AdDao {
    @Autowired
    AdMapper adMapper;


    public Ad addAd(Ad ad){
        ad.setGmtCreate(LocalDateTime.now());
        ad.setGmtModified(LocalDateTime.now());
        if(adMapper.addAd(ad)){
            Integer id=ad.getId();
            return adMapper.findAdById(id);
        }else{
            return null;
        }
    }

    public Ad findAdById(Integer id){
        return adMapper.findAdById(id);
    }

    public List<Ad> findAllAds(Integer page, Integer limit, HashMap<String,Object> map){
        PageHelper.startPage(page,limit);
        return adMapper.adminFindAllAds(map);
    }

    public List<Ad> userFindAds(){
        return adMapper.userFindAllAds();
    }

    public boolean deleteAdById(Integer id){
        Ad ad=adMapper.findAdById(id);
        if(ad==null){
            return false;
        }
        if(ad.getBeDeleted()){
            return false;
        }
        return adMapper.deleteAdById(id);
    }

    public Ad updateAd(Ad ad){
        ad.setGmtModified(LocalDateTime.now());
        if(adMapper.updateAd(ad)){
            return adMapper.findAdById(ad.getId());
        }else{
            return null;
        }
    }
}
