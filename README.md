# web-project-manager
为学生课设作业提供，上传war包后自动部署在Tomcat服务器的系统  
部署环境：Docker Tomcat9/MySQL8  
生产环境基于JDK11，不再支持运行JDK8编译的项目  
简单的数据表结构  
![数据表](./asserts/table.PNG)  

[系统网址](http://114.116.213.241/web-project-manager/)  
[maven打war包配置视频](https://mooc1-1.chaoxing.com/nodedetailcontroller/visitnodedetail?courseId=91374545&knowledgeId=387884587)

基于jquery/ajax/bootstrap/servlet/jstl模拟单页面组件化开发实现，无事务，无容器    
照片尺寸较小，上传后在服务器端按base64编码保存在数据库  
页面通过xlsx库读取选课名单表格，后端引入jackson反序列化  
嗯啊嗯啊，上一次使用这套技术栈写实际项目是2015年，转眼5年了，还好头发没秃。。。  
### 2020.12.15
查询数据量远大于写入，简单实现一个首页table数据的缓存。但系统瓶颈在5M网络带宽，不是数据库  
添加响应式布局，小于md，将左胶囊导航转为上导航横向平均排列    
tomcat容器内使用UTC时区，设置启动容器时统一Tomcat/MySQL时区环境变量Asia/Shanghai  