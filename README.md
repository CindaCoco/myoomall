# 前言
git之前的连接问题一直存在着，之前采用https的连接方法每次都被拒绝，后来采用ssh连接方法，改动origin的url，后来连接成功


git remote add origin **git@github.com:NickHandsom/myoomall.git**


git push -u origin master

---

## 12/19 新增监视流
在微服务端新增依赖**spring-boot-starter-actuator**，spring2.x需要在主启动类定义bean，详情见代码。另外在我的服务器上搭建dashboard服务，地址为handsomeyida.xyz:9527/hystrix,可在这里可视化监视微服务调用情况

## 关于熔断器hystrix

我负责的topic和ad模块其实是不太需要熔断机制的，加上熔断器只是为了springcloud的完整性。
那么，配置熔断器只需要在pom文件中添加spring-cloud-starter-netflix-hystrix依赖，在主启动类添加注解@EnableCircuitBreaker，在controller中定义回调函数，并在需要熔断的api添加注解@HystrixCommand(fallbackMethod = "processHystrixId")，其中，processHystrixId为自定义的回调函数，在这里要注意回调函数的参数必须和相关的api一致，否则不能匹配将导致整个api无法使用。

---

# 12/18 新增消费端处理微服务关闭状况
在topic模块启动类新增注解@EnableCircuitBreaker，新建AdClientServiceFallbackFactory implements FallbackFactory<AdClientService>，其中<~>里面的类型即为feign接口的名字（即熔断机制是配合feign实现的），在这里面要实现方法create，重写feign接口的方法的回调函数，最后在clientService中添加@FeignClient(value="ADSERVICE",fallbackFactory = AdClientServiceFallbackFactory.class)，其中value是微服务的名字，fallbackFactory即为实现类。
