/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.streampipes.connect.container.master.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.streampipes.connect.adapter.exception.AdapterException;
import org.apache.streampipes.connect.container.master.management.UnitMasterManagement;
import org.apache.streampipes.connect.rest.AbstractContainerResource;
import org.apache.streampipes.model.connect.unit.UnitDescription;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v2/connect/{username}/master/unit")
public class UnitResource extends AbstractContainerResource {

    private static final Logger logger = LoggerFactory.getLogger(UnitResource.class);

    private UnitMasterManagement unitManagement;

    public UnitResource() {
        unitManagement = new UnitMasterManagement();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFittingUnits(UnitDescription unitDescription) {
        try {
            String resultingJson = this.unitManagement.getFittingUnits(unitDescription);
            return ok(resultingJson);
        } catch (AdapterException e) {
            logger.error("Error while getting all adapter descriptions", e);
            return fail();
        }
    }

    public void setUnitMasterManagement(UnitMasterManagement unitMasterManagement) {
        this.unitManagement = unitMasterManagement;
    }

}
