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

package cn.gdrfgdrf.ConnectComputerComputer.Language.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Language.Base.Language;

/**
 * @author gdrfgdrf
 */
@SuppressWarnings("unused")
public class ChineseSimplified extends Language {
    public static final String INPUT_SERVER_INFO_ARGUMENT_PASS_THE_DATASTORE_SERVER_INFO_ARGUMENT = "您在启动参数中输入了服务器信息，程序将不会使用本地存储的服务器信息";
    public static final String INPUT_ACCOUNT_ARGUMENT_PASS_THE_DATASTORE_ACCOUNT_ARGUMENT = "您在启动参数中输入了账号，程序将不会使用本地存储的账号";
    public static final String SILENT_MODE_ENABLED = "静默模式已开启";

    public static final String ARGUMENT_TYPE_ERROR = "参数类型错误";

    public static final String ARGUMENT_PASS_THE_ASSIGNOR = "成功赋值 {}";
    public static final String ARGUMENT_ASSIGNOR_THROW_EXCEPTION = "{} 发生错误";
    public static final String ARGUMENT_CANNOT_SKIP_THE_ASSIGNOR = "无法通过 {}";
    public static final String ARGUMENT_STRING_ASSIGNOR = "字符串（String） 赋值器";
    public static final String ARGUMENT_INTEGER_ASSIGNOR = "整数（Integer） 赋值器";
    public static final String ARGUMENT_BOOLEAN_ASSIGNOR = "布尔值（Boolean） 赋值器";

    public static final String ARGUMENT_VALIDATOR_THROW_EXCEPTION = "{} 发生错误";
    public static final String ARGUMENT_SKIP_THE_VALIDATOR = "跳过 {} 的检查，因为参数为空";
    public static final String ARGUMENT_PASS_THE_VALIDATOR = "成功通过 {} 的检查";
    public static final String ARGUMENT_CANNOT_PASS_THE_VALIDATOR = "无法通过 {} 的检查";
    public static final String ARGUMENT_ERROR_MESSAGE = "来自 {} 的错误信息：{}";
    public static final String ARGUMENT_PORT_VALIDATOR = "端口验证器";
    public static final String ARGUMENT_PORT_VALIDATOR_ERROR = "端口必须在 0 – 65535 之间";
    public static final String ARGUMENT_USERNAME_VALIDATOR = "用户名验证器";
    public static final String ARGUMENT_USERNAME_VALIDATOR_ERROR = "用户名只能由字母、数字组成, 至少包含一种， 6 – 18 位";

    public static final String ENTER_PROTOCOL = "请指定服务器协议：http 为 1， https 为 2";
    public static final String ENTER_SERVER_IP = "请输入服务器地址";
    public static final String ENTER_SERVER_PORT = "请输入服务器端口";

    public static final String CHECK_SERVER_CONNECTABLE = "正在校验服务器可用性";
    public static final String SERVER_CONNECTABLE = "服务器可用";
    public static final String NETWORK_ERROR_SILENCE = "静默模式：网络错误，倒计时 {} 秒后将再次尝试";

    public static final String ENTER_USERNAME = "请输入用户名";
    public static final String ENTER_PASSWORD = "请输入密码";
    public static final String USERNAME_ILLEGAL = "用户名只能由字母、数字组成, 至少包含一种， 6 – 18 位";

    public static final String TRY_GETTING_PUBLIC_KEY = "尝试获取服务器公钥中";
    public static final String GETTING_PUBLIC_KEY_SUCCESS = "获取服务器公钥成功";
    public static final String GETTING_PUBLIC_KEY_ERROR = "获取公钥失败，睡眠 {} 秒后重试";

    public static final String TRY_LOGINING = "尝试登录中";
    public static final String LOGIN_ACCOUNT = "登录账户：{}";

