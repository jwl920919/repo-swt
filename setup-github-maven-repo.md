# Github Maven Repository 연동 방법


## Maven Repository 생성

***1) Repository 생성***

- Github에 Maven Repository 생성 ( ex. https://github.com/songagi/mvn-repo )


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

- 경로 : {MAVEN_PATH}/conf/setting.xml

	```xml
	<servers>
   		...
		<server>
			<id>github</id>
			<username>Github-ID</username>
			<password>Password</password>
		</server>
		...
	</servers>
	```

- [참고] Password 암호화 방법

	1. master 암호키 생성
		```
		mvn --encrypt-master-password <PASSWORD>
		{nDpn1bE1vX4HABCDEFGOriBubJhppqAOuy4=}
		```

	2. 아래 경로에 settings-security.xml 파일 생성 후, 마스터 암호키 입력 
	( settings-security.xml 파일의 경로 변경 안됨 )
		```
		C:\Users\{UserName}\.m2\settings-security.xml
		```

		settings-security.xml
		```xml
		<settingsSecurity>  
			<master>{nDpn1bE1vX4HABCDEFGOriBubJhppqAOuy4=}</master>  
		</settingsSecurity> 
		```

	3. Password 암호키 생성
		```
		mvn --encrypt-password <PASSWORD>
		{X/Mnlwkfm90HVsadbsadsadlsakdsalfdlfdhfldsfldslE3LQ8g4=}
		```

	4. setting.xml에 암호 키 입력 ( {MAVEN_PATH}/conf/setting.xml )
		```xml
		<servers>
	   		...
			<server>
				<id>github</id>
				<username>github-user</username>
				<password>{X/Mnlwkfm90HVsadbsadsadlsakdsalfdlfdhfldsfldslE3LQ8g4=}</password>
			</server>
			...
		</servers>
		```

***4) Github Repository에 Deploy***

- Eclipse 이용 시:

	- Project > Run As > Maven Build...

	- Goals 입력: ***'clean deploy'*** 입력

	- Run 실행

- Command 이용 시:

	```cmd
	mvn clean deploy
	```


## Maven Repository 연동

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
