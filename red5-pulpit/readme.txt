@Author Jade
@Creation 2016-01-08
@Description pulpit
1.导出IDE时出需要配置jdk,tomcat,maven等
2.pulpit 是发布在red5/webapps下的插件
3.lib 是对red5/lib 下的核心包引用,编译的时候不会copy到pulpit/WEB-INF/lib中
4.在发布插件的时候不要把pulpit/WEB-INF/lib下的包引入到red5中
