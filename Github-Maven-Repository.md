# Github Maven Repository 연동 방법


## Github Maven Repository 생성

***1) Repository 생성***

- Maven 용 Github Repository 생성 ( ex. https://github.com/songagi/mvn-repo)


***2) Target Maven Project 설정***

- pom.xml 코드 추가

```xml
...
<distributionManagement>
	<repository>
		<id>internal.repo</id>
		<name>Temporary Staging Repository</name>
		<url>file://${project.build.directory}/mvn-repo</url>
	</repository>
</distributionManagement>
...
<properties>
	<github.global.server>github</github.global.server>
</properties>
...
<build>
	<plugins>
		<plugin>
			<groupId>com.github.github</groupId>
			<artifactId>site-maven-plugin</artifactId>
			<version>0.12</version>
			<configuration>
				<message>Maven artifacts for ${project.version}</message>  <!-- git commit message -->
				<noJekyll>true</noJekyll>                                  <!-- disable webpage processing -->
				<outputDirectory>${project.build.directory}/mvn-repo</outputDirectory>
				<branch>refs/heads/master</branch>		<!-- branch -->
				<includes>
					<include>**/*</include>
				</includes>
				<merge>true</merge>				<!-- don't delete old artifacts -->
				
				<repositoryName>mvn-repo</repositoryName>	<!-- github repo name -->
				<repositoryOwner>songagi</repositoryOwner>	<!-- github username -->
				
			</configuration>
			<executions>
				<execution>
					<goals>
						<goal>site</goal>
					</goals>
					<phase>deploy</phase>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```


***3) maven setting.xml 수정***

- 경로 

~~/conf/maven/setting.xml

```xml
<servers>
   	...
	<server>
		<id>github</id>
		<username>Github 접속계정</username>
		<password>Github 패스워드</password>
	</server>
	...
</servers>
```

***4) Github Repository에 Deploy***

- Eclipse 이용 시:

     1) (Target Project) > **Run As** > **Maven Build...**

     2) Goals 입력 창: ***'clean deploy'*** 입력

     3) **Run** 실행
 
- Command 이용 시:

```cmd
mvn clean deploy
```


## Maven 연동

- 연동할 프로젝트의 pom.xml에 아래 코드 추가

```xml
	<repositories>
		<repository>
			<id>songagi-mvn-repo</id>
			<url>https://github.com/songagi/mvn-repo/</url>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>
	</repositories>
	...
	<dependency>
		<groupId>com.shinwootns</groupId>
		<artifactId>swt-common</artifactId>
		<version>0.1.1</version>
	</dependency>
```
