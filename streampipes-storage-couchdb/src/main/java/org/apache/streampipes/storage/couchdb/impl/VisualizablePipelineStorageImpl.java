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

package org.apache.streampipes.storage.couchdb.impl;

import org.apache.streampipes.model.dashboard.VisualizablePipeline;
import org.apache.streampipes.storage.api.IVisualizablePipelineStorage;
import org.apache.streampipes.storage.couchdb.dao.AbstractDao;
import org.apache.streampipes.storage.couchdb.utils.Utils;

import java.util.List;

public class VisualizablePipelineStorageImpl extends AbstractDao<VisualizablePipeline> implements IVisualizablePipelineStorage {

  public VisualizablePipelineStorageImpl() {
    super(Utils::getCouchDbVisualizablePipelineClient, VisualizablePipeline.class);
  }

  @Override
  public List<VisualizablePipeline> getAllVisualizablePipelines() {
    return findAll();
  }

  @Override
  public VisualizablePipeline getVisualizablePipeline(String id) {
    return find(id).orElse(null);
  }
}
