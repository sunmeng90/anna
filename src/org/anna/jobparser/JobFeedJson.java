package org.anna.jobparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JobFeedJson {

	private static final String STARTSTR = "#description:";

	public static String readFile(String fileURL) {
		BufferedReader br = null;
		String jasonStr = null;
		try {
			br = new BufferedReader(new FileReader(fileURL));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.indexOf(STARTSTR) == 0) {
					jasonStr = line.substring(13);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return jasonStr;
	}

	public static Map<String, String> parse(String columnJson) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = mapper.readValue(columnJson, new TypeReference<Map<String, String>>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;

	}

	public static void main(String[] args) {

		String jasonStr = readFile("C:\\Users\\meng_\\Desktop\\jason_entry_test.txt");
		Map<String,String> resMap = parse(jasonStr);
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		JobFeedDao jobFeedDao = (JobFeedDao) context.getBean("jobFeedDao");
		jobFeedDao.insertJason(resMap.get("boxName"), resMap.get("jobName"), resMap.get("jobOwner"));

	}

}
