
-- 2016-02-16 用户安全验证使用自定义验证方法

UPDATE ofProperty SET propValue = 'com.sharegroup.ibaby.provider.auth.IbabyAuthProvider' WHERE NAME = 'provider.auth.className';

UPDATE ofProperty SET propValue = 'com.sharegroup.ibaby.provider.user.MSUserProvider' WHERE NAME = 'provider.user.className';
--  2016 -02-18 新增用户集成 获取用户信息接口属性

INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.netserver.host','http://192.168.1.201:30016/');
INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.netserver.method','api/system/Chat/CheckChat');
INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.netusercount.method','api/system/USER/getUserCount');
INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.netuserload.method','api/system/USER/loadUser');
INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.netuserpage.method','api/system/USER/getUsers');
INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.netuserpwd.method','api/system/USER/getPassword');

INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.pulpitserver.host','http://192.168.1.222:8080/pulpitServer/');
INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.pulpitpushserver.method','pulpitSystem/syncPulpitChatMessage');
INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.pulpitvalserver.method','pulpitSystem/getPulpitUserStatus');

INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.pushserver.host','http://192.168.1.222:8080/pushServer/');
INSERT INTO ofProperty (NAME,propValue) VALUES ('ibaby.pushserver.method','push/pushMessage');

-- 2016-02-20 添加 后台使用用户ibaby 修改admin密码
-- INSERT INTO ofUser (username,encryptedPassword,NAME,creationDate,modificationDate) VALUES('ibaby','b59ae791092ed8fe036ed870c2caa4d950e41ee71d9f0e3f','ibaby',UNIX_TIMESTAMP(),UNIX_TIMESTAMP());
UPDATE ofUser SET encryptedPassword = 'b59ae791092ed8fe036ed870c2caa4d950e41ee71d9f0e3f' WHERE username = 'ibaby';

UPDATE ofUser SET encryptedPassword = '1234' WHERE username = 'admin';


