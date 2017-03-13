/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.lgmshare.component.widget.wheel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * The simple Array wheel adapter
 *
 * @param <T> the element type
 */
public class ArrayListWheelAdapter<T> extends AbstractWheelTextAdapter {

    private List<T> mList;

    /**
     * Constructor
     *
     * @param list the items
     */
    public ArrayListWheelAdapter(Context context, List<T> list) {
        super(context);
        this.mList = list;
    }

    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
        View view = super.getItem(index, cachedView, parent);
        return view;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < mList.size()) {
            return mList.get(index).toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return mList.size();
    }

}
