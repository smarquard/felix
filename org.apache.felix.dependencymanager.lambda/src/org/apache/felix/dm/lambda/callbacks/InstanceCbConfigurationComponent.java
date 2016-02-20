/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
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
package org.apache.felix.dm.lambda.callbacks;

import java.util.Dictionary;
import java.util.Objects;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.lambda.ConfigurationDependencyBuilder;

/**
 * Represents a callback(Configuration, Component) invoked on an Object instance.
 * 
 * <p> The T generic parameter represents the type of the configuration class passed to the callback argument. 
 * 
 * <p> Using such callback provides a way for creating type-safe configurations from the actual {@link Dictionary} that is
 * normally injected by Dependency Manager.
 * For more information about configuration types, please refer to {@link ConfigurationDependencyBuilder}.
 * 
 * @author <a href="mailto:dev@felix.apache.org">Felix Project Team</a>
 */
@FunctionalInterface
public interface InstanceCbConfigurationComponent<T> extends SerializableLambda {
    /**
     * Handles the given arguments
     * @param instance the Component implementation instance on which the callback is invoked on. 
     * @param component the callback Component 
     */
    void accept(T configType, Component component);

    default InstanceCbConfigurationComponent<T> andThen(InstanceCbConfigurationComponent<T> after) {
        Objects.requireNonNull(after);
        return (T instance, Component component) -> {
            accept(instance, component);
            after.accept(instance, component);
        };
    }
}
