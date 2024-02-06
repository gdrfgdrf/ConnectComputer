// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Action/Account/account.proto

package cn.gdrfgdrf.Protobuf.Action.Account;

public final class AccountProto {
  private AccountProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface UserLoginPacketOrBuilder extends
      // @@protoc_insertion_point(interface_extends:cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.UserLoginPacket)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string token = 1;</code>
     * @return The token.
     */
    java.lang.String getToken();
    /**
     * <code>string token = 1;</code>
     * @return The bytes for token.
     */
    com.google.protobuf.ByteString
        getTokenBytes();

    /**
     * <code>.cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum login_mode = 2;</code>
     * @return The enum numeric value on the wire for loginMode.
     */
    int getLoginModeValue();
    /**
     * <code>.cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum login_mode = 2;</code>
     * @return The loginMode.
     */
    cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum getLoginMode();
  }
  /**
   * Protobuf type {@code cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.UserLoginPacket}
   */
  public static final class UserLoginPacket extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.UserLoginPacket)
      UserLoginPacketOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use UserLoginPacket.newBuilder() to construct.
    private UserLoginPacket(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private UserLoginPacket() {
      token_ = "";
      loginMode_ = 0;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new UserLoginPacket();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket.class, cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket.Builder.class);
    }

    public static final int TOKEN_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private volatile java.lang.Object token_ = "";
    /**
     * <code>string token = 1;</code>
     * @return The token.
     */
    @java.lang.Override
    public java.lang.String getToken() {
      java.lang.Object ref = token_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        token_ = s;
        return s;
      }
    }
    /**
     * <code>string token = 1;</code>
     * @return The bytes for token.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getTokenBytes() {
      java.lang.Object ref = token_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        token_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOGIN_MODE_FIELD_NUMBER = 2;
    private int loginMode_ = 0;
    /**
     * <code>.cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum login_mode = 2;</code>
     * @return The enum numeric value on the wire for loginMode.
     */
    @java.lang.Override public int getLoginModeValue() {
      return loginMode_;
    }
    /**
     * <code>.cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum login_mode = 2;</code>
     * @return The loginMode.
     */
    @java.lang.Override public cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum getLoginMode() {
      cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum result = cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum.forNumber(loginMode_);
      return result == null ? cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum.UNRECOGNIZED : result;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(token_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, token_);
      }
      if (loginMode_ != cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum.CONTROLLER.getNumber()) {
        output.writeEnum(2, loginMode_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(token_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, token_);
      }
      if (loginMode_ != cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum.CONTROLLER.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(2, loginMode_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket)) {
        return super.equals(obj);
      }
      cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket other = (cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket) obj;

      if (!getToken()
          .equals(other.getToken())) return false;
      if (loginMode_ != other.loginMode_) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + TOKEN_FIELD_NUMBER;
      hash = (53 * hash) + getToken().hashCode();
      hash = (37 * hash) + LOGIN_MODE_FIELD_NUMBER;
      hash = (53 * hash) + loginMode_;
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.UserLoginPacket}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.UserLoginPacket)
        cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacketOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket.class, cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket.Builder.class);
      }

      // Construct using cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        token_ = "";
        loginMode_ = 0;
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_descriptor;
      }

      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket getDefaultInstanceForType() {
        return cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket.getDefaultInstance();
      }

      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket build() {
        cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket buildPartial() {
        cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket result = new cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.token_ = token_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.loginMode_ = loginMode_;
        }
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket) {
          return mergeFrom((cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket other) {
        if (other == cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket.getDefaultInstance()) return this;
        if (!other.getToken().isEmpty()) {
          token_ = other.token_;
          bitField0_ |= 0x00000001;
          onChanged();
        }
        if (other.loginMode_ != 0) {
          setLoginModeValue(other.getLoginModeValue());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 10: {
                token_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              case 16: {
                loginMode_ = input.readEnum();
                bitField0_ |= 0x00000002;
                break;
              } // case 16
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private java.lang.Object token_ = "";
      /**
       * <code>string token = 1;</code>
       * @return The token.
       */
      public java.lang.String getToken() {
        java.lang.Object ref = token_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          token_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string token = 1;</code>
       * @return The bytes for token.
       */
      public com.google.protobuf.ByteString
          getTokenBytes() {
        java.lang.Object ref = token_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          token_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string token = 1;</code>
       * @param value The token to set.
       * @return This builder for chaining.
       */
      public Builder setToken(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        token_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>string token = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearToken() {
        token_ = getDefaultInstance().getToken();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>string token = 1;</code>
       * @param value The bytes for token to set.
       * @return This builder for chaining.
       */
      public Builder setTokenBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        token_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      private int loginMode_ = 0;
      /**
       * <code>.cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum login_mode = 2;</code>
       * @return The enum numeric value on the wire for loginMode.
       */
      @java.lang.Override public int getLoginModeValue() {
        return loginMode_;
      }
      /**
       * <code>.cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum login_mode = 2;</code>
       * @param value The enum numeric value on the wire for loginMode to set.
       * @return This builder for chaining.
       */
      public Builder setLoginModeValue(int value) {
        loginMode_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>.cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum login_mode = 2;</code>
       * @return The loginMode.
       */
      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum getLoginMode() {
        cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum result = cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum.forNumber(loginMode_);
        return result == null ? cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum.UNRECOGNIZED : result;
      }
      /**
       * <code>.cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum login_mode = 2;</code>
       * @param value The loginMode to set.
       * @return This builder for chaining.
       */
      public Builder setLoginMode(cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.LoginModeEnum value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000002;
        loginMode_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.Enum.LoginModeEnum login_mode = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearLoginMode() {
        bitField0_ = (bitField0_ & ~0x00000002);
        loginMode_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.UserLoginPacket)
    }

    // @@protoc_insertion_point(class_scope:cn.gdrfgdrf.connectComputer.Protobuf.Action.Account.UserLoginPacket)
    private static final cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket();
    }

    public static cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<UserLoginPacket>
        PARSER = new com.google.protobuf.AbstractParser<UserLoginPacket>() {
      @java.lang.Override
      public UserLoginPacket parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<UserLoginPacket> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<UserLoginPacket> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public cn.gdrfgdrf.Protobuf.Action.Account.AccountProto.UserLoginPacket getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\034Action/Account/account.proto\0223cn.gdrfg" +
      "drf.connectComputer.Protobuf.Action.Acco" +
      "unt\032&Action/Account/Enum/account_enum.pr" +
      "oto\"}\n\017UserLoginPacket\022\r\n\005token\030\001 \001(\t\022[\n" +
      "\nlogin_mode\030\002 \001(\0162G.cn.gdrfgdrf.connectC" +
      "omputer.Protobuf.Action.Account.Enum.Log" +
      "inModeEnumB3\n#cn.gdrfgdrf.Protobuf.Actio" +
      "n.AccountB\014AccountProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.getDescriptor(),
        });
    internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Account_UserLoginPacket_descriptor,
        new java.lang.String[] { "Token", "LoginMode", });
    cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}