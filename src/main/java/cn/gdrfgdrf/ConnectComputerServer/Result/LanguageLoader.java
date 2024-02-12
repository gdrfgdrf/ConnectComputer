package cn.gdrfgdrf.ConnectComputerServer.Result;

import cn.gdrfgdrf.ConnectComputerServer.Utils.Jackson.SuperJsonNode;
import cn.gdrfgdrf.ConnectComputerServer.Utils.JacksonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author gdrfgdrf
 */
public class LanguageLoader {
    public static void main(String[] args) throws Exception {
        Class<? extends MESSAGES> clazz = MESSAGES.class;
        Field[] fields = clazz.getDeclaredFields();
        ObjectNode jsonNode = JacksonUtils.newTree();

        for (Field field : fields) {
            jsonNode.put(field.getName(), field.get(null).toString());
        }

        System.out.println(jsonNode);
    }

    public static void load(File file) throws IOException {
        SuperJsonNode superJsonNode = JacksonUtils.readFileTree(file);
        superJsonNode.keySet().forEachRemaining(key -> {
            try {
                Field field = MESSAGES.class.getField(key);
                boolean access = field.canAccess(null);

                if (!access) {
                    field.setAccessible(true);
                }
                field.set(null, superJsonNode.getString(key));

                if (!access) {
                    field.setAccessible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static class MESSAGES {
        public static String AVAILABLE_SERVER = "测试服务器切换成功";

        public static String SUCCESS_GET = "获取成功";
        public static String SUCCESS_LOGIN = "登录成功";
        public static String SUCCESS_LOGOUT = "登出成功";
        public static String SUCCESS_CHANGE_PASSWORD = "密码修改成功, 请重新登录";
        public static String SUCCESS_CHANGE = "修改成功";
        public static String SUCCESS_SWITCH = "切换成功";

        public static String SUCCESS_UPLOAD_FILE = "上传文件成功";

        public static String ERROR_FILE_TOO_BIG = "文件大小超出限制";
        public static String ERROR_INCORRECT_FILE_TYPE = "错误的文件类型";
        public static String ERROR_UPLOAD_FILE = "上传文件失败";

        public static String ERROR_NOT_FOUND_USER = "没有找到该用户";

        public static String ERROR_CLIENT_VERSION_ILLEGAL = "clientVersion 不符合规定";
        public static String ERROR_NOT_FOUND_CLIENT_VERSION_IN_HEADER = "没有在 header 中找到 clientVersion";
        public static String ERROR_NOT_FOUND_TOKEN_IN_HEADER = "没有在请求头中找到 token";
        public static String ERROR_NOT_FOUND_PUBLIC_KEY_IN_HEADER = "没有在 header 中找到公钥";
        public static String ERROR_TOKEN_INVALID = "令牌错误";
        public static String ERROR_TOKEN_EXPIRED = "登录过期，请重新登录";

        public static String ERROR_PARAMETER = "参数错误";
        public static String ERROR_ID_ILLEGAL = "id 不符合规定";
        public static String ERROR_USERNAME_ILLEGAL = "用户名不符合规定";
        public static String ERROR_PASSWORD_ILLEGAL = "密码不符合规定";
        public static String ERROR_DISPLAY_NAME_ILLEGAL = "显示名不符合规定";

        public static String ERROR_USERNAME_OUT_OF_LIMITED = "用户名超出限制: 255";
        public static String ERROR_PASSWORD_OUT_OF_LIMITED = "密码超出限制: 255";
        public static String ERROR_DISPLAY_NAME_OUT_OF_LIMITED = "显示名超出限制: 18";

        public static String ERROR_PASSWORD_INCORRECT_OR_NOT_FOUND_USER = "密码错误或用户不存在";
        public static String ERROR_ORIGINAL_PASSWORD_INCORRECT = "原密码错误";

        public static String ERROR_ID_CANNOT_BE_NULL = "id 不能为空";
        public static String ERROR_USERNAME_CANNOT_BE_NULL = "用户名不能为空";
        public static String ERROR_PASSWORD_CANNOT_BE_NULL = "密码不能为空";
        public static String ERROR_ORIGINAL_PASSWORD_CANNOT_BE_NULL = "原密码不能为空";

        public static String ERROR_RSA_KEY = "RSA 密钥错误";
        public static String ERROR_DECRYPTED = "解密失败";

        public static String NETTY_LOGIN_SUCCESS = "服务器登录成功";

        public static String NETTY_REGISTER_COMPUTER_SUCCESS = "设备注册成功";
        public static String NETTY_ALREADY_REGISTER_COMPUTER = "该设备已注册";

        public static String NETTY_CONNECT_COMPUTER_SUCCESS = "设备连接成功";
        public static String NETTY_COMPUTER_IS_CONNECTED = "该设备已被连接";
        public static String NETTY_CONTROLLER_DISCONNECTED = "控制端已断开连接";
        public static String NETTY_COMPUTER_DISCONNECTED = "远程设备已断开连接";

        public static String NETTY_COMPUTER_ONLINE = "设备已上线: {}";
        public static String NETTY_COMPUTER_OFFLINE = "设备已下线: {}";

        public static String NETTY_AES_KEY_IS_GENERATED = "AES 密钥已生成";
        public static String NETTY_AES_KEY_HAS_BEEN_GENERATED = "AES 密钥已生成，不能再次申请";
        public static String NETTY_AES_KEY_HAS_NOT_BEEN_GENERATED = "AES 密钥还未生成，无法执行其他操作";

        public static String NETTY_CONTROLLER_EXCHANGE_RSA_KEY = "控制端交换 RSA 密钥";
        public static String NETTY_COMPUTER_EXCHANGE_RSA_KEY = "被控端交换 RSA 密钥";
        public static String NETTY_CONTROLLER_ENCRYPTED_MESSAGE = "控制端发送的加密消息";
        public static String NETTY_COMPUTER_ENCRYPTED_MESSAGE = "被控端发送的加密消息";

        public static String NETTY_CONTROLLER_KEY_PRESSED = "控制端按下了按键 {}";
        public static String NETTY_CONTROLLER_KEY_TYPED = "控制端输入了按键 {}";
        public static String NETTY_CONTROLLER_REQUEST = "控制端请求";
        public static String NETTY_COMPUTER_RESPONSE = "被控端响应";
        public static String NETTY_CONTROLLER_TERMINAL_CLOSED = "控制端的终端已被关闭";
        public static String NETTY_COMPUTER_TERMINAL_CLOSED = "被控端的终端已被关闭";

        public static String NETTY_COMPUTER_NOT_ONLINE = "该设备未上线";

        public static String NETTY_NEED_LOGIN = "需要登录到服务器";
        public static String NETTY_ALREADY_LOGIN = "不能重复登录";

        public static String NETTY_LOGIN_MODE_IS_NOT_COMPUTER = "登录模式不是被控端";
        public static String NETTY_LOGIN_MODE_IS_NOT_CONTROLLER = "登录模式不是控制端";
        public static String NETTY_CONTROLLER_IS_CONTROLLING = "一次只能控制一台设备";
        public static String NETTY_CONTROLLER_IS_NOT_CONTROLLING = "不是控制状态";
        public static String NETTY_COMPUTER_IS_NOT_CONTROLLED = "该设备不处于被控制状态";
        public static String NETTY_ONLY_SINGLE_CONNECTION = "该设备已被其他控制端控制";
        public static String NETTY_CONTROLLER_IS_NOT_EXCHANGE_RSA_KEY = "RSA 密钥还未交换，无法执行其他操作";
        public static String NETTY_COMPUTER_IS_NOT_EXCHANGE_RSA_KEY = "RSA 密钥还未交换，无法执行其他操作";
        public static String NETTY_CONTROLLER_ALREADY_EXCHANGED_RSA_KEY = "RSA 密钥已经交换，不能再次交换";
        public static String NETTY_COMPUTER_ALREADY_EXCHANGED_RSA_KEY = "RSA 密钥已经交换，不能再次交换";

        public static String NETTY_FORCED_OFFLINE = "已被强制下线";

        public static String NETTY_NOT_FOUND_COMPUTER = "没有找到该设备";

        public static String NETTY_TIMEOUT = "长时间没有包回应";
        public static String NETTY_GOODBYE = "Goodbye, See you next time";

        public static String NETTY_CLIENT_VERSION_TOO_LOW = "客户端版本过低，请升级";
        public static String VERSION_TOO_LOW = "客户端版本过低，请升级";

        public static String VALIDATION_NOT_NULL = "%VALUE% 为空";
        public static String VALIDATION_NOT_BLANK = "%VALUE% 为空";
        public static String VALIDATION_MAX = "%VALUE% 必须小于等于 %MAX%";
        public static String VALIDATION_PATTERN = "%VALUE% 需要匹配正则 %PATTERN%";
        public static String VALIDATION_ONLY_INTEGER = "%VALUE% 只能是数字";
        public static String VALIDATION_POSITIVE_OR_NEGATIVE = "%VALUE% 只能是 %POSITIVE_OR_NEGATIVE%";

        public static String POSITIVE = "正数";
        public static String NEGATIVE = "负数";

        public static String ERROR = "错误";
        public static String ERROR_HTTP_MESSAGE_NOT_READABLE = "Http Message 读取错误";
        public static String ERROR_CRYPT = "加解密错误";
        public static String UNKNOWN_LANGUAGE = "未知语言";
    }
}
