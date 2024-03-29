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

package cn.gdrfgdrf.ConnectComputerComputer.Data;

import cn.gdrfgdrf.ConnectComputerComputer.Annotation.Keep;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Annotation.CreateOnGetIfNotExist;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.*;
import cn.gdrfgdrf.ConnectComputerComputer.Interceptor.DataStoreCreateOnGetIfNotExistMethodInterceptor;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Annotation.BeanUseMethodInterceptor;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.FileUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

/**
 * @author gdrfgdrf
 */
@Slf4j
@BeanUseMethodInterceptor(
        interceptorClass = DataStoreCreateOnGetIfNotExistMethodInterceptor.class
)
public class DataStore implements Bean {
    @Keep
    private RSA rsa = new RSA();
    @Keep
    private ServerInfo serverInfo = new ServerInfo();
    @Keep
    @CreateOnGetIfNotExist
    private ComputerData computerData = new ComputerData();
    @Keep
    private Account account = new Account();

    public void saveDataBean(DataBean dataBean) throws IOException {
        String clazzName = dataBean.getClass().getSimpleName();
        File clazzNameFile = new File(clazzName + ".json");

        if (!clazzNameFile.exists()) {
            clazzNameFile.createNewFile();
        }

        Writer writer = FileUtils.getWriter(clazzNameFile);
        String json = JacksonUtils.writeJsonString(dataBean);

        writer.write(json);
        writer.close();
    }

    public void createDataBean(Field field) throws Exception {
        String fieldClassName = field.getType().getSimpleName();
        log.info("Initializing {}", fieldClassName);

        File fieldClassNameFile = new File(fieldClassName + ".json");
        if (!fieldClassNameFile.exists()) {
            fieldClassNameFile.createNewFile();

            Writer writer = FileUtils.getWriter(fieldClassNameFile);
            String json = JacksonUtils.writeJsonString(field.get(this));

            writer.write(json);
            writer.close();
        } else {
            Object obj = JacksonUtils.readFile(fieldClassNameFile, field.getType());

            field.set(this, obj);
        }

        DataBean dataBean = (DataBean) field.get(this);
        dataBean.init(this);

        log.info("{} Initialized", fieldClassName);
    }

    @Override
    public void init() throws Exception {
        Class<?> clazz = DataStore.class;
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                if (field.getType().asSubclass(DataBean.class) != null) {
                    if (checkCreateOnGetIfNotExist(field)) {
                        continue;
                    }
                    //Class and field both haven't CreateOnGetIfNotExist
                    //Class has CreateOnGetIfNotExist but field was exclude
                    //Class has CreateOnGetIfNotExist but field doesn't have it
                    createDataBean(field);
                }
            } catch (ClassCastException ignored) {

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean checkCreateOnGetIfNotExist(Field field) {
        Class<?> clazz = DataStore.class;
        boolean result = false;

        //Class and field both have CreateOnGetIfNotExist continue
        if (clazz.isAnnotationPresent(CreateOnGetIfNotExist.class) &&
                field.isAnnotationPresent(CreateOnGetIfNotExist.class)) {
            result = true;
        }
        //Class has CreateOnGetIfNotExist and field wasn't excluded continue
        if (clazz.isAnnotationPresent(CreateOnGetIfNotExist.class) &&
                !field.isAnnotationPresent(CreateOnGetIfNotExist.Exclude.class)) {
            result = true;
        }
        //Class doesn't have CreateOnGetIfNotExist but field has it continue
        if (!clazz.isAnnotationPresent(CreateOnGetIfNotExist.class) &&
                field.isAnnotationPresent(CreateOnGetIfNotExist.class)) {
            result = true;
        }

        return result;
    }

    public RSA getRsa() {
        return rsa;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public ComputerData getComputerData() {
        return computerData;
    }

    public Account getAccount() {
        return account;
    }
}
