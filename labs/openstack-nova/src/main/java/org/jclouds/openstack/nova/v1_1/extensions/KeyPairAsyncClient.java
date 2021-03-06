/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.openstack.nova.v1_1.extensions;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jclouds.openstack.filters.AuthenticateRequest;
import org.jclouds.openstack.nova.v1_1.domain.KeyPair;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.Payload;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.SkipEncoding;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnFalseOnNotFoundOr404;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Key Pairs via the REST API.
 * <p/>
 * 
 * @see KeyPairClient
 * @author Jeremy Daggett
 * @see <a href="http://nova.openstack.org/api_ext/ext_keypairs.html" />
 */
@SkipEncoding({ '/', '=' })
@RequestFilters(AuthenticateRequest.class)
public interface KeyPairAsyncClient {

   @GET
   @Path("/os-keypairs")
   @SelectJson("keypairs")
   @Consumes(MediaType.APPLICATION_JSON)
   @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
   ListenableFuture<Set<Map<String, KeyPair>>> listKeyPairs();
   

   @POST
   @Path("/os-keypairs")
   @SelectJson("keypair")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   @Payload("%7B\"keypair\":%7B\"name\":\"{name}\"%7D%7D")
   ListenableFuture<KeyPair> createKeyPair(@PayloadParam("name") String name);

   @POST
   @Path("/os-keypairs")
   @SelectJson("keypair")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   @Payload("%7B\"keypair\":%7B\"name\":\"{name}\",\"public_key\":\"{publicKey}\"%7D%7D")
   ListenableFuture<KeyPair> createKeyPairWithPublicKey(@PayloadParam("name") String name,
                                           @PayloadParam("publicKey") String publicKey);

   @DELETE
   @Path("/os-keypairs/{name}")
   @ExceptionParser(ReturnFalseOnNotFoundOr404.class)
   @Consumes
   ListenableFuture<Boolean> deleteKeyPair(@PathParam("name") String name);
   
}
