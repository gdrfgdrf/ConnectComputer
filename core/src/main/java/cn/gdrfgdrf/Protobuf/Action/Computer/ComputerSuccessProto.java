// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Action/Computer/success.proto
// Protobuf Java Version: 4.26.0

package cn.gdrfgdrf.Protobuf.Action.Computer;

public final class ComputerSuccessProto {
  private ComputerSuccessProto() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 0,
      /* suffix= */ "",
      ComputerSuccessProto.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface RegisterComputerSuccessPacketOrBuilder extends
      // @@protoc_insertion_point(interface_extends:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.RegisterComputerSuccessPacket)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 id = 1;</code>
     * @return The id.
     */
    int getId();
  }
  /**
   * Protobuf type {@code cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.RegisterComputerSuccessPacket}
   */
  public static final class RegisterComputerSuccessPacket extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.RegisterComputerSuccessPacket)
      RegisterComputerSuccessPacketOrBuilder {
  private static final long serialVersionUID = 0L;
    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 26,
        /* patch= */ 0,
        /* suffix= */ "",
        RegisterComputerSuccessPacket.class.getName());
    }
    // Use RegisterComputerSuccessPacket.newBuilder() to construct.
    private RegisterComputerSuccessPacket(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private RegisterComputerSuccessPacket() {
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket.class, cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private int id_ = 0;
    /**
     * <code>int32 id = 1;</code>
     * @return The id.
     */
    @java.lang.Override
    public int getId() {
      return id_;
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
      if (id_ != 0) {
        output.writeInt32(1, id_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, id_);
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
      if (!(obj instanceof cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket)) {
        return super.equals(obj);
      }
      cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket other = (cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket) obj;

      if (getId()
          != other.getId()) return false;
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
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + getId();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.RegisterComputerSuccessPacket}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.RegisterComputerSuccessPacket)
        cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacketOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket.class, cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket.Builder.class);
      }

      // Construct using cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        id_ = 0;
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_descriptor;
      }

      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket getDefaultInstanceForType() {
        return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket.getDefaultInstance();
      }

      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket build() {
        cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket buildPartial() {
        cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket result = new cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.id_ = id_;
        }
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket) {
          return mergeFrom((cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket other) {
        if (other == cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket.getDefaultInstance()) return this;
        if (other.getId() != 0) {
          setId(other.getId());
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
              case 8: {
                id_ = input.readInt32();
                bitField0_ |= 0x00000001;
                break;
              } // case 8
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

      private int id_ ;
      /**
       * <code>int32 id = 1;</code>
       * @return The id.
       */
      @java.lang.Override
      public int getId() {
        return id_;
      }
      /**
       * <code>int32 id = 1;</code>
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(int value) {

        id_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>int32 id = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearId() {
        bitField0_ = (bitField0_ & ~0x00000001);
        id_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.RegisterComputerSuccessPacket)
    }

    // @@protoc_insertion_point(class_scope:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.RegisterComputerSuccessPacket)
    private static final cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket();
    }

    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<RegisterComputerSuccessPacket>
        PARSER = new com.google.protobuf.AbstractParser<RegisterComputerSuccessPacket>() {
      @java.lang.Override
      public RegisterComputerSuccessPacket parsePartialFrom(
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

    public static com.google.protobuf.Parser<RegisterComputerSuccessPacket> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<RegisterComputerSuccessPacket> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.RegisterComputerSuccessPacket getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface ComputerIsControlledPacketOrBuilder extends
      // @@protoc_insertion_point(interface_extends:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.ComputerIsControlledPacket)
      com.google.protobuf.MessageOrBuilder {
  }
  /**
   * Protobuf type {@code cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.ComputerIsControlledPacket}
   */
  public static final class ComputerIsControlledPacket extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.ComputerIsControlledPacket)
      ComputerIsControlledPacketOrBuilder {
  private static final long serialVersionUID = 0L;
    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 26,
        /* patch= */ 0,
        /* suffix= */ "",
        ComputerIsControlledPacket.class.getName());
    }
    // Use ComputerIsControlledPacket.newBuilder() to construct.
    private ComputerIsControlledPacket(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private ComputerIsControlledPacket() {
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket.class, cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket.Builder.class);
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
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket)) {
        return super.equals(obj);
      }
      cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket other = (cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket) obj;

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
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.ComputerIsControlledPacket}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.ComputerIsControlledPacket)
        cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacketOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket.class, cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket.Builder.class);
      }

      // Construct using cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_descriptor;
      }

      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket getDefaultInstanceForType() {
        return cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket.getDefaultInstance();
      }

      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket build() {
        cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket buildPartial() {
        cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket result = new cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket(this);
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket) {
          return mergeFrom((cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket other) {
        if (other == cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket.getDefaultInstance()) return this;
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

      // @@protoc_insertion_point(builder_scope:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.ComputerIsControlledPacket)
    }

    // @@protoc_insertion_point(class_scope:cn.gdrfgdrf.connectComputer.Protobuf.Action.Computer.ComputerIsControlledPacket)
    private static final cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket();
    }

    public static cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ComputerIsControlledPacket>
        PARSER = new com.google.protobuf.AbstractParser<ComputerIsControlledPacket>() {
      @java.lang.Override
      public ComputerIsControlledPacket parsePartialFrom(
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

    public static com.google.protobuf.Parser<ComputerIsControlledPacket> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ComputerIsControlledPacket> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto.ComputerIsControlledPacket getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\035Action/Computer/success.proto\0224cn.gdrf" +
      "gdrf.connectComputer.Protobuf.Action.Com" +
      "puter\"+\n\035RegisterComputerSuccessPacket\022\n" +
      "\n\002id\030\001 \001(\005\"\034\n\032ComputerIsControlledPacket" +
      "B<\n$cn.gdrfgdrf.Protobuf.Action.Computer" +
      "B\024ComputerSuccessProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_RegisterComputerSuccessPacket_descriptor,
        new java.lang.String[] { "Id", });
    internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_cn_gdrfgdrf_connectComputer_Protobuf_Action_Computer_ComputerIsControlledPacket_descriptor,
        new java.lang.String[] { });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
