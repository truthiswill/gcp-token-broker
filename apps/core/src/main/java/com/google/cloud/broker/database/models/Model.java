// Copyright 2019 Google LLC
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.cloud.broker.database.models;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

import com.google.cloud.broker.database.DatabaseObjectNotFound;
import com.google.cloud.broker.database.backends.AbstractDatabaseBackend;


public abstract class Model {

    protected HashMap<String, Object> values;

    public Model(HashMap<String, Object> values) {
        this.values = new HashMap<>();
        for (String key : values.keySet()) {
            Object value = values.get(key);
            this.values.put(key, value);
        }
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public static Model newModelInstance(Class modelClass, HashMap<String, Object> values) {
        Constructor constructor = null;
        try {
            constructor = modelClass.getConstructor(HashMap.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            return (Model) constructor.newInstance(values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public boolean hasValue(String key) {
        return values.containsKey(key);
    }

    public HashMap<String, Object> getValues() {
        return values;
    }

    public static Model get(Class modelClass, String id) throws DatabaseObjectNotFound {
        return AbstractDatabaseBackend.getInstance().get(modelClass, id);
    }

    public static void save(Model model) {
        AbstractDatabaseBackend.getInstance().save(model);
    }

    public static void delete(Model model) {
        AbstractDatabaseBackend.getInstance().delete(model);
    }

}
