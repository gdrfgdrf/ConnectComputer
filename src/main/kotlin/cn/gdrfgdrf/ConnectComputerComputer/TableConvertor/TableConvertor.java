package cn.gdrfgdrf.ConnectComputerComputer.TableConvertor;

import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Annotation.TableConvertible;
import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Translator.Impl.DefaultTranslator;
import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Translator.Translator;
import github.clyoudu.consoletable.ConsoleTable;
import github.clyoudu.consoletable.table.Cell;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gdrfgdrf
 */
public class TableConvertor {
    private static final Map<Class<? extends Translator<?>>, Translator<?>> TRANSLATOR_MAP = new ConcurrentHashMap<>();

    public static String convert(Object obj, String... headers) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<Cell> headerList = new LinkedList<>() {{
            for (String header : headers) {
                add(new Cell(header));
            }
        }};
        List<List<Cell>> cellList = new LinkedList<>();
        List<Cell> result = convertStringListToCellList(serializeObject(obj));
        cellList.add(result);

        return new ConsoleTable.ConsoleTableBuilder()
                .addHeaders(headerList)
                .addRows(cellList)
                .build()
                .toString();
    }

    public static String convert(
            List<?> list,
            String listEmptyMessage,
            String... headers
    ) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        return convert(list, listEmptyMessage, true, headers);
    }

    public static String convert(
            List<?> list,
            String listEmptyMessage,
            boolean withIndex,
            String... headers
    ) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        List<Cell> headerList = new LinkedList<>() {{
            if (withIndex) {
                add(new Cell(AppLocale.INDEX));
            }
            for (String header : headers) {
                add(new Cell(header));
            }
        }};
        List<List<Cell>> rowList = new LinkedList<>();

        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);

                List<Cell> result = convertStringListToCellList(serializeObject(obj));
                if (withIndex) {
                    result.add(0, new Cell(String.valueOf(i)));
                }

                rowList.add(result);
            }
        } else {
            List<Cell> rows = new LinkedList<>();
            rows.add(new Cell(listEmptyMessage));

            rowList.add(rows);
        }

        return new ConsoleTable.ConsoleTableBuilder()
                .addHeaders(headerList)
                .addRows(rowList)
                .build()
                .toString();
    }

    private static List<String> serializeObject(Object obj) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<String> strings = new LinkedList<>();

        Field[] fields = obj.getClass().getDeclaredFields();
        List<Field> fieldList = new LinkedList<>();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(TableConvertible.class)) {
                continue;
            }
            fieldList.add(field);
        }

        for (Field field : fieldList) {
            boolean changeAccessible = false;
            if (!field.canAccess(obj)) {
                field.setAccessible(true);
                changeAccessible = true;
            }

            TableConvertible tableConvertible = field.getAnnotation(TableConvertible.class);
            assert tableConvertible != null;

            Class<? extends Translator<?>> translatorClazz = tableConvertible.translator();
            Translator translator = TRANSLATOR_MAP.get(translatorClazz);
            if (translator == null) {
                translator = translatorClazz.getDeclaredConstructor().newInstance();
                TRANSLATOR_MAP.put(translatorClazz, translator);
            }

            String content;
            Object fieldObj = field.get(obj);
            if (translatorClazz != DefaultTranslator.class) {
                content = translator.translate(fieldObj);
            } else {
                content = fieldObj.toString();
            }

            strings.add(content);

            if (changeAccessible) {
                field.setAccessible(false);
            }
        }

        return strings;
    }

    private static List<Cell> convertStringListToCellList(List<String> list) {
        List<Cell> result = new LinkedList<>();

        for (String s : list) {
            result.add(new Cell(s));
        }

        return result;
    }
}
