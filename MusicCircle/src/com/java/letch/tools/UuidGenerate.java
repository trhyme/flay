package com.java.letch.tools;

import java.util.UUID;

/**主键生成策略**/
public class UuidGenerate {
	//生成字符主键UUID
	public static String getUUID(){
		String uuid=UUID.randomUUID().toString();
		uuid=uuid.replace("-", "");
		return uuid;
	}
}
