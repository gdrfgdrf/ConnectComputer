// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Action/Account/Enum/account_enum.proto

package cn.gdrfgdrf.Protobuf.Action.Account.Enum;

public final class AccountEnumProto {
  private AccountEnumProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * Protobuf enum {@code cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum}
   */
  public enum LoginModeEnum
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>CONTROLLER = 0;</code>
     */
    CONTROLLER(0),
    /**
     * <code>COMPUTER = 1;</code>
     */
    COMPUTER(1),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>CONTROLLER = 0;</code>
     */
    public static final int CONTROLLER_VALUE = 0;
    /**
     * <code>COMPUTER = 1;</code>
     */
    public static final int COMPUTER_VALUE = 1;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static LoginModeEnum valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static LoginModeEnum forNumber(int value) {
      switch (value) {
        case 0: return CONTROLLER;
        case 1: return COMPUTER;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<LoginModeEnum>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        LoginModeEnum> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<LoginModeEnum>() {
            public LoginModeEnum findValueByNumber(int number) {
              return LoginModeEnum.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.getDescriptor().getEnumTypes().get(0);
    }

    private static final LoginModeEnum[] VALUES = values();

    public static LoginModeEnum valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private LoginModeEnum(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&Action/Account/Enum/account_enum.proto" +
      "\0228cn.gdrfgdrf.connectComputer.Protobuf.A" +
      "ction.Account.Enum*-\n\rLoginModeEnum\022\016\n\nC" +
      "ONTROLLER\020\000\022\014\n\010COMPUTER\020\001B<\n(cn.gdrfgdrf" +
      ".Protobuf.Action.Account.EnumB\020AccountEn" +
      "umProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
