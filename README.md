[Light Forum](http://www.lc4e.com)
==========

----------
converted from spring mvc project -> [lc4e-spring](https://github.com/Teddy-Zhu/lc4e-spring)

###It's a Light Forum.
>#### Based On `Maven` `Jfinal` `MYSQL` `Shiro`

>#### Use Java(`JDK8`),JSP(`HTML5`,`Jquery`,`CSS3`,`Semantic UI`,`animatescroll`,`Animate`)

----------

# [Demo](http://lc4e.coding.io) #
----------

> <del>View [http://lc4e.coding.io/InitDB](http://lc4e.coding.io/InitDB) to initial database 

# Forum Related #
----------

> ###轻量级论坛系统  
>1.  主题  
>  1.Markdown支持  
>  2.@楼层功能  
>2. 用户  
>  1.用户控制权限系统  
>  2.独立自由的用户like,block系统  
>3. 插件拓展系统  
>  1.自由定制系统  
>4. 用户喜好推荐系统  
>  1.浏览习惯推荐主题  
>5. 待补充。。。 

# Jfianl Framework Enhancement Related #
----------

>Jfianl Extend 基于Jfinal轻量级框架   
>实现了类似Spring的注解，Serveice，Controller 自动注入  
>注解参数验证等功能

###Annotation enhancement 

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

### Shiro Related
- `RequireGuest[T/M]`:
- `RequiresAuthentication[T/M]`:
- `RequiresPermissions[T/M]`:
- `RequiresGuest[T/M]`:
- `RequiresUser[T/M]`:


### Other enhancement
- `BaseController`：增强Controller 增加isAJAX ，isPJAX等
- `DBModel`:增加 enhancer方法，增强Model,自动set updateTime，createTime,自动事务，自动Cache
- `IHandler`: handler增强 增加 beforeHandler，afterHandler方法，需使用GlobalHandler注解载入
- `IInterceptor`: interceptor增强，需使用InterceptorHandler载入，新增beforeIntercept，afterIntercept，beforeException，afterException
- `IIPlugin`:plugin增强，需使用PluginHander载入，增加 init方法 ，可以设置Plugins，Routes，Constants，Interceptors，Handlers
- `SetPJAX`:设置PJAX attribute
- `SetAJAX`:设置AJAX attribute

### Exception

- Lc4eAutoSetterException:自动设置response attribute 异常  
- Lc4eApplicationException: 业务层异常  
- Lc4eException:  
- Lc4eRuntimeException:Lc4e自定义代码异常  
- ReflectException ：ReflectTool 类中异常  
- Lc4eValidateException:参数验证过程异常  

----------

#Important Update#
----------

- 6/13/2015 4:22:59 PM 
> Remove Mysql . Replaced with MongoDB.
> Version to V2

- 7/31/2015 1:08:22 PM 
> Convert Spring to Jfinal [Origin Project](https://github.com/Teddy-Zhu/lc4e-spring)  
> Rollback MongoDB to MYSQL

- 10/29/2015 13:46:26 PM 
> Convert View Render from Beelt to Jetbrick 
