# ckb-wallet-java

### 初始化

https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

```
mvn archetype:generate -DgroupId=ckb-wallet -DartifactId=ckb-wallet -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

### 安装依赖

```xml
<dependency>
  <!-- https://mvnrepository.com/artifact/org.bitcoinj/bitcoinj-core -->
  <groupId>org.bitcoinj</groupId>
    <artifactId>bitcoinj-core</artifactId>
  <version>0.15</version>
</dependency>
```

```
mvn compile
```