package com.vftlite.parse.wrapper;

import java.nio.charset.StandardCharsets;

import com.coremedia.iso.boxes.UserBox;

public class UserBoxWrapper {

	private UserBox uuid;
	
	public UserBoxWrapper(UserBox box) {
		this.uuid = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("type=").append(this.uuid.getType());
		result.append(";");
		result.append("userType=").append(new String(this.uuid.getUserType(), StandardCharsets.UTF_8));
		result.append(";");
		result.append("contentLenght=").append(this.uuid.getData().length);
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();
	}
	
}
