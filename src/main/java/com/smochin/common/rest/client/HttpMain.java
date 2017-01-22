/*
 * @(#)HttpMain.java	21 de jan de 2017
 *
 * Copyright 2017 USTO.RE
 */
package com.smochin.common.rest.client;

import com.smochin.common.rest.client.response.HttpResponse;

/**
 * @author <a href="mailto:andre@usto.re">André Henriques</a>
 */
public class HttpMain {

	public static void main(String[] args) {
		Rest rest = new Rest("http://localhost:8958");
		HttpResponse response = rest.get("/opa").call();
		System.out.println(response);
	}

}
