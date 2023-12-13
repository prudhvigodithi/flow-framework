/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.flowframework.model;

import org.opensearch.test.OpenSearchTestCase;

import java.io.IOException;

public class ResourceCreatedTests extends OpenSearchTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testParseFeature() throws IOException {
        ResourceCreated resourceCreated = new ResourceCreated("A", "B");
        assertEquals(resourceCreated.workflowStepName(), "A");
        assertEquals(resourceCreated.resourceId(), "B");

        String expectedJson = "{\"workflow_step_name\":\"A\",\"resource_id\":\"B\"}";
        String json = TemplateTestJsonUtil.parseToJson(resourceCreated);
        assertEquals(expectedJson, json);

        ResourceCreated resourceCreatedTwo = ResourceCreated.parse(TemplateTestJsonUtil.jsonToParser(json));
        assertEquals("A", resourceCreatedTwo.workflowStepName());
        assertEquals("B", resourceCreatedTwo.resourceId());
    }

    public void testExceptions() throws IOException {
        String badJson = "{\"wrong\":\"A\",\"resource_id\":\"B\"}";
        IOException e = assertThrows(IOException.class, () -> ResourceCreated.parse(TemplateTestJsonUtil.jsonToParser(badJson)));
        assertEquals("Unable to parse field [wrong] in a resources_created object.", e.getMessage());

        String missingJson = "{\"resource_id\":\"B\"}";
        e = assertThrows(IOException.class, () -> ResourceCreated.parse(TemplateTestJsonUtil.jsonToParser(missingJson)));
        assertEquals("A ResourceCreated object requires both a workflowStepName and resourceId.", e.getMessage());
    }

}
