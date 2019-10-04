##  技术社区   

##  部署
###  依赖   
 
- git  (git工具管理项目版本)
- JDK   (编译代码)
- Maven  (构建项目)
- MySql   (数据库)

##  步骤  

- yum update (更新yum)
- yum install git 
- mkdir App
- cd App
- git clone https://github.com/coffeecw/community.git
- yum install maven(自动安装openjdk)
- java -version 
- mvn -v  
- mvn clean compile package(清空编译项目)  

###  运行项目  

- cp src/main/resources/application.properties src/main/resources/application-production.properties    
- mvn package  打包项目  
- java -jar -Dspring.profiles.active=production community-0.0.1-SNAPSHOT.jar  (生产环境下运行项目)    
- ps -aux | grep java
- git pull



##  资料 
[spring 文档](https://spring.io/guides)  
[spring web](https://spring.io/guides/gs/serving-web-content/)  
[elasticsearch](https://elasticsearch.cn/explore)  
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)  
前端开发框架  
[BootStrap](https://v3.bootcss.com/getting-started/)  
创建OAuth应用程序  
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
授权OAuth应用程序  
[Authorizing-OAuth-App](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/#web-application-flow)  
[springboot官方文档](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)  
[github用户信息(users后面跟上你的用户名)](https://api.github.com/users/)  
[mybatis官网](https://mybatis.org/mybatis-3/)  
[thymeleaf官网](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)  
[Spring dev tool](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)  
[spring MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)  
[Editor.md](http://editor.md.ipandao.com/)  
[Ufile SDK](https://github.com/ucloud/ufile-sdk-java)  
[icon](https://www.iconfont.cn/)  
[Count(*) VS Count(1)](https://mp.weixin.qq.com/s/Rwpke4BHu7Fz7KOpE2d3Lw)

##  脚本 
```sql
create table user(
	id int(100) auto_increment
		primary key,
	name varchar(50) ,
	account_id varchar(100) ,
	token varchar(36) ,
	gmt_create bigint ,
	gmt_modified bigint 
);
```

##  工具 
git:分布式版本控制工具   
[git官网](https://git-scm.com/)    
[Visual Paradigm](https://www.visual-paradigm.com/cn/)   
[Lombok插件](https://projectlombok.org/)    
[octotree](https://www.octotree.io)   
[Table of content sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)   
[one Tab](https://chrome.google.com/webstore/detail/onetab/chphlpgkkbolifaimnlloiipkdnihall)   
[liveReload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)   
