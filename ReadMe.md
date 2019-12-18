##关于熔断器hystrix

我负责的topic和ad模块其实是不太需要熔断机制的，加上熔断器只是为了springcloud的完整性。

那么，配置熔断器只需要在pom文件中添加spring-cloud-starter-netflix-hystrix依赖，在主启动类添加注解@EnableCircuitBreaker，在controller中定义回调函数，并在需要熔断的api添加注解@HystrixCommand(fallbackMethod = "processHystrixId")，其中，processHystrixId为自定义的回调函数，在这里要注意回调函数的参数必须和相关的api一致，否则不能匹配将导致整个api无法使用。