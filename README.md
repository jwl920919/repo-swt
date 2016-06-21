# repo-swt

### Setup

***swt-common 연동 (Maven)***

연동 할 프로젝트의 pom.xml에 아래 코드 추가

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
