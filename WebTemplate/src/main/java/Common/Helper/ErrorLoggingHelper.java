package Common.Helper;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.*;

public class ErrorLoggingHelper {

	/**
	 * <p>
	 * try Error를 로그로 작성한다.
	 * </p>
	 * @param Logger
	 * @param MethodName
	 * @param Exception
	 **/
	static public void log(Logger logger, String MethodName, Exception err) {
		StringWriter stack = new StringWriter();
		err.printStackTrace(new PrintWriter(stack));
		logger.error("ERROR - [" + MethodName + "] : " + stack.toString());
	}
	
}












