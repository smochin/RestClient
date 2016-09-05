/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smochin.common.rest.client.callable.handler;

@FunctionalInterface
public interface ChunkHandler {
    void handle(String chunk);
}
