package xmu.yida.ad.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.yida.ad.domain.Ad;

import java.util.HashMap;
import java.util.List;

/**
 * @author LYD
 */
@Mapper
@Component
public interface AdMapper {
    /**
     * 增加一个广告
     * @param ad 广告对象
     * @return 返回操作结果
     */
    boolean addAd(Ad ad);

    /**
     * 根据id返回广告
     * @param id 广告id
     * @return 广告
     */
    Ad findAdById(Integer id);
    /**
     * 查找所有Ad
     * @return list
     */
    List<Ad> userFindAllAds();

    /**
     * 通过name和content全字查找结果
     * @param map 包含name和content
     * @return list
     */
    List<Ad> adminFindAllAds(HashMap<String,Object> map);

    /**
     * 通过id删除广告
     * @param id 广告id
     * @return 操作结果
     */
    boolean deleteAdById(Integer id);

    /**
     * 修改广告
     * @param ad 要修改的广告
     * @return 操作结果
     */
    boolean updateAd(Ad ad);
}
