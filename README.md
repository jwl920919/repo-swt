# SinwooTNS - Github Repository

### swt-common 연동 방법(Maven)

- ***pom.xml***에 코드 추가

```xml
...
	
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
	
<dependencies>
	...
	<dependency>
		<groupId>com.shinwootns</groupId>
		<artifactId>swt-common</artifactId>
		<version>0.1.6</version>
	</dependency>
	...
</dependencies>
```

===============================
