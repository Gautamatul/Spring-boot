package com.example.learn.metaData;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.json.JsonSanitizer;

public class Response<T> {

	private T entity;

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	@JsonIgnore
	public ResponseBody<T> toSafe(TypeReference<?> reference) {
		try {
			final ObjectMapper mapper = new ObjectMapper();

			return mapper.readValue(JsonSanitizer.sanitize(mapper.writeValueAsString(this)), reference);
		} catch (final IOException e) {
			return null;
		}
	}
}
