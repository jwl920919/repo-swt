package Common.Helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import Common.Helper.JSONHelper;
import MVC.ShinwooTNS.com.CustomResourceLoader;

public class LanguageHelper {

	// FEFF because this is the Unicode char represented by the UTF-8 byte order
	// mark (EF BB BF).
	private static final String UTF8_BOM = "\uFEFF";
	private static final String Null_Value = "Null_Value";
	private static final String pathFormat = "Language/{}.txt";
	private static String fileName = "ko-KR";
	private static Map<String, Object> languageMap = null;

	public static String getFileName() {
		return fileName;
	}

	// region Instance
	private static LanguageHelper instance = new LanguageHelper();

	private LanguageHelper() {
	}

	public static LanguageHelper getInstance() throws IOException {
		if (languageMap != null) {
			languageMap = null;
		}

		// instance.readFile("Language/ko-KR.txt");
		instance.GetResouce();
		return instance;
	}

	public static LanguageHelper getInstance(String strLanguage) throws IOException {
		if (languageMap != null) {
			languageMap = null;
		}

		fileName = strLanguage;
		instance.GetResouce();
		return instance;
	}

	public static LanguageHelper getInstance(Locale locale) throws IOException {
		if (languageMap != null) {
			languageMap = null;
		}

		// System.out.println("Locale.US : " + Locale.US.toString());
		switch (locale.toLanguageTag()) {
		case "ko":
			fileName = Locale.KOREA.toLanguageTag();
			break;
		case "en":
			fileName = Locale.US.toLanguageTag();
			break;
		case "zh":
			fileName = Locale.CHINA.toLanguageTag();
			break;
		default:
			fileName = Locale.KOREA.toLanguageTag();
			break;
		}
		instance.GetResouce();
		return instance;
	}

	public static void Initialize() throws IOException {
		getInstance();
	}
	// endregion

	// regoin (사용안함) ClassLoader을 이용하여 Resource에서 파일 가져오는 예제
	private JSONArray readFile(String fileName) throws IOException {
		FileInputStream inputStream = null;
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();

			File readFile = new File(classLoader.getResource(fileName).getFile());
			inputStream = new FileInputStream(readFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;

			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return null;
	}
	// endregion

	// Bean 설정을 이용해 Resource 파일 가져오는 함수
	static void GetResouce() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		CustomResourceLoader customResourceLoader = (CustomResourceLoader) context.getBean("customResourceLoader");
		Resource[] resource = customResourceLoader.getResource();

		File languageFile = null;
		try {
			for (Resource r : resource) {
				languageFile = r.getFile();
				// System.out.println("languageFile.getName : " +
				// languageFile.getName());
				// System.out.println("fileName : " + fileName);
				if (languageFile.getName().contains(fileName)) {
					System.out.println("languageFile Name : " + languageFile.getName());
					// 위에서 파일 이름을 languageFile 변수에 넣어 줬기 때문에 바로 break 시킨다.
					break;
				}
			}

			if (languageFile != null) {
				BufferedReader br = new BufferedReader(new FileReader(languageFile));

				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line.replace(UTF8_BOM, ""));
					System.out.println("line : " + line);
				}
				br.close();

				String tmp = sb.toString();
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(tmp);

				languageMap = JSONHelper.getInstance().JSONObjecttoMap(obj);

				// System.out.println(toMap.size());
				// System.out.println(toMap.get("Title"));

			} else {
				System.out.println("File null");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 다국어 변환 메서드
	public static String GetLanguage(String Key) {
		String ret = Null_Value;
		try {
			if (languageMap == null) {
				GetResouce();
			}

			if (languageMap != null) {
				ret = languageMap.get(Key).toString();
			}
		} catch (Exception e) {
			System.out.println("Request Language Key : " + Key);
			System.out.println(e.toString());
		}
		return ret;
	}

}