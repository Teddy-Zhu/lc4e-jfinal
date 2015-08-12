#Full Name：[Light Community For Everyone](http://www.lc4e.com)
###It's a Light Forum.
>#### Based On `Maven` `Jfinal` `MYSQL` `Shiro`

>#### Use Java(`JDK8`),JSP(`HTML5`,`Jquery`,`CSS3`,`Semantic UI`,`animatescroll`,`Animate`)

----------
# [Demo](http://lc4e.coding.io) #
----------
> View [http://lc4e.coding.io/InitDB](http://lc4e.coding.io/InitDB) to initial database 

# Description #
----------
>Jfianl Extend 基于Jfinal轻量级框架 
>实现了类似Spring的注解，Serveice，Controller 自动注入等
##Annotation enhancement 


- `ConfigHandler [T]`:注解Jfianl Config，可以导入多个Config
- `PluginHander [T]`:注解插件类 ，自动加载插件无需在Config中手动配置
- `GlobalHandler [T]`: 注解handler，自动加载全局Handler
- `InterceptorHandler [T]`:注解interceptor 自动加载全局拦截器
- `ExceptionHanders [T]`:注解全局异常处理类，可以手动处理特殊异常，并render
- `ExceptionHander [M]`:配合ExceptionHanders，自动处理指定异常
- `Job [M]`:除了配置之外可以注解Job，自动job
- `Controller [T]`: 注解生成路由配置
- `Service [T]`: 自动注入Service层,需配合 Inject注解，Service 方法加入Transaction 注解 即可自动事物
- `Model [T]`：结合ActiveRecord，自动Mapping数据库， 结合Tools 可以自动生成Dao 与 Mapping数据
- `Inject [F]`: 自动注入，须在Serivce 或者 Controller中。
- `Transaction [M]`:在方法上注入，可以自动事物
- `RequestHeader[T/M]`:request Header 验证
- `RequestMethod[T/M]`:request 类型验证，实现restful
- `ResponseStatus[T/M]`:强制设置返回状态码
- `SetComVar[M]`:设置数据库配置项，预设值
- `SetUIDate[M]`:设置动态UI变量,可指定方法
- `SetUIDates[M]`:批量SetUIDate
- `ValidateComVar[M]`:验证数据库配置值，预设值
- `ValidateComVars[M]`:批量ValidateComVar
- `ValidateParam[M]`:参数验证,支持String Integer,Long,Double,Float,Date,File,POJO等验证，同时设置默认值
- `ValidateParams[M]`:批量参数验证
- `ValidateToken[T/M]`:验证TOKEN
- `Cache[M]`:在Service的方法中注解，可以自动缓存

## Shiro Related
- `RequireGuest[T/M]`:
- `RequiresAuthentication[T/M]`:
- `RequiresPermissions[T/M]`:
- `RequiresGuest[T/M]`:
- `RequiresUser[T/M]`:


##Other enhancement
- `BaseController`：增强Controller 增加isAJAX ，isPJAX等
- `DBModel`:增加 enhancer发放，增强Model,自动set updateTime，createTime,自动事务，自动Cache
- `Handler`: handler增强 增加 beforeHandler，afterHandler方法，需使用GlobalHandler注解载入
- `Interceptor`: interceptor增强，需使用InterceptorHandler载入，新增beforeIntercept，afterIntercept，beforeException，afterException
- `IPlugin`:plugin增强，需使用PluginHander载入，增加 init方法 ，可以设置Plugins，Routes，Constants，Interceptors，Handlers


#Important Update#
----------
- 6/13/2015 4:22:59 PM 
> Remove Mysql . Replaced with MongoDB.
> Version to V2

- 7/31/2015 1:08:22 PM 
> Convert Spring to Jfinal
> Rollback MongoDB to MYSQL