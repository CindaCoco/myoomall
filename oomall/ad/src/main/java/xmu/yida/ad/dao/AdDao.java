package xmu.yida.ad.dao;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.yida.ad.domain.Ad;
import xmu.yida.ad.mapper.AdMapper;

import java.time.LocalDateTime;
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

    public List<Ad> findAllAds(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        return adMapper.findAllAds();
    }

    public boolean deleteAdById(Integer id){
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
