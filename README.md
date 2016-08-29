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
               


This application was generated using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:


## Building for production

To optimize the seuaa client for production, run:

    ./gradlew -Pprod clean bootRepackage

To ensure everything worked, run:

    java -jar build/libs/*.war

## Continuous Integration

To setup this project in Jenkins, use the following configuration:

* Project name: `seuaa`
* Source Code Management
    * Git Repository: `git@github.com:xxxx/seuaa.git`
    * Branches to build: `*/master`
    * Additional Behaviours: `Wipe out repository & force clone`
* Build Triggers
    * Poll SCM / Schedule: `H/5 * * * *`
* Build
    * Invoke Gradle script / Use Gradle Wrapper / Tasks: `-Pprod clean test bootRepackage`
* Post-build Actions
    * Publish JUnit test result report / Test Report XMLs: `build/test-results/*.xml`

[JHipster]: https://jhipster.github.io/
