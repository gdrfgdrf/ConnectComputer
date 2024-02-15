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

package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Exception.ExceptionDispatchException;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Processor.ExceptionPostProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public enum ExceptionDispatcher {
    INSTANCE;

    private final Map<Class<? extends Exception>, List<ExceptionPostProcessor<?>>> EXCEPTION_POST_PROCESSORS = new HashMap<>();
    private final Map<Class<? extends Exception>, List<ExceptionHandler>> HANDLER = new HashMap<>();

    public void registerExceptionPostProcessor(Class<? extends Exception> T, ExceptionPostProcessor<?> processor) {
        EXCEPTION_POST_PROCESSORS.computeIfAbsent(T, k -> new LinkedList<>());
        EXCEPTION_POST_PROCESSORS.get(T).add(processor);
    }

    public void registerExceptionHandler(Class<? extends Exception> type, ExceptionHandler handler) {
        HANDLER.computeIfAbsent(type, k -> new LinkedList<>());
        HANDLER.get(type).add(handler);
    }

    public void dispatch(Throwable throwable, Object args) throws ExceptionDispatchException {
        if (throwable == null) {
            throw new ExceptionDispatchException("The exception is null");
        }
        if (throwable.getClass() == ExceptionDispatchException.class) {
            return;
        }
        if (EXCEPTION_POST_PROCESSORS.containsKey(throwable.getClass())) {
            for (ExceptionPostProcessor processor : EXCEPTION_POST_PROCESSORS.get(throwable.getClass())) {
                throwable = processor.postProcessorBeforeHandler(throwable);
            }
        }

        List<ExceptionHandler> handler = HANDLER.get(throwable.getClass());
        if (handler == null) {
            handler = HANDLER.get(Exception.class);

            if (handler == null) {
                throw new ExceptionDispatchException("Not found the exception handler with " +
                        "the exception type is " + throwable.getClass());
            }
        }

        for (ExceptionHandler exceptionHandler : handler) {
            exceptionHandler.handle(throwable, args);
        }
    }

}