    public static final String WANT_TO_ENABLE_AUTO_LOGIN = "是否开启自动登录：开启 为 1，关闭 为 2";
    public static final String ENABLED_AUTO_LOGIN = "自动登录已开启";
    public static final String DISABLED_AUTO_LOGIN = "自动登录已关闭";

    public static final String CONTROLLER_OR_BE_CONTROLLED = "作为 控制端 还是 被控制端：控制端 为 1，被控制端 为 2";
    public static final String BE_CONTROLLER = "该设备将作为 控制端";
    public static final String BE_CONTROLLED = "该设备将作为 被控制端，并在服务器上注册（如果未注册）";

    public static final String MENU_USER_INPUT_VALIDATOR_ONLY_INTEGER_INVALID = "只能输入数字";

    public static final String COMPUTER_NAME = "名称";
    public static final String COMPUTER_IS_ONLINE = "是否在线";
    public static final String COMPUTER_LIST_EMPTY = "当前没有设备";
    public static final String COMPUTER_LIST_EMPTY_TIP = "当前没有设备，其他设备作为 被控端 登录该账号时该列表会出现对应的设备";
    public static final String COMPUTER_NOT_FOUND = "该设备不存在";

    public static final String MENU_TITLE_PREFIX = "================================ ";
    public static final String MENU_TITLE_SUFFIX = " ================================";
    public static final String MENU_TITLE_COMPUTER_LIST = "设备列表";
    public static final String MENU_TITLE_COMPUTER_DETAIL = "设备详情";
    public static final String MENU_TITLE_COMPUTER_CONTROL = "控制设备";

    public static final String OPERATION_ENTER_INDEX = "请输入需要进行的操作";
    public static final String OPERATION_PREVIOUS_MENU = "返回上级菜单";
    public static final String OPERATION_INDEX_OUT_OF_LIMITED = "请输入正确的操作";

    public static final String TRY_CONNECTING_SERVER = "尝试连接服务器中";
    public static final String CONNECTING_SERVER_SUCCESS = "连接服务器成功";
    public static final String CONNECTING_SERVER_FAILED = "无法连接到服务器";
    public static final String CANNOT_CONNECT_SERVER_WITH_COUNTDOWN = "倒计时 {} 秒后将再次尝试";
    public static final String SOCKET_CHANNEL_ERROR = "Socket 管道错误：{}";
    public static final String PACKET_SENDER_TIMEOUT = "发送数据包超时";

    public static final String NETTY_PACKET_GOODBYE_NOTIFICATION = "服务器主动断开了连接";

    public static final String TRUE = "是";
    public static final String FALSE = "否";
    public static final String INDEX = "索引";

    public static final String INTEGER_POSITIVE_ERROR = "必须是正数";

    public static final String ENTER_ERROR = "输入错误，请重新输入";
    public static final String ENTER_AGAIN = "请重新输入";

    public static final String CONNECT_FAILED_ERROR = "无法连接到服务器";
    public static final String SERVER_SSL_ERROR = "服务器 SSL 错误，请确认并重新输入服务器地址";

    public static final String CAUGHT_ERROR = "发生错误";
    public static final String UNKNOWN_ERROR = "未知错误";

    public static final String PROGRAM_EXIT = "程序退出";

    public static final String CUSTOM_EXCEPTION_EXCEPTION_DISPATCH_ERROR = "全局异常处理器出现异常";
    public static final String CUSTOM_EXCEPTION_NETTY_NOT_FOUND_PACKET_HANDLER_ERROR = "无法为数据类型 {} 找到数据包处理器";

    public static final String CUSTOM_EXCEPTION_ARGUMENT_ASSIGNOR_EXECUTE = "参数赋值器出现异常";
    public static final String CUSTOM_EXCEPTION_ARGUMENT_VALIDATOR_EXECUTE = "参数验证器出现异常";

    public static final String CUSTOM_EXCEPTION_PARSE = "解析失败";

    public static final String NOT_NULL = "参数不能为空";

    public static final String NETTY_AES_KEY_EXCHANGED = "AES 密钥交换成功";
}
