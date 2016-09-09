# seuaa

基于 JHipster 的 UAA server;


##依赖调用

其他 微服务 以
     security:
             authentication:
                 jwt:
                     secret: my-secret-token-to-change-in-production
                     # Token is valid 24 hours
                     tokenValidityInSeconds: 86400
             clientAuthorization:
                 # change this depending on your authorization server
                 accessTokenUri: http://uaa:9999/oauth/token
                 tokenServiceId: uaa
                 clientId: internal
                 clientSecret: internal
                 
这样的方式,依赖这个服务;  
               
##内部实现
增加用户实体表;
提供dubbo rpc 服务;(内部)
提供http 服务;(外部)
               


1. 增加 oauth_client_details 表,用于管理第三方应用;
2. 设置第三方的管理属性;(个人\团队\企业)
3. 增加 用户 和 第三方应用的关系;
4. 增加 云服务 列表  和   对应 云服务所需的角色;jhi_user_authority (access_token 包含 角色信息,这样 在调用云服务时  就可以匹配 是否可访问)




