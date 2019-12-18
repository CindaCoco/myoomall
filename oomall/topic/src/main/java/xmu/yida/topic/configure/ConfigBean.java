package xmu.yida.topic.configure;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author LYD
 */
@Configuration
public class ConfigBean {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 随机负载分配
     * @return IRule
     */
    @Bean
    public IRule myRule(){
        return new RandomRule();
    }

}
