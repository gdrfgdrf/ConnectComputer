/*
 * Copyright (C) 2024 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor;

import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Base.BaseTypeConvertor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gdrfgdrf
 */
public enum TypeConvertor {
    INSTANCE;

    private final Map<Class<?>, BaseTypeConvertor<?>> CONVERTOR_MAP = new ConcurrentHashMap<>();

    public void registerTypeConvertor(Class<?> T, BaseTypeConvertor<?> typeConvertor) {
        CONVERTOR_MAP.put(T, typeConvertor);
    }

    public Object convert(Object obj, Class<?> target) {
        BaseTypeConvertor convertor = CONVERTOR_MAP.get(target);
        if (convertor == null) {
            throw new NullPointerException("Not found type convertor by target " + target);
        }

        return convertor.convert(obj);
    }
}
