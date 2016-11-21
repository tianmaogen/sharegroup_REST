
#description openfire 集成现在用户插件
#date 2016-02-16 10:49:12
#author Jade

<pre>
#1.安全验证使用自定义验证
参数修改：
provider.auth.className:
org.jivesoftware.openfire.auth.DefaultAuthProvider -> com.sharegroup.ibaby.provider.auth.IbabyAuthProvider
</pre>

<pre>
#2.集成用户接口，验证用户，不对用户有其他操作（新增，修改，删除等）
参数修改：
provider.user.className
org.jivesoftware.openfire.user.DefaultUserProvider -> com.sharegroup.ibaby.provider.user.MSUserProvider
</pre>

<pre>
#3 回退到上一个版本
UPDATE ofProperty SET propValue = 'org.jivesoftware.openfire.auth.DefaultAuthProvider' WHERE NAME = 'provider.auth.className';

UPDATE ofProperty SET propValue = 'org.jivesoftware.openfire.user.DefaultUserProvide' WHERE NAME = 'provider.user.className';
</pre>


<pre>
#4 用户修改
#如果库中已经存在ibaby用户，则修改用户密码
UPDATE ofUser SET encryptedPassword = 'b59ae791092ed8fe036ed870c2caa4d950e41ee71d9f0e3f' WHERE username = 'ibaby';

#如果不存在则执行新增
 INSERT INTO ofUser (username,encryptedPassword,NAME,creationDate,modificationDate) VALUES('ibaby','b59ae791092ed8fe036ed870c2caa4d950e41ee71d9f0e3f','ibaby',UNIX_TIMESTAMP(),UNIX_TIMESTAMP());

</pre>