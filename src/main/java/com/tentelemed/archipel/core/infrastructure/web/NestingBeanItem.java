/**
 * Copyright 2000-2013 Vaadin Ltd.
 * Copyright 2013 Tommi S.E. Laukkanen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tentelemed.archipel.core.infrastructure.web;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.NestedMethodProperty2;

import java.util.Collection;

/**
 * Specialized version of BeanItem to allow for automated expansion of nested properties.
 * Partly copied from BeanItem.
 *
 * @param <BT> the bean type
 * @author Vaadin Ltd.
 * @author Tommi S.E. Laukkanen
 */
@SuppressWarnings("serial")
public final class NestingBeanItem<BT> extends BeanItem<BT> {


    public NestingBeanItem(BT bean) {
        super(bean);
    }

    public NestingBeanItem(BT bean, Collection<?> propertyIds) {
        super(bean, propertyIds);
    }

    public NestingBeanItem(BT bean, String... propertyIds) {
        super(bean, propertyIds);
    }

    /**
     * Adds a nested property to the item.
     *
     * @param nestedPropertyId property id to add. This property must not exist in the item
     *                         already and must of of form "field1.field2" where field2 is a
     *                         field in the object referenced to by field1
     */
    public void addNestedProperty(final String nestedPropertyId) {
        addItemProperty(nestedPropertyId, new NestedMethodProperty2<Object>(
                getBean(), nestedPropertyId));
    }

}